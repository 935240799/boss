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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.expressProject.bos.domain.FixedArea;
import com.expressProject.bos.domain.SubArea;
import com.expressProject.bos.service.base.FixedAreaService;
import com.expressProject.bos.web.actions.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:FixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 下午3:27:19 <br/>       
 */
@Namespace("/") // 等价于struts.xml文件中package节点namespace属性
@ParentPackage("struts-default") // 等价于struts.xml文件中package节点extends属性
@Controller // spring 的注解,控制层代码
@Scope("prototype") // spring 的注解,多例
public class FixedAreaAction extends  CommonAction<FixedArea>{


    public FixedAreaAction() {
        super(FixedArea.class);
    }
    
    private FixedArea model = new FixedArea();

    @Override
    public FixedArea getModel() {

        return model;
    }

    @Autowired
    private FixedAreaService fixedAreaService;

    @Action(value = "fixedAreaAction_save", results = {@Result(name = "success",
            location = "/pages/base/fixed_area.html", type = "redirect")})
    public String save() {

        fixedAreaService.save(model);
        return SUCCESS;
    }
    
 // AJAX请求不需要跳转页面
    @Action(value = "fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException {


        Pageable pageable = new PageRequest(page - 1, rows);

        Page<FixedArea> page = fixedAreaService.findAll(pageable);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas", "couriers"});

        page2json(page, jsonConfig);
        return NONE;
    }
    
}
  
