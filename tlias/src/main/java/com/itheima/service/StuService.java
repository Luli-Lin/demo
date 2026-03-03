package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.StuQueryParam;
import com.itheima.pojo.Student;

import java.util.List;

public interface StuService {
    PageResult<Student> page(StuQueryParam stuQueryParam);

    void deleteByIds(List<Integer> ids);

    void save(Student student);

    Student getById(Integer id);

    void update(Student student);

    void updateViolation(Integer id, Integer score);
}
