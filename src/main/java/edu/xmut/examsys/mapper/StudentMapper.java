package edu.xmut.examsys.mapper;


import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【student(学生表)】的数据库操作Mapper
 * @createDate 2023-11-13 11:52:40
 * @Entity edu.xmut.examsys.bean.Student
 */
@Mapper
public interface StudentMapper {
    Student getBySno(Long sno);

    Page<Student> selectPage();

    Integer insertStudent(Student student);

}




