package com.yhlo.oa.mapper;

import com.yhlo.oa.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicDataMapper {

    public List<CustomerSaleDataVO> queryCustomerList(@Param("kunnr") String kunnr,@Param("vkorg") String vkorg);
    public List<It_Kna1VO> querySalesmanList(@Param("kunnr") String kunnr);
    public List<TvkotVO> querySaleOrgList(@Param("vkorg") String vkorg);
    public List<T023tVO> queryMaterialGroupList();
    public List<TspatVO> queryProductGroupList(@Param("spart") String spart);
    public List<TvtwtVO> queryDistributionChannelList(@Param("vtweg") String vtweg);
    public List<TvkbtVO> querySalesDeptList(@Param("vkbur") String vkbur);
    public List<TvgrtVO> querySalesGroupList(@Param("vkgrp") String vkgrp);
    public List<It_KnvvVO> queryCustomerSalesViewList(@Param("vkorg") String vkorg);
    public List<TvautVO> queryOrderReasonList(@Param("augru") String augru);
    public List<TvsttVO> queryShippingPointList(@Param("vstel") String vstel);
    public List<TvsbtVO> queryshippingConditionsList(@Param("vsbed") String vsbed);
    public List<TvzbtVO> getTermOfPayment(@Param("kunnr") String kunnr,@Param("vkorg") String vkorg);
    public List<TvzbtVO> queryTermOfPaymentList();
    public List<It_MaraVO> queryMaterialList(@Param("param") String param,@Param("vkorg") String vkorg);
    public List<It_MarmVO> queryOrderUnitList(@Param("matnr") String matnr, @Param("meinh") String meinh);
    public List<T001wVO> getFactoryList(@Param("matnr") String matnr,@Param("vkorg") String vkorg);
    public List<T001wVO> queryAllFactoryList();
    public List<T001lVO> queryPositionList(@Param("werks") String werks);


    public List<ShowRebatePolicyInfoVO> showRebatePolicyList1(@Param("kunnr")String kunnr,
                                                             @Param("vkorg")String vkorg,
                                                              @Param("kunnr_ec")String kunnr_ec,
                                                              @Param("matkl")String matkl);

    public List<ShowRebatePolicyInfoVO> showRebatePolicyList2(@Param("kunnr") String kunnr,
                                                              @Param("vkorg") String vkorg,
                                                              @Param("kunnr_ec") String kunnr_ec);

    public List<ShowRebatePolicyInfoVO> showRebatePolicyList3(@Param("kunnr") String kunnr,
                                                              @Param("vkorg") String vkorg,
                                                              @Param("matkl") String matkl);

    public List<ShowRebatePolicyInfoVO> showRebatePolicyList4(@Param("kunnr") String kunnr,
                                                              @Param("vkorg") String vkorg);

    public List<ShowRebatePolicyInfoVO> showRebatePolicyList5(@Param("kunnr") String kunnr);


    public List<It_MvkeVO> getTaxList(@Param("matnr") String matnr,@Param("vkorg") String vkorg);

    public List<It_MarcVO> getMarcListByParam(@Param("matnr") String matnr,@Param("werks") String werks);

    public List<T023tVO> getMaterialGroupListByMatkl(@Param("matkl") String matkl);

    public List<T142tVO> getStorageTemperatureByRaube(@Param("raube") String raube);

    public List<It_Ztsd_012VO> getCustomerMaterial(@Param("kunnr") String kunnr,@Param("vkorg") String vkorg,
                                                   @Param("matnr") String matnr);

    public List<It_But000VO> queryCustomerGeneralData(@Param("partner") String partner);

    public List<It_Ztsd_002VO> queryMultiAngleTrade(@Param("kunnr") String kunnr,@Param("vkorg") String vkorg);

    public List<It_MaraVO> getMaterialListByMaterialNo(@Param("matnr") String matnr,@Param("vkorg") String vkorg);

    public List<CommonVo> getShippingPoint(@Param("matnr") String matnr,@Param("vkorg") String vkorg);

}
