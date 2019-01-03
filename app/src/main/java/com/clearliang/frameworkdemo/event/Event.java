package com.clearliang.frameworkdemo.event;

/**
 * Created by 99794 on 2018/4/13.
 */
public class Event<T> {
    private int code;
    private T data;

    public Event(T data) {
        this.data = data;
    }

    public Event(int code) {
        this.code = code;
    }

    public Event(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}