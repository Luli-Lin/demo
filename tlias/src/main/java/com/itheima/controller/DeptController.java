package com.itheima.controller;

import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理控制器
 */
@Slf4j
@RestController
public class DeptController {
    //private static final Logger log= LoggerFactory.getLogger(DeptController.class);
    @Autowired
    private DeptService deptService;

    //查询部门列表
    @GetMapping("/depts")
    public Result list(){
        log.info("查询部门列表");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }
    //路径参数
    //根据ID查询 - GET http://localhost:8080/depts/1
    @GetMapping("/depts/{id}")
    public Result getById(@PathVariable("id") Integer id){
        //请求参数：路径参数;
        log.info("根据ID查询, id={}",id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /*根据ID删除部门
    参数传递：
    1、@RequestParam注解required属性默认为true，代表该参数必须传递，如果不传递将报错。 如果参数可选，可以将属性设置为false
    2、如果前端请求参数名（/depts?id=..）与形参变量名相同，直接定义方法形参即可接收。（省略@RequestParam）
     1中不传参会报错，2不会
     */
    @DeleteMapping("/depts")
    public Result delete(Integer id){
        //请求参数queryString;
        log.info("根据ID删除部门,id={}",id);
        deptService.deleteById(id);
        return Result.success();
    }

    /**
     * 新增部门 - POST http://localhost:8080/depts   请求参数：{"name":"研发部"}
     */
    @PostMapping("/depts")
    public Result save(@RequestBody Dept dept){
        //System.out.println("新增部门, dept=" + dept);
        log.info("新增部门, dept={}",dept);
        deptService.save(dept);
        return Result.success();
    }

    /**
     * 修改部门 - PUT http://localhost:8080/depts  请求参数：{"id":1,"name":"研发部"}
     */
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept){
        //System.out.println("修改部门, dept="+dept);
        log.info("修改部门, dept={}",dept);
        deptService.update(dept);
        return Result.success();
    }
}
/*
请求参数传递的几种方式:
1. URL查询字符串，在URL末尾使用？分隔，多个参数使用&连接，如
    http://localhost:8080?id=1 & name=linlin
2.路径参数  @GetMapping("/depts/{id}")
    (@PathVariable("id") Integer id)
3.json格式  @PostMapping("/depts")
    (@RequestBody Dept dept)
 */