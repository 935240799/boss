package com.expressProject.bos.web.actions.base;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import com.expressProject.bos.domain.SubArea;
import com.expressProject.bos.service.base.SubAreaService;
import com.expressProject.bos.web.actions.CommonAction;

import net.sf.json.JsonConfig;

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

    private SubArea model = new SubArea();

    @Override
    public SubArea getModel() {

        return model;
    }
   
    
    @Autowired
    private SubAreaService subAreaService;

    @Action(value = "subareaAction_save", results = {@Result(name = "success",
            location = "/pages/base/sub_area.html", type = "redirect")})
    public String save() {
        
        subAreaService.save(model);
        return SUCCESS;
    }

   

    //AJAX请求不需要跳页面        fen
    @Action(value = "subAreaAction_pageQuery")
    public String pageQuery() throws IOException {

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas","couriers"});
        
       PageRequest pageRequest = new PageRequest(page-1, rows);
       Page<SubArea> page = subAreaService.findAll(pageRequest);
       page2json(page, jsonConfig);

        return NONE;
    }
}
  
