package edu.xmut.examsys.service.impl;

import edu.xmut.examsys.bean.Department;
import edu.xmut.examsys.mapper.DepartmentMapper;
import edu.xmut.examsys.service.DeptService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;

/**
 * @author 朔风
 * @date 2023-11-28 14:56
 */
@Service
public class DeptServiceImpl implements DeptService {

    private DepartmentMapper departmentMapper;

    public DeptServiceImpl() {
        departmentMapper = SqlSessionFactoryUtils.openSession(true).getMapper(DepartmentMapper.class);
    }

    @Override
    public Department getById(Integer id) {
        return departmentMapper.selectById(id);
    }
}
