package com.expressProject.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.expressProject.bos.domain.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:54:57 <br/>       
 */
public interface StandardService {

    void savve(Standard standard);

    Page<Standard> findAll(Pageable pageable);
    
}
  
