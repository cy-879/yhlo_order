package com.yhlo.oa.service;

import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PublicDataService {
    public List<CustomerSaleDataVO> queryCustomerList(String kunnr,String vkorg);
    public List<It_Kna1VO> querySalesmanList(String kunnr);
    public List<TvkotVO> querySaleOrgList(String vkorg);
    public List<T023tVO> queryMaterialGroupList();
    public List<TspatVO> queryProductGroupList(String spart);
    public List<TvtwtVO> queryDistributionChannelList(String vtweg);
    public List<TvkbtVO> querySalesDeptList(String vkbur);
    public List<TvgrtVO> querySalesGroupList(String vkgrp);
    public List<It_KnvvVO> queryCustomerSalesViewList(String vkorg);
    public List<TvautVO> queryOrderReasonList(String augru);
    public List<TvsttVO> queryShippingPointList(String vstel);
    public List<TvsbtVO> queryshippingConditionsList(String vsbed);
    public List<TvzbtVO> getTermOfPayment(String kunnr,String vkorg);
    public List<TvzbtVO> queryTermOfPaymentList();
    public List<It_MaraVO> queryMaterialList(String param,String vkorg);
    public List<It_MarmVO> queryOrderUnitList(String matnr,String meinh);
    public List<T001wVO> getFactoryList(String matnr,String vkorg);
    public List<T001wVO> queryAllFactoryList();
    public List<T001lVO> queryPositionList(String werks);
    public List<It_MvkeVO> getTaxList(String matnr,String vkorg);
    public List<It_MarcVO> getMarcListByParam(String matnr,String werks);
    public List<T023tVO> getMaterialGroupListByMatkl(String matkl);
    public List<T142tVO> getStorageTemperatureByRaube(String raube);
    public List<It_Ztsd_012VO> getCustomerMaterial(String kunnr,String vkorg,String matnr);
    public List<It_But000VO> queryCustomerGeneralData(String partner);
    public List<It_Ztsd_002VO> queryMultiAngleTrade(String kunnr,String vkorg);
    public List<It_MaraVO> getMaterialListByMaterialNo(String matnr,String vkorg);
    public List<CommonVo> getShippingPoint(String matnr,String vkorg);

    public List<ShowRebatePolicyInfoVO> showRebatePolicyList(List<ShowRebatePolicyInfoVO> reList);

}
