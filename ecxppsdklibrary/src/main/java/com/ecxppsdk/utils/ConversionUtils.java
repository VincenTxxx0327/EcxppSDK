package com.ecxppsdk.utils;

import java.util.Random;

/**
 * Author: VincenT
 * Date: 2017/4/24 16:38
 * Contact:qq 328551489
 * Purpose:字符串转换工具
 */
public class ConversionUtils {

    /**
     * 字节码转十六进制 方式一
     *
     * @param bytes
     * @return
     */
    public static String bytes2HexString01(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String tmp;
        for (byte b : bytes) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1) {// 每个字节8为，转为16进制标志，2个16进制位
                tmp = "0" + tmp;
            }
            sb.append(tmp);
        }
        return sb.toString();
    }

    /**
     * 方式二
     *
     * @param bytes
     * @return
     */
    public static String bytes2HexString02(byte[] bytes) {
        final String HEX = "0123456789abcdef";
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt((b >> 4) & 0x0f));
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt(b & 0x0f));
        }

        return sb.toString();
    }

    /**
     * 将String转为byte数组
     *
     * @param src
     * @return
     */
    public static byte[] hexString2BytesData(String src) {
        int len = src.length();
        byte[] ret = new byte[len / 2];
        String temp;
        for (int i = 0; i < len / 2; i++) {
            temp = src.substring(i * 2, i * 2 + 2);
            ret[i] = (byte) (Integer.parseInt(temp, 16));
        }
        byte[] data = new byte[len / 2];
        for (int j = 0; j < len / 2; j++) {
            data[j] = ret[j];
        }
        return data;
    }

    /**
     * mac地址的冒号形式转换为字节数组
     *
     * @param src
     * @return
     */
    public static byte[] hexString2BytesOld(String src) {
        String str = src.replace(":", "");
        int len = str.length();
        byte[] ret = new byte[len / 2];
        String temp;
        for (int i = 0; i < len / 2; i++) {
            temp = str.substring(i * 2, i * 2 + 2);
            ret[i] = (byte) (Integer.parseInt(temp, 16));
        }
        byte[] data = new byte[len / 2 + 2];
        for (int j = 0; j < len / 2 + 2; j++) {
            if (j < 2) {
                data[0] = 0x01;
                data[1] = 0x0f;
            } else data[j] = ret[j - 2];
        }
        return data;
    }

    /**
     * mac地址的冒号形式转换为字节数组
     *
     * @param src
     * @return
     */
    public static byte[] hexString2BytesNew(String src) {
        String str = src.replace(":", "");
        int len = str.length();
        byte[] ret = new byte[len / 2];
        String temp;
        for (int i = 0; i < len / 2; i++) {
            temp = str.substring(i * 2, i * 2 + 2);
            ret[i] = (byte) (Integer.parseInt(temp, 16));
        }
        return ret;
    }

    /**
     * 传入由16进制组成的String，将其转为单个byte数组
     * 例如：2000-->0x3200
     *
     * @param src
     * @return
     */
    public static byte[] hexString2Bytes(String src) {
        String str = Integer.toBinaryString(8);
        byte[] ret = new byte[1];
        ret[0] = (byte) (Integer.parseInt(str, 16));
        return ret;
    }

    /**
     * 高位在前，低位在后
     * @param num
     * @return
     */
    public static byte integer2byteFront(int num) {
        return (byte) ((num >>> 8) & 0xff);
    }
    /**
     * @param num
     * @return
     */
    public static byte integer2byteBack(int num) {
        return (byte) ((num >>> 0) & 0xff);
    }

    /**
     * 传入由16进制组成的String，将其byte长度大小转为单个byte数组
     * 例如：61646d696e233132333435363738-->0E
     *
     * @param src
     * @return
     */
    public static byte[] hexStringLen2Bytes(String src) {
        String strTmp = Integer.toHexString(src.length() / 2);
        byte[] ret = new byte[1];
        ret[0] = (byte) (Integer.parseInt(strTmp, 16));
        return ret;
    }

    /**
     * 是否16进制字符
     */

    public static boolean isHexString(String strHex) {
        int i = 0;
        if (strHex.length() > 2) {//是否0X 或 0x 开头
            if (strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x')) {
                i = 2;
            }
        }
        for (; i < strHex.length(); ++i) {
            char ch = strHex.charAt(i);
            if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'F') || (ch >= 'a' && ch <= 'f')) {
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * 判断返回的指令内容
     *
     * @param bytes
     * @return
     */
    public static String hexStringType(byte[] bytes) {
        if (bytes[0] == 0x0A && bytes[13] == 0x02) {
            return "《转发串口返回的指令》";
        } else if (bytes[0] == 0x0F && bytes[13] == 0x02) {
            return "《心跳包返回的指令》";
        } else if (bytes[0] == 0x0A && bytes[13] == 0x01) {
            return "《用户登录返回的指令》";
        } else if (bytes[0] == 0x0A && bytes[13] == 0x03) {
            return "《密码修改返回的指令》";
        } else if (bytes[0] == 0x0A && bytes[13] == 0x04) {
            return "《用户退出返回的指令》";
        } else if (bytes[0] == 0x0A && bytes[13] == 0x05) {
            return "《上网配置返回的指令》";
        }
        return "";
    }

    public static int getRandomNum() {
        Random random = new Random(1000000000);
        int value = random.nextInt();
        String valueStr = String.valueOf(value);
        if (valueStr.length() < 10) {
            if (valueStr.length() == 1 || valueStr.length() == 3 || valueStr.length() == 5 || valueStr.length() == 7 || valueStr.length() == 9) {
                for (int i = 0; i < 10; i++) {
                    valueStr += "0";
                }
            } else {
                StringBuilder sb = new StringBuilder(valueStr);//构造一个StringBuilder对象
                for (int i = 0; i < 10; i++) {
                    sb.insert(1, "1");//在指定的位置1，插入指定的字符串
                    valueStr = sb.toString();
                    valueStr += "0";
                }
            }
            return Integer.valueOf(valueStr);
        } else {
            return value;
        }
    }

    /**
     * 获得5byte长度的随机byte
     *
     * @return
     */
    public static String getRandomNumStr() {
        byte[] bytes = new byte[5];
        Random r = new Random();
        r.nextBytes(bytes);
        return bytes2HexString01(bytes).toUpperCase();
    }

}
