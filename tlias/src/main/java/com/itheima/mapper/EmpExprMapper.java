package com.itheima.mapper;

import com.itheima.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    //对员工工作经历表处理
    /**
     * 批量插入员工工作经历信息
     */
    void insertBatch(List<EmpExpr> exprList);

    /**
     * 根据员工的ID批量删除工作经历信息
     */
    void deleteByEmpIds(List<Integer> empIds);
}
