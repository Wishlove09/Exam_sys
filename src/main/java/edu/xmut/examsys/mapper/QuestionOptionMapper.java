package edu.xmut.examsys.mapper;

import edu.xmut.examsys.bean.QuestionOption;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【question_option(试题选项表)】的数据库操作Mapper
 * @createDate 2023-11-29 11:46:42
 * @Entity edu.xmut.examsys.bean.QuestionOption
 */
public interface QuestionOptionMapper {

    Integer addOption(QuestionOption questionOption);


    List<QuestionOption> selectByQid(String qid);

    QuestionOption selectById(String id);

    List<QuestionOption> selectByIds(
            @Param("ids")
            List<String> ids);
}




