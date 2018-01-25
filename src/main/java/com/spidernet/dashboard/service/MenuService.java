package com.spidernet.dashboard.service;

import java.util.List;

import com.spidernet.dashboard.entity.Menu;

public interface MenuService {
	
	List<Menu> queryMenuList();

	int getSortByParentId(String parentId);

	boolean addMenu(Menu menu);

	boolean updateMenu(Menu menu);

	boolean deleteMenu(Menu menu);

	Menu getMenuById(String id);

	String getNameById(String id);
}
