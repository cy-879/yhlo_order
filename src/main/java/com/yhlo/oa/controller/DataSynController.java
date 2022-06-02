package com.yhlo.oa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yhlo.oa.entity.*;
import com.yhlo.oa.service.DataSynService;
import com.yhlo.oa.service.iml.DataSynServiceImpl;
import com.yhlo.oa.util.AutoCompleteComboBoxListener;
import com.yhlo.oa.util.CommonUtil;
import com.yhlo.oa.util.DateUtils;
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
 * @ClassName: DataSynController
 * @Description:
 * @date 2022/5/1915:34
 */

@Slf4j
@FXMLController
public class DataSynController implements Initializable {

    @FXML
    private ComboBox<String>wulKeyList;//物料下拉控件

    @FXML
    private ComboBox<String>kehKeyList;//客户下拉控件

    private ObservableList<Item> obsAll;

    private DataSynService dataSynService;

    private String startDate = DateUtils.dateTime();
    private String endDate = DateUtils.dateTime();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("进入数据同步器界面");
        dataSynService = SpringBeanUtil.getBean(DataSynServiceImpl.class);
        initComboBox();
        initComboBox1();

    }

    /**
     * 初始化物料同步菜单选项
     */
    public void initComboBox(){

        List<Item> itemList = dataSynService.getConfigItem("wlsj");
        if(null != itemList && itemList.size()>0){
            for (Item item :itemList ) {
                wulKeyList.getItems().add(item.getItemValue());
            }
        }
        //System.err.println("itemList=="+itemList);
        wulKeyList.setPromptText("请输入需要同步的接口参数搜索");
        wulKeyList.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener auto = new AutoCompleteComboBoxListener<>(wulKeyList);
    }


    /**
     * @Author cy
     * @Description 初始化物料同步菜单选项
     * @Date 2022/5/26 10:32
     */
    public void initComboBox1(){

        List<Item> itemList = dataSynService.getConfigItem("khsj");
        if(null != itemList && itemList.size()>0){
            for (Item item :itemList ) {
                kehKeyList.getItems().add(item.getItemValue());
            }
        }
        //System.err.println("itemList=="+itemList);
        kehKeyList.setPromptText("请输入需要同步的接口参数搜索");
        kehKeyList.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener auto = new AutoCompleteComboBoxListener<>(kehKeyList);
    }


    public void loadTable(ActionEvent actionEvent) {
        System.err.println("点击了查询");

    }

    //同步增量
    public void toAddWuk(ActionEvent actionEvent) {
        log.info("同步物料表-同步增量");

        String pa = wulKeyList.getValue();//获取同步参数
        if(null == pa || "".equals(pa)){
            CommonUtil._alertInformation("请选择同步参数，再点击同步");
            return;
        }

        String param = pa.substring(0,pa.indexOf("-"));
        String JSONSTR1 = "<![CDATA[[{\"ERSDA_FROM\":\"20220308\",\"ERSDA_TO\":\"20220308\"}]]]>";//

        if("SD127".equals(param)){//SD127-物料基本视图接口
            List<It_MaraVO> dataList = new ArrayList<It_MaraVO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        It_MaraVO ma = new It_MaraVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        if(job.get("TYPE").equals("S")){
                            ma.setMatnr(job.get("MATNR").toString());
                            ma.setMaktx(job.get("MAKTX").toString());
                            ma.setMtart(job.get("MTART").toString());
                            ma.setMatkl(job.get("MATKL").toString());
                            ma.setRaube(job.get("RAUBE").toString());
                            ma.setBismt(job.get("BISMT").toString());
                            ma.setSpart(job.get("SPART").toString());
                            ma.setPrdha(job.get("PRDHA").toString());
                            ma.setMeins(job.get("MEINS").toString());
                            ma.setMstae(job.get("MSTAE").toString());
                            ma.setXchpf(job.get("XCHPF").toString());
                            ma.setExtwg(job.get("EXTWG").toString());
                            ma.setMbrsh(job.get("MBRSH").toString());
                            ma.setMhdrz(job.get("MHDRZ").toString());
                            ma.setMhdhb(job.get("MHDHB").toString());
                            ma.setMtpos_mara(job.get("MTPOS_MARA").toString());
                            ma.setZggxh(job.get("ZGGXH").toString());
                            ma.setZcus01(job.get("ZCUS01").toString());
                            ma.setZcus02(job.get("ZCUS02").toString());
                            ma.setZcus02_1(job.get("ZCUS02_1").toString());
                            ma.setZcus02_2(job.get("ZCUS02_2").toString());
                            ma.setZcus03(job.get("ZCUS03").toString());
                            ma.setZcus04(job.get("ZCUS04").toString());
                            ma.setZcus05(job.get("ZCUS05").toString());
                            ma.setZcus06(job.get("ZCUS06").toString());
                            ma.setZcus07(job.get("ZCUS07").toString());
                            ma.setZcus08(job.get("ZCUS08").toString());
                            ma.setZcus09(job.get("ZCUS09").toString());
                            ma.setZcus10(job.get("ZCUS10").toString());
                            ma.setZcus11(job.get("ZCUS11").toString());
                            ma.setZcus12(job.get("ZCUS12").toString());
                            ma.setZcus13(job.get("ZCUS13").toString());
                            ma.setZcus14(job.get("ZCUS14").toString());
                            ma.setZcus15(job.get("ZCUS15").toString());
                            ma.setZcus16(job.get("ZCUS16").toString());
                            ma.setZcus17(job.get("ZCUS17").toString());
                            ma.setZcus18(job.get("ZCUS18").toString());
                            ma.setZcus19(job.get("ZCUS19").toString());
                            ma.setZcus20(job.get("ZCUS20").toString());
                            ma.setZcus21(job.get("ZCUS21").toString());
                            ma.setZcus22(job.get("ZCUS22").toString());
                            ma.setZcus23(job.get("ZCUS23").toString());
                            ma.setZcus24(job.get("ZCUS24").toString());
                            ma.setZcus25(job.get("ZCUS25").toString());
                            ma.setZcus26(job.get("ZCUS26").toString());
                            ma.setZcus27(job.get("ZCUS27").toString());
                            ma.setZcus28(job.get("ZCUS28").toString());
                            ma.setZcus29(job.get("ZCUS29").toString());
                            ma.setZcus30(job.get("ZCUS30").toString());
                            ma.setZcus31(job.get("ZCUS31").toString());
                            ma.setZcus32(job.get("ZCUS32").toString());
                            ma.setZcus33(job.get("ZCUS33").toString());
                            ma.setZcus34(job.get("ZCUS34").toString());
                            ma.setZcus35(job.get("ZCUS35").toString());
                            ma.setCreate_time(job.get("ZLAEDA_TIME").toString());

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
                        CommonUtil._alertInformation("物料基本数据新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("物料基本数据新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("物料基本接口数据获取失败");
            }
            return;
        }


        if("SD128".equals(param)){//SD128-销售物料视图接口
            List<It_MvkeVO> dataList = new ArrayList<It_MvkeVO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        It_MvkeVO to = new It_MvkeVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        if(job.get("TYPE").equals("S")){
                            to.setMatnr(job.get("MATNR").toString());
                            to.setVkorg(job.get("VKORG").toString());
                            to.setVtweg(job.get("VTWEG").toString());
                            to.setVrkme(job.get("VRKME").toString());
                            to.setMtpos(job.get("MTPOS").toString());
                            to.setDwerk(job.get("DWERK").toString());
                            to.setKtgrm(job.get("KTGRM").toString());
                            to.setLvorm(job.get("LVORM").toString());
                            to.setVmsta(job.get("VMSTA").toString());
                            to.setCreate_time(job.get("ZLAEDA_TIME").toString());
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
                        CommonUtil._alertInformation("销售物料新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("销售物料新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("销售物料接口数据获取失败");
            }
            return;
        }

        if("SD129".equals(param)) {//SD129-物料评估类型视图接口
            List<It_MbewVO> dataList = new ArrayList<It_MbewVO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        It_MbewVO mb = new It_MbewVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        if(job.get("TYPE").equals("S")){
                            mb.setMatnr(job.get("MATNR").toString());
                            mb.setBwkey(job.get("BWKEY").toString());
                            mb.setBklas(job.get("BKLAS").toString());
                            mb.setLvorm(job.get("LVORM").toString());
                            mb.setVprsv(job.get("VPRSV").toString());
                            mb.setVerpr(job.get("VERPR").toString());
                            mb.setStprs(job.get("STPRS").toString());
                            mb.setPeinh(job.get("PEINH").toString());
                            mb.setCreate_time(job.get("ZLAEDA_TIME").toString());
                            List<It_MbewVO> mbList = dataSynService.checkMbewExist(mb);
                            //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                            if(mbList.size()<=0){
                                dataList.add(mb);
                            }
                        }
                    }
                }

                if(dataList.size()>0){
                    String rs =  dataSynService.insertMbew(dataList);

                    if(rs.indexOf("error")!=-1){
                        CommonUtil._alertInformation("物料评估类型新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("物料评估类型新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("物料评估类型接口数据获取失败");
            }

            return;
        }

        if("SD130".equals(param)) {//SD130-物料计量单位视图接口
            List<It_MarmVO> dataList = new ArrayList<It_MarmVO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        It_MarmVO ma = new It_MarmVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        if(job.get("TYPE").equals("S")){
                            ma.setMatnr(job.get("MATNR").toString());
                            ma.setMeinh(job.get("MEINH").toString());
                            ma.setUmrez(job.get("UMREZ").toString());
                            ma.setUmren(job.get("UMREN").toString());
                            ma.setCreate_time(job.get("ZLAEDA_TIME").toString());
                            List<It_MarmVO> maList = dataSynService.checkMarmExist(ma);
                            //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                            if(maList.size()<=0){
                                dataList.add(ma);
                            }
                        }
                    }
                }

                if(dataList.size()>0){
                    String rs =  dataSynService.insertMarm(dataList);

                    if(rs.indexOf("error")!=-1){
                        CommonUtil._alertInformation("物料计量单位新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("物料计量单位新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("物料计量单位接口数据获取失败");
            }

            return;
        }


        if("SD131".equals(param)) {//SD131-物料库存相关视图接口
            List<It_MardVO> dataList = new ArrayList<It_MardVO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        It_MardVO ma = new It_MardVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        if(job.get("TYPE").equals("S")){
                            ma.setMatnr(job.get("MATNR").toString());
                            ma.setWerks(job.get("WERKS").toString());
                            ma.setLgort(job.get("LGORT").toString());
                            ma.setLvorm(job.get("LVORM").toString());
                            ma.setLabst(job.get("LABST").toString());
                            ma.setCreate_time(job.get("ZLAEDA_TIME").toString());
                            List<It_MardVO> maList = dataSynService.checkMardExist(ma);
                            //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                            if(maList.size()<=0){
                                dataList.add(ma);
                            }
                        }
                    }
                }

                if(dataList.size()>0){
                    String rs =  dataSynService.insertMard(dataList);

                    if(rs.indexOf("error")!=-1){
                        CommonUtil._alertInformation("物料库存新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("物料库存单位新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("物料库存接口数据获取失败");
            }

            return;
        }


        if("SD141".equals(param)) {//SD141-物料工厂视图接口
            List<It_MarcVO> dataList = new ArrayList<It_MarcVO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        It_MarcVO ma = new It_MarcVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        if(job.get("TYPE").equals("S")){
                            ma.setMatnr(job.get("MATNR").toString());
                            ma.setWerks(job.get("WERKS").toString());
                            ma.setLvorm(job.get("LVORM").toString());
                            ma.setXchpf(job.get("XCHPF").toString());
                            ma.setXchar(job.get("XCHAR").toString());
                            ma.setLadgr(job.get("LADGR").toString());
                            ma.setSernp(job.get("SERNP").toString());
                            ma.setCreate_time(job.get("ZLAEDA_TIME").toString());
                            List<It_MarcVO> maList = dataSynService.checkMarcExist(ma);
                            //按唯一键和最后修改日期去查是否在本地数据库存在，如果存在，表示已经添加过，此时不添加
                            if(maList.size()<=0){
                                dataList.add(ma);
                            }
                        }
                    }
                }

                if(dataList.size()>0){
                    String rs =  dataSynService.insertMarc(dataList);

                    if(rs.indexOf("error")!=-1){
                        CommonUtil._alertInformation("物料工厂新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("物料工厂新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("物料工厂接口数据获取失败");
            }

            return;
        }


    }


    //同步整表
    public void toAddWukAll(ActionEvent actionEvent) {
        log.info("点击了同步物料表-全同步");
        CommonUtil._alertInformation("开发中，请勿点击");
        return;
    }



    //同步增量
    public void toAddKeh(ActionEvent actionEvent) {
        log.info("点击了同步客户表-同步增量");

        String pa = kehKeyList.getValue();//获取同步参数
        if(null == pa || "".equals(pa)){
            CommonUtil._alertInformation("请选择同步参数，再点击同步");
            return;
        }

        String param = pa.substring(0,pa.indexOf("-"));
        String JSONSTR1 = "<![CDATA[[{\"ERSDA_FROM\":\"20220301\",\"ERSDA_TO\":\"20220407\"}]]]>";

        if("SD132".equals(param)) {//SD132-客户一般视图查询接口
            List<It_But000VO> dataList = new ArrayList<It_But000VO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        It_But000VO ib = new It_But000VO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        if(job.get("TYPE").equals("S")){
                            ib.setPartner(job.get("PARTNER").toString());
                            ib.setBu_group(job.get("BU_GROUP").toString());
                            ib.setName_org1(job.get("NAME_ORG1").toString());
                            ib.setName_org2(job.get("NAME_ORG2").toString());
                            ib.setName_org3(job.get("NAME_ORG3").toString());
                            ib.setName_org4(job.get("NAME_ORG4").toString());
                            ib.setBu_sort1(job.get("BU_SORT1").toString());
                            ib.setBu_sort2(job.get("BU_SORT2").toString());
                            ib.setZynum(job.get("ZYNUM").toString());
                            ib.setZyfzdat(job.get("ZYFZDAT").toString());
                            ib.setZyyxdat(job.get("ZYYXDAT").toString());
                            ib.setZelnum(job.get("ZELNUM").toString());
                            ib.setZelfzdat(job.get("ZELFZDAT").toString());
                            ib.setZjynum(job.get("ZJYNUM").toString());
                            ib.setZjyfzdat(job.get("ZJYFZDAT").toString());
                            ib.setZjyyxdat(job.get("ZJYYXDAT").toString());
                            ib.setZyyfzdat(job.get("ZYYFZDAT").toString());
                            ib.setZscbapz(job.get("ZSCBAPZ").toString());
                            ib.setZscbarq(job.get("ZSCBARQ").toString());
                            ib.setZfr(job.get("ZFR").toString());
                            ib.setCrusr(job.get("CRUSR").toString());
                            ib.setCrdat(job.get("CRDAT").toString());
                            ib.setCrtim(job.get("CRTIM").toString());
                            ib.setChusr(job.get("CHUSR").toString());
                            ib.setChdat(job.get("CHDAT").toString());
                            ib.setChtim(job.get("CHTIM").toString());
                            //ib.setCreate_time(job.get("ZLAEDA_TIME").toString());
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
                        CommonUtil._alertInformation("客户一般数据新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("客户一般数据新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("客户一般数据接口数据获取失败");
            }

            return;
        }


        if("SD133".equals(param)) {//SD133-客户一般视图2查询接口
            List<It_Kna1VO> dataList = new ArrayList<It_Kna1VO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        It_Kna1VO ik = new It_Kna1VO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        if(job.get("TYPE").equals("S")){
                            ik.setKunnr(job.get("KUNNR").toString());
                            ik.setLand1(job.get("LAND1").toString());
                            ik.setName1(job.get("NAME1").toString());
                            ik.setName2(job.get("NAME2").toString());
                            ik.setOrt01(job.get("ORT01").toString());
                            ik.setPstlz(job.get("PSTLZ").toString());
                            ik.setRegio(job.get("REGIO").toString());
                            ik.setStras(job.get("STRAS").toString());
                            ik.setTelf1(job.get("TELF1").toString());
                            ik.setTelfx(job.get("TELFX").toString());
                            ik.setAdrnr(job.get("ADRNR").toString());
                            ik.setErdat(job.get("ERDAT").toString());
                            ik.setErnam(job.get("ERNAM").toString());
                            ik.setKtokd(job.get("KTOKD").toString());
                            ik.setSpras(job.get("SPRAS").toString());
                            ik.setTelf2(job.get("TELF2").toString());
                            ik.setStcd5(job.get("STCD5").toString());
                            ik.setKukla(job.get("KUKLA").toString());
                            //ik.setCreate_time(job.get("ZLAEDA_TIME").toString());
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
                        CommonUtil._alertInformation("客户一般数据2新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("客户一般数据2新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("客户一般数据接口数据2获取失败");
            }
            return;
        }


        if("SD134".equals(param)) {//SD134-客户主数据公司代码数据查询接口
            List<It_Knb1VO> dataList = new ArrayList<It_Knb1VO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        It_Knb1VO ik = new It_Knb1VO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        if(job.get("TYPE").equals("S")){
                            ik.setKunnr(job.get("KUNNR").toString());
                            ik.setBukrs(job.get("BUKRS").toString());
                            ik.setErdat(job.get("ERDAT").toString());
                            ik.setErnam(job.get("ERNAM").toString());
                            ik.setAkont(job.get("AKONT").toString());
                            ik.setZwels(job.get("ZWELS").toString());
                            ik.setZterm(job.get("ZTERM").toString());
                            ik.setSperr(job.get("SPERR").toString());
                            ik.setLoevm(job.get("LOEVM").toString());
                            //ik.setCreate_time(job.get("ZLAEDA_TIME").toString());
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
                        CommonUtil._alertInformation("客户主数据公司代码数据新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("客户主数据公司代码数据新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("客户主数据公司代码数据获取失败");
            }
            return;
        }


        if("SD136".equals(param)) {//SD136-客户主数据销售视图查询接口
            List<It_KnvvVO> dataList = new ArrayList<It_KnvvVO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        It_KnvvVO ik = new It_KnvvVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        if(job.get("TYPE").equals("S")){
                            ik.setKunnr(job.get("KUNNR").toString());
                            ik.setVkorg(job.get("VKORG").toString());
                            ik.setVtweg(job.get("VTWEG").toString());
                            ik.setSpart(job.get("SPART").toString());
                            ik.setErnam(job.get("ERNAM").toString());
                            ik.setErdat(job.get("ERDAT").toString());
                            ik.setBegru(job.get("BEGRU").toString());
                            ik.setLoevm(job.get("LOEVM").toString());
                            ik.setVersg(job.get("VERSG").toString());
                            ik.setAufsd(job.get("AUFSD").toString());
                            ik.setKalks(job.get("KALKS").toString());
                            ik.setKdgrp(job.get("KDGRP").toString());
                            ik.setBzirk(job.get("BZIRK").toString());
                            ik.setKonda(job.get("KONDA").toString());
                            ik.setPltyp(job.get("PLTYP").toString());
                            ik.setInco1(job.get("INCO1").toString());
                            ik.setInco2(job.get("INCO2").toString());
                            ik.setLifsd(job.get("LIFSD").toString());
                            ik.setKzazu(job.get("KZAZU").toString());
                            ik.setVsbed(job.get("VSBED").toString());
                            ik.setFaksd(job.get("FAKSD").toString());
                            ik.setWaers(job.get("WAERS").toString());
                            ik.setKlabc(job.get("KLABC").toString());
                            ik.setKtgrd(job.get("KTGRD").toString());
                            ik.setZterm(job.get("ZTERM").toString());
                            ik.setVwerk(job.get("VWERK").toString());
                            ik.setVkgrp(job.get("VKGRP").toString());
                            ik.setVkbur(job.get("VKBUR").toString());
                            ik.setPrfre(job.get("PRFRE").toString());
                            ik.setKkber(job.get("KKBER").toString());
                            ik.setPodkz(job.get("PODKZ").toString());
                            //ik.setCreate_time(job.get("ZLAEDA_TIME").toString());
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
                        CommonUtil._alertInformation("客户主数据销售数据新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("客户主数据销售数据新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("客户主数据销售数据获取失败");
            }
            return;
        }


        if("SD137".equals(param)) {//SD137-客户主数据合作伙伴查询接口
            List<It_KnvpVO> dataList = new ArrayList<It_KnvpVO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        It_KnvpVO ik = new It_KnvpVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        if(job.get("TYPE").equals("S")){
                            ik.setKunnr(job.get("KUNNR").toString());
                            ik.setVkorg(job.get("VKORG").toString());
                            ik.setVtweg(job.get("VTWEG").toString());
                            ik.setSpart(job.get("SPART").toString());
                            ik.setParvw(job.get("PARVW").toString());
                            ik.setParza(job.get("PARZA").toString());
                            ik.setKunn2(job.get("KUNN2").toString());
                            ik.setLifnr(job.get("LIFNR").toString());
                            ik.setPernr(job.get("PERNR").toString());
                            ik.setParnr(job.get("PARNR").toString());
                            ik.setKnref(job.get("KNREF").toString());
                            ik.setDefpa(job.get("DEFPA").toString());
                            //ik.setCreate_time(job.get("ZLAEDA_TIME").toString());
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
                        CommonUtil._alertInformation("客户主数据合作伙伴新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("客户主数据合作伙伴新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("客户主数据合作伙伴获取失败");
            }
            return;
        }


        if("SD138".equals(param)) {//SD138-客户主数据地址信息查询接口
            List<It_AdrcVO> dataList = new ArrayList<It_AdrcVO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        It_AdrcVO ia = new It_AdrcVO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        if(job.get("TYPE").equals("S")){
                            ia.setAddrnumber(job.get("ADDRNUMBER").toString());
                            ia.setName1(job.get("NAME1").toString());
                            ia.setName2(job.get("NAME2").toString());
                            ia.setName3(job.get("NAME3").toString());
                            ia.setName4(job.get("NAME4").toString());
                            ia.setStr_suppl1(job.get("STR_SUPPL1").toString());
                            ia.setStr_suppl2(job.get("STR_SUPPL2").toString());
                            ia.setStr_suppl3(job.get("STR_SUPPL3").toString());
                            ia.setLocation(job.get("LOCATION").toString());
                            ia.setRemark(job.get("REMARK").toString());
                            //ik.setCreate_time(job.get("ZLAEDA_TIME").toString());
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
                        CommonUtil._alertInformation("客户主数据地址信息新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("客户主数据地址信息新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("客户主数据地址信息获取失败");
            }
            return;
        }


        if("SD139".equals(param)) {//SD139-客户物料查询接口
            List<It_Ztsd_012VO> dataList = new ArrayList<It_Ztsd_012VO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
            if(!"".equals(result)&& !"error".equals(result) ){
                JSONArray objects = JSON.parseArray(result);
                if(objects.size()>0){
                    for(int i=0;i<objects.size();i++){
                        It_Ztsd_012VO iz = new It_Ztsd_012VO();
                        JSONObject job = objects.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        if(job.get("TYPE").equals("S")){
                            iz.setKunnr(job.get("KUNNR").toString());
                            iz.setVkorg(job.get("VKORG").toString());
                            iz.setVtweg(job.get("VTWEG").toString());
                            iz.setMatnr(job.get("MATNR").toString());
                            iz.setName1(job.get("NAME1").toString());
                            iz.setArktx(job.get("ARKTX").toString());
                            iz.setKdmat(job.get("KDMAT").toString());
                            iz.setPostx(job.get("POSTX").toString());
                            iz.setZkdmat_2(job.get("ZKDMAT_2").toString());
                            iz.setZkdmat_3(job.get("ZKDMAT_3").toString());
                            iz.setZkdmat_4(job.get("ZKDMAT_4").toString());
                            iz.setZernam1(job.get("ZERNAM1").toString());
                            iz.setZerdat1(job.get("ZERDAT1").toString());
                            iz.setZernam2(job.get("ZERNAM2").toString());
                            iz.setZerdat2(job.get("ZERDAT2").toString());
                            //ik.setCreate_time(job.get("ZLAEDA_TIME").toString());
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
                        CommonUtil._alertInformation("客户物料数据新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("客户物料数据新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("客户物料数据获取失败");
            }
            return;
        }


    }

    //同步整表
    public void toAddKehAll(ActionEvent actionEvent) {
        log.info("点击了同步客户表-全同步");
        CommonUtil._alertInformation("开发中，请勿点击");
        return;
    }


}
