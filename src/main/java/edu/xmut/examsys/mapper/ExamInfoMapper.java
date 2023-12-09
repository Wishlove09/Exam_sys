package edu.xmut.examsys.mapper;

import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.ExamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【exam_info(考试信息表)】的数据库操作Mapper
 * @createDate 2023-12-06 21:23:01
 * @Entity edu.xmut.examsys.bean.ExamInfo
 */
public interface ExamInfoMapper {

    Page<ExamInfo> pages(Long creator);

    Integer insert(ExamInfo examInfo);

    Page<ExamInfo> selectByIds(
            @Param("examIds")
            List<Long> examIds,
            @Param("search")
            String search);

    ExamInfo selectById(Long id);

    List<ExamInfo> selectAll();

    Integer updateStatus(ExamInfo examInfo);

}




