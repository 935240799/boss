package com.expressProject.bos.web.actions.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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

import com.expressProject.bos.domain.Area;
import com.expressProject.bos.service.base.AreaService;
import com.expressProject.bos.web.actions.CommonAction;
import com.expressProjectUtils.PinYin4jUtils;

import net.sf.json.JsonConfig;

/**  
 * ClassName:AreaServiceAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 上午10:53:51 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends CommonAction<Area> {

    private static final String SUCCESS = null;

    public AreaAction() {
        super(Area.class);
    }

    @Autowired
    private AreaService areaService;

    // 使用属性驱动获取用户上传的文件
    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    @Action(value = "areaAction_importXLS", results = {@Result(name = "success",
            location = "/pages/base/area.html", type = "redirect")})
    public String importXLS() {

        try {
            HSSFWorkbook hssfWorkbook =
                    new HSSFWorkbook(new FileInputStream(file));
            // 读取第一个工作簿
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            // 储存对象的集合
            List<Area> list = new ArrayList<>();
            for (Row row : sheet) {
                // 跳过第一行
                if (row.getRowNum() == 0) {
                    continue;
                }
                // 读取数据
                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();
                // 截掉最后一个字符
                province = province.substring(0, province.length() - 1);
                city = city.substring(0, city.length() - 1);
                district = district.substring(0, district.length() - 1);
                // 获取城市编码和简码
                String citycode =
                        PinYin4jUtils.hanziToPinyin(city, "").toUpperCase();
                String[] headByString = PinYin4jUtils
                        .getHeadByString(province + city + district);
                String shortcode =
                        PinYin4jUtils.stringArrayToString(headByString);
                // 封装数据
                Area area = new Area();
                area.setProvince(province);
                area.setCity(city);
                area.setDistrict(district);
                area.setPostcode(postcode);
                area.setCitycode(citycode);
                area.setShortcode(shortcode);
                // 添加到集合
                list.add(area);

            }
            // 执行保存
            areaService.save(list);

            // 释放资源
            hssfWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SUCCESS;
    }
    @Action(value="areaAction_pageQuery")
    public String pageQuery() throws IOException{
        
        Pageable pageable = new PageRequest(page - 1, rows);

        Page<Area> page = areaService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});

        page2json(page, jsonConfig);

        return NONE;
    }

    private String q;
    
    public void setQ(String q) {
        this.q = q;
    }
    
    @Action(value="areaAction_findAll")
    public String findAll() throws IOException{
        Page<Area> page = areaService.findAll(null);
        List<Area> list; 
        if (StringUtils.isNotEmpty(q)) {
            //根据条件模糊查询
            list = areaService.findByQ(q);
        }else{
            //查询所有
            list = page.getContent();
        }
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
        
        list2json(list, jsonConfig);
        
        return NONE;
    }
}
  
