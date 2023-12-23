package edu.xmut.examsys.bean.vo;

import lombok.Data;

@Data
public class FaceUserInfo {

    private String faceId;
    private String name;
    private Integer similarValue;
    private byte[] faceFeature;

}
