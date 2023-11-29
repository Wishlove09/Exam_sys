package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.User;
import edu.xmut.examsys.bean.vo.TeacherVO;

/**
 * @author 朔风
 * @date 2023-11-29 09:07
 */
public interface TeacherService {

    TeacherVO getById(Long id);

}
