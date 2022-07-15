package com.example.demo.service.impl;

import com.example.demo.entity.DepartmentDO;
import com.example.demo.manager.DepartmentManager;
import com.example.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentManager departmentManager;

    @Override
    public List<DepartmentDO> getDepartmentList() {
        return departmentManager.getDepartmentList();
    }
}
