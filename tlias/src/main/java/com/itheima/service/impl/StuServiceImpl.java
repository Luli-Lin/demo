package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.StuMapper;
import com.itheima.pojo.*;
import com.itheima.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StuServiceImpl implements StuService {
    @Autowired
    private StuMapper stuMapper;

    /**
     * 分页查询 & 条件查询
     */
    @Override
    public PageResult<Student> page(StuQueryParam stuQueryParam) {
        //1. 设置分页参数
        PageHelper.startPage(stuQueryParam.getPage(), stuQueryParam.getPageSize());

        //2. 执行查询
        List<Student> stuList = stuMapper.list(stuQueryParam);
        Page<Student> p = (Page<Student>) stuList;

        //3. 封装结果
        return new PageResult(p.getTotal(), p.getResult());
    }

    /**
     * 批量删除
     */
    @Override
    public void deleteByIds(List<Integer> ids) {
        stuMapper.deleteByIds(ids);
    }

    /**
     * 新增学生
     */
    @Override
    public void save(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        stuMapper.insert(student);
    }

    /**
     * 根据ID查询
     */
    @Override
    public Student getById(Integer id) {
        return stuMapper.getById(id);
    }

    /**
     *修改学生
     */
    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        stuMapper.update(student);
    }

    /**
     * 根据ID修改违纪处理
     */
    @Override
    public void updateViolation(Integer id, Integer score) {
        stuMapper.updateViolation(id, score);
    }
}


























