package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.Department;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-28 14:55
 */
public interface DeptService {
    Department getById(Integer id);

    List<Department> getAll();

}
