package edu.xmut.examsys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xmut.examsys.bean.PaperInfo;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.dto.PageInfoDTO;
import edu.xmut.examsys.bean.dto.PaperDetailsDTO;
import edu.xmut.examsys.bean.vo.PageInfoVO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.PaperInfoVO;
import edu.xmut.examsys.constants.SystemConstant;
import edu.xmut.examsys.mapper.PaperInfoMapper;
import edu.xmut.examsys.mapper.PaperQuestionMapper;
import edu.xmut.examsys.mapper.UserMapper;
import edu.xmut.examsys.service.PaperService;
import edu.xmut.examsys.utils.JwtTokenUtil;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 朔风
 * @date 2023-12-04 22:11
 */
@Service
public class PaperServiceImpl implements PaperService {

    private final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
    private final SqlSession sqlSession;


    private PaperInfoMapper paperInfoMapper;
    private PaperQuestionMapper paperQuestionMapper;
    private UserMapper userMapper;

    public PaperServiceImpl() {
        sqlSession = SqlSessionFactoryUtils.openSession(false);
        paperQuestionMapper = sqlSession.getMapper(PaperQuestionMapper.class);
        paperInfoMapper = sqlSession.getMapper(PaperInfoMapper.class);
        userMapper = SqlSessionFactoryUtils.openSession(true).getMapper(UserMapper.class);
    }

    @Override
    public PageVO pages(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<PaperInfo> pages = paperInfoMapper.pages(pageDTO.getSearch());

        List<PaperInfoVO> collect = pages.stream().map(paperInfo -> {
            PaperInfoVO paperInfoVO = new PaperInfoVO();
            paperInfoVO.setId(paperInfo.getId());
            paperInfoVO.setStatus(paperInfo.getStatus());
            paperInfoVO.setDesc(paperInfo.getDesc());
            paperInfoVO.setTitle(paperInfo.getTitle());
            paperInfoVO.setTotal(paperInfo.getTotalScore());
            paperInfoVO.setCreator(userMapper.selectById(paperInfo.getCreator()).getRealName());
            return paperInfoVO;
        }).collect(Collectors.toList());


        return PageVO.builder()
                .pageNum(pages.getPageNum())
                .pageSize(pages.getPageSize())
                .records(collect)
                .total(pages.getTotal())
                .build();
    }

    @Override
    public Boolean addPaper(PaperDetailsDTO paperDetailsDTO, HttpServletRequest request) {
        Integer singleCount = paperDetailsDTO.getSingleCount();
        Integer multiCount = paperDetailsDTO.getMultiCount();
        Integer judgeCount = paperDetailsDTO.getJudgeCount();
        Integer fillCount = paperDetailsDTO.getFillCount();

        PaperInfo paperInfo = new PaperInfo();
        paperInfo.setId(System.currentTimeMillis());
        BeanUtil.copyProperties(paperDetailsDTO, paperInfo);
        paperInfo.setTitle(paperDetailsDTO.getTitle());
        paperInfo.setDesc(paperDetailsDTO.getDesc());
        paperInfo.setTotalScore(paperDetailsDTO.getTotalScore());
        paperInfo.setRadioCount(singleCount);
        paperInfo.setRadioScore(paperDetailsDTO.getSingleScore());
        paperInfo.setMultiCount(multiCount);
        paperInfo.setMultiScore(paperDetailsDTO.getMultiScore());
        paperInfo.setJudgeCount(judgeCount);
        paperInfo.setJudgeScore(paperDetailsDTO.getJudgeScore());
        paperInfo.setFillCount(fillCount);
        paperInfo.setFillScore(paperDetailsDTO.getFillScore());
        String token = request.getHeader(SystemConstant.AUTHORIZATION);
        paperInfo.setCreator(Long.valueOf(jwtTokenUtil.getUserId(token)));
        List<PaperDetailsDTO.QuestionIdsDTO> collect = paperDetailsDTO.getQuestionIds().stream()
                .peek(questionIdsDTO -> {
                    if (questionIdsDTO.getType() == 0) {
                        questionIdsDTO.setScore(paperDetailsDTO.getSingleScore());
                    } else if (questionIdsDTO.getType() == 1) {
                        questionIdsDTO.setScore(paperDetailsDTO.getMultiScore());
                    } else if (questionIdsDTO.getType() == 2) {
                        questionIdsDTO.setScore(paperDetailsDTO.getJudgeScore());
                    } else if (questionIdsDTO.getType() == 3) {
                        questionIdsDTO.setScore(paperDetailsDTO.getFillScore());
                    }
                }).collect(Collectors.toList());

        Integer r1 = paperInfoMapper.insert(paperInfo);
        Integer r2 = paperQuestionMapper.insert(paperInfo.getId(), collect);

        sqlSession.commit();
        return r1 > 0 && r2 > 0;
    }

    @Override
    public Boolean updateWithStatus(PageInfoDTO pageInfoDTO) {
        Integer result = paperInfoMapper.update(pageInfoDTO);
        sqlSession.commit();
        return result > 0;
    }

    @Override
    public List<PageInfoVO> getAllBySearch(String search) {

        Page<PaperInfo> pages = paperInfoMapper.pages(search);
        List<PaperInfo> list = pages.getResult();
        return list.stream()
                .filter(paperInfo -> paperInfo.getStatus() == 1)
                .map(paperInfo -> PageInfoVO.builder()
                        .id(paperInfo.getId())
                        .title(paperInfo.getTitle())
                        .desc(paperInfo.getDesc())
                        .totalScore(paperInfo.getTotalScore())
                        .build()).collect(Collectors.toList());
    }
}
