package edu.xmut.examsys.bean.vo;

import lombok.Data;

/**
 * 用户面部信息
 */

@Data
public class FaceUserInfo {

    private String faceId;
    private Float similarValue;
    private byte[] faceFeature;

}
