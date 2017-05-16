package com.spidernet.dashboard.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.spidernet.dashboard.dao.EmployeeInfoMapper;
import com.spidernet.dashboard.entity.EmployeeInfo;
import com.spidernet.dashboard.entity.PageCondition;
import com.spidernet.dashboard.service.EmployeeInfoService;

/**
 * 
 * @author nick
 *
 */
@Service
public class EmployeeInfoServiceImpl implements EmployeeInfoService
{

    @Resource
    private EmployeeInfoMapper employeeInfoMapper;
    
    @Override
    public List<EmployeeInfo> queryEmpInfo(PageCondition pageCondition)
    {
        List<EmployeeInfo> listE = employeeInfoMapper.queryEmpInfo(pageCondition);
        return listE;
    }

    @Override
    public int countPage(PageCondition pageCondition)
    {
        return employeeInfoMapper.countPage(pageCondition)/10 + 1;
    }

}
