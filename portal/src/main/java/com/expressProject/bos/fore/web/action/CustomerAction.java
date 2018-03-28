package com.expressProject.bos.fore.web.action;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.express.crm.domain.Customer;
import com.expressProjectUtils.SmsUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午9:52:56 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{

    private Customer model = new Customer();
    
    @Override
    public Customer getModel() {
          
        return model;
    }

    @Action(value="customerAction_sendSMS")
    public String sendSMS() throws IOException{

        //随机生成验证码
        String code = RandomStringUtils.randomNumeric(6);
        System.out.println(code);
        //储存验证码
        ServletActionContext.getRequest().getSession().setAttribute("serverCode", code);
        try {
            //发送验证码
            SmsUtils.sendSms(model.getTelephone(), code);
        } catch (ClientException e) {
            e.printStackTrace();  
        }
        
        return NONE;
    }
    
    
    //使用属性驱动获取用户输入的验证码
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    @Action(value = "customerAction_regist", results = {
            @Result(name = "success", location = "/signup-success.html", type = "redirect"),
            @Result(name = "error", location = "/signup-fail.html", type = "redirect")})
    public String regist() {

        //校验验证码
        String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("serverCode");
        
        if(StringUtils.isNotEmpty(checkcode)&&StringUtils.isNotEmpty(serverCode)&&serverCode.equals(checkcode)){
            //校验通过,完成注册
            WebClient.create(
                    "http://localhost:8088/crm/webService/customerService/save")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON).post(model);
            return SUCCESS;
        }
        return ERROR;
    }
}
  
