package com.expressProject.bos.web.actions.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.dom4j.CDATA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.expressProject.bos.domain.Area;
import com.expressProject.bos.domain.Courier;
import com.expressProject.bos.domain.Standard;
import com.expressProject.bos.service.base.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午7:34:04 <br/>       
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier>{
    
    /**  
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).  
     * @since JDK 1.6  
     */
    private static final long serialVersionUID = 1L;
    private Courier model = new Courier();
    
    @Override
    public Courier getModel() {
          
        return model;
    }
    
    @Autowired
    private CourierService courierService;
    
    //快递员增加
    @Action(value="courierAction_save",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String save(){
        
        courierService.save(model);
        
        return SUCCESS;
    }
    
    private int page;
    private int rows;
    
    
    
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }

    //快递员分页
    @Action("courierAction_PageQuery")
    public String PageQuery() throws IOException{
        
        Specification<Courier> specification = new Specification<Courier>() {

            /**
             * 创建一个查询的where语句
             * 
             * @param root : 根对象.可以简单的认为就是泛型对象
             * @param cb : 构建查询条件
             * @return a {@link Predicate}, must not be {@literal null}.
             */
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                String courierNum = model.getCourierNum();
                String company = model.getCompany();
                String type = model.getType();
                Standard standard = model.getStandard();
                
                List<Predicate> list = new ArrayList<>();
                
                if(StringUtils.isNotEmpty(courierNum)){
                    //如果工号不为空,构建等值查询条件
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }
                if(StringUtils.isNotEmpty(company)){
                    //如果公司不为空,构建模糊查询条件
                    Predicate p2 = cb.like(root.get("company").as(String.class), "%"+company+"%");
                    list.add(p2);
                }
                if(StringUtils.isNotEmpty(type)){
                    //如果类型不为空,构建模糊查询条件
                    Predicate p3 = cb.like(root.get("type").as(String.class), "%"+type+"%");
                    list.add(p3);
                }
                
                if(standard!=null){
                    String name = standard.getName();
                    if(StringUtils.isNotEmpty(name)){
                        //连表查询,查询标准名字
                        Join<Object, Object> join = root.join("standard");
                        Predicate p4 = cb.like(join.get("name").as(String.class), "%"+name+"%");
                        list.add(p4);
                    }
                }
                
                if(list.size()==0){
                    //用户没有输入查询条件
                    return null;
                }
                
                //用户输入了查询条件
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                //用户输入了多少查询条件就让多少个条件同时满足
                Predicate predicate = cb.and(arr);
                
                return predicate;
            }};
        
        
        Pageable pageable = new PageRequest(page - 1, rows);
        
        Page<Courier> page = courierService.findAll(specification,pageable);
        
        long total = page.getTotalElements();
        List<Courier> content = page.getContent();
        
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", content);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"takeTime","fixedAreas"});
        
        String json = JSONObject.fromObject(map,jsonConfig).toString();
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        
        return NONE;
    }
    
    private String ids;
    
    public void setIds(String ids) {
        this.ids = ids;
    }
    //作废快递员
    @Action(value="courierAction_batchDel",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String batchDel(){
        courierService.batchDel(ids);
        return SUCCESS;
    }
}
  
