package edu.xmut.examsys.service;


import edu.xmut.examsys.bean.Student;

/**
* @author shuofeng
* @description 针对表【student(学生表)】的数据库操作Service
* @createDate 2023-11-13 11:52:40
*/
public interface StudentService {
    Student login(Long sno, String password);
}
