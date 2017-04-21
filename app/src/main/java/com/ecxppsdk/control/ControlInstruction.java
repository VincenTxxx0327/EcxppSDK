package com.ecxppsdk.control;

import com.ecxppsdk.R;
import com.ecxppsdk.utils.DeviceUtils;
import com.ecxppsdk.utils.SharedPreUtils;

import static com.ecxppsdk.control.Instruction.concatAll;
import static com.ecxppsdk.control.Instruction.ctr01_cmd;
import static com.ecxppsdk.control.Instruction.ctr02_cmd;
import static com.ecxppsdk.control.Instruction.ctr03_cmd;
import static com.ecxppsdk.control.Instruction.ctr04_cmd;
import static com.ecxppsdk.control.Instruction.ctr05_cmd;
import static com.ecxppsdk.control.Instruction.ctr07_cmd;
import static com.ecxppsdk.control.Instruction.ctr08_cmd;
import static com.ecxppsdk.control.Instruction.data_id;
import static com.ecxppsdk.control.Instruction.dst_Mac;
import static com.ecxppsdk.control.Instruction.prea_cmd;
import static com.ecxppsdk.control.Instruction.src_Mac;
import static com.ecxppsdk.control.Instruction.uart_cmd;
import static com.ecxppsdk.utils.ConversionUtils.bytes2HexString01;
import static com.ecxppsdk.utils.ConversionUtils.hexString2BytesData;
import static com.ecxppsdk.utils.ConversionUtils.hexStringLen2Bytes;
import static com.ecxppsdk.utils.ConversionUtils.integer2byteBack;
import static com.ecxppsdk.utils.ConversionUtils.integer2byteFront;

/**
 * Created by uen on 2016/9/4.
 * <p>设备控制指令</p>
 */
public class ControlInstruction {

    /**
     * 开灯
     * @param id 设备ID
     * @param ctr 单个设备01 群组设备06
     */
    public static void openLight(String id, byte[] ctr) {
        byte[] data;
        byte[] id_cmd = hexString2BytesData(id);
        byte[] ctr_cmd = concatAll(id_cmd, ctr, new byte[]{100, 100, 100, 100, 100});
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else{
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 关灯
     *
     * @param id 设备ID
     */
    public static void closeLight(String id, byte[] ctr) {
        byte[] data;
        byte[] id_cmd = hexString2BytesData(id);
        byte[] ctr_cmd = concatAll(id_cmd, ctr, new byte[]{0, 0, 0, 0, 0});
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else{
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, true);
    }
    /**
     * 开灯
     * @param id 设备ID
     */
    public static void openLumen(String id) {
        byte[] data;
        byte[] id_cmd = hexString2BytesData(id);
        byte[] ctr_cmd = concatAll(id_cmd, ctr07_cmd, ctr01_cmd);
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else{
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 关灯
     *
     * @param id 设备ID
     */
    public static void closeLumen(String id) {
        byte[] data;
        byte[] id_cmd = hexString2BytesData(id);
        byte[] ctr_cmd = concatAll(id_cmd, ctr07_cmd, ctr02_cmd);
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else{
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 调灯光(五路控制)
     * @param id    设备ID
     * @param ctr   01单设备控制，06群组控制
     * @param red   红光值
     * @param blue  蓝光值
     * @param white 白光值
     * @param infrared 红外线
     * @param ultraviolet 紫光值
     * @param isLimitTime 是否限制操作频繁
     */
    public static void changeLight(String id, byte[] ctr, int red, int blue, int white, int infrared, int ultraviolet, boolean isLimitTime) {
        byte[] data;
        byte[] id_cmd = hexString2BytesData(id);//因控制板顺序是红白蓝 故此处顺序如此
        byte[] ctr_cmd = concatAll(id_cmd, ctr, new byte[]{(byte) red, (byte) white, (byte) blue, (byte) infrared, (byte) ultraviolet});
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else {
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, isLimitTime);
    }
    /**
     * 设置群组ID
     * @param soleID
     * @param groupID
     */
    public static void setLightGroup(String soleID, String groupID) {
        byte[] data;
        byte[] sole_cmd = hexString2BytesData(soleID);
        byte[] group_cmd = hexString2BytesData(groupID);
        byte[] ctr_cmd = concatAll(sole_cmd, ctr05_cmd, ctr01_cmd, group_cmd);
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else {
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, true);
    }
    /**
     * 删除群组ID
     * @param soleID
     */
    public static void cancelLightGroup(String soleID) {
        byte[] data;
        byte[] id_cmd = hexString2BytesData(soleID);
        byte[] ctr_cmd = concatAll(id_cmd, ctr05_cmd, ctr01_cmd, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else {
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 设置区间
     * @param soleID
     * @param groupID
     */
    public static void setLumenGroup(String soleID, String groupID) {
        byte[] data;
        byte[] sole_cmd = hexString2BytesData(soleID);
        byte[] group_cmd = hexString2BytesData(groupID);
        byte[] ctr_cmd = concatAll(sole_cmd, ctr05_cmd, ctr01_cmd, group_cmd);
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else{
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, true);
    }
    /**
     * 设置区间
     * @param deviceId    设备ID
     * @param maxLumen
     * @param minLumen
     */
    public static void setLumen(String deviceId, int minLumen, int maxLumen, int red, int blue, int white, int infrared, int ultraviolet) {
        byte[] data;
        byte[] id_cmd = hexString2BytesData(deviceId);
        byte[] ctr_cmd = concatAll(id_cmd, ctr08_cmd, new byte[]{
                integer2byteFront(minLumen), integer2byteBack(minLumen),
                integer2byteFront(maxLumen), integer2byteBack(maxLumen),
                (byte) red, (byte) white, (byte) blue, (byte) infrared, (byte) ultraviolet});//因控制板顺序是红白蓝 故此处顺序如此
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else{
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, true);
    }
    /**
     * 擦除区间
     * @param id 设备ID
     */
    public static void cancelLumen(String id) {
        byte[] data;
        byte[] id_cmd = hexString2BytesData(id);
        byte[] ctr_cmd = concatAll(id_cmd, ctr07_cmd, ctr03_cmd);
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else{
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, true);
    }
    /**
     * 设置闹钟
     * @param id 设备ID
     * @param hour 当前小时
     * @param min 当前分钟
     * @param red 红光
     * @param blue 蓝光
     * @param white 白光
     * @param infrared 红外线
     * @param ultraviolet 紫光
     */
    public static void setAlarm(String id, int hour, int min, int red, int blue, int white, int infrared, int ultraviolet) {
        byte[] data;
        byte[] id_cmd = hexString2BytesData(id);//因控制板顺序是红白蓝 故此处顺序如此
        byte[] ctr_cmd = concatAll(id_cmd, ctr02_cmd, new byte[]{(byte) hour, (byte) min, (byte) red, (byte) white, (byte) blue, (byte) infrared, (byte) ultraviolet});
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else{
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, true);
    }
    /**
     * 取消闹钟
     * @param id 设备ID
     */
    public static void cancelAlarm(String id, int hour, int min) {
        byte[] data;
        byte[] id_cmd = hexString2BytesData(id);
        byte[] ctr_cmd = concatAll(id_cmd, ctr03_cmd, new byte[]{(byte) hour, (byte) min});
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else{
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, true);
    }
    /**
     * 设置校准时间
     * @param id 设备ID
     * @param hour 当前小时
     * @param min 当前分钟
     * @param second 当前秒
     */
    public static void setBaseTime(String id, int hour, int min, int second) {
        byte[] data;
        byte[] id_cmd = hexString2BytesData(id);
        byte[] ctr_cmd = concatAll(id_cmd, ctr04_cmd, new byte[]{(byte) hour, (byte) min, (byte) second });
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else{
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 发送指令控制多路
     * @param deviceId 设备ID
     * @param multiInstruction 多路控制指令
     */
    public static String changeLight(String deviceId, byte[]... multiInstruction) {
        byte[] data;
        byte[] id_cmd = hexString2BytesData(deviceId);
        byte[] ctr_cmd = concatAll(id_cmd, multiInstruction);
        if(SharedPreUtils.getBoolean(LnledApp.getInstance(), LnledApp.getInstance().getString(R.string.cmdNew), false)) {
            byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
            byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAll(len_ctr_cmd, ctr_cmd)));
            data = concatAll(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        }else{
            data = concatAll(data_id, DeviceUtils.getDstMac(), uart_cmd, ctr_cmd);
        }
        InstructionService.sendInstruction(data, false);
        return bytes2HexString01(data);
    }

}
