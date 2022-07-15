package com.example.demo.manager.impl;

import com.example.demo.dao.DepartmentDao;
import com.example.demo.entity.DepartmentDO;
import com.example.demo.manager.DepartmentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentManagerImpl implements DepartmentManager {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public List<DepartmentDO> getDepartmentList() {
        return departmentDao.getDepartmentList();
    }
}
