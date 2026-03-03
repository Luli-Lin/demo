package com.itheima.controller;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    /**
     * 查询班级列表  简单查询
     * 参数：无
     */
    @GetMapping("/clazzs/lists")
    public Result list(){
        log.info("查询班级列表");
        List<Clazz> clazzList = clazzService.findAll();
        return Result.success(clazzList);
    }

    /**
     * 查询班级列表  分页查询 & 条件查询
     * 参数：queryString
     */
    @GetMapping("/clazzs")
    public Result page(ClazzQueryParam clazzQueryParam){
        //请求参数：queryString，条件查询
        log.info("查询班级列表");
        PageResult<Clazz> pageResult = clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 删除班级
     * 参数：
     * 1、路径参数：@PathVariable("id") Integer id
     */
    @DeleteMapping("/clazzs/{id}")
    public Result delete(@PathVariable("id") Integer id){
        //请求参数queryString;
        log.info("根据ID删除班级,id={}",id);
        clazzService.deleteById(id);
        return Result.success();
    }

    /**
     * 新增班级
     * 参数：json 格式
     * 1、@RequestBody
     */
    @PostMapping("/clazzs")
    public Result save(@RequestBody Clazz clazz){
        log.info("新增班级, clazz={}",clazz);
        clazzService.save(clazz);
        return Result.success();
    }

    /**
     * 根据ID查询   用于实现修改时显示信息——查询回显
     * 请求参数：路径参数  @PathVariable("id") Integer id
     */
    @GetMapping("/clazzs/{id:\\d+}")
    public Result getById(@PathVariable("id") Integer id){
        log.info("根据ID查询, id={}",id);
        Clazz clazz = clazzService.getById(id);
        return Result.success(clazz);
    }

    /**
     * 修改班级
     * 参数：json格式
     * 1、@RequestBody
     */
    @PutMapping("/clazzs")
    public Result update(@RequestBody Clazz clazz){
        log.info("修改班级, clazz={}",clazz);
        clazzService.update(clazz);
        return Result.success();
    }
}
