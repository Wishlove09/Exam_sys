package edu.xmut.examsys.mapper;

import edu.xmut.examsys.bean.ExamInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author shuofeng
* @description 针对表【exam_info(考试信息表)】的数据库操作Mapper
* @createDate 2023-11-13 23:08:33
* @Entity edu.xmut.examsys.bean.ExamInfo
*/
public interface ExamInfoMapper {
    List<ExamInfo> selectPage();
}




