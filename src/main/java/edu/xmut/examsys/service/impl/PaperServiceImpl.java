package edu.xmut.examsys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.xmut.examsys.bean.PaperInfo;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.PageVO;
import edu.xmut.examsys.bean.vo.PaperInfoVO;
import edu.xmut.examsys.mapper.PaperInfoMapper;
import edu.xmut.examsys.mapper.UserMapper;
import edu.xmut.examsys.service.PaperService;
import edu.xmut.examsys.utils.SqlSessionFactoryUtils;
import fun.shuofeng.myspringmvc.annotaion.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 朔风
 * @date 2023-12-04 22:11
 */
@Service
public class PaperServiceImpl implements PaperService {

    private PaperInfoMapper paperInfoMapper;
    private UserMapper userMapper;

    public PaperServiceImpl() {
        paperInfoMapper = SqlSessionFactoryUtils.openSession(true).getMapper(PaperInfoMapper.class);
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
}
