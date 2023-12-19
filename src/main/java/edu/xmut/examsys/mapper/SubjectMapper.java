package edu.xmut.examsys.mapper;

import edu.xmut.examsys.bean.Subject;

import java.util.List;

/**
* @author shuofeng
* @description 针对表【subject(学科表)】的数据库操作Mapper
* @createDate 2023-12-19 16:24:55
* @Entity edu.xmut.examsys.bean.Subject
*/
public interface SubjectMapper {

    Subject selectById(Long subjectId);

    List<Subject> selectAll();
}




