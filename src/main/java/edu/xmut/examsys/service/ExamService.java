package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.ExamInfo;
import edu.xmut.examsys.bean.dto.ExamAddDTO;
import edu.xmut.examsys.bean.dto.ExamInfoDTO;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.ExamDetailsVO;
import edu.xmut.examsys.bean.vo.ExamInfoVO;
import edu.xmut.examsys.bean.vo.PageVO;
import org.quartz.SchedulerException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 朔风
 * @date 2023-12-06 21:23
 */
public interface ExamService {
    PageVO pages(PageDTO pageDTO, Long userId);

    Boolean addExam(ExamAddDTO examAddDTO, HttpServletRequest request);

    PageVO listByUser(PageDTO pageDTO, String userId);

    ExamDetailsVO getDetailsById(String examId);

    ExamInfoVO getExamInfoById(String examId);

    Boolean update(ExamInfoDTO examInfoDTO) throws SchedulerException;

    Boolean deleteById(String id);
}
