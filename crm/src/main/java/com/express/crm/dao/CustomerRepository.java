package com.express.crm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.express.crm.domain.Customer;

/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 下午2:33:22 <br/>       
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
  
