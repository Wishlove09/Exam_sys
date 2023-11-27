package edu.xmut.examsys.service.impl;

import edu.xmut.examsys.bean.Clazz;
import edu.xmut.examsys.mapper.ClazzMapper;
import edu.xmut.examsys.service.ClazzService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-27 22:04
 */
@Service
public class ClazzServiceImpl implements ClazzService {

    private ClazzMapper clazzMapper;

    public ClazzServiceImpl() {
        clazzMapper = SqlSessionFactoryUtils.openSession(true).getMapper(ClazzMapper.class);
    }

    @Override
    public List<Clazz> getAll() {
        return clazzMapper.selectAll();
    }
}
