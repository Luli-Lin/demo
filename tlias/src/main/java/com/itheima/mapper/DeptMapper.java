package com.itheima.mapper;

import com.itheima.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {

    /*对名称不一致的字段解决方法：
    1、手动封装
    @Results({@Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")})
    2、起别名
    @Select("select id, name, create_time createTime, update_time updateTime from dept")
    3、开启驼峰命名
    表中字段名：abc_xyz  类中属性名：abcXyz
    修改yml文件：
    mybatis:
        configuration:
        map-underscore-to-camel-case: true
    */
    @Select("select id, name, create_time createTime, update_time updateTime from dept order by update_time")
    public List<Dept> findAll();

    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into dept(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    @Select("select id, name, create_time createTime, update_time updateTime from dept where id = #{id}")
    Dept getById(Integer id);

    @Update("update dept set name = #{name},update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
