package com.express.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.express.crm.domain.Customer;

/**  
 * ClassName:CustomerService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 下午2:34:46 <br/>       
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CustomerService {

    @GET
    @Path("/findAll")
    List<Customer> findAll();
    
}
  
