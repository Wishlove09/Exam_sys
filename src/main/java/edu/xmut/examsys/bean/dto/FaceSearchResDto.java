package edu.xmut.examsys.bean.dto;


import lombok.Data;

@Data
public class FaceSearchResDto {
    private String faceId;
    private Float similarValue;
    private Integer age;
    private String gender;
    private String image;

}
