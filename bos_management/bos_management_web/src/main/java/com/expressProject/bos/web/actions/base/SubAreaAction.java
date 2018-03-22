package com.expressProject.bos.web.actions.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.expressProject.bos.domain.SubArea;
import com.expressProject.bos.service.base.SubAreaService;
import com.expressProject.bos.web.actions.CommonAction;

/**  
 * ClassName:SubAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午9:36:44 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubAreaAction extends CommonAction<SubArea>{

    public SubAreaAction() {
        super(SubArea.class);  
        
    }

    @Autowired
    private SubAreaService subAreaService;
    
    @Action(value = "subareaAction_save", results = {
            @Result(name = "success", location = "/pages/base/sub_area.html", type = "redirect")})
    public String save() {

        subAreaService.save(getModel());
        return SUCCESS;
    }
    
}
  
