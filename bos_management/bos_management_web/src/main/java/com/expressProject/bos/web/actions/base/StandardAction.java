package com.expressProject.bos.web.actions.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
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

import com.expressProject.bos.domain.Standard;
import com.expressProject.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * ClassName:StandardAction <br/>
 * Function: <br/>
 * Date: 2018年3月15日 下午8:25:34 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

    private Standard model = new Standard();

    @Override
    public Standard getModel() {

        return model;
    }

    @Autowired
    private StandardService standardService;

    
    // value : // 等价于struts.xml文件中action节点中的name属性
    // 多个结果就使用@Result注解标识
    // name : 结果
    // location: 跳转页面路径
    // type : 使用的方式,重定向或转发
    @Action(value = "standardAction_save", results = {
            @Result(name = "success", location = "/pages/base/standard.html", type = "redirect")})
    public String save() {

        standardService.savve(model);
        return SUCCESS;
    }

    // 使用属性驱动获取数据
    private int page;// 第几页
    private int rows;// 每页显示数据条数

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    //AJAX请求不需要跳页面        fen
    @Action(value = "standardAction_pageQuery")
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);

        Page<Standard> page = standardService.findAll(pageable);

        long total = page.getTotalElements();

        List<Standard> list = page.getContent();

        Map<String, Object> map = new HashMap<>();

        map.put("total", total);
        map.put("rows", list);

        String json = JSONObject.fromObject(map).toString();

        HttpServletResponse response = ServletActionContext.getResponse();

        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().write(json);

        return NONE;
    }

    @Action(value = "standard_findAll")
    public String findAll() throws IOException {

        Page<Standard> page = standardService.findAll(null);

        List<Standard> list = page.getContent();

        String json = JSONArray.fromObject(list).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);

        return NONE;
    }
}
