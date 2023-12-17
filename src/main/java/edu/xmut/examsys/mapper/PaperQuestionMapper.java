package edu.xmut.examsys.mapper;

import edu.xmut.examsys.bean.PaperQuestion;
import edu.xmut.examsys.bean.dto.PaperDetailsDTO;
import edu.xmut.examsys.bean.dto.QuestionBatchDTO;
import edu.xmut.examsys.bean.vo.QuestionDetailsVO;
import edu.xmut.examsys.bean.vo.QuestionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【paper_question(试卷试题关系表)】的数据库操作Mapper
 * @createDate 2023-12-05 19:44:41
 * @Entity edu.xmut.examsys.bean.PaperQuestion
 */
public interface PaperQuestionMapper {

    Integer insert(
            @Param("pid")
            Long pid,
            @Param("questionList")
            List<PaperDetailsDTO.QuestionIdsDTO> questionList);

    List<PaperQuestion> selectQIdByPId(@Param("pid") Long paperId);
}




