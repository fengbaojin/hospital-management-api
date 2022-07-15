package com.example.demo.service.impl;

import com.example.demo.bo.*;
import com.example.demo.common.ResponseResult;
import com.example.demo.entity.*;
import com.example.demo.manager.DrugInventoryManager;
import com.example.demo.mapper.DrugInventoryMapper;
import com.example.demo.service.DrugInventoryService;
import com.example.demo.utils.BeanUtils;
import com.example.demo.utils.DateUtils;
import com.example.demo.utils.RedisUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DrugInventoryServiceImpl implements DrugInventoryService {

    @Autowired
    private DrugInventoryManager drugInventoryManager;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Pager<DrugBO> listDrugPage(String drugName, Integer startMoney, Integer endMoney, Date startProductionDate, Date endProductionDate, int pageIndex, int pageSize) {
        return drugInventoryManager.listDrugPage(drugName, startMoney, endMoney, startProductionDate, endProductionDate, pageIndex, pageSize);
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Transactional
    @Override
    public ResponseResult<?> updateDrug(Long userId, DrugBO drugBO) {
        // 查询该ID药品详情
        DrugDO drugDO = drugInventoryManager.getDrugOne(drugBO.getDrugId());
        // 获取修改详情
        DrugNoteDO drugNoteDO = DrugInventoryMapper.mapperToDrugNoteDO(drugDO, drugBO, userId);
        if (drugNoteDO.getDescribe().equals("")) {
            return ResponseResult.error(-1, "无改动");
        }
        DrugDO updateDrugDO = DrugInventoryMapper.mapperToDrugDO(userId, drugBO);
        drugInventoryManager.updateDrug(updateDrugDO);
        drugInventoryManager.insertDrugNoteDO(drugNoteDO);
        return ResponseResult.success();
    }

    @Override
    public List<DrugNoteBO> getDrugNoteList(Long drugId) {
        return drugInventoryManager.getDrugNoteList(drugId);
    }

    @Transactional
    @Override
    public ResponseResult<?> insetDrugWareHouse(Long userId, DrugWareHouseBO drugWareHouseBO) {
        // 查询是否有该药品
        DrugDO drugDO = drugInventoryManager.getIsDrugOne(drugWareHouseBO.getDrugName());
        Long drugRecordId = null;
        if (BeanUtils.isEmpty(drugDO)) {
            DrugDO drug = DrugInventoryMapper.mapperToDrugWareHouseDO(drugWareHouseBO, userId);
            drugRecordId = drugInventoryManager.insertDrug(drug);
        } else {
            StringBuilder checkMessage = checkDrug(drugDO, drugWareHouseBO);
            if (checkMessage.length() > 0) {
                return ResponseResult.error(-1, checkMessage.toString());
            }
            DrugDO wareHouseDrug = DrugInventoryMapper.mapperToWareHouseDrugDO(drugDO, userId, drugWareHouseBO);
            drugInventoryManager.updateDrug(wareHouseDrug);
            drugRecordId = drugDO.getDrugId();
        }
        DrugWareHouseDO record = DrugInventoryMapper.mapperToDrugRecord(drugWareHouseBO, userId, drugRecordId);
        drugInventoryManager.insertDrugRecord(record);
        return ResponseResult.success();
    }

    private StringBuilder checkDrug(DrugDO drugDO, DrugWareHouseBO drugWareHouseBO) {
        StringBuilder checkMessage = new StringBuilder();
        StringBuilder errorMessage = new StringBuilder();
        if (!Objects.equals(drugDO.getDrugName(), drugWareHouseBO.getDrugName())) {
            errorMessage.append("药品名称:" + drugWareHouseBO.getDrugName() + "不符;");
        }
        if (!Objects.equals(drugDO.getProductionDate(), drugWareHouseBO.getProductionDate())) {
            errorMessage.append("药品生产日期:" + drugWareHouseBO.getProductionDate() + "不符;");
        }
        if (!Objects.equals(drugDO.getMoney(), drugWareHouseBO.getMoney())) {
            errorMessage.append("药品价格:" + drugWareHouseBO.getMoney() + "不符;");
        }
        if (!Objects.equals(drugDO.getCompanyName(), drugWareHouseBO.getCompanyName())) {
            errorMessage.append("药品公司:" + drugWareHouseBO.getDescribe() + "不符;");
        }
        if (errorMessage.length() > 0) {
            checkMessage.append("该药品已存在[");
            checkMessage.append(errorMessage);
            checkMessage.append("]");
        }
        return checkMessage;
    }

    @Override
    public Pager<DrugWareHouseDO> listDrugRecordPage(String drugName, Date startDate, Date endDate, int pageIndex, int pageSize) {
        return drugInventoryManager.listDrugRecordPage(drugName, startDate, endDate, pageIndex, pageSize);
    }

    @Transactional
    @Override
    public ResponseResult<?> saleOfDrugs(Long userId, SaleDrugBO saleDrugBO) {
        // 查询该ID药品详情
        DrugDO drugDO = drugInventoryManager.getDrugOne(saleDrugBO.getDrugId());
        if (BeanUtils.isEmpty(drugDO)) {
            return ResponseResult.error(-1, "无此药品");
        }
        DrugDO saleDrug = DrugInventoryMapper.mapperToSaleDrugDO(drugDO, userId, saleDrugBO);
        // 修改数量
        drugInventoryManager.updateDrug(saleDrug);
        // 出库列表增加记录
        DrugSaleDO drugSaleDO = DrugInventoryMapper.mapperToDrugSaleDO(saleDrugBO, userId, drugDO);
        drugInventoryManager.insertDrugSale(drugSaleDO);
        return ResponseResult.success();
    }

    @Override
    public Pager<DrugSaleDO> listDrugSaleRecordPage(String drugName, Date startDate, Date endDate, int pageIndex, int pageSize) {
        return drugInventoryManager.listDrugSaleRecordPage(drugName, startDate, endDate, pageIndex, pageSize);
    }

    @Override
    public SalesDetailsBO getTurnoverByLastThirtyDay() {
        return drugInventoryManager.getTurnoverByLastThirtyDay();
    }

    @Override
    public List<SalesDetailsBO> getTurnoverByEveryMonth(Date month) {
        List<SalesDetailsBO> list = this.getEveryDateByMonth(month);
        List<SalesDetailsBO> drugList = drugInventoryManager.getTurnoverByEveryMonth(month);
        return list.stream().map(p -> mapperToSaleDrugBO(p, drugList)).collect(Collectors.toList());
    }

    private SalesDetailsBO mapperToSaleDrugBO(SalesDetailsBO salesDetailsBO, List<SalesDetailsBO> drugList) {
        SalesDetailsBO check = drugList.stream().filter(p -> p.getDate().compareTo(salesDetailsBO.getDate()) == 0).findAny().orElse(null);
        if (BeanUtils.isEmpty(check)) {
            return salesDetailsBO;
        }
        salesDetailsBO.setAmount(check.getAmount());
        salesDetailsBO.setProfitNumber(check.getProfitNumber());
        salesDetailsBO.setSalesVolume(check.getSalesVolume());
        return salesDetailsBO;
    }

    /**
     * 获取该月份所有天数
     *
     * @param month
     * @return
     */
    private List<SalesDetailsBO> getEveryDateByMonth(Date month) {
        List<SalesDetailsBO> list = new ArrayList<>();
        if (BeanUtils.isEmpty(month)) {
            return list;
        }
        LocalDate firstDay = DateUtils.convertToLocalDate(month).with(TemporalAdjusters.firstDayOfMonth()); // 获取当前月的第一天
        LocalDate lastDay = DateUtils.convertToLocalDate(month).with(TemporalAdjusters.lastDayOfMonth());

        for (LocalDate currentdate = firstDay; currentdate.isBefore(lastDay) || currentdate.isEqual(lastDay); currentdate = currentdate.plusDays(1)) {
            SalesDetailsBO salesDetailsBO = new SalesDetailsBO(Date.from(currentdate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant()));
            list.add(salesDetailsBO);
        }
        return list;
    }


    @Override
    public List<DrugBO> getDrugByName() {
        return drugInventoryManager.getDrugByName();
    }

    @Override
    public Pager<SalesQuotaRankBO> getRankingByEveryMonth(Date startDate, Date endDate, int pageSize, int pageIndex, String orderBy) {
        return drugInventoryManager.getRankingByEveryMonth(startDate, endDate, pageSize, pageIndex, orderBy);
    }

    @Override
    public List<SalesDetailsBO> getSalesVolumeByName(Date month) {
        // 获取各个药品详情
        List<SalesDetailsBO> salesDetailsBOList = drugInventoryManager.getSalesVolumeByName(month);
        // 小于5条直接输出
        if (salesDetailsBOList.size() < 6) {
            return salesDetailsBOList;
        }
        // 大于5转成其他
        return transformation(salesDetailsBOList);
    }

    private List<SalesDetailsBO> transformation(List<SalesDetailsBO> salesDetailsBOList) {
        // 前五条数据
        List<SalesDetailsBO> font = salesDetailsBOList.subList(0, 5);
        // 后五条数据
        List<SalesDetailsBO> after = salesDetailsBOList.subList(5, salesDetailsBOList.size());
        SalesDetailsBO salesDetailsBO = new SalesDetailsBO();
        salesDetailsBO.setDrugName("其他");
        salesDetailsBO.setSalesVolume(after.stream().map(SalesDetailsBO::getSalesVolume).reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0)));
        salesDetailsBO.setAmount(after.stream().map(SalesDetailsBO::getAmount).reduce(Integer::sum).orElse(0));
        salesDetailsBO.setProfitNumber(after.stream().map(SalesDetailsBO::getProfitNumber).reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0)));
        font.add(salesDetailsBO);
        return font;
    }

    @Override
    public List<SalesDetailsBO> getAmountByName(Date month) {
        // 获取各个药品详情
        List<SalesDetailsBO> salesDetailsBOList = drugInventoryManager.getAmountByName(month);
        // 小于5条直接输出
        if (salesDetailsBOList.size() < 6) {
            return salesDetailsBOList;
        }
        // 大于5转成其他
        return transformation(salesDetailsBOList);
    }

    @Override
    public SalesBO getSaleByName(Date month) {
        SalesBO salesBO = new SalesBO();
        salesBO.setSalesVolumeList(this.getSalesVolumeByName(month));
        salesBO.setAmountList(this.getAmountByName(month));
        return salesBO;
    }

    @Override
    public ResponseResult<?> updateSellingPrice(AddSaleBO addSaleBO) {
        boolean count = drugInventoryManager.updateSellingPrice(addSaleBO);
        if (!count) {
            return ResponseResult.error(-1, "修改失败");
        }
        return ResponseResult.success();
    }

    @Override
    public List<DrugBO> getNoSellingPrice() {
        return drugInventoryManager.getNoSellingPrice();
    }

    @Override
    public ResponseResult<?> batchSellingPrice(List<AddSaleBO> addSaleList) {
        for (AddSaleBO addSaleBO : addSaleList) {
            if (BeanUtils.isNotEmpty(addSaleBO.getDrugId()) && BeanUtils.isNotEmpty(addSaleBO.getSellingPrice())) {
                boolean count = drugInventoryManager.updateSellingPrice(addSaleBO);
            }
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<?> test() {
        redisTemplate.opsForValue().set("yyyyy", "myValue");
        redisTemplate.opsForValue().set("ttttt", "hhh");
        redisUtil.expire("ttttt", 12);
        long token = redisUtil.getExpire("ttttt");
        System.out.printf(String.valueOf(token));
        System.out.printf(redisTemplate.opsForValue().get("token"));

        System.out.println(redisTemplate.opsForValue().get("yyyyy"));
        return ResponseResult.success();
    }


    @Override
    public ResponseResult<?> importStore(Long userId, List<DrugWareHouseExcelDO> dtoList) {
        StringBuilder checkMessage = new StringBuilder();
        List<DrugWareHouseBO> list = dtoList.stream().map(DrugInventoryMapper::mapperToDrugWareHouseBO).collect(Collectors.toList());
        for (DrugWareHouseBO drugWareHouseBO : list) {
            // 查询是否有该药品
            DrugDO drugDO = drugInventoryManager.getIsDrugOne(drugWareHouseBO.getDrugName());
            Long drugRecordId = null;
            if (BeanUtils.isEmpty(drugDO)) {
                DrugDO drug = DrugInventoryMapper.mapperToDrugWareHouseDO(drugWareHouseBO, userId);
                drugRecordId = drugInventoryManager.insertDrug(drug);
            } else {
                checkMessage = checkDrug(drugDO, drugWareHouseBO);
                if (checkMessage.length() > 0) {
                    continue;
                }
                DrugDO wareHouseDrug = DrugInventoryMapper.mapperToWareHouseDrugDO(drugDO, userId, drugWareHouseBO);
                drugInventoryManager.updateDrug(wareHouseDrug);
                drugRecordId = drugDO.getDrugId();
            }
            DrugWareHouseDO record = DrugInventoryMapper.mapperToDrugRecord(drugWareHouseBO, userId, drugRecordId);
            drugInventoryManager.insertDrugRecord(record);
        }
        if (checkMessage.length() > 0) {
            return ResponseResult.error(-1, checkMessage.toString());
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<?> importStoreS(Long userId, MultipartFile file) throws IOException {
        StringBuilder checkMessage = new StringBuilder();
        List<DrugWareHouseBO> list = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0); // 获取第一个张表
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            DrugWareHouseBO drugWareHouseBO = new DrugWareHouseBO();
            Row row = sheet.getRow(i);  // 获取行
            if (row == null) break;
            // 获取单元格中的值
            drugWareHouseBO.setDrugName(row.getCell(0).getStringCellValue()); //药品名称
            drugWareHouseBO.setMoney(BigDecimal.valueOf(31));
            drugWareHouseBO.setQuantity(15);
            drugWareHouseBO.setCompanyName(row.getCell(4).getStringCellValue());   //
            drugWareHouseBO.setDescribe(row.getCell(5).getStringCellValue());   //
            list.add(drugWareHouseBO);
        }
        for (DrugWareHouseBO drugWareHouseBO : list) {
            // 查询是否有该药品
            DrugDO drugDO = drugInventoryManager.getIsDrugOne(drugWareHouseBO.getDrugName());
            Long drugRecordId = null;
            if (BeanUtils.isEmpty(drugDO)) {
                DrugDO drug = DrugInventoryMapper.mapperToDrugWareHouseDO(drugWareHouseBO, userId);
                drugRecordId = drugInventoryManager.insertDrug(drug);
            } else {
//                checkMessage = checkDrug(drugDO, drugWareHouseBO);
//                if (checkMessage.length() > 0) {
//                    continue;
//                }
                DrugDO wareHouseDrug = DrugInventoryMapper.mapperToWareHouseDrugDO(drugDO, userId, drugWareHouseBO);
                drugInventoryManager.updateDrug(wareHouseDrug);
                drugRecordId = drugDO.getDrugId();
            }
            DrugWareHouseDO record = DrugInventoryMapper.mapperToDrugRecord(drugWareHouseBO, userId, drugRecordId);
            drugInventoryManager.insertDrugRecord(record);
        }
        if (checkMessage.length() > 0) {
            return ResponseResult.error(-1, checkMessage.toString());
        }
        return ResponseResult.success();
    }
}