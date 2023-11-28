package edu.xmut.examsys.service;

import edu.xmut.examsys.bean.Clazz;
import edu.xmut.examsys.bean.dto.ClazzDTO;

import java.util.List;

/**
 * @author 朔风
 * @date 2023-11-27 22:03
 */
public interface ClazzService {
    List<Clazz> getAll();

    Boolean add(ClazzDTO clazzDTO);
}
