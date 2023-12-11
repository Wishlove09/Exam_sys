package edu.xmut.examsys.mapper;

import edu.xmut.examsys.bean.ExamRecord;
import org.apache.ibatis.annotations.Param;

/**
 * @author shuofeng
 * @description 针对表【exam_record(考试记录表)】的数据库操作Mapper
 * @createDate 2023-12-11 21:55:42
 * @Entity edu.xmut.examsys.bean.ExamRecord
 */
public interface ExamRecordMapper {

    ExamRecord selectByExamIdAndUserId(
            @Param("examId")
            Long id,
            @Param("userId")
            Long userId);
}




