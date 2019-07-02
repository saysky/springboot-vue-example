package com.example.sens.enums;

/**
 * @author 言曌
 * @date 2019/1/24 下午5:08
 */

public enum ServerStatus {

    /**
     * 空闲的
     */
    FREE("free"),

    /**
     * 使用中
     */
    USED("used");


    private String desc;

    ServerStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
