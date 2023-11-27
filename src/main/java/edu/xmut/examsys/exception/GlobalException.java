package edu.xmut.examsys.exception;

import lombok.Data;

/**
 * @author 朔风
 * @date 2023-11-17 14:20
 */

public class GlobalException extends RuntimeException {

    public GlobalException(String message) {
        super(message);
    }

}
