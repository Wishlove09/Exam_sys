package edu.xmut.examsys.mapper;

import edu.xmut.examsys.bean.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【teacher(教师表)】的数据库操作Mapper
 * @createDate 2023-11-13 16:57:55
 * @Entity edu.xmut.examsys.bean.Teacher
 */
public interface TeacherMapper {

    List<Teacher> getAll();

    Teacher getByTno(Long tno);

    Integer insertTeacher(Teacher teacher);


}




