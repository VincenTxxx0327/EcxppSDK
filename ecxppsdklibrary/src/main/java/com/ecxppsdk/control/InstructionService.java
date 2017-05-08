package com.ecxppsdk.control;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ecxppsdk.data.Constant;
import com.ecxppsdk.event.NetworkEvent;
import com.ecxppsdk.utils.ConversionUtils;
import com.ecxppsdk.utils.DeviceUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: VincenT
 * Date: 2017/4/24 14:38
 * Contact:qq 328551489
 * Purpose:基本连接服务
 */
public class InstructionService extends Service {

    private String mIP;
    private Socket mSocket;
    private ServiceThread mServiceThread;
    private InputStream mInputStream;
    private static OutputStream mOutputStream;
    private static InstructionService instance;

    private TimerTask mTimerTask;
    private Timer mHeartTimer = new Timer();

    private static boolean isConnecting;
    private static boolean connectServer = true;
    private static long lastControlTime = 0;

    public static InstructionService getInstance() {
        if (instance == null) {
            instance = new InstructionService();
        }
        return instance;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        startServiceThread();
        startHeartBeatThread();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            connectServer = intent.getBooleanExtra(Constant.EXTRA_CONNECT_SERVER, true);
        }
        return START_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("============MSG", "onDestroyService");
        isConnecting = false;
        cancelHeartBeatThread();
        cancelServiceThread();
        closeSocket();
    }

    public static void setConnectServer(boolean connectServer) {
        InstructionService.connectServer = connectServer;
    }

    public static boolean isConnectServer() {
        return connectServer;
    }

    public static boolean isConnecting() {
        return isConnecting;
    }

    /**
     * 重启服务线程
     */
    public void restartService(boolean isConnectServer) {
        connectServer = isConnectServer;
        Log.i("============MSG", "restartService");
        cancelHeartBeatThread();
        cancelServiceThread();
        if (mHeartTimer == null) {
            mHeartTimer = new Timer();
        }
        startServiceThread();
        startHeartBeatThread();
    }

    /**
     * 开启服务线程
     */
    private void startServiceThread() {
        mIP = DeviceUtils.getIP(instance.getApplicationContext());
        Log.d("============MSG", "startServiceThread   IP=" + mIP + "  connectServer:" + connectServer);
        mServiceThread = new ServiceThread();
        mServiceThread.start();
    }

    /**
     * 关闭服务线程
     */
    private void cancelServiceThread() {
        if (mServiceThread != null) {
            Log.d("============MSG", "cancelServiceThread");
            mServiceThread.interrupt();
            mServiceThread = null;
        }
    }

    /**
     * 发送心跳包
     */
    private void startHeartBeatThread() {
        Log.d("============MSG", "startHeartBeatThread");
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (mOutputStream != null && isConnecting) {
                        byte[] heartInstruction = Instruction.heartInstruction();
                        Log.d("============MSG", "mTimerTask run 发送心跳包");
                        sendInstruction(heartInstruction, false);
                        EventBus.getDefault().post(new NetworkEvent(NetworkEvent.HEARTBEET, "heartbeet"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mHeartTimer.schedule(mTimerTask, 5000, 10000);
    }

    /**
     * 关闭心跳包
     */
    private void cancelHeartBeatThread() {
        if (mHeartTimer != null) {
            Log.d("============MSG", "cancelHeartBeatThread");
            mHeartTimer.cancel();
            mHeartTimer.purge();
            mHeartTimer = null;
        }
    }

    /**
     * 关闭套接字
     */
    private void closeSocket() {
        try {
            if (mSocket != null) {
                mSocket.close();
                mSocket = null;
            }
            if (mOutputStream != null) {
                mOutputStream.close();
            }
            if (mInputStream != null) {
                mInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ServiceThread extends Thread {
        private byte[] buff = new byte[1024];
        private int len = -1;

        @Override
        public void run() {
            super.run();
            try {
                mSocket = new Socket();
                InetSocketAddress inetSocketAddress = new InetSocketAddress(mIP, 8002);
                mSocket.connect(inetSocketAddress, 6000);
                mSocket.setKeepAlive(true);
                mSocket.setSoTimeout(Integer.MAX_VALUE);
                mInputStream = mSocket.getInputStream();
                mOutputStream = mSocket.getOutputStream();
                isConnecting = true;
                if (connectServer) {//互联网连接:根据勾选判断ip使用局域网还是互联网,执行登录指令发送;局域网连接:ip默认使用局域网，直接去执行登录指令发送
                    registerInstruction();
                }
                EventBus.getDefault().post(new NetworkEvent(NetworkEvent.LOCAL_NETWORK, "set network"));
                while (isConnecting) {
                    try {
                        if ((len = mInputStream.read(buff)) != -1) {
                            byte[] read = new byte[len];
                            for (int i = 0; i < len; i++) {
                                read[i] = buff[i];
                            }
                            byte[] back = Instruction.encode(read);
                            Log.i("============BACK", ConversionUtils.bytes2HexString01(back) + " isConnecting:" + isConnecting);
                            EventBus.getDefault().post(new NetworkEvent(NetworkEvent.FEEDBACK, "feed back", back));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("============ERROR", "while--IOException e");
                        if (isConnecting) {
                            EventBus.getDefault().post(new NetworkEvent(NetworkEvent.NETWORK_ERROR, "feed back", null));
                            isConnecting = false;
                        } else {
                            Log.d("============MSG", "stopService...");
                            EventBus.getDefault().post(new NetworkEvent(NetworkEvent.NETWORK_DISCONNECT, "disconnect", null));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("============ERROR", "run--Exception e");
                EventBus.getDefault().post(new NetworkEvent(NetworkEvent.NETWORK_ERROR, "socket error"));
                isConnecting = false;
            } finally {
                try {
                    if (mSocket != null) {
                        mSocket.close();
                    }
                    if (mOutputStream != null) {
                        mOutputStream.close();
                    }
                    if (mInputStream != null) {
                        mInputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 执行注册
     */
    public void registerInstruction() {
        try {
            byte[] registerInstruction = Instruction.registerInstruction();
            sendInstruction(registerInstruction, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行登录
     */
    public void loginInstruction() {
        try {
            byte[] loginInstruction = Instruction.loginInstruction();
            sendInstruction(loginInstruction, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注销登录
     */
    public static void logoutInstruction() {
        byte[] data = Instruction.logoutInstruction();
        byte[] logoutInstruction = Instruction.encode(data);
        try {
            Log.d("============MSG", "退出网关指令发送...");
            sendInstruction(logoutInstruction, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接
     */
    public static void setNetInstruction(final String ssidAndPsw) {
        try {
            Thread.sleep(500);
            byte[] setNetInstruction = Instruction.setNetInstruction(ssidAndPsw);
            sendInstruction(setNetInstruction, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送附带指令
     *
     * @param instruction 指令内容
     * @param isLimitTime 是否限制操作时间，当前操作时间与上次操作时间小于300毫秒，提示操作太频繁
     */
    public static void sendInstruction(byte[] instruction, boolean isLimitTime) {
        try {
            if (System.currentTimeMillis() - lastControlTime < Constant.TIME_LIMIT300 && isLimitTime) {//当前操作时间与上次操作时间小于300毫秒，提示操作太频繁
                EventBus.getDefault().post(new NetworkEvent(NetworkEvent.TOOSOON, "too soon"));
                return;
            }
            Log.i("============SEND", ConversionUtils.bytes2HexString01(instruction) + " isConnecting:" + isConnecting);
            if (mOutputStream != null) {
                mOutputStream.write(instruction, 0, instruction.length);
                mOutputStream.flush();
                if (isLimitTime) {//避免发送数据心跳时记录了时间影响正在发送的指令
                    lastControlTime = System.currentTimeMillis();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("============ERROR", "sendInstruction");
            EventBus.getDefault().post(new NetworkEvent(NetworkEvent.NETWORK_ERROR, "socket error"));
        }
    }
}
