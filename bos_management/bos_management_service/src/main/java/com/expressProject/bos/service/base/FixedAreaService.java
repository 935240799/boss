package com.expressProject.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.expressProject.bos.domain.FixedArea;
import com.expressProject.bos.domain.SubArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 下午3:25:10 <br/>       
 */
public interface FixedAreaService {

    void save(FixedArea model);

    Page<FixedArea> findAll(Pageable pageable);

}
  
