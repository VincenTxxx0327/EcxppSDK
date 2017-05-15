package com.ecxppsdk.control;

import com.ecxppsdk.utils.ConversionUtils;

/**
 * Author: VincenT
 * Date: 2017/4/24 10:23
 * Contact:qq 328551489
 * Purpose:反馈信息、数据 筛选器
 */
public class FeedbackDataFilter {

    public static final int OLD_LOGIN_FEEDBACK = 0x02;//旧网关登录返回02开头类别
    public static final int OLD_REGISTER_SUCCESS = 0x0A;//旧网关注册返回0A开头类别
    public static final int NEW_REGISTER_SUCCESS = 0x0F;//新网关注册返回0F开头类别
    public static final int NEW_OPERATE_FEEDBACK = 0x0B;//新网关操作返回0B开头类别
    public static final int OLD_GATEWAY_OFFLINE = 0x0B;//旧网关离线返回0B开头类别

    public static final int OPERATE_SUCCESS = 0x01;//操作成功返回码
    public static final int OPERATE_FAILED = 0x00;//操作失败返回码
    public static final int SENSOR_SUCCESS = 0xF1;//操作成功返回码
    public static final int SENSOR_FAILED = 0x00;//操作失败返回码
    public static final int LOGIN_SUCCESS = 0x03;//登录成功返回码
    public static final int LOGIN_FAILED = 0x04;//登录失败返回码
    public static final int REGISTER_SUCCESS = 0x05;//注册成功返回码，注册失败无返回

    public static int getMessage(byte[] feedback) {
        int back = -1;
        if (feedback == null) {
            return back;
        }
        switch (feedback[0]) {//旧网关:通过判断首byte内容，再判断12、13位内容决定返回
            case OLD_LOGIN_FEEDBACK:
                if (feedback.length > 12) {
//                    Log.i("============MSG", "feedback[12]="+feedback[12]);
//                    Log.i("============MSG", "feedback[13]="+feedback[13]);
                    if ((feedback[12] == 0x01) && (feedback[13] == 0x00)) {
                        back = LOGIN_SUCCESS;
                    } else if ((feedback[12] == 0x01) && (feedback[13] == -1)) {
                        back = LOGIN_FAILED;
                    }
                }
                break;
            case OLD_REGISTER_SUCCESS:
            case NEW_REGISTER_SUCCESS:
                back = REGISTER_SUCCESS;
                break;
//            case OLD_GATEWAY_OFFLINE://旧网关则为网关未找到
            case NEW_OPERATE_FEEDBACK://新网关则为操作返回
                if (feedback[0] == Instruction.preb_cmd[0] && feedback[13] == 0x01) {//用户登录
                    if (feedback[feedback.length - 1] == 0x01) {//操作成功
                        back = LOGIN_SUCCESS;
                    } else if (feedback[feedback.length - 1] == 0x00) {//操作失败
                        back = LOGIN_FAILED;
                    }
                } else if (feedback[0] == Instruction.pref_cmd[0] && feedback[13] == 0x02) {//注册APP结点，心跳包 0f 1c887951ea46 f409d861185f 02
                    if (feedback[feedback.length - 1] == 0x01) {//操作成功
                        back = OPERATE_SUCCESS;
                    } else if (feedback[feedback.length - 1] == 0x00) {//操作失败
                        back = OPERATE_FAILED;
                    }
                } else if (feedback[0] == Instruction.preb_cmd[0] && feedback[13] == 0x02) {//转发串口指令 0b f409d861185f 1c887951ea46 02 0c d48a74ee28 06 0000000000
                    if (feedback[14] != 0x00) {///识别码后面不为00则操作成功
                        if (ConversionUtils.bytesSubBytes(feedback, 14)[5] != (byte) 0xFF) {   //非传感器  D48A74EE28 06    0000000000
                            back = OPERATE_SUCCESS;
                        } else {                        //传感器    D48A74EE28 FF 01 1234
                            back = SENSOR_SUCCESS;
                        }
                    } else {//////////////////////////////////////////////识别码后面为00则操作失败
                        back = OPERATE_FAILED;
                    }
                } else if (feedback[0] == Instruction.preb_cmd[0] && feedback[13] == 0x03) {//用户登录 0b f409d861185f 1c887951ea46 01 01 01
                    if (feedback[feedback.length - 1] == 0x01) {//操作成功
                        back = OPERATE_SUCCESS;
                    } else if (feedback[feedback.length - 1] == 0x00) {//操作失败
                        back = OPERATE_FAILED;
                    }
                } else if (feedback[0] == Instruction.preb_cmd[0] && feedback[13] == 0x04) {//用户退出
                    if (feedback[feedback.length - 1] == 0x01) {//操作成功
                        back = OPERATE_SUCCESS;
                    } else if (feedback[feedback.length - 1] == 0x00) {//操作失败
                        back = OPERATE_FAILED;
                    }
                } else if (feedback[0] == Instruction.preb_cmd[0] && feedback[13] == 0x05) {//无线上网配置（局域网指令） 0b f409d861185f 1c887951ea46 05 01 01
                    if (feedback[feedback.length - 1] == 0x01) {//操作成功
                        back = OPERATE_SUCCESS;
                    } else if (feedback[feedback.length - 1] == 0x00) {//操作失败
                        back = OPERATE_FAILED;
                    }
                }
                break;
        }
        return back;
    }
}
