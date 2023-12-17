package edu.xmut.examsys.mapper;

import edu.xmut.examsys.bean.ExamAnswerRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【exam_answer_record(答题记录表)】的数据库操作Mapper
 * @createDate 2023-12-14 20:34:13
 * @Entity edu.xmut.examsys.bean.ExamAnswerRecord
 */
public interface ExamAnswerRecordMapper {

    Integer insert(ExamAnswerRecord examAnswerRecord);


    ExamAnswerRecord selectByExamIdAndPaperIdAndUserIdAndQId(
            @Param("examId")
            Long examId,
            @Param("paperId")
            Long paperId,
            @Param("qId")
            String qId,
            @Param("userId")
            Long userId);

    int update(ExamAnswerRecord examAnswerRecordObject);

    List<ExamAnswerRecord> selectByExamIdAndPaperIdAndUserId(
            @Param("examId")
            Long examId,
            @Param("paperId")
            Long paperId,
            @Param("userId")
            Long userId);

}




