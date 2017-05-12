package com.ecxppsdk.utils;


import com.ecxppsdk.R;

import org.xutils.x;

import java.util.Locale;

/**
 * Author: VincenT
 * Date: 2017/5/8 15:12
 * Contact:qq 328551489
 * Purpose:此类用于枚举类型
 */

public class Enums {

    /**
     * 获得灯类
     *
     * @author jack
     */
    public static enum LightType {

        Base(0, x.app().getResources().getString(R.string.base_light)),
        Red(1, x.app().getResources().getString(R.string.red_light)),
        Blue(2, x.app().getResources().getString(R.string.blue_light)),
        White(3, x.app().getResources().getString(R.string.white_light)),
        Infrared(4, x.app().getResources().getString(R.string.infrared_light)),
        Ultraviolet(5, x.app().getResources().getString(R.string.ultraviolet_light));

        private int id;
        private String name;

        private LightType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static LightType getLightType(int id) {
            for (LightType ut : values()) {
                if (ut.getId() == id)
                    return ut;
            }
            return Red;
        }

    }

    /**
     * 获得host父类项目
     *
     * @author jack
     */
    public static enum HostType {

        ZWBG(1, x.app().getResources().getString(R.string.hostType_ZWBG)),
        YCSJ(2, x.app().getResources().getString(R.string.hostType_YCSJ)),
        DLZM(3, x.app().getResources().getString(R.string.hostType_DLZM));

        private int id;
        private String name;

        private HostType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static HostType getLightType(int id) {
            for (HostType ut : values()) {
                if (ut.getId() == id)
                    return ut;
            }
            return ZWBG;
        }

    }

    /**
     * 获得当前语言包
     *
     * @author jack
     */
    public static enum LanguageType {

        ZH_CN(1, "简体中文", Locale.SIMPLIFIED_CHINESE),
        ZH_TW(2, "繁體中文", Locale.TRADITIONAL_CHINESE),
        EN_US(3, "English", Locale.ENGLISH),
        JA_JP(4, "日本語", Locale.JAPANESE),
        ES_ES(5, "Español", new Locale("spa","es","ES"));

        private int id;
        private String name;
        private Locale locale;

        private LanguageType(int id, String name, Locale locale) {
            this.id = id;
            this.name = name;
            this.locale = locale;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Locale getLocale() {
            return locale;
        }

        public void setLocale(Locale locale) {
            this.locale = locale;
        }

        public static LanguageType getLanguageType(int id) {
            for (LanguageType ut : values()) {
                if (ut.getId() == id)
                    return ut;
            }
            return ZH_CN;
        }

    }

}
