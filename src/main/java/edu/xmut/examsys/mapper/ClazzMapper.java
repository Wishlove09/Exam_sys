package edu.xmut.examsys.mapper;

import com.github.pagehelper.Page;
import edu.xmut.examsys.bean.Clazz;
import edu.xmut.examsys.bean.ClazzStudent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【clazz(班级表)】的数据库操作Mapper
 * @createDate 2023-11-27 21:08:15
 * @Entity edu.xmut.examsys.bean.Clazz
 */
public interface ClazzMapper {

    List<ClazzStudent> selectCStById(Long userId);

    Clazz selectClazzById(Long clazzId);

    List<Clazz> selectAll();

    Integer insertCS(@Param("clazzId") Long clazzId,
                     @Param("userId") Long id);

    Integer updateCS(@Param("clazzId") Long clazzId,
                     @Param("userId") Long id);

    Integer insertClazz(Clazz clazz);

    Page<Clazz> pages();

    Integer countByClazzId(Long clazzId);

    Integer deleteById(Long id);
}




