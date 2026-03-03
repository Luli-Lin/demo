package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//分页结果查询
@Data
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class PageResult<T> {
    private Long total;
    private List<T> rows;

}
/*
分页查询:
    前端传给后端：页码、每页记录条数
    后端返回前端：总记录数、每页的结果列表
 */
