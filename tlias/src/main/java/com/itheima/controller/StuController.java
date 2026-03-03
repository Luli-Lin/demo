package com.itheima.controller;

import com.itheima.pojo.*;
import com.itheima.service.StuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/students")
@RestController
public class StuController {
    @Autowired
    private StuService stuService;

    /**
     * 查询学生列表  分页查询 & 条件查询
     * 参数：queryString
     */
    @GetMapping
    public Result page(StuQueryParam stuQueryParam){
        //请求参数：queryString，条件查询
        log.info("查询学术列表 分页查询 & 条件查询");
        PageResult<Student> pageResult = stuService.page(stuQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 批量删除学生
     * 1、路径参数：@PathVariable("ids")
     * 请求示例   /students/1,2,3
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids){
        // 将字符串按逗号分割并转换为Integer List
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        log.info("批量删除学生, ids={}",ids);
        stuService.deleteByIds(idList);
        return Result.success();
    }

    /**
     * 新增学生
     * 1、参数：@RequestBody
     * 2、返回值：Result
     */
    @PostMapping
    public Result save(@RequestBody Student student){
        log.info("新增学生, student={}", student);
        stuService.save(student);
        return Result.success();
    }

    /**
     * 根据ID查询
     * 1、路径参数：@PathVariable("id") Integer id
     */
    @GetMapping("/{id:\\d+}")
    public Result getById(@PathVariable("id") Integer id){
        log.info("根据ID查询, id={}",id);
        Student student = stuService.getById(id);
        return Result.success(student);
    }
    /**
     * 修改学生信息
     * 1、参数：@RequestBody
     */
    @PutMapping
    public Result update(@RequestBody Student student){
        log.info("修改学生, student={}", student);
        stuService.update(student);
        return Result.success();
    }

    /**
     * 违纪处理，根据ID修改学员数据信息
     */
    @PutMapping("/violation/{id}/{score}")
    public Result violation(@PathVariable Integer id, @PathVariable Integer score){
        log.info("违纪处理，修改学员数据信息, id={}, score={}", id, score);
        stuService.updateViolation(id, score);
        return Result.success();
    }


}























