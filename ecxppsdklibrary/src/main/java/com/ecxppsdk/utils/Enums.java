package com.ecxppsdk.utils;


import com.ecxppsdk.R;

import org.xutils.x;

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

        private LightType(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public void setId(Integer id) {
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

}
