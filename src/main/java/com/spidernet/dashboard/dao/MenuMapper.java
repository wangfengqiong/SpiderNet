package com.spidernet.dashboard.dao;

import java.util.List;

import com.spidernet.dashboard.entity.Menu;

public interface MenuMapper {
	

	List<Menu> queryMenuList();

	int getSortByParentId(String parentId);

	int addMenu(Menu menu);

	int updateMenu(Menu menu);
	
	int deleteMenu(Menu menu);

	Menu getMenuById(String id);

	String getNameById(String id);

}
