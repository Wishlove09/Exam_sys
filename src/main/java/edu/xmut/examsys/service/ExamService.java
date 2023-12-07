package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.dto.ExamAddDTO;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.PageVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 朔风
 * @date 2023-12-06 21:23
 */
public interface ExamService {
    PageVO pages(PageDTO pageDTO);

    Boolean addExam(ExamAddDTO examAddDTO, HttpServletRequest request);

    PageVO listByUser(PageDTO pageDTO, String userId);
}
