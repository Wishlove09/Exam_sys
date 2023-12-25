package edu.xmut.examsys.mapper;

import edu.xmut.examsys.bean.UserFaceInfo;
import edu.xmut.examsys.bean.vo.FaceUserInfo;

import java.util.List;

/**
* @author shuofeng
* @description 针对表【user_face_info】的数据库操作Mapper
* @createDate 2023-12-25 19:47:00
* @Entity edu.xmut.examsys.bean.UserFaceInfo
*/
public interface UserFaceInfoMapper {

    List<FaceUserInfo> getByUserId(Long userId);

    void insert(UserFaceInfo userFaceInfo);

    List<UserFaceInfo> getAllByUserId(Long userId);
}




