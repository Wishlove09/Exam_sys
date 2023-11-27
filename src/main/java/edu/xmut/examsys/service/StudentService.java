package edu.xmut.examsys.service;

import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.dto.StudentDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.StudentVO;

/**
 * @author 朔风
 * @date 2023-11-27 20:36
 */
public interface StudentService {

    PageVO getAllStudent(PageDTO pageDTO);

    StudentVO getById(Long id);

    Boolean update(StudentDTO studentDTO);

    Boolean updateStatus(Long id);
}
