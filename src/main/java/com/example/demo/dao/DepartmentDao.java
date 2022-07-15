package com.example.demo.dao;

import com.example.demo.entity.DepartmentDO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface DepartmentDao {

    @Select("select a.department_id, a.department_name from department a ")
    List<DepartmentDO> getDepartmentList();
}
