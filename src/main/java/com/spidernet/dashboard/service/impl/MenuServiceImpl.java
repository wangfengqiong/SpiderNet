package com.spidernet.dashboard.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.spidernet.dashboard.dao.MenuMapper;
import com.spidernet.dashboard.entity.Menu;
import com.spidernet.dashboard.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{
	
	@Resource
    private MenuMapper menuMapper;


	@Override
	public List<Menu> queryMenuList() {
		List<Menu> list = menuMapper.queryMenuList();
		return list;
	}

	@Override
	public int getSortByParentId(String parentId) {
		return menuMapper.getSortByParentId(parentId) + 1;
	}

	@Override
	public boolean addMenu(Menu menu) {
		
		if (menuMapper.addMenu(menu) > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
	}

	@Override
	public boolean updateMenu(Menu menu) {

        if (menuMapper.updateMenu(menu) > 0)
        {
            return true;
        }
        else
        {
            return false;
        }

	}

	@Override
	public boolean deleteMenu(Menu menu) {
		if (menuMapper.deleteMenu(menu) > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
	}

	@Override
	public Menu getMenuById(String id) {
		return menuMapper.getMenuById(id);
	}

	@Override
	public String getNameById(String id) {
		return menuMapper.getNameById(id);
	}

}
