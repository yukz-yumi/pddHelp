package com.yukz.daodaoping.pddhelp.enums;

import java.util.HashMap;
import java.util.Map;

public enum  EnumYesOrNo {
    YES("1", "是"),NO("0", "否");
    private String code;

    private String name;

    EnumYesOrNo(String code, String name) {

        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumYesOrNo tradeType : values()) {
            enumDataMap.put(tradeType.getCode(), tradeType.getName());
        }
        return enumDataMap;
    }

    public static Map<String, String> toOppositeMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (EnumYesOrNo tradeType : values()) {
            enumDataMap.put(tradeType.getName(),tradeType.getCode());
        }
        return enumDataMap;
    }
}
