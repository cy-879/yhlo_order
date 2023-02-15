package com.yhlo.oa.service.iml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.*;
import com.yhlo.oa.mapper.PublicDataMapper;
import com.yhlo.oa.mapper.RebatePolicyMapper;
import com.yhlo.oa.service.PublicDataService;
import com.yhlo.oa.service.RebatePolicyService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cy
 * @ClassName: 公共基础数据查询
 * @Description:
 * @date 2022/7/1814:06
 */

@Service
public class PublicDataServiceImpl implements PublicDataService {

    @Autowired
    private PublicDataMapper pdMapper;

    @Override
    public List<CustomerSaleDataVO> queryCustomerList(String kunnr,String vkorg) {
        return pdMapper.queryCustomerList(kunnr,vkorg);
    }

    @Override
    public List<It_Kna1VO> querySalesmanList(String kunnr) {
        return pdMapper.querySalesmanList(kunnr);
    }

    @Override
    public List<TvkotVO> querySaleOrgList(String vkorg) {
        return pdMapper.querySaleOrgList(vkorg);
    }

    @Override
    public List<T023tVO> queryMaterialGroupList() {
        return pdMapper.queryMaterialGroupList();
    }

    @Override
    public List<TspatVO> queryProductGroupList(String spart) {
        return pdMapper.queryProductGroupList(spart);
    }

    @Override
    public List<TvtwtVO> queryDistributionChannelList(String vtweg) {
        return pdMapper.queryDistributionChannelList(vtweg);
    }

    @Override
    public List<TvkbtVO> querySalesDeptList(String vkbur) {
        return pdMapper.querySalesDeptList(vkbur);
    }

    @Override
    public List<TvgrtVO> querySalesGroupList(String vkgrp) {
        return pdMapper.querySalesGroupList(vkgrp);
    }

    @Override
    public List<It_KnvvVO> queryCustomerSalesViewList(String vkorg) {
        return pdMapper.queryCustomerSalesViewList(vkorg);
    }

    @Override
    public List<TvautVO> queryOrderReasonList(String augru) {
        return pdMapper.queryOrderReasonList(augru);
    }

    @Override
    public List<TvsttVO> queryShippingPointList(String vstel) {
        return pdMapper.queryShippingPointList(vstel);
    }

    @Override
    public List<TvsbtVO> queryshippingConditionsList(String vsbed) {
        return pdMapper.queryshippingConditionsList(vsbed);
    }

    @Override
    public List<TvzbtVO> getTermOfPayment(String kunnr, String vkorg) {
        return pdMapper.getTermOfPayment(kunnr,vkorg);
    }

    @Override
    public List<TvzbtVO> queryTermOfPaymentList() {
        return pdMapper.queryTermOfPaymentList();
    }

    @Override
    public List<It_MaraVO> queryMaterialList(String param,String vkorg) {
        return pdMapper.queryMaterialList(param,vkorg);
    }

    @Override
    public List<It_MarmVO> queryOrderUnitList(String matnr,String meinh) {
        return pdMapper.queryOrderUnitList(matnr,meinh);
    }

    @Override
    public List<T001wVO> getFactoryList(String matnr, String vkorg) {
        return pdMapper.getFactoryList(matnr,vkorg);
    }

    @Override
    public List<T001wVO> queryAllFactoryList() {
        return pdMapper.queryAllFactoryList();
    }

    @Override
    public List<T001lVO> queryPositionList(String werks) {
        return pdMapper.queryPositionList(werks);
    }

    @Override
    public List<ShowRebatePolicyInfoVO> showRebatePolicyList(List<ShowRebatePolicyInfoVO> reList) {

        List<ShowRebatePolicyInfoVO> showList = new ArrayList<ShowRebatePolicyInfoVO>();

        for (ShowRebatePolicyInfoVO srp:reList) {

           if( null != srp.getKunnr() && !"".equals(srp.getKunnr()) &&
               null != srp.getVkorg() && !"".equals(srp.getVkorg()) &&
               null != srp.getKunnr_ec() && !"".equals(srp.getKunnr_ec()) &&
               null != srp.getMatkl() && !"".equals(srp.getMatkl())){

               List<ShowRebatePolicyInfoVO> ls1 = pdMapper.showRebatePolicyList1(srp.getKunnr(), srp.getVkorg(),
                       srp.getKunnr_ec(), srp.getMatkl());
               if(ls1.size()>0){
                   showList.addAll(ls1);
               }else{
                   List<ShowRebatePolicyInfoVO> ls2 = pdMapper.showRebatePolicyList2(srp.getKunnr(), srp.getVkorg(),
                           srp.getKunnr_ec());
                   if(ls2.size()>0){
                       showList.addAll(ls2);
                   }else{
                       List<ShowRebatePolicyInfoVO> ls3 = pdMapper.showRebatePolicyList3(srp.getKunnr(), srp.getVkorg(),
                               srp.getMatkl());
                       if(ls3.size()>0){
                           showList.addAll(ls3);
                       }else{
                           List<ShowRebatePolicyInfoVO> ls4 = pdMapper.showRebatePolicyList4(srp.getKunnr(), srp.getVkorg());
                           if(ls4.size()>0){
                               showList.addAll(ls4);
                           }else{
                               List<ShowRebatePolicyInfoVO> ls5 = pdMapper.showRebatePolicyList5(srp.getKunnr());
                               if(ls5.size()>0){
                                   showList.addAll(ls5);
                               }
                           }
                       }
                   }
               }

           }else if(null != srp.getKunnr() && !"".equals(srp.getKunnr()) &&
                   null != srp.getVkorg() && !"".equals(srp.getVkorg()) &&
                   null != srp.getKunnr_ec() && !"".equals(srp.getKunnr_ec())){

               List<ShowRebatePolicyInfoVO> ls2 = pdMapper.showRebatePolicyList2(srp.getKunnr(), srp.getVkorg(),
                       srp.getKunnr_ec());
               if(ls2.size()>0){
                   showList.addAll(ls2);
               }else{
                   List<ShowRebatePolicyInfoVO> ls4 = pdMapper.showRebatePolicyList4(srp.getKunnr(), srp.getVkorg());
                   if(ls4.size()>0){
                       showList.addAll(ls4);
                   }else{
                       List<ShowRebatePolicyInfoVO> ls5 = pdMapper.showRebatePolicyList5(srp.getKunnr());
                       if(ls5.size()>0){
                           showList.addAll(ls5);
                       }
                   }
               }

           }else if(null != srp.getKunnr() && !"".equals(srp.getKunnr()) &&
                   null != srp.getVkorg() && !"".equals(srp.getVkorg()) &&
                   null != srp.getMatkl() && !"".equals(srp.getMatkl())){

               List<ShowRebatePolicyInfoVO> ls3 = pdMapper.showRebatePolicyList3(srp.getKunnr(), srp.getVkorg(),
                       srp.getMatkl());
               if(ls3.size()>0){
                   showList.addAll(ls3);
               }else{
                   List<ShowRebatePolicyInfoVO> ls4 = pdMapper.showRebatePolicyList4(srp.getKunnr(), srp.getVkorg());
                   if(ls4.size()>0){
                       showList.addAll(ls4);
                   }else{
                       List<ShowRebatePolicyInfoVO> ls5 = pdMapper.showRebatePolicyList5(srp.getKunnr());
                       if(ls5.size()>0){
                           showList.addAll(ls5);
                       }
                   }
               }

           }else if(null != srp.getKunnr() && !"".equals(srp.getKunnr()) &&
                   null != srp.getVkorg() && !"".equals(srp.getVkorg())){

               List<ShowRebatePolicyInfoVO> ls4 = pdMapper.showRebatePolicyList4(srp.getKunnr(), srp.getVkorg());
               if(ls4.size()>0){
                   showList.addAll(ls4);
               }else{
                   List<ShowRebatePolicyInfoVO> ls5 = pdMapper.showRebatePolicyList5(srp.getKunnr());
                   if(ls5.size()>0){
                       showList.addAll(ls5);
                   }
               }

           }else if(null != srp.getKunnr() && !"".equals(srp.getKunnr())){

               List<ShowRebatePolicyInfoVO> ls5 = pdMapper.showRebatePolicyList5(srp.getKunnr());
               if(ls5.size()>0){
                   showList.addAll(ls5);
               }
           }
        }
        return showList;
    }

    @Override
    public List<It_MvkeVO> getTaxList(String matnr, String vkorg) {
        return pdMapper.getTaxList(matnr,vkorg);
    }

    @Override
    public List<It_MarcVO> getMarcListByParam(String matnr, String werks) {
        return pdMapper.getMarcListByParam(matnr,werks);
    }

    @Override
    public List<T023tVO> getMaterialGroupListByMatkl(String matkl) {
        return pdMapper.getMaterialGroupListByMatkl(matkl);
    }

    @Override
    public List<T142tVO> getStorageTemperatureByRaube(String raube) {
        return pdMapper.getStorageTemperatureByRaube(raube);
    }

    @Override
    public List<It_Ztsd_012VO> getCustomerMaterial(String kunnr, String vkorg, String matnr) {
        return pdMapper.getCustomerMaterial(kunnr,vkorg,matnr);
    }

    @Override
    public List<It_But000VO> queryCustomerGeneralData(String partner) {
        return pdMapper.queryCustomerGeneralData(partner);
    }

    @Override
    public List<It_Ztsd_002VO> queryMultiAngleTrade(String kunnr,String vkorg) {
        return pdMapper.queryMultiAngleTrade(kunnr,vkorg);
    }

    @Override
    public List<It_MaraVO> getMaterialListByMaterialNo(String matnr,String vkorg) {
        return pdMapper.getMaterialListByMaterialNo(matnr,vkorg);
    }

    @Override
    public List<CommonVo> getShippingPoint(String matnr, String vkorg) {
        return pdMapper.getShippingPoint(matnr,vkorg);
    }

}
