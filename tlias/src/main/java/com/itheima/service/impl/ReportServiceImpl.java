package com.itheima.service.impl;

import com.itheima.mapper.EmpMapper;
import com.itheima.mapper.StuMapper;
import com.itheima.pojo.JobOption;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private StuMapper stuMapper;

    /**
     * 统计各个职位的员工人数
     */
    @Override
    public JobOption getEmpJobData() {
        List<Map<String,Object>> list = empMapper.countEmpJobData();
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("total")).toList();
        return new JobOption(jobList, dataList);
    }

    /**
     * 统计员工性别信息
     */
    @Override
    public List<Map> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    /**
     * 统计学员学历信息
     */
    @Override
    public List<Map> getStudentDegreeData() {
        return stuMapper.countStudentDegreeData();
    }

    /**
     * 班级人数统计 --参照“统计各个职位的员工人数”
     */
    @Override
    public JobOption getStudentCountData() {
        List<Map<String,Object>> list = stuMapper.getStudentCountData();
        List<Object> classList = list.stream().map(dataMap -> dataMap.get("clazzName")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("studentCount")).toList();
        return new JobOption(classList, dataList);
    }
}
