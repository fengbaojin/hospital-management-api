package com.example.demo.dao;

import com.example.demo.bo.*;
import com.example.demo.entity.DrugDO;
import com.example.demo.entity.DrugNoteDO;
import com.example.demo.entity.DrugSaleDO;
import com.example.demo.entity.DrugWareHouseDO;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface DrugInventoryDao {

    @Select("<script>" +
            "select a.*\n" +
            "from drug a\n" +
            "where !a.is_deleted \n" +
            "<if test='drugName != null '>" +
            "and (a.`drug_name` like concat('%', #{drugName}, '%')) " +
            "</if>" +
            "<if test='startMoney != null and endMoney != null'>" +
            "and a.money <![CDATA[ >= ]]> #{startMoney} and a.money <![CDATA[ <= ]]> #{endMoney}\n" +
            "</if>" +
            "<if test='startProductionDate != null and endProductionDate != null'>" +
            "and DATE_FORMAT(a.production_date,'%Y-%m-%d') <![CDATA[ >= ]]> DATE_FORMAT(#{startProductionDate},'%Y-%m-%d')\n" +
            "and DATE_FORMAT(a.production_date,'%Y-%m-%d') <![CDATA[ <= ]]> DATE_FORMAT(#{endProductionDate},'%Y-%m-%d') " +
            "</if>" +
            "order by a.created_date desc\n" +
            "</script>")
    List<DrugDO> listDrugPage(@Param("drugName") String drugName, @Param("startMoney") Integer startMoney, @Param("endMoney") Integer endMoney, @Param("startProductionDate") Date startProductionDate, @Param("endProductionDate") Date endProductionDate);

    @Select("select a.* from drug a where !a.is_deleted and a.drug_id = #{drugId}")
    DrugDO getDrugOne(Long drugId);

    @Update("update drug set drug_name = #{drugName}, money = #{money}, `describe` = #{describe}, production_date = #{productionDate}, quantity = #{quantity} , modifier_id = #{modifierId} , modified_date = #{modifiedDate} \n" +
            "where drug_id = #{drugId} ")
    void updateDrug(DrugDO drugDO);

    @Insert("insert into drug_note(drug_id, drug_name, `describe`, created_id)\n" +
            "values \n" +
            "(#{drugId}, #{drugName}, #{describe}, #{createdId})")
    void insertDrugNoteDO(DrugNoteDO drugNoteDO);

    @Select("select a.* , b. name createdName \n" +
            "from drug_note a " +
            "left join user b on b.user_id = a.created_id\n " +
            "where !a.is_deleted and a.drug_id = #{drugId}\n" +
            "order by a.created_date desc\n")
    List<DrugNoteBO> getDrugNoteList(Long drugId);

    @Select("select a.* from drug a where !a.is_deleted and a.drug_name = #{drugName}")
    DrugDO getIsDrugOne(String drugName);

    @Insert("insert into drug(drug_name, money, company_name, production_date, quantity, `describe` ,created_id)\n" +
            "values \n" +
            "( #{drugName}, #{money}, #{companyName}, #{productionDate}, #{quantity}, #{describe}, #{createdId})")
    @Options(useGeneratedKeys = true, keyProperty = "drugId", keyColumn = "drugId")
    Long insertDrug(DrugDO drug);

    @Insert("insert into drug_record (drug_id ,drug_name, `describe`, production_date, quantity, created_id)\n" +
            "values \n" +
            "(#{drugId},#{drugName}, #{describe}, #{productionDate}, #{quantity}, #{createdId})")
    void insertDrugRecord(DrugWareHouseDO record);

    @Select("<script>" +
            "select a.*, b. name createdName\n" +
            "from drug_record a\n" +
            "left join user b on b.user_id = a.created_id\n " +
            "where !a.is_deleted \n" +
            "<if test='drugName != null '>" +
            "and (a.`drug_name` like concat('%', #{drugName}, '%')) " +
            "</if>" +
            "<if test='startDate != null and endDate != null'>" +
            "and DATE_FORMAT(a.created_date,'%Y-%m-%d') <![CDATA[ >= ]]> DATE_FORMAT(#{startDate},'%Y-%m-%d')\n" +
            "and DATE_FORMAT(a.created_date,'%Y-%m-%d') <![CDATA[ <= ]]> DATE_FORMAT(#{endDate},'%Y-%m-%d') " +
            "</if>" +
            "order by a.created_date desc\n" +
            "</script>")
    List<DrugWareHouseDO> listDrugRecordPage(@Param("drugName") String drugName, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Insert("insert into drug_sale_record (drug_id ,drug_name, production_date, `comment`, selling_price, price_difference, money, quantity, created_id)\n" +
            "values \n" +
            "(#{drugId},#{drugName}, #{productionDate}, #{comment}, #{sellingPrice}, #{priceDifference}, #{money}, #{quantity}, #{createdId})")
    void insertDrugSale(DrugSaleDO drugSaleDO);

    @Select("<script>" +
            "select a.*, b. name createdName\n" +
            "from drug_sale_record a\n" +
            "left join user b on b.user_id = a.created_id\n " +
            "where !a.is_deleted \n" +
            "<if test='drugName != null '>" +
            "and (a.`drug_name` like concat('%', #{drugName}, '%')) " +
            "</if>" +
            "<if test='startDate != null and endDate != null'>" +
            "and DATE_FORMAT(a.created_date,'%Y-%m-%d') <![CDATA[ >= ]]> DATE_FORMAT(#{startDate},'%Y-%m-%d')\n" +
            "and DATE_FORMAT(a.created_date,'%Y-%m-%d') <![CDATA[ <= ]]> DATE_FORMAT(#{endDate},'%Y-%m-%d') " +
            "</if>" +
            "order by a.created_date desc\n" +
            "</script>")
    List<DrugSaleDO> listDrugSaleRecordPage(@Param("drugName") String drugName, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Select("SELECT\n" +
            "round(ifnull(sum(a.selling_price),0)) salesVolume,\n" +
            "round(ifnull(sum(a.quantity),0)) amount,\n" +
            "round(ifnull(sum(a.price_difference),0)) profitNumber\n" +
            "FROM drug_sale_record a\n" +
            "WHERE !a.is_deleted and DATE_FORMAT(a.created_date,'%Y-%m-%d') >=  DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 MONTH),'%Y-%m-%d')")
    SalesDetailsBO getTurnoverByLastThirtyDay();

    @Select("<script>" +
            "SELECT\n" +
            "date_format(a.created_date, '%Y-%m-%d') date,\n" +
            "round(ifnull(sum(a.selling_price),0)) salesVolume,\n" +
            "round(ifnull(sum(a.quantity),0)) amount,\n" +
            "round(ifnull(sum(a.price_difference),0)) profitNumber\n" +
            "FROM drug_sale_record a\n" +
            "WHERE !a.is_deleted " +
            "<if test='month != null '>" +
            "and DATE_FORMAT(a.created_date,'%Y-%m') <![CDATA[ = ]]> DATE_FORMAT(#{month},'%Y-%m')\n" +
            "</if>" +
            "group by date\n"+
            "</script>")
    List<SalesDetailsBO> getTurnoverByEveryMonth(@Param("month") Date month);

    @Select("<script>" +
            "SELECT\n" +
            "b.name name,\n" +
            "round(ifnull(sum(a.selling_price),0)) salesVolume,\n" +
            "round(ifnull(sum(a.quantity),0)) amount\n" +
            "FROM drug_sale_record a\n" +
            "left join user b on b.user_id = a.created_id\n " +
            "WHERE !a.is_deleted " +
            "<if test='startDate != null and endDate != null'>" +
            "and DATE_FORMAT(a.created_date,'%Y-%m-%d') <![CDATA[ >= ]]> DATE_FORMAT(#{startDate},'%Y-%m-%d')\n" +
            "and DATE_FORMAT(a.created_date,'%Y-%m-%d') <![CDATA[ <= ]]> DATE_FORMAT(#{endDate},'%Y-%m-%d')\n " +
            "</if>" +
            "<if test='startDate == null and endDate == null'>" +
            "and DATE_FORMAT(a.created_date,'%Y-%m') <![CDATA[ = ]]> DATE_FORMAT(now(),'%Y-%m')\n" +
            "</if>" +
            "group by b.name\n"+
            "</script>")
    List<SalesQuotaRankBO> getRankingByEveryMonth(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Select("select a.drug_id, a.drug_name\n" +
            "from drug a \n" +
            "where !a.is_deleted")
    List<DrugBO> getDrugByName();

    @Select("<script>" +
            "SELECT\n" +
            "a.drug_name drugName,\n" +
            "round(ifnull(sum(a.selling_price),0)) salesVolume\n" +
            "FROM drug_sale_record a\n" +
            "WHERE !a.is_deleted " +
            "<if test='month != null '>" +
            "and DATE_FORMAT(a.created_date,'%Y-%m') <![CDATA[ = ]]> DATE_FORMAT(#{month},'%Y-%m')\n" +
            "</if>" +
            "group by a.drug_id\n"+
            "order by salesVolume desc\n" +
            "</script>")
    List<SalesDetailsBO> getSalesVolumeByName(Date month);

    @Select("<script>" +
            "SELECT\n" +
            "a.drug_name drugName,\n" +
            "round(ifnull(sum(a.quantity),0)) amount\n" +
            "FROM drug_sale_record a\n" +
            "WHERE !a.is_deleted " +
            "<if test='month != null '>" +
            "and DATE_FORMAT(a.created_date,'%Y-%m') <![CDATA[ = ]]> DATE_FORMAT(#{month},'%Y-%m')\n" +
            "</if>" +
            "group by a.drug_id\n"+
            "order by amount desc\n" +
            "</script>")
    List<SalesDetailsBO> getAmountByName(Date month);

    @Update("update drug set selling_price = #{sellingPrice},comment = #{comment}\n" +
            "where drug_id = #{drugId} ")
    boolean updateSellingPrice(AddSaleBO addSaleBO);

    @Select("select a.* from drug a where !a.is_deleted and a.selling_price is null")
    List<DrugBO> getNoSellingPrice();



    @Update({
            "<script>",
            "<foreach collection='list' item='item' index='index' separator=';'>",
            "update drug b",
            "set b.selling_price= #{item.sellingPrice} where b.drug_id= #{item.drugId}",
            "</foreach>",
            "</script>"
    })
    boolean batchSellingPrice(@Param("list")  List<AddSaleBO> addSaleList);
}
