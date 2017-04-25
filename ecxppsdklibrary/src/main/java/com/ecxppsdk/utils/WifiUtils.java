package com.ecxppsdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

import com.ecxppsdk.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WifiUtils {
    private WifiManager localWifiManager;// �ṩWifi����ĸ�����ҪAPI����Ҫ��wifi��ɨ�衢�������ӡ�������Ϣ��
    // private List<ScanResult>
    // wifiScanList;//ScanResult���������Ѿ������Ľ���㣬��������ĵ�ַ����ơ������֤��Ƶ�ʡ��ź�ǿ�ȵ�
    private List<WifiConfiguration> wifiConfigList;// WIFIConfiguration����WIFI��������Ϣ������SSID��SSID���ء�password�ȵ�����
    private WifiInfo wifiConnectedInfo;// �Ѿ��������������ӵ���Ϣ
    private WifiLock wifiLock;// �ֻ���������ֹWIFIҲ����˯��״̬��WIFI�Ĺر�

    public WifiUtils(Context context) {
        localWifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
    }


    /**
     * @return// 得到了wi - fi启用状态。
     */
    public int WifiCheckState() {
        return localWifiManager.getWifiState();
    }

    /**
     * // 开启WIFI
     */

    public void WifiOpen() {
        if (!localWifiManager.isWifiEnabled()) {
            localWifiManager.setWifiEnabled(true);

        }
    }

    /**
     * // 关闭WIFI
     */
    public void WifiClose() {
        if (localWifiManager.isWifiEnabled()) {
            localWifiManager.setWifiEnabled(false);
        }
    }


    /**
     * // 开始扫描wifi
     */
    public void WifiStartScan() {
        localWifiManager.startScan();
    }

    /**
     * @return 得到扫描结果
     */

    public List<ScanResult> getScanResults() {
        return localWifiManager.getScanResults();// �õ�ɨ����
    }

    /**
     * 返回当前的wi - fi连接动态信息,是否活跃。
     *
     * @return
     */
    public WifiInfo getConnectionInfo() {
        return localWifiManager.getConnectionInfo();
    }

    /**
     * 返回是否启用或禁用无线网络
     *
     * @return
     */
    public boolean isWifiEnabled() {
        return localWifiManager.isWifiEnabled();
    }

    /**
     * 设置wifi是否可用
     *
     * @param b
     */
    public void setWifiEnabled(boolean b) {
        localWifiManager.setWifiEnabled(b);
    }

    /**
     * 扫描结果字符串
     *
     * @param list
     * @return
     */
    public List<String> scanResultToString(List<ScanResult> list) {
        List<String> strReturnList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            ScanResult strScan = list.get(i);
            String str = strScan.toString();
            boolean bool = strReturnList.add(str);
            if (!bool) {
                Log.i("scanResultToSting", "Addfail");
            }
        }
        return strReturnList;
    }

    //得到Wifi配置好的信息
    public void getConfiguration() {
        wifiConfigList = localWifiManager.getConfiguredNetworks();//得到配置好的网络信息
        for (int i = 0; i < wifiConfigList.size(); i++) {
            Log.i("getConfiguration", wifiConfigList.get(i).SSID);
            Log.i("getConfiguration",
                    String.valueOf(wifiConfigList.get(i).networkId));

        }
    }

    // �ж�ָ��WIFI�Ƿ��Ѿ����ú�,����WIFI�ĵ�ַBSSID,����NetId
    public int IsConfiguration(String SSID) {
        Log.i("IsConfiguration", String.valueOf(wifiConfigList.size()));
        for (int i = 0; i < wifiConfigList.size(); i++) {
            Log.i(wifiConfigList.get(i).SSID,
                    String.valueOf(wifiConfigList.get(i).networkId));
            if (wifiConfigList.get(i).SSID.equals(SSID)) {// ��ַ��ͬ
                return wifiConfigList.get(i).networkId;
            }
        }
        return -1;
    }

    /**
     * //添加指定WIFI的配置信息,原列表不存在此SSID
     *
     * @param wifiList
     * @param ssid
     * @param pwd
     * @return
     */

    public int AddWifiConfig(List<ScanResult> wifiList, String ssid, String pwd) {
        int wifiId = -1;
        for (int i = 0; i < wifiList.size(); i++) {
            ScanResult wifi = wifiList.get(i);
            if (wifi.SSID.equals(ssid)) {
                Log.i("AddWifiConfig", "equals");
                WifiConfiguration wifiCong = new WifiConfiguration();
                wifiCong.SSID = "\"" + wifi.SSID + "\"";// \"ת���ַ���"
                wifiCong.preSharedKey = "\"" + pwd + "\"";// WPA-PSK����
                wifiCong.hiddenSSID = false;
                wifiCong.status = WifiConfiguration.Status.ENABLED;
                wifiId = localWifiManager.addNetwork(wifiCong);// �����úõ��ض�WIFI������Ϣ���,�����ɺ�Ĭ���ǲ�����״̬���ɹ�����ID������Ϊ-1
                if (wifiId != -1) {
                    return wifiId;
                }
            }
        }
        return wifiId;
    }

    /**
     * 连接wifi
     *
     * @param wifiId
     * @return
     */
    public boolean ConnectWifi(int wifiId) {
        for (int i = 0; i < wifiConfigList.size(); i++) {
            WifiConfiguration wifi = wifiConfigList.get(i);
            if (wifi.networkId == wifiId) {
                while (!(localWifiManager.enableNetwork(wifiId, true))) {// �����Id����������
                    Log.i("ConnectWifi",
                            String.valueOf(wifiConfigList.get(wifiId).status));// status:0--�Ѿ����ӣ�1--�������ӣ�2--��������
                }
                return true;
            }
        }
        return false;
    }

    // ����һ��WIFILock
    public void createWifiLock(String lockName) {
        wifiLock = localWifiManager.createWifiLock(lockName);
    }

    // ��wifilock
    public void acquireWifiLock() {
        wifiLock.acquire();
    }

    // ����WIFI
    public void releaseWifiLock() {
        if (wifiLock.isHeld()) {// �ж��Ƿ���
            wifiLock.release();
        }
    }


    /**
     * 返回当前的wi - fi连接动态信息,是否活跃
     */
    public void getConnectedInfo() {
        wifiConnectedInfo = localWifiManager.getConnectionInfo();
    }

    // �õ����ӵ�MAC��ַ
    public String getConnectedMacAddr() {
        return (wifiConnectedInfo == null) ? "NULL" : wifiConnectedInfo
                .getMacAddress();
    }

    // �õ����ӵ����SSID
    public String getConnectedSSID() {
        return (wifiConnectedInfo == null) ? "NULL" : wifiConnectedInfo
                .getSSID();
    }

    // �õ����ӵ�IP��ַ
    public String getConnectedIPAddr() {
        return int2ip((wifiConnectedInfo == null) ? 0 : wifiConnectedInfo
                .getIpAddress());

    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */

    public String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    public int getConnectedID() {

        return (wifiConnectedInfo == null) ? 0 : wifiConnectedInfo
                .getNetworkId();

    }

    // �õ����ӵ�ID
    public int getConnectedRssi() {
        return (wifiConnectedInfo == null) ? 0 : wifiConnectedInfo.getRssi();
    }

    /**
     * 获取网关
     *
     * @return
     */
    public String getGateway() {
        int gateway = localWifiManager.getDhcpInfo().gateway;
        //子网掩码
        //		localWifiManager.getDhcpInfo().netmask;
        // 子网掩码
//        		localWifiManager.getDhcpInfo().;
        String gatewayIpS = long2ip(gateway);
        return gatewayIpS;
    }

    /**
     * 获取子网掩码
     *
     * @return
     */
    public String getNetmask() {
        //子网掩码
        int netmask = localWifiManager.getDhcpInfo().netmask;
        // 子网掩码
        //        		localWifiManager.getDhcpInfo().;
        String netmaskIpS = long2ip(netmask);
        return netmaskIpS;
    }

    /**
     * Dns1
     *
     * @return
     */
    public String getDns1() {
        //子网掩码
        int dns1 = localWifiManager.getDhcpInfo().dns1;
        // 子网掩码
        //        		localWifiManager.getDhcpInfo().;
        return long2ip(dns1);
    }

    /**
     * Dns2
     *
     * @return
     */
    public String getDns2() {
        //子网掩码
        int dns2 = localWifiManager.getDhcpInfo().dns2;
        // 子网掩码
        //        		localWifiManager.getDhcpInfo().;
        String dns2IpS = long2ip(dns2);
        return dns2IpS;
    }

    private String long2ip(long ip) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf((int) (ip & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 8) & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 16) & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 24) & 0xff)));
        return sb.toString();
    }


    /**
     * 判断当前手机是否连上WIFI.
     *
     * @param context 上下文
     * @return boolean 是否连上网络
     * <p>
     * *
     */
    static public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                if (mWiFiNetworkInfo.isAvailable()) {
                    return mWiFiNetworkInfo.isConnected();
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 打开WIFI
     */
    public static void openWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

    }

    /**
     * 获取当前WIFI的SSID.
     *
     * @param context 上下文
     * @return SSID
     */
    public static String getCurrentWifiSSID(Context context) {
        String ssid = "";
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            ssid = wifiInfo.getSSID();
            if(ssid.length()>0) {
                if (ssid.substring(0, 1).equals("\"") && ssid.substring(ssid.length() - 1).equals("\"")) {
                    ssid = ssid.substring(1, ssid.length() - 1);
                }
            }
        }
        if (ssid.contains("unknown")) {
            ssid = context.getString(R.string.toastText_wifi_disconnect);
        }
        return ssid;
    }

    /**
     * 用来获得手机扫描到的所有WIFI的信息.
     *
     * @param c 上下文
     * @return the current WIFI scan result
     */
    static public List<ScanResult> getCurrentWifiScanResult(Context c) {
        WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        return wifiManager.getScanResults();
    }

    /**
     * 判断是否可连外网
     *
     * @return
     */
    public static boolean ping() {
        String result = null;
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuilder stringBuffer = new StringBuilder();
            String content;
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
//            Log.d("------ping-----", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            Log.d("----result---", "result = " + result);
        }
        return false;
    }
}
