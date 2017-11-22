package com.yidatec.mapper;

import com.yidatec.model.Customer;
import com.yidatec.model.FollowHistory;
import com.yidatec.vo.CustomerVO;
import com.yidatec.vo.ProjectVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */
@Mapper
public interface CustomerMapper {
    /**
     * 载入客户可列表
     * @return
     */
    @Select("SELECT * FROM T_CUSTOMER WHERE id = #{id}")
    Customer selectCustomer(String id);

    @Select("SELECT * FROM T_CUSTOMER WHERE name = #{name}")
    Customer getCustomer(String name);

    @Select("SELECT * FROM T_CUSTOMER order by convert(name using gbk) asc")
    List<Customer> selectCustomerAll();


    @SelectProvider(type=com.yidatec.mapper.CustomerQueryProvider.class,method = "selectCustomer")
    List<CustomerVO> selectCustomerList(CustomerVO customerVO);

    @SelectProvider(type=com.yidatec.mapper.CustomerQueryProvider.class,method = "countCustomer")
    int countCustomerList(CustomerVO customerVO);

    @Insert("INSERT INTO T_CUSTOMER (id,source,name,industry,nature,country,province,city,region,address,level,state,photo,creatorId,createTime,modifierId,modifyTime) VALUES (#{id},#{source},#{name}," +
            "#{industry},#{nature},#{country},#{province},#{city},#{region},#{address},#{level},#{state},#{photo},#{creatorId},#{createTime},#{modifierId},#{modifyTime})")
    int create(Customer customer);

    @Update("UPDATE T_CUSTOMER SET `name`=#{name},source=#{source},industry=#{industry},nature=#{nature}," +
            "country=#{country},province=#{province},city=#{city},region=#{region},address=#{address},level=#{level},state=#{state},photo=#{photo}," +
            "modifierId=#{modifierId}," +
            "modifyTime=#{modifyTime} WHERE id=#{id}")
    int update(Customer customer);

    @Insert("INSERT INTO T_CUSTOMER_CONTACT (customerId,contactId) VALUES (#{customerid},#{contactid})")
    int createRelation(@Param(value="customerid") String customerid,@Param(value="contactid") String contactid);


    @Delete("DELETE FROM T_CUSTOMER_CONTACT  WHERE customerId =#{customerId}")
    int deleteCustomerRelation(String customerId);

    /**
     * 客户大表下载
     * @return
     */
    @Select("SELECT a.id,c.`name` as saleName,b.`name` as customerName,d.`value` as industry," +
            "h.`name` as campaignName,b.country,b.province,b.city,a.exhibitionNumber," +
            "f.`name` as userName,f.mobilePhone,g.value as position,f.email,a.remark,a.createTime" +
            " FROM `T_PROJECT` a" +
            " LEFT JOIN T_CUSTOMER b ON a.customerId = b.id" +
            " LEFT JOIN T_CAMPAIGN h ON a.campaignId = h.id" +
            " LEFT JOIN T_CUSTOMER_CONTACT e ON e.customerId = b.id"+
            " LEFT JOIN T_CONTACT f ON e.contactId = f.id"+
            " LEFT JOIN T_DICTIONARY g ON f.position = g.id"+
            " LEFT JOIN T_USER c ON a.developSaleId = c.id"+
            " LEFT JOIN T_DICTIONARY d ON b.industry = d.id"+
            " where a.createTime BETWEEN DATE(#{startTime}) AND DATE(#{endTime})"+
            " ORDER BY a.createTime ")
    List<ProjectVO> customerDownLoad(@Param(value = "startTime") String startTime, @Param(value = "endTime")String endTime);

    @Select("SELECT * " +
            " FROM T_FOLLOW_HISTORY" +
            " WHERE id" +
            " IN (SELECT historyId FROM T_CUSTOMER_HISTORY" +
            " WHERE customerId = #{id}) order by followTime asc")
    List<FollowHistory> getHistoryList(String id);

    @Delete("DELETE FROM T_FOLLOW_HISTORY  WHERE id in (SELECT historyId FROM T_CUSTOMER_HISTORY WHERE customerId=#{id})")
    int deleteHistory(String id);

    @Delete("DELETE FROM T_CUSTOMER_HISTORY WHERE customerId=#{id}")
    int deleteCustomerHistory(String id);

    @Insert("INSERT INTO T_FOLLOW_HISTORY (id,followDetail,followTime,creatorId,createTime,modifierId,modifyTime) VALUES (#{id},#{followDetail},#{followTime}," +
            "#{creatorId},#{createTime},#{modifierId},#{modifyTime})")
    int createHistory(FollowHistory history);

    @Insert("INSERT INTO T_CUSTOMER_HISTORY (customerId,historyId) VALUES (#{customerid},#{historyId})")
    int createCustomerHistory(@Param(value="customerid") String customerid,@Param(value="historyId") String historyId);


}
