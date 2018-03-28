package com.express.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.express.crm.dao.CustomerRepository;
import com.express.crm.domain.Customer;
import com.express.crm.service.CustomerService;

/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 下午2:35:14 <br/>       
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    // 查询未关联定区的客户
    @Override
    public List<Customer> findCustomersUnAssociated() {
        return customerRepository.findByFixedAreaIdIsNull();
    }

    // 查询已关联到指定定区的客户
    @Override
    public List<Customer> findCustomersAssociated2FixedArea(String fixedAreaId) {
          
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }
    

    @Override
    public void assignCustomers2FixedArea(Long[] customerIds, String fixedAreaId) {
        //根据定区ID,把关联到这个定区的所有客户全部解绑
        if(StringUtils.isNotEmpty(fixedAreaId)){
            customerRepository.unbindCustomerByFixedArea(fixedAreaId);
        }

        //要关联的数据和定区ID绑定
        if(customerIds != null && fixedAreaId.length()>0){
            for (Long customerId : customerIds) {
                customerRepository.bindCustomer2FixedArea(customerId,fixedAreaId);
            }
        }
    }

    //注册用户
    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}
  
