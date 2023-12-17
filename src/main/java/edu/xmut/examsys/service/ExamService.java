package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.dto.*;
import edu.xmut.examsys.bean.vo.*;
import org.quartz.SchedulerException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 朔风
 * @date 2023-12-06 21:23
 */
public interface ExamService {
    PageVO pages(PageDTO pageDTO, Long userId);

    Boolean addExam(ExamAddDTO examAddDTO, HttpServletRequest request);

    PageVO listByUser(PageDTO pageDTO, Long userId);

    ExamDetailsVO getDetailsById(String examId);

    ExamInfoVO getExamInfoById(String examId);

    Boolean update(ExamInfoDTO examInfoDTO) throws SchedulerException;

    Boolean deleteById(String id);

    Boolean checkExamPermission(Long id, Long userId);

    StartExamVO startExam(Long examId, Long userId);

    Integer checkExamIsStartOrEnd(long examId);

    ExamQuestionDetailVO getQuestionDetailByQid(String qid);

    Boolean saveAnswer(ExamSaveDTO examSaveDTO, HttpServletRequest request);

    ExamAnsweredVO getAnswered(ExamAnsweredQueryDTO examAnsweredQueryDTO, HttpServletRequest request);

    boolean submitExam(ExamSubmitDTO examSubmitDTO, HttpServletRequest request);
}
