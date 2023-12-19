package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.Score;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.dto.ScoreQueryPageDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.StudentVO;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-12-17 16:46
 */
public interface ScoreService {

    PageVO studentListPages(ScoreQueryPageDTO pageDTO);

    StudentVO getStudentByUserId(Long userId);

    PageVO getAllExam(PageDTO pageDTO);

    List<Score> statisticalScoreInterval(String examId);

    PageVO queryStudentScoreList(long userId, PageDTO pageDTO);
}
