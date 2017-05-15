package com.ecxppsdk.control;

import android.support.annotation.Nullable;

import static com.ecxppsdk.control.Instruction.concatAllFirst;
import static com.ecxppsdk.control.Instruction.concatAllSecond;
import static com.ecxppsdk.control.Instruction.concatAllThird;
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
import static com.ecxppsdk.utils.ConversionUtils.bytes2HexString;
import static com.ecxppsdk.utils.ConversionUtils.hexString2Bytes;
import static com.ecxppsdk.utils.ConversionUtils.hexStringLen2Bytes;
import static com.ecxppsdk.utils.ConversionUtils.integer2ByteBack;
import static com.ecxppsdk.utils.ConversionUtils.integer2ByteFront;

/**
 * Author: VincenT
 * Date: 2017/4/24 10:43
 * Contact:qq 328551489
 * Purpose:设备控制指令
 */
public class ControlInstruction {

    /**
     * 取消设备时控数值
     *
     * @param id 设备id
     */
    public static void cancelAlarm(String id) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr03_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 取消设备时控数值
     *
     * @param id          设备id
     * @param isLimitTime 是否限制频繁操作
     */
    public static void cancelAlarm(String id, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr03_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 取消设备时控数值
     *
     * @param id   设备id
     * @param hour 设备当前定时小时
     * @param min  设备当前定时分钟
     */
    public static void cancelAlarm(String id, int hour, int min) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr03_cmd, new byte[]{(byte) hour, (byte) min});
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 取消设备时控数值
     *
     * @param id          设备id
     * @param hour        设备当前定时小时
     * @param min         设备当前定时分钟
     * @param isLimitTime 是否限制频繁操作
     */
    public static void cancelAlarm(String id, int hour, int min, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr03_cmd, new byte[]{(byte) hour, (byte) min});
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 删除群组id
     *
     * @param soleId 组id
     */
    public static void cancelLightGroup(String soleId) {
        byte[] id_cmd = hexString2Bytes(soleId);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr05_cmd, ctr01_cmd, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 清除设备群组id
     *
     * @param soleId      组id
     * @param isLimitTime 是否限制频繁操作
     */
    public static void cancelLightGroup(String soleId, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(soleId);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr05_cmd, ctr01_cmd, new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 擦除区间
     *
     * @param id 设备id
     */
    public static void cancelLumen(String id) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr03_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 清除光控数值
     *
     * @param id          设备id
     * @param isLimitTime 是否限制频繁操作
     */
    public static void cancelLumen(String id, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr03_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 改变设备数值
     *
     * @param id               设备id
     * @param code_cmd         01：单个设备 06：群组设备 可为null
     * @param multiInstruction 多路指令
     */
    public static void changeLight(String id, @Nullable byte[] code_cmd, byte[]... multiInstruction) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd;
        if (code_cmd == null) {
            ctr_cmd = concatAllFirst(id_cmd, multiInstruction);
        } else {
            ctr_cmd = concatAllSecond(id_cmd, code_cmd, multiInstruction);
        }
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 改变设备数值
     *
     * @param id               设备id
     * @param code_cmd         01：单个设备 06：群组设备 可为null
     * @param isLimitTime      是否限制频繁操作
     * @param multiInstruction 多路指令
     */
    public static void changeLight(String id, @Nullable byte[] code_cmd, boolean isLimitTime, byte[]... multiInstruction) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd;
        if (code_cmd == null) {
            ctr_cmd = concatAllFirst(id_cmd, multiInstruction);
        } else {
            ctr_cmd = concatAllSecond(id_cmd, code_cmd, multiInstruction);
        }
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 改变设备数值
     *
     * @param id       设备id
     * @param code_cmd 01：单个设备 06：群组设备 可为null
     * @param isOpen   是否开关
     */
    public static void changeLight(String id, @Nullable byte[] code_cmd, boolean isOpen) {
        changeLight(id, code_cmd, isOpen, 8);
    }

    /**
     * 改变设备数值
     *
     * @param id          设备id
     * @param code_cmd    01：单个设备 06：群组设备 可为null
     * @param isOpen      是否开关
     * @param isLimitTime 是否限制频繁操作
     */
    public static void changeLight(String id, @Nullable byte[] code_cmd, boolean isOpen, boolean isLimitTime) {
        changeLight(id, code_cmd, isOpen, 8, isLimitTime);
    }

    /**
     * 改变设备数值
     *
     * @param id       设备id
     * @param code_cmd 01：单个设备 06：群组设备 可为null
     * @param isOpen   开还是关
     * @param roadNum  控制路数
     */
    public static void changeLight(String id, @Nullable byte[] code_cmd, boolean isOpen, int roadNum) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] multiInstruction = new byte[roadNum];
        for (int i = 0; i < multiInstruction.length; i++) {
            if (isOpen) {
                multiInstruction[i] = (byte) 0x32;
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
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 改变设备数值
     *
     * @param id          设备id
     * @param code_cmd    01：单个设备 06：群组设备 可为null
     * @param isOpen      开还是关
     * @param roadNum     控制路数
     * @param isLimitTime 是否限制频繁操作
     */
    public static void changeLight(String id, @Nullable byte[] code_cmd, boolean isOpen, int roadNum, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] multiInstruction = new byte[roadNum];
        for (int i = 0; i < multiInstruction.length; i++) {
            if (isOpen) {
                multiInstruction[i] = (byte) 0x32;
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
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 改变设备数值
     *
     * @param id        设备id
     * @param code_cmd  01：单个设备 06：群组设备 可为null
     * @param multi_cmd 多路发送单独数值
     * @param roadNum   控制路数
     */
    public static void changeLight(String id, @Nullable byte[] code_cmd, byte multi_cmd, int roadNum) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] multiInstruction = new byte[roadNum];
        for (int i = 0; i < multiInstruction.length; i++) {
            multiInstruction[i] = multi_cmd;
        }
        byte[] ctr_cmd;
        if (code_cmd == null) {
            ctr_cmd = concatAllFirst(id_cmd, multiInstruction);
        } else {
            ctr_cmd = concatAllSecond(id_cmd, code_cmd, multiInstruction);
        }
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 改变设备数值
     *
     * @param id          设备id
     * @param code_cmd    01：单个设备 06：群组设备 可为null
     * @param multi_cmd   多路发送单独数值
     * @param roadNum     控制路数
     * @param isLimitTime 是否限制频繁操作
     */
    public static void changeLight(String id, @Nullable byte[] code_cmd, byte multi_cmd, int roadNum, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] multiInstruction = new byte[roadNum];
        for (int i = 0; i < multiInstruction.length; i++) {
            multiInstruction[i] = multi_cmd;
        }
        byte[] ctr_cmd;
        if (code_cmd == null) {
            ctr_cmd = concatAllFirst(id_cmd, multiInstruction);
        } else {
            ctr_cmd = concatAllSecond(id_cmd, code_cmd, multiInstruction);
        }
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 改变光控数值
     *
     * @param id     设备id
     * @param isOpen 是否开关
     */
    public static void changeLumen(String id, boolean isOpen) {
        byte[] ctr_cmd;
        byte[] id_cmd = hexString2Bytes(id);
        if (isOpen) {
            ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr01_cmd);
        } else {
            ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr02_cmd);
        }
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 改变光控数值
     *
     * @param id          设备id
     * @param isOpen      是否开关
     * @param isLimitTime 是否限制频繁操作
     */
    public static void changeLumen(String id, boolean isOpen, boolean isLimitTime) {
        byte[] ctr_cmd;
        byte[] id_cmd = hexString2Bytes(id);
        if (isOpen) {
            ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr01_cmd);
        } else {
            ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr02_cmd);
        }
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 关闭设备
     *
     * @param id       设备id
     * @param code_cmd 01：单个设备 06：群组设备 可为null
     */
    public static void closeLight(String id, @Nullable byte[] code_cmd) {
        closeLight(id, code_cmd, 8);
    }

    /**
     * 关闭设备
     *
     * @param id          设备id
     * @param code_cmd    01：单个设备 06：群组设备 可为null
     * @param isLimitTime 是否限制频繁操作
     */
    public static void closeLight(String id, @Nullable byte[] code_cmd, boolean isLimitTime) {
        closeLight(id, code_cmd, 8, isLimitTime);
    }

    /**
     * 关闭设备
     *
     * @param id       设备id
     * @param code_cmd 01：单个设备 06：群组设备 可为null
     * @param roadNum  控制路数
     */
    public static void closeLight(String id, @Nullable byte[] code_cmd, int roadNum) {
        byte[] id_cmd = hexString2Bytes(id);
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
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 关闭设备
     *
     * @param id       设备id
     * @param code_cmd 01：单个设备 06：群组设备 可为null
     * @param roadNum  控制路数
     */
    public static void closeLight(String id, @Nullable byte[] code_cmd, int roadNum, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(id);
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
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 关闭光控
     *
     * @param id 设备id
     */
    public static void closeLumen(String id) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr02_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 关闭光控
     *
     * @param id 设备id
     */
    public static void closeLumen(String id, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr02_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 关闭光控
     *
     * @param id 设备id
     */
    public static void getLumen(String id) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, new byte[]{(byte) 0xFF}, ctr01_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 关闭光控
     *
     * @param id 设备id
     */
    public static void getLumen(String id, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, new byte[]{(byte) 0xFF}, ctr01_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 获取某传感器
     *
     * @param id 设备id
     */
    public static void getSensor(String id, byte[] code_cmd) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, new byte[]{(byte) 0xFF}, code_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 获取某传感器
     *
     * @param id 设备id
     */
    public static void getSensor(String id, byte[] code_cmd, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, new byte[]{(byte) 0xFF}, code_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 打开设备
     *
     * @param id       设备id
     * @param code_cmd 01：单个设备 06：群组设备 可为null
     */
    public static void openLight(String id, @Nullable byte[] code_cmd) {
        openLight(id, code_cmd, 8);
    }

    /**
     * 打开设备
     *
     * @param id          设备id
     * @param code_cmd    01：单个设备 06：群组设备 可为null
     * @param isLimitTime 是否限制频繁操作
     */
    public static void openLight(String id, @Nullable byte[] code_cmd, boolean isLimitTime) {
        openLight(id, code_cmd, 8, isLimitTime);
    }

    /**
     * 打开设备
     *
     * @param id       设备id
     * @param code_cmd 01：单个设备 06：群组设备 可为null
     * @param roadNum  控制路数
     */
    public static void openLight(String id, @Nullable byte[] code_cmd, int roadNum) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] multiInstruction = new byte[roadNum];
        for (int i = 0; i < multiInstruction.length; i++) {
            multiInstruction[i] = (byte) 0x32;
        }
        byte[] ctr_cmd;
        if (code_cmd == null) {
            ctr_cmd = concatAllFirst(id_cmd, multiInstruction);
        } else {
            ctr_cmd = concatAllSecond(id_cmd, code_cmd, multiInstruction);
        }
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 打开设备
     *
     * @param id          设备id
     * @param code_cmd    01：单个设备 06：群组设备 可为null
     * @param roadNum     控制路数
     * @param isLimitTime 是否限制频繁操作
     */
    public static void openLight(String id, @Nullable byte[] code_cmd, int roadNum, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] multiInstruction = new byte[roadNum];
        for (int i = 0; i < multiInstruction.length; i++) {
            multiInstruction[i] = (byte) 0x32;
        }
        byte[] ctr_cmd;
        if (code_cmd == null) {
            ctr_cmd = concatAllFirst(id_cmd, multiInstruction);
        } else {
            ctr_cmd = concatAllSecond(id_cmd, code_cmd, multiInstruction);
        }
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 打开光控
     *
     * @param id 设备id
     */
    public static void openLumen(String id) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr01_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 打开光控
     *
     * @param id          设备id
     * @param isLimitTime 是否限制频繁操作
     */
    public static void openLumen(String id, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr07_cmd, ctr01_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 设置时控数值
     *
     * @param id               设备id
     * @param hour             当前小时
     * @param min              当前分钟
     * @param multiInstruction 多路指令
     */
    public static void setAlarm(String id, int hour, int min, byte[]... multiInstruction) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllThird(id_cmd, ctr02_cmd, new byte[]{(byte) hour, (byte) min}, multiInstruction);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 设置时控数值
     *
     * @param id               设备id
     * @param hour             当前小时
     * @param min              当前分钟
     * @param isLimitTime      是否限制频繁操作
     * @param multiInstruction 多路指令
     */
    public static void setAlarm(String id, int hour, int min, boolean isLimitTime, byte[]... multiInstruction) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllThird(id_cmd, ctr02_cmd, new byte[]{(byte) hour, (byte) min}, multiInstruction);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 设置时控数值
     *
     * @param id          设备id
     * @param hour        当前小时
     * @param min         当前分钟
     * @param red         红光
     * @param blue        蓝光
     * @param white       白光
     * @param infrared    红外线
     * @param ultraviolet 紫光
     */
    public static void setAlarm(String id, int hour, int min, int red, int blue, int white, int infrared, int ultraviolet) {
        byte[] id_cmd = hexString2Bytes(id);//因控制板顺序是红白蓝 故此处顺序如此
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr02_cmd, new byte[]{(byte) hour, (byte) min, (byte) red, (byte) blue, (byte) white, (byte) infrared, (byte) ultraviolet});
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 设置时控数值
     *
     * @param id          设备id
     * @param hour        当前小时
     * @param min         当前分钟
     * @param red         红光
     * @param blue        蓝光
     * @param white       白光
     * @param infrared    红外线
     * @param ultraviolet 紫光
     * @param isLimitTime 是否限制频繁操作
     */
    public static void setAlarm(String id, int hour, int min, int red, int blue, int white, int infrared, int ultraviolet, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(id);//因控制板顺序是红白蓝 故此处顺序如此
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr02_cmd, new byte[]{(byte) hour, (byte) min, (byte) red, (byte) blue, (byte) white, (byte) infrared, (byte) ultraviolet});
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 设置时控校准时间
     *
     * @param id     设备id
     * @param hour   当前小时
     * @param min    当前分钟
     * @param second 当前秒
     */
    public static void setAlarmBase(String id, int hour, int min, int second) {
        byte[] id_cmd = hexString2Bytes(id);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr04_cmd, new byte[]{(byte) hour, (byte) min, (byte) second});
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 设置设备群组id
     *
     * @param soleId  设备id
     * @param groupId 组id
     */
    public static void setLightGroup(String soleId, String groupId) {
        byte[] sole_cmd = hexString2Bytes(soleId);
        byte[] group_cmd = hexString2Bytes(groupId);
        byte[] ctr_cmd = concatAllFirst(sole_cmd, ctr05_cmd, ctr01_cmd, group_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 设置设备群组id
     *
     * @param soleId      设备id
     * @param groupId     组id
     * @param isLimitTime 是否限制频繁操作
     */
    public static void setLightGroup(String soleId, String groupId, boolean isLimitTime) {
        byte[] sole_cmd = hexString2Bytes(soleId);
        byte[] group_cmd = hexString2Bytes(groupId);
        byte[] ctr_cmd = concatAllFirst(sole_cmd, ctr05_cmd, ctr01_cmd, group_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 设置光控数值
     *
     * @param deviceId         设备id
     * @param minLumen         最小区间
     * @param maxLumen         最大区间
     * @param multiInstruction 多路指令
     */
    public static void setLumen(String deviceId, int minLumen, int maxLumen, byte[]... multiInstruction) {
        byte[] id_cmd = hexString2Bytes(deviceId);
        byte[] ctr_cmd = concatAllThird(id_cmd, ctr08_cmd, new byte[]{integer2ByteFront(minLumen), integer2ByteBack(minLumen), integer2ByteFront(maxLumen), integer2ByteBack(maxLumen)}, multiInstruction);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 设置光控数值
     *
     * @param deviceId         设备id
     * @param minLumen         最小区间
     * @param maxLumen         最大区间
     * @param isLimitTime      是否限制频繁操作
     * @param multiInstruction 多路指令
     */
    public static void setLumen(String deviceId, int minLumen, int maxLumen, boolean isLimitTime, byte[]... multiInstruction) {
        byte[] id_cmd = hexString2Bytes(deviceId);
        byte[] ctr_cmd = concatAllThird(id_cmd, ctr08_cmd, new byte[]{integer2ByteFront(minLumen), integer2ByteBack(minLumen), integer2ByteFront(maxLumen), integer2ByteBack(maxLumen)}, multiInstruction);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 设置光控数值
     *
     * @param deviceId    设备id
     * @param minLumen    最小区间
     * @param maxLumen    最大区间
     * @param red         红光
     * @param blue        蓝光
     * @param white       白光
     * @param infrared    红外线
     * @param ultraviolet 紫光
     */
    public static void setLumen(String deviceId, int minLumen, int maxLumen, int red, int blue, int white, int infrared, int ultraviolet) {
        byte[] id_cmd = hexString2Bytes(deviceId);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr08_cmd, new byte[]{
                integer2ByteFront(minLumen), integer2ByteBack(minLumen),
                integer2ByteFront(maxLumen), integer2ByteBack(maxLumen),
                (byte) red, (byte) blue, (byte) white, (byte) infrared, (byte) ultraviolet});//因控制板顺序是红白蓝 故此处顺序如此
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 设置光控数值
     *
     * @param deviceId    设备id
     * @param minLumen    最小区间
     * @param maxLumen    最大区间
     * @param red         红光
     * @param blue        蓝光
     * @param white       白光
     * @param infrared    红外线
     * @param ultraviolet 紫光
     * @param isLimitTime 是否限制频繁操作
     */
    public static void setLumen(String deviceId, int minLumen, int maxLumen, int red, int blue, int white, int infrared, int ultraviolet, boolean isLimitTime) {
        byte[] id_cmd = hexString2Bytes(deviceId);
        byte[] ctr_cmd = concatAllFirst(id_cmd, ctr08_cmd, new byte[]{
                integer2ByteFront(minLumen), integer2ByteBack(minLumen),
                integer2ByteFront(maxLumen), integer2ByteBack(maxLumen),
                (byte) red, (byte) blue, (byte) white, (byte) infrared, (byte) ultraviolet});//因控制板顺序是红白蓝 故此处顺序如此
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

    /**
     * 设置光控群组id
     *
     * @param soleId  设备id
     * @param groupId 组id
     */
    public static void setLumenGroup(String soleId, String groupId) {
        byte[] sole_cmd = hexString2Bytes(soleId);
        byte[] group_cmd = hexString2Bytes(groupId);
        byte[] ctr_cmd = concatAllFirst(sole_cmd, ctr05_cmd, ctr01_cmd, group_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, true);
    }

    /**
     * 设置光控群组id
     *
     * @param soleId      设备id
     * @param groupId     组id
     * @param isLimitTime 是否限制频繁操作
     */
    public static void setLumenGroup(String soleId, String groupId, boolean isLimitTime) {
        byte[] sole_cmd = hexString2Bytes(soleId);
        byte[] group_cmd = hexString2Bytes(groupId);
        byte[] ctr_cmd = concatAllFirst(sole_cmd, ctr05_cmd, ctr01_cmd, group_cmd);
        byte[] len_ctr_cmd = hexStringLen2Bytes(bytes2HexString(ctr_cmd));
        byte[] data = concatAllFirst(prea_cmd, dst_Mac, src_Mac, ctr02_cmd, len_ctr_cmd, ctr_cmd);
        InstructionService.sendInstruction(data, isLimitTime);
    }

}
