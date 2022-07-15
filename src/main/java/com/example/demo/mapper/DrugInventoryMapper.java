package com.example.demo.mapper;

import com.example.demo.bo.DrugBO;
import com.example.demo.bo.DrugWareHouseBO;
import com.example.demo.bo.SaleDrugBO;
import com.example.demo.bo.SalesDetailsBO;
import com.example.demo.common.Constants;
import com.example.demo.common.ResponseResult;
import com.example.demo.entity.*;
import com.example.demo.exception.BusinessException;
import com.example.demo.utils.BeanUtils;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.Objects;

public class DrugInventoryMapper {

    public static DrugBO mapperToDrugBO(DrugDO drugDO) {
        if (BeanUtils.isNotEmpty(drugDO)) {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(drugDO, DrugBO.class);
        }
        return null;
    }

    public static DrugNoteDO mapperToDrugNoteDO(DrugDO drugDO, DrugBO drugBO, Long userId) {
        DrugNoteDO drugNoteDO = new DrugNoteDO();
        if (BeanUtils.isNotEmpty(drugDO) && BeanUtils.isNotEmpty(drugBO)) {
            drugNoteDO.setDrugId(drugBO.getDrugId());
            drugNoteDO.setDrugName(drugBO.getDrugName());
            StringBuilder updateMessage = new StringBuilder();
            if (!Objects.equals(drugDO.getDrugName(), drugBO.getDrugName())) {
                updateMessage.append("药品名称修改为:" + drugBO.getDrugName() + ";");
            }
            if (!Objects.equals(drugDO.getProductionDate(), drugBO.getProductionDate())) {
                updateMessage.append("药品生产日期修改为:" + drugBO.getProductionDate() + ";");
            }
            if (!(drugDO.getMoney().compareTo(drugBO.getMoney()) == 0)) {
                updateMessage.append("药品价格修改为:" + drugBO.getMoney() + ";");
            }
            if (!Objects.equals(drugDO.getQuantity(), drugBO.getQuantity())) {
                updateMessage.append("药品数量修改为:" + drugBO.getQuantity() + ";");
            }
            if (!Objects.equals(drugDO.getDescribe(), drugBO.getDescribe())) {
                updateMessage.append("药品描述修改为:" + drugBO.getDescribe() + ";");
            }
            drugNoteDO.setDescribe(updateMessage.toString());
            drugNoteDO.setCreatedId(userId);
        }
        return drugNoteDO;
    }

    public static DrugDO mapperToDrugDO(Long userId, DrugBO drugBO) {
        ModelMapper modelMapper = new ModelMapper();
        DrugDO drugDO = modelMapper.map(drugBO, DrugDO.class);
        if (BeanUtils.isNotEmpty(drugDO)) {
            drugDO.setModifierId(userId);
            drugDO.setModifiedDate(new Date());
        }
        return drugDO;
    }

    public static DrugDO mapperToDrugWareHouseDO(DrugWareHouseBO drugWareHouseBO, Long userId) {
        ModelMapper modelMapper = new ModelMapper();
        DrugDO drugDO = modelMapper.map(drugWareHouseBO, DrugDO.class);
        if (BeanUtils.isNotEmpty(drugDO)) {
            drugDO.setCreatedId(userId);
        }
        return drugDO;
    }

    public static DrugWareHouseDO mapperToDrugRecord(DrugWareHouseBO drugWareHouseBO, Long userId, Long drugId) {
        ModelMapper modelMapper = new ModelMapper();
        DrugWareHouseDO drugWareHouseDO = modelMapper.map(drugWareHouseBO, DrugWareHouseDO.class);
        if (BeanUtils.isNotEmpty(drugWareHouseDO)) {
            drugWareHouseDO.setDrugId(drugId);
            drugWareHouseDO.setCreatedId(userId);
        }
        return drugWareHouseDO;
    }

    public static DrugDO mapperToWareHouseDrugDO(DrugDO drugDO, Long userId, DrugWareHouseBO drugWareHouseBO) {
        drugDO.setQuantity(drugDO.getQuantity() + drugWareHouseBO.getQuantity());
        drugDO.setModifierId(userId);
        drugDO.setModifiedDate(new Date());
        return drugDO;
    }

    public static DrugDO mapperToSaleDrugDO(DrugDO drugDO, Long userId, SaleDrugBO saleDrugBO) {
        if (saleDrugBO.getQuantity() > drugDO.getQuantity()) {
            throw new BusinessException(Constants.CODE_ERROR, "药品数量不足，当前数量为" + drugDO.getQuantity());
        }
        drugDO.setQuantity(drugDO.getQuantity() - saleDrugBO.getQuantity());
        drugDO.setModifierId(userId);
        drugDO.setModifiedDate(new Date());
        return drugDO;
    }

    public static DrugSaleDO mapperToDrugSaleDO(SaleDrugBO saleDrugBO, Long userId, DrugDO drugDO) {
        DrugSaleDO drugSaleDO = new DrugSaleDO();
        drugSaleDO.setDrugId(saleDrugBO.getDrugId());
        drugSaleDO.setDrugName(drugDO.getDrugName());
        drugSaleDO.setProductionDate(drugDO.getProductionDate());
        drugSaleDO.setComment(saleDrugBO.getComment());
        drugSaleDO.setQuantity(saleDrugBO.getQuantity());
        drugSaleDO.setMoney(drugDO.getMoney());
        drugSaleDO.setSellingPrice(drugDO.getSellingPrice());
        if (BeanUtils.isEmpty(drugDO.getSellingPrice())) {
            throw new BusinessException(Constants.CODE_ERROR, "售价为空，请添加售价");
        }
        drugSaleDO.setPriceDifference(drugDO.getSellingPrice().subtract(drugDO.getMoney()));
        drugSaleDO.setCreatedId(userId);
        return drugSaleDO;
    }

    public static DrugWareHouseBO mapperToDrugWareHouseBO(DrugWareHouseExcelDO drugWareHouseExcelDO) {
        ModelMapper modelMapper = new ModelMapper();
        DrugWareHouseBO drugWareHouseBO = modelMapper.map(drugWareHouseExcelDO, DrugWareHouseBO.class);
        return drugWareHouseBO;
    }
}
