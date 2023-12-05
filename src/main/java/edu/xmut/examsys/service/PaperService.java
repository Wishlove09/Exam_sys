package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.PageVO;

/**
 * @author 朔风
 * @date 2023-12-04 22:11
 */
public interface PaperService {
    PageVO pages(PageDTO pageDTO);
}
