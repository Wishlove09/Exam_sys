package edu.xmut.examsys.service.impl;

import edu.xmut.examsys.bean.Subject;
import edu.xmut.examsys.mapper.SubjectMapper;
import edu.xmut.examsys.service.SubjectService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-12-19 16:38
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectMapper subjectMapper;

    public SubjectServiceImpl() {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(SqlSessionFactoryUtils.initSqlSessionFactory());
        subjectMapper = sessionTemplate.getMapper(SubjectMapper.class);
    }

    @Override
    public List<Subject> getAll() {
        return subjectMapper.selectAll();
    }
}
