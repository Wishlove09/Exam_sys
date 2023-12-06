package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.dto.PageInfoDTO;
import edu.xmut.examsys.bean.dto.PaperDetailsDTO;
import edu.xmut.examsys.bean.vo.PageInfoVO;
import edu.xmut.examsys.bean.vo.PageVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 朔风
 * @date 2023-12-04 22:11
 */
public interface PaperService {
    PageVO pages(PageDTO pageDTO);

    Boolean addPaper(PaperDetailsDTO paperDetailsDTO, HttpServletRequest request);

    Boolean updateWithStatus(PageInfoDTO pageInfoDTO);

    List<PageInfoVO> getAllBySearch(String search);
}
