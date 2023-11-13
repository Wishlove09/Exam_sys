package edu.xmut.examsys.mapper;


import edu.xmut.examsys.bean.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author shuofeng
 * @description 针对表【student(学生表)】的数据库操作Mapper
 * @createDate 2023-11-13 11:52:40
 * @Entity edu.xmut.examsys.bean.Student
 */
@Mapper
public interface StudentMapper {
    Student getBySno(Long sno);

}




