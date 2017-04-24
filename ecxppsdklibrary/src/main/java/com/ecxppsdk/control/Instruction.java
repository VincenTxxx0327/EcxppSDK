package com.ecxppsdk.control;

import com.ecxppsdk.utils.ConversionUtils;

import java.util.Arrays;

/**
 * Author: VincenT
 * Date: 2017/4/24 10:28
 * Contact:qq 328551489
 * Purpose:连接、控制等基础指令
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
     *
     * @return
     */
    public static byte[] registerInstruction() {
        return encode(concatAllFirst(pref_cmd, dst_Mac, src_Mac, ctr02_cmd));//新指令
    }

    /**
     * 登陆指令
     *
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
        userAndPsw = user + "#" + "12345678";
        byte[] ctr_cmd = userAndPsw.getBytes();
        byte[] len_cmd = ConversionUtils.hexStringLen2Bytes(ConversionUtils.bytes2HexString01(ctr_cmd));
        return encode(concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr01_cmd, len_cmd, ctr_cmd));//新指令
    }

    /**
     * 注销登陆指令
     *
     * @return
     */
    public static byte[] logoutInstruction() {
        return encode(concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr04_cmd));//新指令
    }

    /**
     * 心跳包指令
     *
     * @return
     */
    public static byte[] heartInstruction() {
        return encode(heart_cmd);//新指令
    }

    /**
     * 设置网关WiFi指令
     *
     * @return
     */
    public static byte[] setNetInstruction(String ssidAndPsw) {
        //规则：0a dst_mac(6Byte) src_mac(6Byte) 05 len(1Byte) ssid 23 passwd
        byte[] ctr_cmd = ssidAndPsw.getBytes();
        byte[] len_cmd = ConversionUtils.hexStringLen2Bytes(ConversionUtils.bytes2HexString01(ctr_cmd));
        byte[] data = Instruction.concatAllFirst(Instruction.prea_cmd, Instruction.dst_Mac, Instruction.src_Mac, Instruction.ctr05_cmd, len_cmd, ctr_cmd);
        byte[] buf = Instruction.encode(data);
        return encode(buf);//新指令
    }

    /**
     * 指令拼接
     *
     * @param first
     * @param rest
     * @return
     */
    public static byte[] concatAllFirst(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * 指令拼接
     *
     * @param first
     * @param second
     * @param rest
     * @return
     */
    public static byte[] concatAllSecond(byte[] first, byte[] second, byte[]... rest) {
        int totalLength = first.length + second.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        System.arraycopy(second, 0, result, first.length, second.length);
        int offset = first.length + second.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    /**
     * 加密
     *
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
