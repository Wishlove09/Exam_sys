package edu.xmut.examsys.mapper;

import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.Question;

/**
* @author shuofeng
* @description 针对表【question(试题主表)】的数据库操作Mapper
* @createDate 2023-11-29 08:28:58
* @Entity edu.xmut.examsys.bean.Question
*/
public interface QuestionMapper  {


    Page<Question> pages(String search);

}




