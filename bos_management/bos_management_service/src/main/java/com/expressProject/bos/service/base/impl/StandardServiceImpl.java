package com.expressProject.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expressProject.bos.dao.base.StandardRepository;
import com.expressProject.bos.domain.Standard;
import com.expressProject.bos.service.base.StandardService;

/**
 * ClassName:StandardServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月15日 下午8:55:57 <br/>
 */
@Transactional
@Service
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardRepository standardRepository;

    @Override
    public void savve(Standard standard) {
        standardRepository.save(standard);
    }

    @Override
    public Page<Standard> findAll(Pageable pageable) {
          
        return standardRepository.findAll(pageable);
    }
}
