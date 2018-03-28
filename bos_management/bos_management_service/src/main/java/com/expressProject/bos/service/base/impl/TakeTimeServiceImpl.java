package com.expressProject.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expressProject.bos.dao.base.TakeTimeRepository;
import com.expressProject.bos.domain.TakeTime;
import com.expressProject.bos.service.base.TakeTimeService;

/**  
 * ClassName:TakeTimeServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午7:50:40 <br/>       
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
    
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public List<TakeTime> findAll() {
          
        return takeTimeRepository.findAll();
    }
}
  
