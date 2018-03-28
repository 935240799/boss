package com.expressProject.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expressProject.bos.dao.base.CourierRepository;
import com.expressProject.bos.domain.Courier;
import com.expressProject.bos.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午8:08:29 <br/>       
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
    
    @Autowired
    private CourierRepository courierRepository;

    // 保存
    @Override
    public void save(Courier courier) {

        courierRepository.save(courier);
    }

    //查询
    @Override
    public Page<Courier> findAll(Pageable pageable) {
          
        return courierRepository.findAll(pageable);
    }

    //批量删除
    @Override
    public void batchDel(String ids) {
        
        if(StringUtils.isNotEmpty(ids)){
            String[] split = ids.split(",");
            for (String id : split) {
                courierRepository.updateDelTagById(Long.parseLong(id));
            }
        }
    }

    @Override
    public Page<Courier> findAll(Specification<Courier> spec, Pageable pageable) {
          
        return courierRepository.findAll(spec, pageable);
    }

    @Override
    public List<Courier> findAvaible() {
        return courierRepository.findByDeltagIsNull();
    }
}
  
