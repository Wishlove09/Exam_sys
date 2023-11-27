package edu.xmut.examsys.mapper;

import edu.xmut.examsys.bean.Clazz;
import edu.xmut.examsys.bean.ClazzStudent;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【clazz(班级表)】的数据库操作Mapper
 * @createDate 2023-11-27 21:08:15
 * @Entity edu.xmut.examsys.bean.Clazz
 */
public interface ClazzMapper {

    ClazzStudent selectById(Long userId);

    Clazz selectClazzById(Long clazzId);

    List<Clazz> selectAll();

}




