package edu.xmut.examsys.service;

import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.vo.FaceUserInfo;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author 朔风
 * @date 2023-12-23 22:54
 */
public interface FaceEngineService {
    List<FaceInfo> detectFaces(ImageInfo imageInfo);

    List<User> process(ImageInfo imageInfo);

    /**
     * 人脸特征
     * @param imageInfo
     * @return
     */
    byte[] extractFaceFeature(ImageInfo imageInfo) throws InterruptedException;

    /**
     * 人脸比对
     * @param groupId
     * @param faceFeature
     * @return
     */
    List<FaceUserInfo> compareFaceFeature(byte[] faceFeature, Integer groupId) throws InterruptedException, ExecutionException;


}
