package com.yhlo.oa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yhlo.oa.entity.*;
import com.yhlo.oa.service.iml.DataSynServiceImpl;
import com.yhlo.oa.util.CommonUtil;
import com.yhlo.oa.util.Convert;
import com.yhlo.oa.util.DateUtils;
import com.yhlo.oa.webservice.GetSapConfigTableInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cy
 * @ClassName: TaskUtil
 * @Description:
 * @date 2022/5/2416:11
 */

@Slf4j
@Component // 把此类托管给 Spring，不能省略
public class TaskUtilController {

    private String startDate = DateUtils.dateTime();
    private String endDate = DateUtils.dateTime();
    private String JSONSTR1 = "<![CDATA[[{\"ERSDA_FROM\":\""+startDate+"\",\"ERSDA_TO\":\""+endDate+"\"}]]]>";
   // private String JSONSTR1 = "<![CDATA[[{\"ERSDA_FROM\":\"20220301\",\"ERSDA_TO\":\"20220407\"}]]]>";

    @Autowired
    private DataSynServiceImpl dataSynService;

    @Scheduled(cron = "${kafka.cron.global_material}") // cron表达式：每隔10分钟执行一次
    public void insertMara(){
        log.info("执行物料基本数据同步任务~");
        String result = GetSapConfigTableInfo.sendRequest("SD127",JSONSTR1);

        List<It_MaraVO> dataList = new ArrayList<It_MaraVO>();

        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_MaraVO ma = new It_MaraVO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        ma.setMatnr(job.getString("MATNR"));
                        ma.setMaktx(job.getString("MAKTX"));
                        ma.setMtart(job.getString("MTART"));
                        ma.setMatkl(job.getString("MATKL"));
                        ma.setRaube(job.getString("RAUBE"));
                        ma.setBismt(job.getString("BISMT"));
                        ma.setSpart(job.getString("SPART"));
                        ma.setPrdha(job.getString("PRDHA"));
                        ma.setMeins(job.getString("MEINS"));
                        ma.setMstae(job.getString("MSTAE"));
                        ma.setXchpf(job.getString("XCHPF"));
                        ma.setExtwg(job.getString("EXTWG"));
                        ma.setMbrsh(job.getString("MBRSH"));
                        ma.setMhdrz(job.getString("MHDRZ"));
                        ma.setMhdhb(job.getString("MHDHB"));
                        ma.setMtpos_mara(job.getString("MTPOS_MARA"));
                        ma.setZggxh(job.getString("ZGGXH"));
                        ma.setZcus01(job.getString("ZCUS01"));
                        ma.setZcus02(job.getString("ZCUS02"));
                        ma.setZcus02_1(job.getString("ZCUS02_1"));
                        ma.setZcus02_2(job.getString("ZCUS02_2"));
                        ma.setZcus03(job.getString("ZCUS03"));
                        ma.setZcus04(job.getString("ZCUS04"));
                        ma.setZcus05(job.getString("ZCUS05"));
                        ma.setZcus06(job.getString("ZCUS06"));
                        ma.setZcus07(job.getString("ZCUS07"));
                        ma.setZcus08(job.getString("ZCUS08"));
                        ma.setZcus09(job.getString("ZCUS09"));
                        ma.setZcus10(job.getString("ZCUS10"));
                        ma.setZcus11(job.getString("ZCUS11"));
                        ma.setZcus12(job.getString("ZCUS12"));
                        ma.setZcus13(job.getString("ZCUS13"));
                        ma.setZcus14(job.getString("ZCUS14"));
                        ma.setZcus15(job.getString("ZCUS15"));
                        ma.setZcus16(job.getString("ZCUS16"));
                        ma.setZcus17(job.getString("ZCUS17"));
                        ma.setZcus18(job.getString("ZCUS18"));
                        ma.setZcus19(job.getString("ZCUS19"));
                        ma.setZcus20(job.getString("ZCUS20"));
                        ma.setZcus21(job.getString("ZCUS21"));
                        ma.setZcus22(job.getString("ZCUS22"));
                        ma.setZcus23(job.getString("ZCUS23"));
                        ma.setZcus24(job.getString("ZCUS24"));
                        ma.setZcus25(job.getString("ZCUS25"));
                        ma.setZcus26(job.getString("ZCUS26"));
                        ma.setZcus27(job.getString("ZCUS27"));
                        ma.setZcus28(job.getString("ZCUS28"));
                        ma.setZcus29(job.getString("ZCUS29"));
                        ma.setZcus30(job.getString("ZCUS30"));
                        ma.setZcus31(job.getString("ZCUS31"));
                        ma.setZcus32(job.getString("ZCUS32"));
                        ma.setZcus33(job.getString("ZCUS33"));
                        ma.setZcus34(job.getString("ZCUS34"));
                        ma.setZcus35(job.getString("ZCUS35"));
                        ma.setCreate_time(job.getString("ZLAEDA_TIME"));

                        List<It_MaraVO> maList = dataSynService.checkMaraExist(ma);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(maList.size()<=0){
                            dataList.add(ma);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs = dataSynService.insertMara(dataList);
                if(rs.indexOf("error")!=-1){
                    log.info("物料基本数据新增失败"+rs);
                }else{
                    log.info("物料基本数据新增成功");
                }
            }
        }

    }


    @Scheduled(cron = "${kafka.cron.global_material}") // cron表达式：每隔10分钟执行一次
    public void insertMvke(){
        log.info("执行销售物料数据同步任务~");
        List<It_MvkeVO> dataList = new ArrayList<It_MvkeVO>();
        String result = GetSapConfigTableInfo.sendRequest("SD128",JSONSTR1);
        // System.err.println(result);

        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_MvkeVO to = new It_MvkeVO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        to.setMatnr(job.getString("MATNR"));
                        to.setVkorg(job.getString("VKORG"));
                        to.setVtweg(job.getString("VTWEG"));
                        to.setVrkme(job.getString("VRKME"));
                        to.setMtpos(job.getString("MTPOS"));
                        to.setDwerk(job.getString("DWERK"));
                        to.setKtgrm(job.getString("KTGRM"));
                        to.setLvorm(job.getString("LVORM"));
                        to.setVmsta(job.getString("VMSTA"));
                        to.setTaxm1(job.getString("TAXM1"));
                        to.setCreate_time(job.getString("ZLAEDA_TIME"));
                        List<It_MvkeVO> mvList = dataSynService.checkExist(to);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(mvList.size()<=0){
                            dataList.add(to);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs =  dataSynService.insertMvke(dataList);

                if(rs.indexOf("error")!=-1){
                    log.info("销售物料新增失败"+rs);
                }else{
                    log.info("销售物料新增成功");
                }
            }
        }
    }


    @Scheduled(cron = "${kafka.cron.global_material}") // cron表达式：每隔10分钟执行一次
    public void insertMbew() {
        log.info("执行物料评估类型数据同步任务~");
        String result = GetSapConfigTableInfo.sendRequest("SD129", JSONSTR1);
        List<It_MbewVO> dataList = new ArrayList<It_MbewVO>();
        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_MbewVO mb = new It_MbewVO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        mb.setMatnr(job.getString("MATNR"));
                        mb.setBwkey(job.getString("BWKEY"));
                        mb.setBklas(job.getString("BKLAS"));
                        mb.setLvorm(job.getString("LVORM"));
                        mb.setVprsv(job.getString("VPRSV"));
                        mb.setVerpr(job.getString("VERPR"));
                        mb.setStprs(job.getString("STPRS"));
                        mb.setPeinh(job.getString("PEINH"));
                        mb.setCreate_time(job.getString("ZLAEDA_TIME"));
                        List<It_MbewVO> mbList = dataSynService.checkMbewExist(mb);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(mbList.size()<=0){
                            dataList.add(mb);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs = dataSynService.insertMbew(dataList);
                if(rs.indexOf("error")!=-1){
                    log.info("物料评估类型新增失败"+rs);
                }else{
                    log.info("物料评估类型新增成功");
                }
            }
        }
    }

    @Scheduled(cron = "${kafka.cron.global_material}") // cron表达式：每隔10分钟执行一次
    public void insertMarm() {
        log.info("执行物料计量单位数据同步任务~");

        String result = GetSapConfigTableInfo.sendRequest("SD130", JSONSTR1);
        List<It_MarmVO> dataList = new ArrayList<It_MarmVO>();
        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_MarmVO ma = new It_MarmVO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        ma.setMatnr(job.getString("MATNR"));
                        ma.setMeinh(job.getString("MEINH"));
                        ma.setUmrez(job.getString("UMREZ"));
                        ma.setUmren(job.getString("UMREN"));
                        ma.setCreate_time(job.getString("ZLAEDA_TIME"));
                        List<It_MarmVO> maList = dataSynService.checkMarmExist(ma);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(maList.size()<=0){
                            dataList.add(ma);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs = dataSynService.insertMarm(dataList);
                if(rs.indexOf("error")!=-1){
                    log.info("物料计量单位新增失败"+rs);
                }else{
                    log.info("物料计量单位新增成功");
                }
            }
        }
    }


    @Scheduled(cron = "${kafka.cron.global_material}") // cron表达式：每隔10分钟执行一次
    public void insertMard() {
        log.info("执行物料库存数据同步任务~");

        String result = GetSapConfigTableInfo.sendRequest("SD131", JSONSTR1);
        List<It_MardVO> dataList = new ArrayList<It_MardVO>();
        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_MardVO ma = new It_MardVO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        ma.setMatnr(job.getString("MATNR"));
                        ma.setWerks(job.getString("WERKS"));
                        ma.setLgort(job.getString("LGORT"));
                        ma.setLvorm(job.getString("LVORM"));
                        ma.setLabst(job.getString("LABST"));
                        ma.setCreate_time(job.getString("ZLAEDA_TIME"));
                        List<It_MardVO> maList = dataSynService.checkMardExist(ma);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(maList.size()<=0){
                            dataList.add(ma);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs = dataSynService.insertMard(dataList);
                if(rs.indexOf("error")!=-1){
                    log.info("物料库存新增失败"+rs);
                }else{
                    log.info("物料库存新增成功");
                }
            }
        }
    }

    @Scheduled(cron = "${kafka.cron.global_material}") // cron表达式：每隔10分钟执行一次
    public void insertMarc() {
        log.info("执行物料工厂数据同步任务~");

        String result = GetSapConfigTableInfo.sendRequest("SD141", JSONSTR1);
        List<It_MarcVO> dataList = new ArrayList<It_MarcVO>();
        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_MarcVO ma = new It_MarcVO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        ma.setMatnr(job.getString("MATNR"));
                        ma.setWerks(job.getString("WERKS"));
                        ma.setLvorm(job.getString("LVORM"));
                        ma.setXchpf(job.getString("XCHPF"));
                        ma.setXchar(job.getString("XCHAR"));
                        ma.setLadgr(job.getString("LADGR"));
                        ma.setSernp(job.getString("SERNP"));
                        ma.setCreate_time(job.getString("ZLAEDA_TIME"));
                        List<It_MarcVO> maList = dataSynService.checkMarcExist(ma);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(maList.size()<=0){
                            dataList.add(ma);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs = dataSynService.insertMarc(dataList);
                if(rs.indexOf("error")!=-1){
                    log.info("物料工厂新增失败"+rs);
                }else{
                    log.info("物料工厂新增成功");
                }
            }
        }
    }


    @Scheduled(cron = "${kafka.cron.global_customer}") // cron表达式：每隔10分钟执行一次
    public void insertBut000() {
        log.info("执行客户一般数据同步任务~");

        String result = GetSapConfigTableInfo.sendRequest("SD132", JSONSTR1);
        List<It_But000VO> dataList = new ArrayList<It_But000VO>();
        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_But000VO ib = new It_But000VO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        ib.setPartner(job.getString("PARTNER"));
                        ib.setBu_group(job.getString("BU_GROUP"));
                        ib.setName_org1(job.getString("NAME_ORG1"));
                        ib.setName_org2(job.getString("NAME_ORG2"));
                        ib.setName_org3(job.getString("NAME_ORG3"));
                        ib.setName_org4(job.getString("NAME_ORG4"));
                        ib.setBu_sort1(job.getString("BU_SORT1"));
                        ib.setBu_sort2(job.getString("BU_SORT2"));
                        ib.setZynum(job.getString("ZYNUM"));
                        ib.setZyfzdat(job.getString("ZYFZDAT"));
                        ib.setZyyxdat(job.getString("ZYYXDAT"));
                        ib.setZelnum(job.getString("ZELNUM"));
                        ib.setZelfzdat(job.getString("ZELFZDAT"));
                        ib.setZjynum(job.getString("ZJYNUM"));
                        ib.setZjyfzdat(job.getString("ZJYFZDAT"));
                        ib.setZjyyxdat(job.getString("ZJYYXDAT"));
                        ib.setZyyfzdat(job.getString("ZYYFZDAT"));
                        ib.setZscbapz(job.getString("ZSCBAPZ"));
                        ib.setZscbarq(job.getString("ZSCBARQ"));
                        ib.setZfr(job.getString("ZFR"));
                        ib.setCrusr(job.getString("CRUSR"));
                        ib.setCrdat(job.getString("CRDAT"));
                        ib.setCrtim(job.getString("CRTIM"));
                        ib.setChusr(job.getString("CHUSR"));
                        ib.setChdat(job.getString("CHDAT"));
                        ib.setChtim(job.getString("CHTIM"));
                        ib.setCreate_time(job.getString("ZLAEDA_TIME"));
                        List<It_But000VO> maList = dataSynService.checkBut000Exist(ib);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(maList.size()<=0){
                            dataList.add(ib);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs =  dataSynService.insertBut000(dataList);
                if(rs.indexOf("error")!=-1){
                    log.info("客户一般数据新增失败"+rs);
                }else{
                    log.info("客户一般数据新增成功");
                }
            }
        }
    }


    @Scheduled(cron = "${kafka.cron.global_customer}") // cron表达式：每隔10分钟执行一次
    public void insertKna1() {
        log.info("执行客户一般数据2同步任务~");

        String result = GetSapConfigTableInfo.sendRequest("SD133", JSONSTR1);
        List<It_Kna1VO> dataList = new ArrayList<It_Kna1VO>();
        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_Kna1VO ik = new It_Kna1VO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        ik.setKunnr(job.getString("KUNNR"));
                        ik.setLand1(job.getString("LAND1"));
                        ik.setName1(job.getString("NAME1"));
                        ik.setName2(job.getString("NAME2"));
                        ik.setOrt01(job.getString("ORT01"));
                        ik.setPstlz(job.getString("PSTLZ"));
                        ik.setRegio(job.getString("REGIO"));
                        ik.setStras(job.getString("STRAS"));
                        ik.setTelf1(job.getString("TELF1"));
                        ik.setTelfx(job.getString("TELFX"));
                        ik.setAdrnr(job.getString("ADRNR"));
                        ik.setErdat(job.getString("ERDAT"));
                        ik.setErnam(job.getString("ERNAM"));
                        ik.setKtokd(job.getString("KTOKD"));
                        ik.setSpras(job.getString("SPRAS"));
                        ik.setTelf2(job.getString("TELF2"));
                        ik.setStcd5(job.getString("STCD5"));
                        ik.setKukla(job.getString("KUKLA"));
                        ik.setCreate_time(job.getString("ZLAEDA_TIME"));
                        List<It_Kna1VO> maList = dataSynService.checkKna1Exist(ik);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(maList.size()<=0){
                            dataList.add(ik);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs =  dataSynService.insertKna1(dataList);
                if(rs.indexOf("error")!=-1){
                    log.info("客户一般数据2新增失败"+rs);
                }else{
                    log.info("客户一般数据2新增成功");
                }
            }
        }
    }

    @Scheduled(cron = "${kafka.cron.global_customer}") // cron表达式：每隔10分钟执行一次
    public void insertKnb1() {
        log.info("执行客户主数据公司代码数据同步任务~");

        String result = GetSapConfigTableInfo.sendRequest("SD134", JSONSTR1);
        List<It_Knb1VO> dataList = new ArrayList<It_Knb1VO>();
        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_Knb1VO ik = new It_Knb1VO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        ik.setKunnr(job.getString("KUNNR"));
                        ik.setBukrs(job.getString("BUKRS"));
                        ik.setErdat(job.getString("ERDAT"));
                        ik.setErnam(job.getString("ERNAM"));
                        ik.setAkont(job.getString("AKONT"));
                        ik.setZwels(job.getString("ZWELS"));
                        ik.setZterm(job.getString("ZTERM"));
                        ik.setSperr(job.getString("SPERR"));
                        ik.setLoevm(job.getString("LOEVM"));
                        ik.setCreate_time(job.getString("ZLAEDA_TIME"));
                        List<It_Knb1VO> maList = dataSynService.checkKnb1Exist(ik);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(maList.size()<=0){
                            dataList.add(ik);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs =  dataSynService.insertKnb1(dataList);
                if(rs.indexOf("error")!=-1){
                    log.info("客户主数据公司代码数据新增失败"+rs);
                }else{
                    log.info("客户主数据公司代码数据新增成功");
                }
            }
        }
    }

    @Scheduled(cron = "${kafka.cron.global_customer}") // cron表达式：每隔10分钟执行一次
    public void insertKnvv() {
        log.info("执行客户主数据销售同步任务~");

        String result = GetSapConfigTableInfo.sendRequest("SD136", JSONSTR1);
        List<It_KnvvVO> dataList = new ArrayList<It_KnvvVO>();
        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_KnvvVO ik = new It_KnvvVO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        ik.setKunnr(job.getString("KUNNR"));
                        ik.setVkorg(job.getString("VKORG"));
                        ik.setVtweg(job.getString("VTWEG"));
                        ik.setSpart(job.getString("SPART"));
                        ik.setErnam(job.getString("ERNAM"));
                        ik.setErdat(job.getString("ERDAT"));
                        ik.setBegru(job.getString("BEGRU"));
                        ik.setLoevm(job.getString("LOEVM"));
                        ik.setVersg(job.getString("VERSG"));
                        ik.setAufsd(job.getString("AUFSD"));
                        ik.setKalks(job.getString("KALKS"));
                        ik.setKdgrp(job.getString("KDGRP"));
                        ik.setBzirk(job.getString("BZIRK"));
                        ik.setKonda(job.getString("KONDA"));
                        ik.setPltyp(job.getString("PLTYP"));
                        ik.setInco1(job.getString("INCO1"));
                        ik.setInco2(job.getString("INCO2"));
                        ik.setLifsd(job.getString("LIFSD"));
                        ik.setKzazu(job.getString("KZAZU"));
                        ik.setVsbed(job.getString("VSBED"));
                        ik.setFaksd(job.getString("FAKSD"));
                        ik.setWaers(job.getString("WAERS"));
                        ik.setKlabc(job.getString("KLABC"));
                        ik.setKtgrd(job.getString("KTGRD"));
                        ik.setZterm(job.getString("ZTERM"));
                        ik.setVwerk(job.getString("VWERK"));
                        ik.setVkgrp(job.getString("VKGRP"));
                        ik.setVkbur(job.getString("VKBUR"));
                        ik.setPrfre(job.getString("PRFRE"));
                        ik.setKkber(job.getString("KKBER"));
                        ik.setPodkz(job.getString("PODKZ"));
                        ik.setCreate_time(job.getString("ZLAEDA_TIME"));
                        List<It_KnvvVO> maList = dataSynService.checkKnvvExist(ik);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(maList.size()<=0){
                            dataList.add(ik);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs =  dataSynService.insertKnvv(dataList);
                if(rs.indexOf("error")!=-1){
                    log.info("客户主数据销售数据新增失败"+rs);
                }else{
                    log.info("客户主数据销售数据新增成功");
                }
            }
        }
    }

    @Scheduled(cron = "${kafka.cron.global_customer}") // cron表达式：每隔10分钟执行一次
    public void insertKnvp() {
        log.info("执行客户主数据合作伙伴同步任务~");

        String result = GetSapConfigTableInfo.sendRequest("SD137", JSONSTR1);
        List<It_KnvpVO> dataList = new ArrayList<It_KnvpVO>();
        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_KnvpVO ik = new It_KnvpVO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        ik.setKunnr(job.getString("KUNNR"));
                        ik.setVkorg(job.getString("VKORG"));
                        ik.setVtweg(job.getString("VTWEG"));
                        ik.setSpart(job.getString("SPART"));
                        ik.setParvw(job.getString("PARVW"));
                        ik.setParza(job.getString("PARZA"));
                        ik.setKunn2(job.getString("KUNN2"));
                        ik.setLifnr(job.getString("LIFNR"));
                        ik.setPernr(job.getString("PERNR"));
                        ik.setParnr(job.getString("PARNR"));
                        ik.setKnref(job.getString("KNREF"));
                        ik.setDefpa(job.getString("DEFPA"));
                        ik.setCreate_time(job.getString("ZLAEDA_TIME"));
                        List<It_KnvpVO> maList = dataSynService.checkKnvpExist(ik);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(maList.size()<=0){
                            dataList.add(ik);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs =  dataSynService.insertKnvp(dataList);
                if(rs.indexOf("error")!=-1){
                    log.info("客户主数据合作伙伴新增失败"+rs);
                }else{
                    log.info("客户主数据合作伙伴新增成功");
                }
            }
        }
    }


    @Scheduled(cron = "${kafka.cron.global_customer}") // cron表达式：每隔10分钟执行一次
    public void insertAdrc() {
        log.info("执行客户主数据地址信息同步任务~");

        String result = GetSapConfigTableInfo.sendRequest("SD138", JSONSTR1);
        List<It_AdrcVO> dataList = new ArrayList<It_AdrcVO>();
        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_AdrcVO ia = new It_AdrcVO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        ia.setAddrnumber(job.getString("ADDRNUMBER"));
                        ia.setName1(job.getString("NAME1"));
                        ia.setName2(job.getString("NAME2"));
                        ia.setName3(job.getString("NAME3"));
                        ia.setName4(job.getString("NAME4"));
                        ia.setStr_suppl1(job.getString("STR_SUPPL1"));
                        ia.setStr_suppl2(job.getString("STR_SUPPL2"));
                        ia.setStr_suppl3(job.getString("STR_SUPPL3"));
                        ia.setLocation(job.getString("LOCATION"));
                        ia.setRemark(job.getString("REMARK"));
                        ia.setCreate_time(job.getString("ZLAEDA_TIME"));
                        List<It_AdrcVO> maList = dataSynService.checkAdrcExist(ia);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(maList.size()<=0){
                            dataList.add(ia);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs =  dataSynService.insertAdrc(dataList);
                if(rs.indexOf("error")!=-1){
                    log.info("客户主数据地址信息新增失败"+rs);
                }else{
                    log.info("客户主数据地址信息新增成功");
                }
            }
        }
    }


    @Scheduled(cron = "${kafka.cron.global_customer}") // cron表达式：每隔10分钟执行一次
    public void insertZtsd_012() {
        log.info("执行客户物料数据同步任务~");

        String result = GetSapConfigTableInfo.sendRequest("SD139", JSONSTR1);
        List<It_Ztsd_012VO> dataList = new ArrayList<It_Ztsd_012VO>();
        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_Ztsd_012VO iz = new It_Ztsd_012VO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        iz.setKunnr(job.getString("KUNNR"));
                        iz.setVkorg(job.getString("VKORG"));
                        iz.setVtweg(job.getString("VTWEG"));
                        iz.setMatnr(job.getString("MATNR"));
                        iz.setName1(job.getString("NAME1"));
                        iz.setArktx(job.getString("ARKTX"));
                        iz.setKdmat(job.getString("KDMAT"));
                        iz.setPostx(job.getString("POSTX"));
                        iz.setZkdmat_2(job.getString("ZKDMAT_2"));
                        iz.setZkdmat_3(job.getString("ZKDMAT_3"));
                        iz.setZkdmat_4(job.getString("ZKDMAT_4"));
                        iz.setZernam1(job.getString("ZERNAM1"));
                        iz.setZerdat1(job.getString("ZERDAT1"));
                        iz.setZernam2(job.getString("ZERNAM2"));
                        iz.setZerdat2(job.getString("ZERDAT2"));
                        iz.setCreate_time(job.getString("ZLAEDA_TIME"));
                        List<It_Ztsd_012VO> maList = dataSynService.checkZtsd_012Exist(iz);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(maList.size()<=0){
                            dataList.add(iz);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs =  dataSynService.insertZtsd_012(dataList);
                if(rs.indexOf("error")!=-1){
                    log.info("客户物料数据新增失败"+rs);
                }else{
                    log.info("客户物料数据新增成功");
                }
            }
        }
    }


    @Scheduled(cron = "${kafka.cron.global_customer}") // cron表达式：每隔10分钟执行一次
    public void insertZtsd_002() {
        log.info("执行客户分配多角贸易代码同步任务~");

        String result = GetSapConfigTableInfo.sendRequest("SD142", JSONSTR1);
        List<It_Ztsd_002VO> dataList = new ArrayList<It_Ztsd_002VO>();
        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    It_Ztsd_002VO iz = new It_Ztsd_002VO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("S")){
                        iz.setKunnr(job.getString("KUNNR"));
                        iz.setVkorg(job.getString("VKORG"));
                        iz.setKnumh(job.getString("KNUMH"));
                        iz.setName1(job.getString("NAME1"));
                        iz.setC_default(job.getString("C_DEFAULT"));
                        iz.setName2(job.getString("NAME2"));
                        iz.setDatab(job.getString("DATAB"));
                        iz.setDatbi(job.getString("DATBI"));
                        iz.setLoevm_ko(job.getString("LOEVM_KO"));
                        iz.setZernam1(job.getString("ZERNAM1"));
                        iz.setZerdat1(job.getString("ZERDAT1"));
                        iz.setZernam2(job.getString("ZERNAM2"));
                        iz.setZerdat2(job.getString("ZERDAT2"));
                        iz.setCreate_time(job.getString("ZERDAT2"));
                        List<It_Ztsd_002VO> maList = dataSynService.checkZtsd_002Exist(iz);
                        //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                        if(maList.size()<=0){
                            dataList.add(iz);
                        }
                    }
                }
            }

            if(dataList.size()>0){
                String rs =  dataSynService.insertZtsd_002(dataList);
                if(rs.indexOf("error")!=-1){
                    log.info("客户分配多角贸易代码新增失败"+rs);
                }else{
                    log.info("客户分配多角贸易代码新增成功");
                }
            }
        }
    }


    @Scheduled(cron = "${kafka.cron.global_Rebate}") // cron表达式：每隔10分钟执行一次
    public void insertZtsd206() {

        log.info("执行返利策略同步任务~");

        List<Ztsd206_headVO> headList = new ArrayList<Ztsd206_headVO>();

        String param = "<![CDATA[[{\"ZDATE1\":\""+startDate+"\",\"ZDATE2\":\""+endDate+"\"}]]]>";

        String result = GetSapConfigTableInfo.sendRequest("SD140",param);

        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                for(int i=0;i<objects.size();i++){
                    Ztsd206_headVO zh = new Ztsd206_headVO();
                    JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if(job.getString("TYPE").equals("Y")){
                        zh.setZzbname(job.getString("ZZBNAME"));
                        zh.setZzbname_txt(job.getString("ZZBNAME_TXT"));
                        zh.setZcont(job.getString("ZCONT"));
                        zh.setZcont_txt(job.getString("ZCONT_TXT"));
                        zh.setKunnr(job.getString("KUNNR"));
                        zh.setName1(job.getString("NAME1"));
                        zh.setVkorg(job.getString("VKORG"));
                        zh.setVtext(job.getString("VTEXT"));
                        zh.setZrebate(Convert.toFloat(job.get("ZREBATE"),0f));
                        zh.setZrebate_used(Convert.toFloat(job.get("ZREBATE_USED"),0f));
                        zh.setZrebate_last(Convert.toFloat(job.get("ZREBATE_LAST"),0f));
                        zh.setDatab(job.getString("DATAB"));
                        zh.setDatbi(job.getString("DATBI"));
                        zh.setZkfrst(job.getString("ZKFRST"));
                        zh.setZzkl(Convert.toFloat(job.get("ZZKL"),0f));
                        zh.setZname(job.getString("ZNAME"));
                        zh.setZname1(job.getString("ZNAME1"));
                        zh.setZsh(job.getString("ZSH"));
                        zh.setZdate_sh(job.getString("ZDATE_SH"));
                        zh.setZdate(job.getString("ZDATE"));
                        zh.setZdate1(job.getString("ZDATE1"));
                        zh.setZtime(job.getString("ZTIME"));
                        zh.setZtime1(job.getString("ZTIME1"));
                        zh.setZtime_sh(job.getString("ZTIME_SH"));
                        zh.setZbase(job.getString("ZBASE"));
                        zh.setZly(job.getString("ZLY"));
                        zh.setZoa(job.getString("ZOA"));
                        zh.setZdel_h(job.getString("ZDEL_H"));


                        List<Ztsd206_ItemVO> itemList = new ArrayList<Ztsd206_ItemVO>();
                        List<Ztsd206_kunnr_ecVO> kunnr_ecList = new ArrayList<Ztsd206_kunnr_ecVO>();


                        /*返利策略物料组行*/
                        JSONArray itemArray = JSON.parseArray(job.getString("ITEM"));
                        if(itemArray.size()>0){
                            for(int j=0;j<itemArray.size();j++){
                                Ztsd206_ItemVO zi = new Ztsd206_ItemVO();
                                JSONObject itemJob = itemArray.getJSONObject(j);

                                zi.setZzbname(itemJob.getString("ZZBNAME"));
                                zi.setZposnr(itemJob.getString("ZPOSNR"));
                                zi.setZzkl(Convert.toFloat(itemJob.get("ZZKL"),0f));
                                zi.setMatkl(itemJob.getString("MATKL"));
                                zi.setMatnr(itemJob.getString("MATNR"));
                                zi.setZdel(itemJob.getString("ZDEL"));

                                List<Ztsd206_ItemVO> ziList = dataSynService.checkZtsd206_itemExist(zi);
                                //根据返利单号+物料组去查，如果存在了，则不增加
                                if(ziList.size()<=0){
                                    itemList.add(zi);
                                }
                            }
                        }


                        /*返利策略终端客户行*/
                        JSONArray kunnr_ecArray = JSON.parseArray(job.getString("KITEM"));
                        if(kunnr_ecArray.size()>0){
                            for(int k=0;k<kunnr_ecArray.size();k++){
                                Ztsd206_kunnr_ecVO zk = new Ztsd206_kunnr_ecVO();
                                JSONObject kunnr_ecJob = kunnr_ecArray.getJSONObject(k);

                                zk.setZzbname(kunnr_ecJob.getString("ZZBNAME"));
                                zk.setKunnr_ec(kunnr_ecJob.getString("KUNNR_EC"));
                                zk.setZzbname_txt(kunnr_ecJob.getString("ZZBNAME_TXT"));

                                List<Ztsd206_kunnr_ecVO> zkList = dataSynService.checkZtsd206_kunnr_ecExist(zk);
                                //根据返利单号+终端客户去查，如果存在了，则不增加
                                if(zkList.size()<=0){
                                    kunnr_ecList.add(zk);
                                }
                            }
                        }

                        zh.setZiList(itemList);
                        zh.setZkuList(kunnr_ecList);

                        List<Ztsd206_headVO> ztsList = dataSynService.checkZtsd206_headExist(zh);
                        //根据返利单号去查，如果存在了，则不增加
                        if(ztsList.size()<=0){
                            headList.add(zh);
                        }
                    }
                }
            }


            if(headList.size()>0){
                String rs =  dataSynService.insertZtsd206(headList);
                if(rs.indexOf("error")!=-1){
                    log.info("返利策略新增失败"+rs);
                }else{
                    log.info("返利策略新增成功");
                }
            }else{
                log.info("无可同步的返利策略数据");
            }
        }else{
            log.info("返利策略获取失败："+result);
        }
    }

}
