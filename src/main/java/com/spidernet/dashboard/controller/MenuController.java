package com.spidernet.dashboard.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spidernet.dashboard.entity.Menu;
import com.spidernet.dashboard.service.MenuService;

@Controller
@RequestMapping("/manage")
public class MenuController {
	
	@Resource
	MenuService menuService;
	
	private static Logger logger = LoggerFactory
            .getLogger(MenuController.class);
	
	
	public static Map<String,Object> mapArray = new LinkedHashMap<String, Object>();  
    public List<Menu> menuCommon;  
    
    
    @RequestMapping("/getMenuById")
    @ResponseBody
    public Menu getMenuById(final HttpServletRequest request,
            final HttpServletResponse response){
    	
    	String id = request.getParameter("id");
		Menu menu = menuService.getMenuById(id);
		
		return menu;
	}
    
    @RequestMapping("/menu")
    public String update2(final HttpServletRequest request,
            final HttpServletResponse response)
    {
        return "menu/menu";
    }
	
    @RequestMapping("/showMenu")
    @ResponseBody
    public List<Object> showMenu(final HttpServletRequest request,
            final HttpServletResponse response){
		
		List<Menu> menuList = menuService.queryMenuList();
		
		List<Object> menu = menuList(menuList);
		return menu;
	}
    
    @RequestMapping("/updateMenu")
    @ResponseBody
    public Boolean updateMenu(final HttpServletRequest request,
            final HttpServletResponse response, Menu menu){
    	
    	logger.debug("updateMenu");
    	Boolean resultFlag = menuService.updateMenu(menu);
		
		return resultFlag;
	}
    
    @RequestMapping("/deleteMenu")
    @ResponseBody
    public boolean deleteMenu(final HttpServletRequest request,
            final HttpServletResponse response,
            Menu menu){
    	boolean resultFlag = menuService.deleteMenu(menu);
		
		return resultFlag;
    }
    
    
    @RequestMapping("/addMenu")
    @ResponseBody
    public boolean addMenu(final HttpServletRequest request,
            final HttpServletResponse response){
    	
    	String id = request.getParameter("id");
    	String name = request.getParameter("name");
    	String parentId = request.getParameter("parentId");
        String picUrl = request.getParameter("picUrl");
        String remark = request.getParameter("remark");
        int sort = menuService.getSortByParentId(parentId);
        String url = request.getParameter("url");
        
        Menu menu = new Menu();
        
        menu.setId(id);
        menu.setName(name);
        menu.setParentId(parentId);
        menu.setPicUrl(picUrl);
        menu.setRemark(remark);
        menu.setSort(sort);
        menu.setUrl(url);
        
        
        boolean resultFlag = menuService.addMenu(menu);
        
        return resultFlag;
    }
	
	
	private List<Object> menuList(List<Menu> menu){  
		 List<Object> list = new ArrayList<Object>(); 
        this.menuCommon = menu;  
        for (Menu x : menu) {     
            Map<String,Object> mapArr = new LinkedHashMap<String, Object>(); 
            mapArr.put("id", x.getId());  
            mapArr.put("name", x.getName());
            mapArr.put("url", x.getUrl());
            mapArr.put("picUrl", x.getPicUrl());
            mapArr.put("pId", x.getParentId()); 
            String pName = getNameById(x.getParentId());
            mapArr.put("pName", pName);  
            mapArr.put("remark", x.getRemark());
            //System.out.println(x.getParentId());
//            if(x.getParentId().equals("0")){  
               // mapArr.put("child", menuChild(x.getId()));    
                list.add(mapArr);  
//            }  
        }     
        return list;  
    }

	private String getNameById(String id) {
		return menuService.getNameById(id);
	}  
     		 
      
//    private List<?> menuChild(String id){  
//        List<Object> lists = new ArrayList<Object>();  
//        for(Menu a:menuCommon){  
//            Map<String,Object> childArray = new LinkedHashMap<String, Object>();  
//            if(a.getParentId().equals(id)){  
//                childArray.put("id", a.getId());  
//                childArray.put("name", a.getName());
//                childArray.put("url", a.getUrl());
//                childArray.put("picUrl", a.getPicUrl());
//                childArray.put("parentId", a.getParentId());  
//                childArray.put("child", menuChild(a.getId()));    
//                lists.add(childArray);  
//            }  
//        }  
//        return lists;  
//          
//    }  
	

}
