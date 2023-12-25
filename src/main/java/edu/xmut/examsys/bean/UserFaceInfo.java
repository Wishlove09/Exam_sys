package edu.xmut.examsys.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user_face_info
 */
@Data
public class UserFaceInfo implements Serializable {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 面部id
     */
    private String faceId;

    /**
     * 人脸图像
     */
    private String faceImage;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 面部特征
     */
    private byte[] faceFeature;

    private static final long serialVersionUID = 1L;
}