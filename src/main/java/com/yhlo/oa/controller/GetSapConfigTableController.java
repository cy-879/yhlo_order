package com.yhlo.oa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yhlo.oa.entity.*;
import com.yhlo.oa.service.GetSapConfigTableInfoService;
import com.yhlo.oa.service.iml.GetSapConfigTableInfoServiceImpl;
import com.yhlo.oa.util.AutoCompleteComboBoxListener;
import com.yhlo.oa.util.CommonUtil;
import com.yhlo.oa.util.SpringBeanUtil;
import com.yhlo.oa.webservice.GetSapConfigTableInfo;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author cy
 * @ClassName: GetSapConfigTableController
 * @Description:
 * @date 2022/5/1915:34
 */

@Slf4j
@FXMLController
public class GetSapConfigTableController implements Initializable {

    @FXML
    private ComboBox<String>tbcs;//下拉控件

    private ObservableList<Item> obsAll;

    private GetSapConfigTableInfoService gtService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("进入配置同步界面");
        gtService = SpringBeanUtil.getBean(GetSapConfigTableInfoServiceImpl.class);
        initComboBox();

    }


    public void loadTable(ActionEvent actionEvent) {
        System.err.println("点击了查询");

    }


    //同步所有
    public void toAddAll(ActionEvent actionEvent) {
        System.err.println("点击了同步");

        String pa = tbcs.getValue();//获取同步参数

        if(null == pa || "".equals(pa)){
            CommonUtil._alertInformation("请选择同步参数，再点击同步");
            return;
        }

        String param = pa.substring(0,pa.indexOf("-"));

        String JSONSTR1 = "<![CDATA[[{\"ZSAP_TABLE\":\""+param+"\"}]]]>";
        log.info("param==="+param);
        if("T001".equals(param.trim())){//公司代码
            List<T001VO> dataList = new ArrayList<T001VO>();
            String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T001VO to = new T001VO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        to.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        to.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        to.setBukrs((String) job.get("ZSD_01"));
                        to.setButxt((String) job.get("ZSD_02"));
                        dataList.add(to);
                    }
                }
            }

            String rs =  gtService.insertT001(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("公司代码-同步失败");
            }else{
                CommonUtil._alertInformation("公司代码-同步成功");
            }
            return;
        }

        if("TVKO".equals(param.trim())){//公司代码分配销售组织
            List<TvkoVO> dataList = new ArrayList<TvkoVO>();
            String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TvkoVO tv = new TvkoVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setVkorg((String) job.get("ZSD_01"));
                        tv.setBukrs((String) job.get("ZSD_02"));
                        dataList.add(tv);
                    }
                }
            }

            String rs =  gtService.insertTvko(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("公司代码分配销售组织-同步失败");
            }else{
                CommonUtil._alertInformation("公司代码分配销售组织-同步成功");
            }
            return;
        }

        if("TVKOT".equals(param.trim())){//销售组织
            List<TvkotVO> dataList = new ArrayList<TvkotVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TvkotVO tv = new TvkotVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setVkorg((String) job.get("ZSD_02"));
                        tv.setVtxtk((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTvkot(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("销售组织-同步失败");
            }else{
                CommonUtil._alertInformation("销售组织-同步成功");
            }
            return;
        }

        if("TVKWZ".equals(param.trim())){//销售机构
            List<TvkwzVO> dataList = new ArrayList<TvkwzVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TvkwzVO tv = new TvkwzVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setVkorg((String) job.get("ZSD_01"));
                        tv.setVtweg((String) job.get("ZSD_02"));
                        tv.setWerks((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTvkwz(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("销售机构-同步失败");
            }else{
                CommonUtil._alertInformation("销售机构-同步成功");
            }
            return;
        }

        if("TVKBT".equals(param.trim())){//销售办公室
            List<TvkbtVO> dataList = new ArrayList<TvkbtVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TvkbtVO tv = new TvkbtVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setVkbur((String) job.get("ZSD_02"));
                        tv.setBezei((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTvkbt(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("销售办公室-同步失败");
            }else{
                CommonUtil._alertInformation("销售办公室-同步成功");
            }
            return;
        }


        if("TVGRT".equals(param.trim())){//销售小组
            List<TvgrtVO> dataList = new ArrayList<TvgrtVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TvgrtVO tv = new TvgrtVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setVkgrp((String) job.get("ZSD_02"));
                        tv.setBezei((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTvgrt(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("销售小组-同步失败");
            }else{
                CommonUtil._alertInformation("销售小组-同步成功");
            }
            return;
        }

        if("TVKBZ".equals(param.trim())){//销售范围分配销售办公室
            List<TvkbzVO> dataList = new ArrayList<TvkbzVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TvkbzVO tv = new TvkbzVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setVkorg((String) job.get("ZSD_01"));
                        tv.setVkbur((String) job.get("ZSD_02"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTvkbz(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("销售范围分配销售办公室-同步失败");
            }else{
                CommonUtil._alertInformation("销售范围分配销售办公室-同步成功");
            }
            return;
        }


        if("TVBVK".equals(param.trim())){//销售办公室分配销售小组
            List<TvbvkVO> dataList = new ArrayList<TvbvkVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TvbvkVO tv = new TvbvkVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setVkbur((String) job.get("ZSD_01"));
                        tv.setVkgrp((String) job.get("ZSD_02"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTvbvk(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("销售办公室分配销售小组-同步失败");
            }else{
                CommonUtil._alertInformation("销售办公室分配销售小组-同步成功");
            }
            return;
        }

        if("TVSTT".equals(param.trim())){//装运点
            List<TvsttVO> dataList = new ArrayList<TvsttVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TvsttVO tv = new TvsttVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setVstel((String) job.get("ZSD_02"));
                        tv.setVtext((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTvstt(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("装运点-同步失败");
            }else{
                CommonUtil._alertInformation("装运点-同步成功");
            }
            return;
        }

        if("TVTWT".equals(param.trim())){//分销渠道
            List<TvtwtVO> dataList = new ArrayList<TvtwtVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TvtwtVO tv = new TvtwtVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setVtweg((String) job.get("ZSD_02"));
                        tv.setVtxtk((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTvtwt(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("分销渠道-同步失败");
            }else{
                CommonUtil._alertInformation("分销渠道-同步成功");
            }
            return;
        }


        if("TSPAT".equals(param.trim())){//产品组
            List<TspatVO> dataList = new ArrayList<TspatVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TspatVO tv = new TspatVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setSpart((String) job.get("ZSD_02"));
                        tv.setVtxtk((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTspat(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("产品组-同步失败");
            }else{
                CommonUtil._alertInformation("产品组-同步成功");
            }
            return;
        }

        if("TVZBT".equals(param.trim())){//付款条件
            List<TvzbtVO> dataList = new ArrayList<TvzbtVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TvzbtVO tv = new TvzbtVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setZterm((String) job.get("ZSD_02"));
                        tv.setVtext((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTvzbt(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("付款条件-同步失败");
            }else{
                CommonUtil._alertInformation("付款条件-同步成功");
            }
            return;
        }

        if("T042ZT".equals(param.trim())){//付款方式
            List<T042ztVO> dataList = new ArrayList<T042ztVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T042ztVO tv = new T042ztVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setZlsch((String) job.get("ZSD_02"));
                        tv.setText2((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertT042zt(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("付款方式-同步失败");
            }else{
                CommonUtil._alertInformation("付款方式-同步成功");
            }
            return;
        }

        if("T001W".equals(param.trim())){//工厂
            List<T001wVO> dataList = new ArrayList<T001wVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T001wVO tv = new T001wVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setWerks((String) job.get("ZSD_01"));
                        tv.setName1((String) job.get("ZSD_02"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertT001w(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("工厂-同步失败");
            }else{
                CommonUtil._alertInformation("工厂-同步成功");
            }
            return;
        }

        if("T001L".equals(param.trim())){//仓位
            List<T001lVO> dataList = new ArrayList<T001lVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T001lVO tv = new T001lVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setWerks((String) job.get("ZSD_01"));
                        tv.setLgort((String) job.get("ZSD_02"));
                        tv.setLgobe((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertT001l(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("仓位-同步失败");
            }else{
                CommonUtil._alertInformation("仓位-同步成功");
            }
            return;
        }

        if("T024E".equals(param.trim())){//采购组织
            List<T024eVO> dataList = new ArrayList<T024eVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T024eVO tv = new T024eVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setEkorg((String) job.get("ZSD_01"));
                        tv.setEkotx((String) job.get("ZSD_02"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertT024e(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("采购组织-同步失败");
            }else{
                CommonUtil._alertInformation("采购组织-同步成功");
            }
            return;
        }


        if("V_024".equals(param.trim())){//采购组
            List<V_024VO> dataList = new ArrayList<V_024VO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        V_024VO tv = new V_024VO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setEkgrp((String) job.get("ZSD_01"));
                        tv.setEknam((String) job.get("ZSD_02"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertV_024(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("采购组-同步失败");
            }else{
                CommonUtil._alertInformation("采购组-同步成功");
            }
            return;
        }

        if("T134T".equals(param.trim())){//物料类型
            List<T134tVO> dataList = new ArrayList<T134tVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T134tVO tv = new T134tVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setMtart((String) job.get("ZSD_02"));
                        tv.setMtbez((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertT134t(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("物料类型-同步失败");
            }else{
                CommonUtil._alertInformation("物料类型-同步成功");
            }
            return;
        }


        if("TWEWT".equals(param.trim())){//外部物料组
            List<TwewtVO> dataList = new ArrayList<TwewtVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TwewtVO tv = new TwewtVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setExtwg((String) job.get("ZSD_02"));
                        tv.setEwbez((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTwewt(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("外部物料组-同步失败");
            }else{
                CommonUtil._alertInformation("外部物料组-同步成功");
            }
            return;
        }

        if("T023T".equals(param.trim())){//物料组
            List<T023tVO> dataList = new ArrayList<T023tVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T023tVO tv = new T023tVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setMatkl((String) job.get("ZSD_02"));
                        tv.setWgbez60((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertT023t(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("物料组-同步失败");
            }else{
                CommonUtil._alertInformation("物料组-同步成功");
            }
            return;
        }

        if("T179".equals(param.trim())){//产品组层次
            List<T179VO> dataList = new ArrayList<T179VO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T179VO tv = new T179VO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setStufe((String) job.get("ZSD_01"));
                        tv.setProdh((String) job.get("ZSD_02"));
                        tv.setVtext((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertT179(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("产品组层次-同步失败");
            }else{
                CommonUtil._alertInformation("产品组层次-同步成功");
            }
            return;
        }

        if("T005U".equals(param.trim())){//中国城市
            List<T005uVO> dataList = new ArrayList<T005uVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T005uVO tv = new T005uVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setLand1((String) job.get("ZSD_02"));
                        tv.setBland((String) job.get("ZSD_03"));
                        tv.setBezei((String) job.get("ZSD_04"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertT005u(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("中国城市-同步失败");
            }else{
                CommonUtil._alertInformation("中国城市-同步成功");
            }
            return;
        }


        if("T077X".equals(param.trim())){//账户组
            List<T077xVO> dataList = new ArrayList<T077xVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T077xVO tv = new T077xVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setKtokd((String) job.get("ZSD_02"));
                        tv.setTxt30((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertT077x(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("账户组-同步失败");
            }else{
                CommonUtil._alertInformation("账户组-同步成功");
            }
            return;
        }

        if("T171T".equals(param.trim())){//销售地区
            List<T171tVO> dataList = new ArrayList<T171tVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T171tVO tv = new T171tVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setBzirk((String) job.get("ZSD_02"));
                        tv.setBztxt((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertT171t(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("销售地区-同步失败");
            }else{
                CommonUtil._alertInformation("销售地区-同步成功");
            }
            return;
        }

        if("T189T".equals(param.trim())){//价格清单
            List<T189tVO> dataList = new ArrayList<T189tVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T189tVO tv = new T189tVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setPltyp((String) job.get("ZSD_02"));
                        tv.setPtext((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertT189t(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("价格清单-同步失败");
            }else{
                CommonUtil._alertInformation("价格清单-同步成功");
            }
            return;
        }

        if("CSKT".equals(param.trim())){//成本中心描述
            List<CsktVO> dataList = new ArrayList<CsktVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        CsktVO tv = new CsktVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setKostl((String) job.get("ZSD_02"));
                        tv.setLtext((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertCskt(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("成本中心描述-同步失败");
            }else{
                CommonUtil._alertInformation("成本中心描述-同步成功");
            }
            return;
        }

        if("T005T".equals(param.trim())){//国家代码
            List<T005tVO> dataList = new ArrayList<T005tVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T005tVO tv = new T005tVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setLand1((String) job.get("ZSD_02"));
                        tv.setLandx((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertT005t(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("国家代码-同步失败");
            }else{
                CommonUtil._alertInformation("国家代码-同步成功");
            }
            return;
        }


        if("TVSBT".equals(param.trim())){//装运条件
            List<TvsbtVO> dataList = new ArrayList<TvsbtVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TvsbtVO tv = new TvsbtVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setVsbed((String) job.get("ZSD_02"));
                        tv.setVtext((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTvsbt(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("装运条件-同步失败");
            }else{
                CommonUtil._alertInformation("装运条件-同步成功");
            }
            return;
        }

        if("TVAUT".equals(param.trim())){//订单原因
            List<TvautVO> dataList = new ArrayList<TvautVO>();
             String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TvautVO tv = new TvautVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setAugru((String) job.get("ZSD_02"));
                        tv.setBezei((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTvaut(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("订单原因-同步失败");
            }else{
                CommonUtil._alertInformation("订单原因-同步成功");
            }
            return;
        }

        if("TVAGT".equals(param.trim())){//拒绝原因
            List<TvagtVO> dataList = new ArrayList<TvagtVO>();
            String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        TvagtVO tv = new TvagtVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setSpras((String) job.get("ZSD_01"));
                        tv.setAbgru((String) job.get("ZSD_02"));
                        tv.setBezei((String) job.get("ZSD_03"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertTvagt(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("拒绝原因-同步失败");
            }else{
                CommonUtil._alertInformation("拒绝原因-同步成功");
            }
            return;
        }


        if("T142T".equals(param.trim())){//存储条件
            List<T142tVO> dataList = new ArrayList<T142tVO>();
            String result = GetSapConfigTableInfo.sendRequest("SD135",JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        T142tVO tv = new T142tVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        tv.setZsap_tabel((String) job.get("ZSAP_TABLE"));
                        tv.setZsap_tabletxt((String) job.get("ZSAP_TABLETXT"));
                        tv.setRaube((String) job.get("ZSD_01"));
                        tv.setRbtxt((String) job.get("ZSD_02"));
                        dataList.add(tv);
                    }
                }
            }

            String rs = gtService.insertT142t(dataList);
            if(rs.indexOf("error")!=-1){
                CommonUtil._alertInformation("存储条件因-同步失败");
            }else{
                CommonUtil._alertInformation("存储条件-同步成功");
            }
            return;
        }

    }


    //同步增量
    public void toAdd(ActionEvent actionEvent) {
        CommonUtil._alertInformation("开发中，请勿点击");
        return;
    }


    /**
     * 初始化同步菜单选项
     */
    public void initComboBox(){

        List<Item> itemList = gtService.getConfigItem();
        if(null != itemList && itemList.size()>0){
            for (Item item :itemList ) {
                tbcs.getItems().add(item.getItemValue());
            }
        }
        //System.err.println("itemList=="+itemList);
        tbcs.setPromptText("请输入需要同步的表名参数搜索");
        tbcs.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener auto = new AutoCompleteComboBoxListener<>(tbcs);
    }
}
