package com.express.crm.service.impl;

import java.util.List;

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
    
}
  
