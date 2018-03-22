package com.expressProject.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expressProject.bos.dao.base.AreaRepository;
import com.expressProject.bos.domain.Area;
import com.expressProject.bos.service.base.AreaService;



/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 上午10:47:31 <br/>       
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    
    @Autowired
    private AreaRepository areaRepository;

    //批量保存
    @Override
    public void save(List<Area> list) {
          
        areaRepository.save(list);
        
    }

//    分页查询
    @Override
    public Page<Area> findAll(Pageable pageable) {
          
        return areaRepository.findAll(pageable);
    }

    @Override
    public List<Area> findByQ(String q) {
         
        q="%"+q.toUpperCase()+"%";
        return areaRepository.findQ(q);
    }
}
  
