package edu.xmut.examsys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xmut.examsys.bean.ExamInfo;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.ExamInfoVO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.mapper.ExamInfoMapper;
import edu.xmut.examsys.mapper.UserMapper;
import edu.xmut.examsys.service.ExamService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 朔风
 * @date 2023-12-06 21:23
 */
@Service
public class ExamServiceImpl implements ExamService {

    private final SqlSession sqlSession;
    private final ExamInfoMapper examInfoMapper;
    private final UserMapper userMapper;

    public ExamServiceImpl() {
        sqlSession = SqlSessionFactoryUtils.openSession(true);
        examInfoMapper = sqlSession.getMapper(ExamInfoMapper.class);
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @Override
    public PageVO pages(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<ExamInfo> page = examInfoMapper.pages();

        List<ExamInfoVO> collect = page.getResult().stream()
                .map(examInfo -> {
                    ExamInfoVO examInfoVO = new ExamInfoVO();
                    BeanUtil.copyProperties(examInfo, examInfoVO);
                    examInfoVO.setCreator(userMapper.selectById(examInfo.getCreator()).getRealName());
                    return examInfoVO;
                }).collect(Collectors.toList());


        return PageVO.builder()
                .pageNum(page.getPageNum())
                .pageSize(page.getPageSize())
                .total(page.getTotal())
                .records(collect)
                .build();
    }
}
