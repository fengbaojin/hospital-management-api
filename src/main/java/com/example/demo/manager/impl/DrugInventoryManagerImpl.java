package com.example.demo.manager.impl;

import com.example.demo.bo.*;
import com.example.demo.dao.DrugInventoryDao;
import com.example.demo.entity.*;
import com.example.demo.manager.DrugInventoryManager;
import com.example.demo.mapper.DrugInventoryMapper;
import com.example.demo.utils.BeanUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrugInventoryManagerImpl implements DrugInventoryManager {

    @Autowired
    private DrugInventoryDao drugInventoryDao;

    @Override
    public Pager<DrugBO> listDrugPage(String drugName, Integer startMoney, Integer endMoney, Date startProductionDate, Date endProductionDate, int pageIndex, int pageSize) {
        Pager<DrugBO> pager = new Pager<>();
        try {
            PageHelper.startPage(pageIndex, pageSize);
            List<DrugDO> list = drugInventoryDao.listDrugPage(drugName, startMoney, endMoney, startProductionDate, endProductionDate);
            pager.setData(list.stream().map(DrugInventoryMapper::mapperToDrugBO).collect(Collectors.toList()));
            Long total = ((Page) list).getTotal();
            pager.setTotal(total);
            return pager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DrugDO getDrugOne(Long drugId) {
        return drugInventoryDao.getDrugOne(drugId);
    }

    @Transactional
    @Override
    public void updateDrug(DrugDO drugDO) {
        drugInventoryDao.updateDrug(drugDO);
    }

    @Transactional
    @Override
    public void insertDrugNoteDO(DrugNoteDO drugNoteDO) {
        drugInventoryDao.insertDrugNoteDO(drugNoteDO);
    }

    @Override
    public List<DrugNoteBO> getDrugNoteList(Long drugId) {
        return drugInventoryDao.getDrugNoteList(drugId);
    }

    @Override
    public DrugDO getIsDrugOne(String drugName) {
        return drugInventoryDao.getIsDrugOne(drugName);
    }


    @Transactional
    @Override
    public Long insertDrug(DrugDO drug) {
        return drugInventoryDao.insertDrug(drug);
    }

    @Transactional
    @Override
    public void insertDrugRecord(DrugWareHouseDO record) {
        drugInventoryDao.insertDrugRecord(record);
    }

    @Override
    public Pager<DrugWareHouseDO> listDrugRecordPage(String drugName, Date startDate, Date endDate, int pageIndex, int pageSize) {
        Pager<DrugWareHouseDO> pager = new Pager<>();
        try {
            PageHelper.startPage(pageIndex, pageSize);
            List<DrugWareHouseDO> list = drugInventoryDao.listDrugRecordPage(drugName, startDate, endDate);
            pager.setData(list);
            Long total = ((Page) list).getTotal();
            pager.setTotal(total);
            return pager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    @Override
    public void insertDrugSale(DrugSaleDO drugSaleDO) {
        drugInventoryDao.insertDrugSale(drugSaleDO);
    }

    @Override
    public Pager<DrugSaleDO> listDrugSaleRecordPage(String drugName, Date startDate, Date endDate, int pageIndex, int pageSize) {
        Pager<DrugSaleDO> pager = new Pager<>();
        try {
            PageHelper.startPage(pageIndex, pageSize);
            List<DrugSaleDO> list = drugInventoryDao.listDrugSaleRecordPage(drugName, startDate, endDate);
            pager.setData(list);
            Long total = ((Page) list).getTotal();
            pager.setTotal(total);
            return pager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SalesDetailsBO getTurnoverByLastThirtyDay() {
        return drugInventoryDao.getTurnoverByLastThirtyDay();
    }

    @Override
    public List<SalesDetailsBO> getTurnoverByEveryMonth(Date month) {
        return drugInventoryDao.getTurnoverByEveryMonth(month);
    }

    @Override
    public List<DrugBO> getDrugByName() {
        return drugInventoryDao.getDrugByName();
    }

    @Override
    public Pager<SalesQuotaRankBO> getRankingByEveryMonth(Date startDate, Date endDate, int pageSize, int pageIndex, String orderBy) {
        Pager<SalesQuotaRankBO> pager = new Pager<>();
        try {
            PageHelper.startPage(pageIndex, pageSize);
            if (BeanUtils.isEmpty(orderBy)) {
                PageHelper.orderBy("salesVolume desc");
            } else {
                PageHelper.orderBy(orderBy);
            }
            List<SalesQuotaRankBO> list = drugInventoryDao.getRankingByEveryMonth(startDate, endDate);
            pager.setData(list);
            Long total = ((Page) list).getTotal();
            pager.setTotal(total);
            return pager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SalesDetailsBO> getSalesVolumeByName(Date month) {
        return drugInventoryDao.getSalesVolumeByName(month);
    }

    @Override
    public List<SalesDetailsBO> getAmountByName(Date month) {
        return drugInventoryDao.getAmountByName(month);
    }

    @Override
    public boolean updateSellingPrice(AddSaleBO addSaleBO) {
        return drugInventoryDao.updateSellingPrice(addSaleBO);
    }

    @Override
    public List<DrugBO> getNoSellingPrice() {
        return drugInventoryDao.getNoSellingPrice();
    }

    @Override
    public boolean batchSellingPrice(List<AddSaleBO> addSaleList) {
        return drugInventoryDao.batchSellingPrice(addSaleList);
    }
}
