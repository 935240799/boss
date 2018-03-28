package com.expressProject.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expressProject.bos.dao.base.CourierRepository;
import com.expressProject.bos.dao.base.FixedAreaRepository;
import com.expressProject.bos.dao.base.TakeTimeRepository;
import com.expressProject.bos.domain.Courier;
import com.expressProject.bos.domain.FixedArea;
import com.expressProject.bos.domain.TakeTime;
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
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    
    @Override
    public void save(FixedArea model) {
        fixedAreaRepository.save(model);
    }

    @Override
    public Page<FixedArea> findAll(Pageable pageRequest) {
        return fixedAreaRepository.findAll(pageRequest);
    }

    @Override
    public void associationCourierToFixedArea(Long fixedAreaId, Long courierId, Long takeTimeId) {
        // 代码执行成功以后,快递员表发生update操作,快递员和定区中间表会发生insert操作

        // 持久态对象
        FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        
        // 建立快递员和时间的关联
        courier.setTakeTime(takeTime);
        // 建立快递员和定区的关联
        // 下面的写法是错的,因为在Courier实体类中fixedAreas字段的上方添加了mappedBy属性
        // 就代表快递员放弃了对关系的维护
        // courier.getFixedAreas().add(fixedArea);
        fixedArea.getCouriers().add(courier);
        
    }
}
  
