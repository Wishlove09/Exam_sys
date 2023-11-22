package edu.xmut.examsys.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import edu.xmut.examsys.bean.Student;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.StudentVO;

/**
* @author shuofeng
* @description 针对表【student(学生表)】的数据库操作Service
* @createDate 2023-11-13 11:52:40
*/
public interface StudentService {
    Student login(Long sno, String password);

    PageVO<StudentVO> page(Integer pageNum, Integer pageSize);

    Boolean register(Student student);
}
