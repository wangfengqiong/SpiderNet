package com.spidernet.dashboard.controller;
/**
 * rule
 * @author Lulu
 *
 */
import java.util.HashMap;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spidernet.dashboard.entity.Rule;
import com.spidernet.dashboard.entity.RulePageCondition;
import com.spidernet.dashboard.service.RuleService;
import com.spidernet.util.Utils;


@Controller
@RequestMapping("/rule")
public class RuleController
{
    @Resource
    RuleService ruleService;
    
    private static Logger logger = LoggerFactory.getLogger(RuleController.class);
    
    //Lulu
    @RequestMapping("/rule")
    public String Rule(final HttpServletRequest request,
            final HttpServletResponse response)
    {
        return "employee/rule";
    }
   
    @RequestMapping("/ruleInfoList")
    @ResponseBody
    public Object ruleInfoList(final HttpServletRequest request,
            final HttpServletResponse response)
    {
    	logger.debug("---------Find rule list----------");
        String ruleName = request.getParameter("ruleName");
        
        String pageState = request.getParameter("pageState");

        String currentPage = null;
        
        int countPage = 0;
        
        RulePageCondition rulePageCondition = new RulePageCondition();
        
        if("".equals(pageState) || pageState == null){
            currentPage = "0";
            rulePageCondition.setRuleName(ruleName);
            rulePageCondition.setCurrentPage(currentPage);
            countPage = ruleService.countRulePage(rulePageCondition);
            rulePageCondition.setPageCount(countPage+"");
            request.getSession().setAttribute("rulePageCondition", rulePageCondition);
        }else if("frist".equals(pageState)){
            currentPage = "0";
            rulePageCondition = (RulePageCondition) request.getSession().getAttribute("rulePageCondition");
            rulePageCondition.setCurrentPage(currentPage);
            request.getSession().setAttribute("rulePageCondition", rulePageCondition);
        }else if("next".equals(pageState)){
        	rulePageCondition = (RulePageCondition) request.getSession().getAttribute("rulePageCondition");
            currentPage = Integer.parseInt(rulePageCondition.getCurrentPage()) + 10 +"";
            rulePageCondition.setCurrentPage(currentPage);
            request.getSession().setAttribute("rulePageCondition", rulePageCondition);
        }else if("previous".equals(pageState)){
        	rulePageCondition = (RulePageCondition) request.getSession().getAttribute("rulePageCondition");
            currentPage = Integer.parseInt(rulePageCondition.getCurrentPage()) - 10 +"";
            rulePageCondition.setCurrentPage(currentPage);
            request.getSession().setAttribute("rulePageCondition", rulePageCondition);
        }else if("last".equals(pageState)){
        	rulePageCondition = (RulePageCondition) request.getSession().getAttribute("rulePageCondition");
            currentPage = (Integer.parseInt(rulePageCondition.getPageCount()) - 1) * 10 +"";
            rulePageCondition.setCurrentPage(currentPage);
            request.getSession().setAttribute("rulePageCondition", rulePageCondition);
        }
        
        List<Rule> ruleInfoList = ruleService.queryRuleInfo(rulePageCondition);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("data", ruleInfoList);
        result.put("pageInfo", request.getSession().getAttribute("rulePageCondition"));
        return result;
    }
    
    

    @RequestMapping("/addRule")
    @ResponseBody
    public Boolean addRule(final HttpServletRequest request,
            final HttpServletResponse response)
    {
    	logger.debug("---------Add rule---------");
        String id = Utils.getUUID();
        String name = request.getParameter("name");
        String sort = request.getParameter("sort");
        String remark = request.getParameter("remark"); 
        
        Rule rule = new Rule();        
        rule.setId(id);
        rule.setName(name);
        rule.setSort(sort);
        rule.setRemark(remark);              
        boolean resultFlag = ruleService.addRule(rule);
               
        return (resultFlag);
    }
    
    @RequestMapping("/queryRuleByName")
    @ResponseBody
    public Object queryRuleByName(final HttpServletRequest request,
            final HttpServletResponse response)
    {
    	logger.debug("---------Query rule by name----------");
        String name = request.getParameter("name");       
        List<Rule> ruleList = ruleService.queryRuleByName(name);
        
        return ruleList;
    }
    
    @RequestMapping("/queryRuleName")
    @ResponseBody
    public Object queryRuleName(final HttpServletRequest request,
            final HttpServletResponse response)
    {
    	logger.debug("---------Query rule name----------");
        List<Rule> ruleList = ruleService.queryRuleName();
        
        return ruleList;
    }
    
    @RequestMapping("/queryRuleById")
    @ResponseBody
    public Rule queryRuleById(final HttpServletRequest request,
            final HttpServletResponse response)
    {
    	logger.debug("---------Query rule by id----------");    	
        String id = request.getParameter("id");       
        Rule rule = ruleService.queryRuleById(id);
        
        return rule;
    }
    
    @RequestMapping("/editRule")
    @ResponseBody
    public Boolean editRule(final HttpServletRequest request,
            final HttpServletResponse response)
    {
        logger.debug("---------Edit rule----------");
        Boolean resultFlag = false;         
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String sort = request.getParameter("sort");
        String remark = request.getParameter("remark"); 
        
        Rule rule = new Rule();        
        rule.setId(id);
        rule.setName(name);
        rule.setSort(sort);
        rule.setRemark(remark); 
        
        resultFlag = ruleService.editRule(rule);        
        if (resultFlag == true)
        {
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
    @RequestMapping("/deleteRule")
    @ResponseBody
    public Boolean deleteRule(final HttpServletRequest request,
            final HttpServletResponse response)
    {
        logger.debug("---------Delete rule----------");
        Boolean resultFlag = false;       
        String id = request.getParameter("id");
        
        Rule rule = new Rule();        
        rule.setId(id);
        
        resultFlag = ruleService.deleteRule(rule);        
        if (resultFlag == true)
        {
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
    @RequestMapping("/checkNameExists")
    @ResponseBody
    public Object checkNameExists(final HttpServletRequest request,
            final HttpServletResponse response)
    {
    	logger.debug("---------Query rule name----------");
    	String name = request.getParameter("name"); 
    	
        boolean result = ruleService.checkNameExists(name);
        
        Map<String,Boolean> map = new HashMap<>();
        
        map.put("valid", result);
        
        ObjectMapper mapper = new ObjectMapper();
        
        String resultString = "";
        
        try
        {
            resultString = mapper.writeValueAsString(map);
        }
        catch (JsonProcessingException e)
        {
            logger.error("[RuleController.checkNameExists] exception",e);
        }
        
        return resultString;
    }

}
