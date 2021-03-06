package com.expressProject.bos.web.actions.base;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
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

import com.expressProject.bos.domain.Customer;
import com.expressProject.bos.domain.FixedArea;
import com.expressProject.bos.service.base.FixedAreaService;
import com.expressProject.bos.web.actions.CommonAction;

import net.sf.json.JsonConfig;

/**
 * ClassName:FixedAreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月24日 下午3:27:19 <br/>
 */
@Namespace("/") // 等价于struts.xml文件中package节点namespace属性
@ParentPackage("struts-default") // 等价于struts.xml文件中package节点extends属性
@Controller // spring 的注解,控制层代码
@Scope("prototype") // spring 的注解,多例
public class FixedAreaAction extends CommonAction<FixedArea> {

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

    @Action(value = "fixedAreaAction_save", results = {
            @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
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

    // 向CRM系统发起请求,查询未关联定区的客户
    @Action(value = "fixedAreaAction_findUnAssociatedCustomers")
    public String findUnAssociatedCustomers() throws IOException {

        List<Customer> list = (List<Customer>) WebClient.create(
                "http://localhost:8088/crm/webService/customerService/findCustomersUnAssociated")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);

        list2json(list, null);
        
        return NONE;
    }
    
 // 向CRM系统发起请求,查询已关联指定定区的客户
    @Action(value = "fixedAreaAction_findAssociatedCustomers")
    public String findAssociatedCustomers() throws IOException {

        List<Customer> list = (List<Customer>) WebClient.create(
                "http://localhost:8088/crm/webService/customerService/findCustomersAssociated2FixedArea")
                .query("fixedAreaId", getModel().getId())
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);

        list2json(list, null);
        return NONE;
    }
    
    //使用属性驱动获取要关联到指定区域的客户ID
    private Long[] CustomerIds;
    
    public void setCustomerIds(Long[] customerIds) {
        CustomerIds = customerIds;
    }
    
    // 向CRM系统发起请求,关联客户
    @Action(value = "fixedAreaAction_assignCustomers2FixedArea",
            results = {
                    @Result(name = "success", 
                            location = "/pages/base/fixed_area.html", 
                            type = "redirect")})
    public String assignCustomers2FixedArea() throws IOException {
        
        WebClient.create(
                "http://localhost:8088/crm/webService/customerService/assignCustomers2FixedArea")
                .query("fixedAreaId", model.getId())
                .query("customerIds", CustomerIds)
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .put(null);
        return SUCCESS;
    }
    
    //使用属性驱动获取快递员的时间和ID
    private Long courierId;
    private Long takeTimeId;

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    
    //关联快递员
    @Action(value="fixedAreaAction_associationCourierToFixedArea",
            results={@Result(name="success",
                       location="/pages/base/fixed_area.html",
                       type="redirect")})
    public String associationCourierToFixedArea(){
        
        fixedAreaService.associationCourierToFixedArea(getModel().getId(),courierId,takeTimeId);
        
        return SUCCESS;
    }
}

















