package edu.xmut.examsys.mapper;

import edu.xmut.examsys.bean.ClazzStudent;
import edu.xmut.examsys.bean.ExamParticipants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【exam_participants】的数据库操作Mapper
 * @createDate 2023-12-07 09:30:04
 * @Entity edu.xmut.examsys.bean.ExamParticipants
 */
public interface ExamParticipantsMapper {

    List<ExamParticipants> selectByClassId(ClazzStudent clazzStudent);

    Integer insertBatch(
            @Param("examId")
            Long examId,
            @Param("classIds")
            List<Long> crowds);
}




