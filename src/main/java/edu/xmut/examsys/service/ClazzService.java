package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.Clazz;
import edu.xmut.examsys.bean.dto.ClazzDTO;
import edu.xmut.examsys.bean.dto.PageDTO;
import edu.xmut.examsys.bean.vo.ClazzVO;
import edu.xmut.examsys.bean.vo.PageVO;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-27 22:03
 */
public interface ClazzService {


    List<Clazz> getAll();

    Boolean add(ClazzDTO clazzDTO);

    PageVO pages(PageDTO pageDTO);

    Boolean deleteById(Long id);

    List<ClazzVO> search(String q);
}
