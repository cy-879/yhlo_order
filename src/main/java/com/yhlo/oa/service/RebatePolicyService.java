package com.yhlo.oa.service;

import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RebatePolicyService {

    public String insertRebatePolicy(RebatePolicyVO rebatePolicyVO, List<RebatePolicyDetailVO> detailVOList);
    public List<RebatePolicyVO> queryRebatePolicyList(String rebateFormName,String name1,String status);
    public List<RebatePolicyVO> queryRebatePolicyToBeApprovedList(String rebateFormName,String name1);
    public List<RebatePolicyVO> checkRebateNoExists(String rebateFormNo);
    public PageInfo<RebatePolicyVO> getRebatePolicyPageInfoList(Integer currentPage, Integer pageSize,String rebateFormName,String name1,String status);
    public PageInfo<RebatePolicyVO> getRebatePolicyToBeApprovedList(Integer currentPage, Integer pageSize,String rebateFormName,String name1);
    public List<RebatePolicyDetailVO> queryRebateDetailList(String rebateFormNo);
    public String submitForApproval(List<RebatePolicyVO> list);
    public String updateApprovalStatu(List<RebatePolicyVO> list);

}
