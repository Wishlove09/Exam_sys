package edu.xmut.examsys.service;

import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.PageVO;

/**
 * @author 朔风
 * @date 2023-11-27 20:36
 */
public interface StudentService {

    PageVO getAllStudent(PageDTO pageDTO);

}
