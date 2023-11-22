package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.Teacher;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【teacher(教师表)】的数据库操作Service
 * @createDate 2023-11-13 16:57:55
 */
public interface TeacherService {
    List<Teacher> getAll();

    Teacher login(Long tno, String password);

    Boolean register(Teacher teacher);


}
