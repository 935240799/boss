package com.expressProject.bos.web.actions.base;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.expressProject.bos.domain.Courier;
import com.expressProject.bos.domain.FixedArea;
import com.expressProject.bos.domain.TakeTime;
import com.expressProject.bos.service.base.TakeTimeService;
import com.expressProject.bos.web.actions.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午7:52:26 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TakeTimeAction extends CommonAction<TakeTime>{

    public TakeTimeAction() {
        super(TakeTime.class);  
    }
    
    @Autowired
    private TakeTimeService takeTimeService;

    @Action(value="takeTimeAction_listajax")
    public String listajax2() throws IOException{
        //查询所有的在职的快递员
        List<TakeTime> list = takeTimeService.findAll();
        
        list2json(list,null);
        
        return NONE;
        
    }
    
}
  
