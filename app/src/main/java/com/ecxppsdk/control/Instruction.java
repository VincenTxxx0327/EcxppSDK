package com.ecxppsdk.control;

import com.ecxppsdk.R;
import com.ecxppsdk.utils.ConversionUtils;
import com.ecxppsdk.utils.SharedPreUtils;

import org.xutils.x;

import java.util.Arrays;
import java.util.List;

import static com.ecxppsdk.utils.DeviceUtils.getDstMac;
import static com.ecxppsdk.utils.DeviceUtils.getSrcMac;

/**
 * Created by VincenT on 2016/11/15.
 * <p>连接、控制等基础指令</p>
 */
public class Instruction {

    public static byte[] con_id = {0x01, 0x00, 0x00, 0x00};
    public static byte[] data_id = {0x02, 0x00, 0x00, 0x00};
    public static byte[] data_ctrl = {0x02, 0x00, 0x00, 0x00};//控制
    public static byte[] login_cmd = {0x01, 0x00, 0x00, 0x00};
    public static byte[] uart_cmd = {0x02, 0x00, 0x00, 0x00};//发送
    public static byte[] pwd_cmd = {0x03, 0x00, 0x00, 0x00};//修改用户密码
    public static byte[] logout_cmd = {0x04, 0x00, 0x00, 0x00};//注销登陆
    public static byte[] close_all = {0x0C, (byte) 0x00};//全关
    public static byte[] open_all = {0x0C, (byte) 0xFA};//全关
    public static byte[] open_single = {(byte) 0x80};
    public static byte[] close_single = {(byte) 0x00};
    public static byte[] light_id1 = ConversionUtils.hexString2BytesData("3EEE110B1B");//三路灯
    public static byte[] light_id2 = ConversionUtils.hexString2BytesData("6DD7BF081B");//光管
    public static byte[] light_id3 = ConversionUtils.hexString2BytesData("72BFB80A02");//条灯
    public static byte[] heart_data = {0x0f, 0x00, 0x00, 0x00, (byte) 0xEE, (byte) 0xEE};
    public static byte[] key = {0x1b, 0x2a, 0x0d, 0x3b, 0x4a, (byte) 0xae, (byte) 0xb3, 0x2d};//加密密钥
    //新指令
    public static String ip = null;
    public static byte[] src_Mac;
    public static byte[] dst_Mac;
    public static byte[] passwd;
    public static byte[] prea_cmd = {0x0A};//0A前缀，用于用户登录、密码修改、转发串口指令、用户退出、无线上网配置（局域网指令）
    public static byte[] pred_cmd = {0x0D};//0D前缀，用于转发指令补充链接
    public static byte[] preb_cmd = {0x0B};//0D前缀，用于转发指令补充链接
    public static byte[] pref_cmd = {0x0F};//0F前缀，用于注册APP结点、心跳包发送
    public static byte[] ctr01_cmd = {0x01};//用户登录
    public static byte[] ctr02_cmd = {0x02};//注册APP结点，心跳包，转发串口指令
    public static byte[] ctr03_cmd = {0x03};//用于密码修改
    public static byte[] ctr04_cmd = {0x04};//用户退出
    public static byte[] ctr05_cmd = {0x05};//配置上网
    public static byte[] ctr06_cmd = {0x06};//识别码
    public static byte[] ctr07_cmd = {0x07};//识别码
    public static byte[] ctr08_cmd = {0x08};//识别码
    public static byte[] heart_cmd = {0x00};//心跳包

    /**
     * 注册指令
     * @return
     */
    public static byte[] registerInstruction() {
        if (SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)){
            return encode(concatAll(pref_cmd, dst_Mac, src_Mac, ctr02_cmd));//新指令
        }else{
            return encode(concatAll(con_id, getSrcMac()));//旧指令
        }
    }

    /**
     * 登陆指令
     * @return
     */
    public static byte[] loginInstruction() {
        byte[] cig = {0x00};
        String user = "admin";
        String pwd = "123456";
        String userAndPsw = user + "#" + pwd;
//        Log.i("============MSG", "user_pwd = " + user_pwd);
        while (userAndPsw.length() % 16 != 0) {
            userAndPsw = userAndPsw + "0";
        }
        if (SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)){
            userAndPsw = user + "#" + "12345678";
            byte[]  ctr_cmd = userAndPsw.getBytes();
            byte[] len_cmd = ConversionUtils.hexStringLen2Bytes(ConversionUtils.bytes2HexString01(ctr_cmd));
            return encode(concatAll(prea_cmd, dst_Mac, src_Mac, ctr01_cmd, len_cmd, ctr_cmd));//新指令
        }else{
            return encode(concatAll(data_id, getDstMac(), login_cmd, userAndPsw.getBytes(), cig, getSrcMac()));//旧指令
        }
    }

    /**
     * 注销登陆指令
     * @return
     */
    public static byte[] logoutInstruction() {
        if (SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)){
            return encode(concatAll(prea_cmd, dst_Mac, src_Mac, ctr04_cmd));//新指令
        }else{
            return concatAll(data_id, getDstMac(), logout_cmd, getSrcMac());//旧指令
        }
    }

    /**
     * 心跳包指令
     * @return
     */
    public static byte[] heartInstruction() {
        if (SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)){
            return encode(heart_cmd);//新指令
        }else{
            return encode(concatAll(Instruction.heart_data, getSrcMac()));//旧指令
        }
    }

    /**
     * 设置网关WiFi指令
     * @return
     */
    public static byte[] setNetInstruction(String ssidAndPsw) {
        if (SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)){
            //规则：0a dst_mac(6Byte) src_mac(6Byte) 05 len(1Byte) ssid 23 passwd
            byte[]  ctr_cmd = ssidAndPsw.getBytes();
            byte[] len_cmd = ConversionUtils.hexStringLen2Bytes(ConversionUtils.bytes2HexString01(ctr_cmd));
            byte[] data = Instruction.concatAll(Instruction.prea_cmd, Instruction.dst_Mac, Instruction.src_Mac, Instruction.ctr05_cmd, len_cmd, ctr_cmd);
            byte[] buf = Instruction.encode(data);
            return encode(buf);//新指令
        }else{
            return encode(concatAll(Instruction.heart_data));//旧指令，无，以前是UDP设置
        }
    }

    public static byte[] modifyUserPwd(String pwd) {
        String changePwd = SharedPreUtils.getString(x.app(), "changePwd");
        String str_old;
        if (changePwd != null) {
            str_old = changePwd;
        } else {
            str_old = "123456";
        }
        String modify_pwd = str_old + "#" + pwd;
        while (modify_pwd.length() % 16 != 0) {
            modify_pwd = modify_pwd + "0";
        }
        return concatAll(data_id, getDstMac(), pwd_cmd, modify_pwd.getBytes());
    }

    /**
     * 指令拼接
     * @param first
     * @param rest
     * @param <T>
     * @return
     */
    public static <T> byte[] concatAll(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
//            if (array!=null) {
            totalLength += array.length;
//            }
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**指令拼接加三字节
     * @param rest
     * @return
     */
    public static byte[] concatAllCmd(List<byte[]> rest) {
        int totalLength = 0;
        for (byte[] array : rest) {
            totalLength += array.length + 3;
        }
        totalLength = totalLength - 3;
        byte[] result = new byte[totalLength];
        int offset = 0;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            //加3个字节000;
            result[array.length] = (byte) 0;
            result[array.length + 1] = (byte) 0;
            result[array.length + 2] = (byte) 0;
            offset += array.length + 1;
        }
        return result;
    }

    /**
     * 指令拼接
     * @param rest
     * @return
     */
    public static byte[] concatAllCmd2(List<byte[]> rest) {
        int totalLength = 0;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = new byte[totalLength];
        int offset = 0;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * 加密
     * @param data
     * @return
     */
    public static byte[] encode(byte[] data) {
        int len = data.length;
        byte[] buf = new byte[len];
        int i, j;
        for (i = 0; i < len; i++) {
            for (j = 0; j < 8; j++) {
                if (i % 8 == j)
                    buf[i] = (byte) (data[i] ^ key[j]);
            }
        }
        return data;
    }

}
