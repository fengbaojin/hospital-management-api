package com.example.demo.controller;

import com.example.demo.bo.*;
import com.example.demo.common.ResponseResult;
import com.example.demo.entity.DrugSaleDO;
import com.example.demo.entity.DrugWareHouseDO;
import com.example.demo.entity.Pager;

import com.example.demo.service.DrugInventoryService;
import com.example.demo.utils.BeanUtils;
import com.example.demo.utils.ExcelUtil;
import com.example.demo.utils.ToKenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author fengbaojin
 * @date 2021/10/28
 */
@RestController
@RequestMapping(value = "/drugInventory")
@Api(description = "药品管理")
public class DrugInventoryController {

    @Autowired
    private DrugInventoryService drugInventoryService;

    @Autowired
    private ExcelUtil excelUtil;

    @GetMapping(value = "/listDrugPage")
    @ApiOperation(value = "获取药品库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "drugName", value = "药瓶名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startMoney", value = "价格开始区间", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "endMoney", value = "价格结束区间", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(paramType = "query", name = "startProductionDate", value = "生产日期起始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endProductionDate", value = "生产日期结束时间", dataType = "Date"),
            @ApiImplicitParam(name = "pageIndex", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query")
    })
    public ResponseResult<Pager<DrugBO>> listDrugPage(HttpServletRequest request,
                                                      String drugName,
                                                      Integer startMoney,
                                                      Integer endMoney,
                                                      @DateTimeFormat(pattern = "yyyy-MM-dd") Date startProductionDate,
                                                      @DateTimeFormat(pattern = "yyyy-MM-dd") Date endProductionDate,
                                                      @RequestParam(defaultValue = "1") int pageIndex,
                                                      @RequestParam(defaultValue = "10") int pageSize) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        Pager<DrugBO> drugBOPager = drugInventoryService.listDrugPage(drugName, startMoney, endMoney, startProductionDate, endProductionDate, pageIndex, pageSize);
        return ResponseResult.success(drugBOPager);
    }

    @PostMapping(value = "/updateDrug")
    @ApiOperation(value = "修改药品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header")
    })
    public ResponseResult<?> updateDrug(HttpServletRequest request, @RequestBody DrugBO drugBO) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        if (BeanUtils.isEmpty(drugBO)) {
            return ResponseResult.error("无效参数", "无效的修改参数");
        }
        return drugInventoryService.updateDrug(userId, drugBO);
    }


    @GetMapping(value = "/getDrugNoteList")
    @ApiOperation(value = "获取药品修改记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "drugId", value = "药品ID", dataType = "Long", paramType = "query"),
    })
    public ResponseResult<List<DrugNoteBO>> getDrugNoteList(HttpServletRequest request, Long drugId) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        if (BeanUtils.isEmpty(drugId)) {
            return ResponseResult.error("无效的药品ID", "无效的药品信息，请确认");
        }
        List<DrugNoteBO> drugNoteBOList = drugInventoryService.getDrugNoteList(drugId);
        return ResponseResult.success(drugNoteBOList);
    }


    @PostMapping(value = "/insetDrugWareHouse")
    @ApiOperation(value = "新增药品入库信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header")
    })
    public ResponseResult<?> insetDrugWareHouse(HttpServletRequest request, @RequestBody DrugWareHouseBO drugWareHouseBO) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        if (BeanUtils.isEmpty(drugWareHouseBO)) {
            return ResponseResult.error("无效参数", "无效的修改参数");
        }
        return drugInventoryService.insetDrugWareHouse(userId, drugWareHouseBO);
    }


    @GetMapping(value = "/listDrugRecordPage")
    @ApiOperation(value = "获取药品入库记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "drugName", value = "药瓶名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(paramType = "query", name = "startDate", value = "入库日期起始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endDate", value = "入库日期结束时间", dataType = "Date"),
            @ApiImplicitParam(name = "pageIndex", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query")
    })
    public ResponseResult<Pager<DrugWareHouseDO>> listDrugRecordPage(HttpServletRequest request,
                                                                     String drugName,
                                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                                     @RequestParam(defaultValue = "1") int pageIndex,
                                                                     @RequestParam(defaultValue = "10") int pageSize) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        Pager<DrugWareHouseDO> drugWareHouseDOPager = drugInventoryService.listDrugRecordPage(drugName, startDate, endDate, pageIndex, pageSize);
        return ResponseResult.success(drugWareHouseDOPager);
    }


    @PostMapping(value = "/saleOfDrugs")
    @ApiOperation(value = "出售药品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header")
    })
    public ResponseResult<?> saleOfDrugs(HttpServletRequest request, @RequestBody SaleDrugBO saleDrugBO) throws Exception {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        if (BeanUtils.isEmpty(saleDrugBO)) {
            return ResponseResult.error("无效参数", "无效的修改参数");
        }
        return drugInventoryService.saleOfDrugs(userId, saleDrugBO);
    }


    @GetMapping(value = "/listDrugSaleRecordPage")
    @ApiOperation(value = "获取药品出库记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "drugName", value = "药瓶名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(paramType = "query", name = "startDate", value = "入库日期起始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endDate", value = "入库日期结束时间", dataType = "Date"),
            @ApiImplicitParam(name = "pageIndex", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query")
    })
    public ResponseResult<Pager<DrugSaleDO>> listDrugSaleRecordPage(HttpServletRequest request,
                                                                    String drugName,
                                                                    @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                    @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                                    @RequestParam(defaultValue = "1") int pageIndex,
                                                                    @RequestParam(defaultValue = "10") int pageSize) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        Pager<DrugSaleDO> drugSaleDOPager = drugInventoryService.listDrugSaleRecordPage(drugName, startDate, endDate, pageIndex, pageSize);
        return ResponseResult.success(drugSaleDOPager);
    }

    @GetMapping(value = "/getDrugByName")
    @ApiOperation(value = "获取所有药品集齐名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header")
    })
    public ResponseResult<List<DrugBO>> getDrugByName(HttpServletRequest request) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        List<DrugBO> list = drugInventoryService.getDrugByName();
        return ResponseResult.success(list);
    }


    @GetMapping(value = "/getTurnoverByLastThirtyDay")
    @ApiOperation(value = "获取过去一个月销售情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header")
    })
    public ResponseResult<SalesDetailsBO> getTurnoverByLastThirtyDay(HttpServletRequest request) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        SalesDetailsBO salesDetailsBO = drugInventoryService.getTurnoverByLastThirtyDay();
        return ResponseResult.success(salesDetailsBO);
    }

    @GetMapping(value = "/getTurnoverByEveryMonth")
    @ApiOperation(value = "获取某月每天销售额度记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"),
            @ApiImplicitParam(paramType = "query", name = "month", value = "起始月份", dataType = "Date")
    })
    public ResponseResult<List<SalesDetailsBO>> getTurnoverByEveryMonth(HttpServletRequest request,
                                                                        @DateTimeFormat(pattern = "yyyy-MM") Date month) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        if (BeanUtils.isEmpty(month)) {
            return ResponseResult.error("无效月份", "请选择月份");
        }
        List<SalesDetailsBO> salesDetailsBOList = drugInventoryService.getTurnoverByEveryMonth(month);
        return ResponseResult.success(salesDetailsBOList);
    }


    @GetMapping(value = "/getRankingByEveryMonth")
    @ApiOperation(value = "获取每个月销售额排名(药品)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"),
            @ApiImplicitParam(paramType = "query", name = "startDate", value = "起始时间", dataType = "Date"),
            @ApiImplicitParam(paramType = "query", name = "endDate", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(name = "pageIndex", value = "当前页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(paramType = "query", name = "orderBy", value = "字段和需排序顺序", dataType = "String")
    })
    public ResponseResult<Pager<SalesQuotaRankBO>> getRankingByEveryMonth(HttpServletRequest request,
                                                                          String orderBy,
                                                                          @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                          @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                                          @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                                                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        Pager<SalesQuotaRankBO> pager = drugInventoryService.getRankingByEveryMonth(startDate, endDate, pageSize, pageIndex, orderBy);
        return ResponseResult.success(pager);
    }

    @GetMapping(value = "/getSalesVolumeByName")
    @ApiOperation(value = "获取药品销售额情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"),
            @ApiImplicitParam(paramType = "query", name = "month", value = "起始月份", dataType = "Date")
    })
    public ResponseResult<List<SalesDetailsBO>> getSalesVolumeByName(HttpServletRequest request,
                                                                     @DateTimeFormat(pattern = "yyyy-MM") Date month) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        if (BeanUtils.isEmpty(month)) {
            return ResponseResult.error("无效月份", "请选择月份");
        }
        List<SalesDetailsBO> list = drugInventoryService.getSalesVolumeByName(month);
        return ResponseResult.success(list);
    }


    @GetMapping(value = "/getAmountByName")
    @ApiOperation(value = "获取药品销售数量情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"),
            @ApiImplicitParam(paramType = "query", name = "month", value = "起始月份", dataType = "Date")
    })
    public ResponseResult<List<SalesDetailsBO>> getAmountByName(HttpServletRequest request,
                                                                @DateTimeFormat(pattern = "yyyy-MM") Date month) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        if (BeanUtils.isEmpty(month)) {
            return ResponseResult.error("无效月份", "请选择月份");
        }
        List<SalesDetailsBO> list = drugInventoryService.getAmountByName(month);
        return ResponseResult.success(list);
    }

    @GetMapping(value = "/getSaleByName")
    @ApiOperation(value = "获取药品销售情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"),
            @ApiImplicitParam(paramType = "query", name = "month", value = "起始月份", dataType = "Date")
    })
    public ResponseResult<SalesBO> getSaleByName(HttpServletRequest request,
                                                 @DateTimeFormat(pattern = "yyyy-MM") Date month) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        if (BeanUtils.isEmpty(month)) {
            return ResponseResult.error("无效月份", "请选择月份");
        }
        SalesBO salesBO = drugInventoryService.getSaleByName(month);
        return ResponseResult.success(salesBO);
    }


    @PostMapping(value = "/updateSellingPrice")
    @ApiOperation(value = "修改售价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header"),

    })
    public ResponseResult<?> updateSellingPrice(HttpServletRequest request,
                                                @RequestBody AddSaleBO addSaleBO
    ) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        return drugInventoryService.updateSellingPrice(addSaleBO);
    }

    @GetMapping(value = "/getNoSellingPrice")
    @ApiOperation(value = "获取没有售价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header")
    })
    public ResponseResult<List<DrugBO>> getNoSellingPrice(HttpServletRequest request) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        List<DrugBO> list = drugInventoryService.getNoSellingPrice();
        return ResponseResult.success(list);
    }

    @PostMapping(value = "/batchSellingPrice")
    @ApiOperation(value = "批量修改售价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header")
    })
    public ResponseResult<?> batchSellingPrice(HttpServletRequest request,
                                               @RequestBody List<AddSaleBO> addSaleList) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        return drugInventoryService.batchSellingPrice(addSaleList);
    }

    @PostMapping(value = "/importStore")
    @ApiOperation(value = "上传入库文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header")
    })
    public ResponseResult<?> importStore(HttpServletRequest request) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 通过参数名获取指定文件
        MultipartFile file = multipartRequest.getFile("file");
        if (file == null) {
            return ResponseResult.error("参数错误", "文件不能为空");
        }
        ResponseResult<?> data = drugInventoryService.importStoreS(userId, file);
        return ResponseResult.success(data);
    }

    @PostMapping(value = "/downloadExcel")
    @ApiOperation(value = "上传入库文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "登录以后获取的token信息", dataType = "String", paramType = "header")
    })
    public ResponseResult downloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = ToKenUtils.getUserId(request);
        if (BeanUtils.isEmpty(userId)) {
            return ResponseResult.error("无效的用户ID", "无效的登录信息，请登录");
        }
//        drugInventoryService.writeExcel("学生信息" ,DrugWareHouseDO.class ,response,getStudentList());
        return ResponseResult.success();
    }

}
