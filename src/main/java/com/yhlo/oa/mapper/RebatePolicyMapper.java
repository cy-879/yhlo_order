package com.yhlo.oa.mapper;

import com.yhlo.oa.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RebatePolicyMapper {

    public void insertRebatePolicy(RebatePolicyVO rebatePolicyVO);
    public void insertRebatePolicyDetail(RebatePolicyDetailVO rebatePolicyDetailVO);
    public List<RebatePolicyVO> queryRebatePolicyList(@Param("rebateFormName")String rebateFormName_search,@Param("name1")String name1,@Param("status")String status);
    public List<RebatePolicyVO> queryRebatePolicyToBeApprovedList(@Param("rebateFormName")String rebateFormName_search,@Param("name1")String name1);
    public List<RebatePolicyVO> checkRebateNoExists(@Param("rebateFormNo")String rebateFormNo);
    public List<RebatePolicyDetailVO> getRebatePolicyDetailList(@Param("rebateFormNo")String rebateFormNo);
    public void submitForApproval(RebatePolicyVO rebatePolicyVO);
    public void updateApprovalStatu(RebatePolicyVO rebatePolicyVO);

}
