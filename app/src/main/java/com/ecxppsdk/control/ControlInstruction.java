package com.ecxppsdk.control;

import static com.ecxppsdk.control.Instruction.concatAllFirst;
import static com.ecxppsdk.control.Instruction.concatAllSecond;
import static com.ecxppsdk.control.Instruction.ctr01_cmd;
import static com.ecxppsdk.control.Instruction.ctr02_cmd;
import static com.ecxppsdk.control.Instruction.ctr03_cmd;
import static com.ecxppsdk.control.Instruction.ctr04_cmd;
import static com.ecxppsdk.control.Instruction.ctr05_cmd;
import static com.ecxppsdk.control.Instruction.ctr07_cmd;
import static com.ecxppsdk.control.Instruction.ctr08_cmd;
import static com.ecxppsdk.control.Instruction.dst_Mac;
import static com.ecxppsdk.control.Instruction.prea_cmd;
import static com.ecxppsdk.control.Instruction.src_Mac;
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
     * 取消闹钟
     *
     * @param id 设备ID
     */
    public static void cancelAlarm(String id, int hour, int min) {
        byte[] id_cmd = hexString2BytesData(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr03_cmd, new byte[]{(byte) hour, (byte) min});
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 删除群组ID
     *
     * @param soleID
     */
    public static void cancelLightGroup(String soleID) {
        byte[] id_cmd = hexString2BytesData(soleID);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr05_cmd, ctr01_cmd, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 擦除区间
     *
     * @param id 设备ID
     */
    public static void cancelLumen(String id) {
        byte[] id_cmd = hexString2BytesData(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr03_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 改变灯的状态
     *
     * @param id               设备ID
     * @param code_cmd         01：单个设备 06：群组设备 可为null
     * @param multiInstruction
     */
    public static void changeLight(String id, byte[] code_cmd, byte[]... multiInstruction) {
        byte[] id_cmd = hexString2BytesData(id);
        byte[] ctr_cmd;
        if (code_cmd == null) {
            ctr_cmd = concatAllFirst(id_cmd, multiInstruction);
        } else {
            ctr_cmd = concatAllSecond(id_cmd, code_cmd, multiInstruction);
        }
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 改变灯的状态
     *
     * @param id       设备ID
     * @param code_cmd 01：单个设备 06：群组设备 可为null
     * @param isOpen   开还是关
     * @param roadNum  控制路数
     */
    public static void changeLight(String id, byte[] code_cmd, boolean isOpen, int roadNum) {
        byte[] id_cmd = hexString2BytesData(id);
        byte[] multiInstruction = new byte[roadNum];
        for (int i = 0; i < multiInstruction.length; i++) {
            if (isOpen) {
                multiInstruction[i] = (byte) 0xFF;
            } else {
                multiInstruction[i] = (byte) 0x00;
            }
        }
        byte[] ctr_cmd;
        if (code_cmd == null) {
            ctr_cmd = concatAllFirst(id_cmd, multiInstruction);
        } else {
            ctr_cmd = concatAllSecond(id_cmd, code_cmd, multiInstruction);
        }
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 开灯
     *
     * @param id 设备ID
     */
    public static void changeLumen(String id, boolean isOpen) {
        byte[] ctr_cmd;
        byte[] id_cmd = hexString2BytesData(id);
        if (isOpen) {
            ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr01_cmd);
        } else {
            ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr02_cmd);
        }
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 改变灯的状态
     *
     * @param id       设备ID
     * @param code_cmd 01：单个设备 06：群组设备 可为null
     * @param roadNum  控制路数
     */
    public static void closeLight(String id, byte[] code_cmd, int roadNum) {
        byte[] id_cmd = hexString2BytesData(id);
        byte[] multiInstruction = new byte[roadNum];
        for (int i = 0; i < multiInstruction.length; i++) {
            multiInstruction[i] = (byte) 0x00;
        }
        byte[] ctr_cmd;
        if (code_cmd == null) {
            ctr_cmd = concatAllFirst(id_cmd, multiInstruction);
        } else {
            ctr_cmd = concatAllSecond(id_cmd, code_cmd, multiInstruction);
        }
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 关灯
     *
     * @param id 设备ID
     */
    public static void closeLumen(String id) {
        byte[] id_cmd = hexString2BytesData(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr02_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 改变灯的状态
     *
     * @param id       设备ID
     * @param code_cmd 01：单个设备 06：群组设备 可为null
     * @param roadNum  控制路数
     */
    public static void openLight(String id, byte[] code_cmd, int roadNum) {
        byte[] id_cmd = hexString2BytesData(id);
        byte[] multiInstruction = new byte[roadNum];
        for (int i = 0; i < multiInstruction.length; i++) {
            multiInstruction[i] = (byte) 0xFF;
        }
        byte[] ctr_cmd;
        if (code_cmd == null) {
            ctr_cmd = concatAllFirst(id_cmd, multiInstruction);
        } else {
            ctr_cmd = concatAllSecond(id_cmd, code_cmd, multiInstruction);
        }
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 开灯
     *
     * @param id 设备ID
     */
    public static void openLumen(String id) {
        byte[] id_cmd = hexString2BytesData(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr01_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 设置闹钟
     *
     * @param id          设备ID
     * @param hour        当前小时
     * @param min         当前分钟
     * @param red         红光
     * @param blue        蓝光
     * @param white       白光
     * @param infrared    红外线
     * @param ultraviolet 紫光
     */
    public static void setAlarm(String id, int hour, int min, int red, int blue, int white, int infrared, int ultraviolet) {
        byte[] id_cmd = hexString2BytesData(id);//因控制板顺序是红白蓝 故此处顺序如此
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr02_cmd, new byte[]{(byte) hour, (byte) min, (byte) red, (byte) white, (byte) blue, (byte) infrared, (byte) ultraviolet});
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 设置校准时间
     *
     * @param id     设备ID
     * @param hour   当前小时
     * @param min    当前分钟
     * @param second 当前秒
     */
    public static void setAlarmBase(String id, int hour, int min, int second) {
        byte[] id_cmd = hexString2BytesData(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr04_cmd, new byte[]{(byte) hour, (byte) min, (byte) second});
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 设置群组ID
     *
     * @param soleID
     * @param groupID
     */
    public static void setLightGroup(String soleID, String groupID) {
        byte[] sole_cmd = hexString2BytesData(soleID);
        byte[] group_cmd = hexString2BytesData(groupID);
        byte[] ctr_cmd = concatAllFirst(sole_cmd, ctr05_cmd, ctr01_cmd, group_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 设置区间
     *
     * @param deviceId 设备ID
     * @param maxLumen
     * @param minLumen
     */
    public static void setLumen(String deviceId, int minLumen, int maxLumen, int red, int blue, int white, int infrared, int ultraviolet) {
        byte[] id_cmd = hexString2BytesData(deviceId);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr08_cmd, new byte[]{
                integer2byteFront(minLumen), integer2byteBack(minLumen),
                integer2byteFront(maxLumen), integer2byteBack(maxLumen),
                (byte) red, (byte) white, (byte) blue, (byte) infrared, (byte) ultraviolet});//因控制板顺序是红白蓝 故此处顺序如此
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 设置区间
     *
     * @param soleID
     * @param groupID
     */
    public static void setLumenGroup(String soleID, String groupID) {
        ;
        byte[] sole_cmd = hexString2BytesData(soleID);
        byte[] group_cmd = hexString2BytesData(groupID);
        byte[] ctr_cmd = concatAllFirst(sole_cmd, ctr05_cmd, ctr01_cmd, group_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString01(ctr_cmd));
        byte[] len_send_cmd = hexStringLen2Bytes(bytes2HexString01(concatAllFirst(len_ctr_cmd, ctr_cmd)));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_send_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

}
