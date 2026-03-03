package com.itheima.mapper;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.StuQueryParam;
import com.itheima.pojo.Student;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StuMapper {
    List<Student> list(StuQueryParam stuQueryParam);

    void deleteByIds(List<Integer> ids);

    void insert(Student student);

    Student getById(Integer id);

    void update(Student student);

    void updateViolation(Integer id, Integer score);

    @MapKey("degreeName")
    List<Map> countStudentDegreeData();

    @MapKey("clazzName")
    List<Map<String, Object>> getStudentCountData();
}
