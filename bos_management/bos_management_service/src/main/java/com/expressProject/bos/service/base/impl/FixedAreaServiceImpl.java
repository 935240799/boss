package com.expressProject.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expressProject.bos.dao.base.FixedAreaRepository;
import com.expressProject.bos.domain.FixedArea;
import com.expressProject.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 下午3:25:29 <br/>       
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

    @Autowired
    private FixedAreaRepository fixedAreaRepository;

    @Override
    public void save(FixedArea model) {
        fixedAreaRepository.save(model);
    }

    @Override
    public Page<FixedArea> findAll(Pageable pageRequest) {
        return fixedAreaRepository.findAll(pageRequest);
    }
    
}
  
