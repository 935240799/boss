package com.expressProject.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.expressProject.bos.domain.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午8:07:54 <br/>       
 */
public interface CourierService {

    void save(Courier courier);

    Page<Courier> findAll(Pageable pageable);

    void batchDel(String ids);

    Page<Courier> findAll(Specification<Courier> specification, Pageable pageable);

    List<Courier> findAvaible();


}
  
