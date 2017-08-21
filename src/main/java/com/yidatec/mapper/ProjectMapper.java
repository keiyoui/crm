package com.yidatec.mapper;

import com.yidatec.model.ProjectEntity;
import com.yidatec.vo.ProjectVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */
@Mapper
public interface ProjectMapper {

    @Insert("INSERT INTO T_PROJECT (id,name,type,customerId,campaignId,code,budget,exhibitionNumber,area,setupTime,tearDownTime," +
            "cost,degreeOfImportance,potential,designProgress,pmId,projectProgress,factoryProgress,projectScore,designScore,pmScore,developSaleId,traceSaleId,photo,state," +
            "creatorId,createTime,modifierId,modifyTime) VALUES (#{id},#{name},#{type},#{customerId},#{campaignId},#{code},#{budget},#{exhibitionNumber}," +
            "#{area},#{setupTime},#{tearDownTime},#{cost},#{degreeOfImportance},#{potential},#{designProgress},#{pmId},#{projectProgress},#{factoryProgress},#{projectScore},#{designScore},#{pmScore},#{developSaleId},#{traceSaleId},#{photo},#{state}," +
            "#{creatorId},#{createTime},#{modifierId},#{modifyTime})")
    int create(ProjectEntity project);

    @Update("UPDATE T_PROJECT SET `name`=#{name},type=#{type},customerId=#{customerId},campaignId=#{campaignId},code=#{code},budget=#{budget},exhibitionNumber=#{exhibitionNumber}," +
            "area=#{area},setupTime=#{setupTime},tearDownTime=#{tearDownTime},cost=#{cost},degreeOfImportance=#{degreeOfImportance},potential=#{potential},designProgress=#{designProgress},pmId=#{pmId},projectProgress=#{projectProgress},factoryProgress=#{factoryProgress}," +
            "projectScore=#{projectScore},designScore=#{designScore},pmScore=#{pmScore},developSaleId=#{developSaleId},traceSaleId=#{traceSaleId},photo=#{photo},state=#{state},modifierId=#{modifierId}," +
            "modifyTime=#{modifyTime} WHERE id=#{id}")
    int update(ProjectEntity project);

    @Insert("INSERT INTO T_PROJECT_DESIGNER (projectId,designerId) VALUES (#{projectId},#{designerId})")
    int createDesignerRelation(@Param(value="projectId") String projectId, @Param(value="designerId") String designerId);

    @Delete("DELETE FROM T_PROJECT_DESIGNER  WHERE projectId =#{projectId}")
    int deleteDesignerRelation(String projectId);

    @Insert("INSERT INTO T_PROJECT_FACTORY (projectId,factoryId) VALUES (#{projectId},#{factoryId})")
    int createFactoryRelation(@Param(value="projectId") String projectId, @Param(value="factoryId") String factoryId);

    @Delete("DELETE FROM T_PROJECT_FACTORY  WHERE projectId =#{projectId}")
    int deleteFactoryRelation(String projectId);

    @SelectProvider(type=com.yidatec.mapper.ProjectQueryProvider.class,method = "selectProject")
    List<ProjectEntity> selectProjectList(ProjectVO projectVO);

    @SelectProvider(type=com.yidatec.mapper.ProjectQueryProvider.class,method = "countProject")
    int countCustomerList(ProjectVO projectVO);

    @Select("SELECT * FROM T_PROJECT WHERE id = #{id}")
    ProjectEntity selectProject(String id);

    @Select("SELECT designerId FROM T_PROJECT_DESIGNER WHERE projectId = #{id}")
    List<String> selectDesigner(String id);

    @Select("SELECT factoryId FROM T_PROJECT_FACTORY WHERE projectId = #{id}")
    List<String> selectFactory(String id);

}