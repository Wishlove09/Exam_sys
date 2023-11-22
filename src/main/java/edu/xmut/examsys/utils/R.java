package edu.xmut.examsys.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 朔风
 * @date 2023-11-12 20:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {
    private Integer code;

    private String message;

    private T data;

    public static <T> R ok() {
        return new R(200, "成功", null);
    }

    public static <T> R ok(Integer code) {
        return new R(code, "成功", null);
    }

    public static <T> R ok(T data) {
        return new R(200, "成功", data);
    }

    public static <T> R ok(String message) {
        return new R(200, message, null);
    }

    public static R fail() {
        return new R(400, "失败", null);
    }

    public static R fail(String message) {
        return new R(400, message, null);
    }


    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R data(T data) {
        this.setData(data);
        return this;
    }


}
