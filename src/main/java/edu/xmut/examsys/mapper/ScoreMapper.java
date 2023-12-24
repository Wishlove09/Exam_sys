package edu.xmut.examsys.mapper;

import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.Score;
import edu.xmut.examsys.bean.vo.StudentScoreVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【score(成绩表)】的数据库操作Mapper
 * @createDate 2023-12-17 15:49:19
 * @Entity edu.xmut.examsys.bean.Score
 */
public interface ScoreMapper {

    Page<StudentScoreVO> selectByUserIdAndTitle(
            @Param("userId")
            Long userId,
            @Param("title")
            String title);


    List<Score> statisticalScoreInterval(String examId);

    Page<Score> selectByUserId(long userId);

    int insert(Score score);

    Score selectByUserIdAndExamId(
            @Param("userId")
            Long userId,
            @Param("examId")
            Long examId);
}




