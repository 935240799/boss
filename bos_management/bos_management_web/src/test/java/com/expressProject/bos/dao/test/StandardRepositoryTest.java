package com.expressProject.bos.dao.test;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.expressProject.bos.dao.base.StandardRepository;
import com.expressProject.bos.domain.Standard;

/**  
 * ClassName:StandardRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午5:50:13 <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {
    
    @Autowired
    private StandardRepository StandardRepository;
    
    @Test
    public void test() {
        List<Standard> list = StandardRepository.findAll();
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }

}
  
