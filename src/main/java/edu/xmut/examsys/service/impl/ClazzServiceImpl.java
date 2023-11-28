package edu.xmut.examsys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xmut.examsys.bean.Clazz;
import edu.xmut.examsys.bean.Department;
import edu.xmut.examsys.bean.dto.ClazzDTO;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.ClazzVO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.mapper.ClazzMapper;
import edu.xmut.examsys.mapper.DepartmentMapper;
import edu.xmut.examsys.mapper.UserMapper;
import edu.xmut.examsys.service.ClazzService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 朔风
 * @date 2023-11-27 22:04
 */
@Service
public class ClazzServiceImpl implements ClazzService {

    private DepartmentMapper departmentMapper;
    private ClazzMapper clazzMapper;
    private UserMapper userMapper;

    public ClazzServiceImpl() {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSession(true);
        clazzMapper = sqlSession.getMapper(ClazzMapper.class);
        departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @Override
    public List<Clazz> getAll() {
        return clazzMapper.selectAll();
    }

    @Override
    public Boolean add(ClazzDTO clazzDTO) {
        Clazz clazz = new Clazz();
        clazz.setClassName(clazzDTO.getClassName());
        clazz.setId(clazzDTO.getClassId());
        clazz.setDeptId(clazzDTO.getDeptId());
        Integer result = clazzMapper.insertClazz(clazz);
        return result > 0;
    }

    @Override
    public PageVO pages(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<Clazz> clazzPage = clazzMapper.pages();
        List<ClazzVO> collect = clazzPage.getResult().stream()
                .map(clazz -> {
                    Integer count = clazzMapper.countByClazzId(Math.toIntExact(clazz.getId()));
                    Department department = departmentMapper.selectById(clazz.getDeptId());
                    ClazzVO clazzVO = new ClazzVO();
                    clazzVO.setCount(count);
                    clazzVO.setClassId(clazz.getId());
                    clazzVO.setClassName(clazz.getClassName());
                    if (Objects.nonNull(department)) {
                        clazzVO.setDeptName(department.getDeptName());
                    }
                    return clazzVO;
                }).collect(Collectors.toList());

        return PageVO.builder()
                .pageNum(clazzPage.getPageNum())
                .pageSize(clazzPage.getPageSize())
                .total(clazzPage.getTotal())
                .records(collect)
                .build();
    }
}
