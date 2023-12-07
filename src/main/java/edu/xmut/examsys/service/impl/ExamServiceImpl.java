package edu.xmut.examsys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xmut.examsys.bean.ClazzStudent;
import edu.xmut.examsys.bean.ExamInfo;
import edu.xmut.examsys.bean.ExamParticipants;
import edu.xmut.examsys.bean.dto.ExamAddDTO;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.ExamInfoVO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.constants.SystemConstant;
import edu.xmut.examsys.mapper.ClazzMapper;
import edu.xmut.examsys.mapper.ExamInfoMapper;
import edu.xmut.examsys.mapper.ExamParticipantsMapper;
import edu.xmut.examsys.mapper.UserMapper;
import edu.xmut.examsys.service.ExamService;
import edu.xmut.examsys.utils.JwtTokenUtil;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    private final ExamParticipantsMapper examParticipantsMapper;
    private final ClazzMapper clazzMapper;
    private final UserMapper userMapper;

    public ExamServiceImpl() {
        sqlSession = SqlSessionFactoryUtils.openSession(true);
        examInfoMapper = sqlSession.getMapper(ExamInfoMapper.class);
        userMapper = sqlSession.getMapper(UserMapper.class);
        examParticipantsMapper = sqlSession.getMapper(ExamParticipantsMapper.class);
        clazzMapper = sqlSession.getMapper(ClazzMapper.class);
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

    @Override
    public Boolean addExam(ExamAddDTO examAddDTO, HttpServletRequest request) {
        ExamInfo examInfo = new ExamInfo();
        BeanUtil.copyProperties(examAddDTO, examInfo);
        examInfo.setExamId(System.currentTimeMillis());
        String token = request.getHeader(SystemConstant.AUTHORIZATION);
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String userId = jwtTokenUtil.getUserId(token);
        examInfo.setCreator(Long.valueOf(userId));
        examInfo.setStartTime(examAddDTO.getExamDate().get(0));
        examInfo.setEndTime(examAddDTO.getExamDate().get(1));

        Integer r = examInfoMapper.insert(examInfo);

        return r > 0;
    }

    @Override
    public PageVO listByUser(PageDTO pageDTO, String userId) {

        // 查询学生所在的班级列表
        List<ClazzStudent> classList = clazzMapper.selectCStById(Long.valueOf(userId));

        if (classList.isEmpty()) {
            return PageVO.builder()
                    .pageNum(pageDTO.getPageNum())
                    .pageSize(pageDTO.getPageSize())
                    .total(0L)
                    .records(new ArrayList())
                    .build();
        }

        // 查询关联班级的考试id
        List<ExamParticipants> examParticipants = new ArrayList<>();
        for (ClazzStudent clazzStudent : classList) {
            examParticipants.addAll(examParticipantsMapper.selectByClassId(clazzStudent));
        }
        List<Long> examIds = examParticipants.stream()
                .map(ExamParticipants::getExamId)
                .collect(Collectors.toList());

        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        // 根据考试id查询所有考试
        Page<ExamInfo> page = examInfoMapper.selectById(examIds,pageDTO.getSearch());

        List<ExamInfoVO> collect = page.getResult().stream()
                .map(examInfo -> {
                    ExamInfoVO examInfoVO = new ExamInfoVO();
                    BeanUtil.copyProperties(examInfo, examInfoVO);
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
