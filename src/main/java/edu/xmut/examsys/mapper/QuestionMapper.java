package edu.xmut.examsys.mapper;

import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【question(试题主表)】的数据库操作Mapper
 * @createDate 2023-11-29 08:28:58
 * @Entity edu.xmut.examsys.bean.Question
 */
public interface QuestionMapper {


    Integer insert(Question question);

    Question selectById(String id);

    List<Question> selectByIdsBatch(
            @Param("ids")
            List<String> ids);

    Page<Question> pages(
            @Param("searchContent")
            String searchContent,
            @Param("searchType")
            Integer searchType,
            @Param("searchSubject")
            Integer searchSubject);
}




