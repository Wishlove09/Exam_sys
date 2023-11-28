package edu.xmut.examsys.mapper;

import edu.xmut.examsys.bean.Department;

import java.util.List;

/**
 * @author shuofeng
 * @description 针对表【department(系部表)】的数据库操作Mapper
 * @createDate 2023-11-28 14:51:54
 * @Entity edu.xmut.examsys.bean.Department
 */
public interface DepartmentMapper {

    Department selectById(Integer id);

    List<Department> selectAll();

}




