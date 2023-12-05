package edu.xmut.examsys.mapper;

import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.PaperInfo;

/**
 * @author shuofeng
 * @description 针对表【paper_info(试卷信息表)】的数据库操作Mapper
 * @createDate 2023-11-30 10:44:34
 * @Entity edu.xmut.examsys.bean.PaperInfo
 */
public interface PaperInfoMapper {

    Page<PaperInfo> pages(String search);

    Integer insert(PaperInfo paperInfo);
}




