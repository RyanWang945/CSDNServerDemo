package com.ryan.entity;

/**
 * @author Ryan
 * @date 2020/7/10 8:43
 */
public class Result {
    private Boolean flag;
    private Integer code;
    private String message;
    private Object data;

    public Result() {
    }
    public Result(Boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
    public Result(Boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 获取
     * @return flag
     */
    public Boolean getFlag() {
        return flag;
    }

    /**
     * 设置
     * @param flag
     */
    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    /**
     * 获取
     * @return code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置
     * @param code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取
     * @return data
     */
    public Object getData() {
        return data;
    }

    /**
     * 设置
     * @param data
     */
    public void setData(Object data) {
        this.data = data;
    }

    public String toString() {
        return "entity{flag = " + flag + ", code = " + code + ", message = " + message + ", data = " + data + "}";
    }
}
