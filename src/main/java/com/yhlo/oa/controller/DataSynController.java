package com.yhlo.oa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yhlo.oa.entity.*;
import com.yhlo.oa.service.DataSynService;
import com.yhlo.oa.service.iml.DataSynServiceImpl;
import com.yhlo.oa.util.*;
import com.yhlo.oa.webservice.GetSapConfigTableInfo;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    public DatePicker startDateAssembly;
    @FXML
    public DatePicker endDateAssembly;

    private static String pattern = "yyyyMMdd";//日期格式类型样板串

    @FXML
    private ComboBox<String>wulKeyList;//物料下拉控件

    @FXML
    private ComboBox<String>kehKeyList;//客户下拉控件

    @FXML
    private ComboBox<String>fanlKeyList;//返利策略下拉控件

    private ObservableList<Item> obsAll;

    private DataSynService dataSynService;

    private String startDate = DateUtils.dateTime();
    private String endDate = DateUtils.dateTime();
    private String JSONSTR1 = "<![CDATA[[{\"ERSDA_FROM\":\""+startDate+"\",\"ERSDA_TO\":\""+endDate+"\"}]]]>";
   // private String JSONSTR1 = "<![CDATA[[{\"ERSDA_FROM\":\"20220308\",\"ERSDA_TO\":\"20220408\"}]]]>";//

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("进入数据同步器界面");
        dataSynService = SpringBeanUtil.getBean(DataSynServiceImpl.class);
        initComboBox();
        initComboBox1();
        initComboBox2();
        JavaFxUtil.setFormatDateField(pattern, startDateAssembly);
        JavaFxUtil.setFormatDateField(pattern, endDateAssembly);
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
     * @Description 初始化客户同步菜单选项
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

    /**
     * @Author cy
     * @Description 初始化返利策略同步菜单选项
     * @Date 2023/2/6 14:23
     */
    public void initComboBox2(){

        List<Item> itemList = dataSynService.getConfigItem("flcl");
        if(null != itemList && itemList.size()>0){
            for (Item item :itemList ) {
                fanlKeyList.getItems().add(item.getItemValue());
            }
        }
        fanlKeyList.setPromptText("请输入需要同步的接口参数搜索");
        fanlKeyList.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener auto = new AutoCompleteComboBoxListener<>(fanlKeyList);
    }


    //同步增量
    public void toAddWul(ActionEvent actionEvent) {
        log.info("同步物料表-同步增量");

        String pa = wulKeyList.getValue();//获取同步参数
        if(null == pa || "".equals(pa)){
            CommonUtil._alertInformation("请选择同步参数，再点击同步");
            return;
        }

        String stDate = String.valueOf(startDateAssembly.getValue());
        String edDate = String.valueOf(endDateAssembly.getValue());
        if(null != stDate && !"null".equals(stDate) && null !=edDate && !"null".equals(edDate)){
            stDate = stDate.replaceAll("-","");
            edDate = edDate.replaceAll("-","");
            JSONSTR1 = "<![CDATA[[{\"ERSDA_FROM\":\""+stDate+"\",\"ERSDA_TO\":\""+edDate+"\"}]]]>";
        }

        String param = pa.substring(0,pa.indexOf("-"));

        if("SD127".equals(param)){//SD127-物料基本视图接口
            List<It_MaraVO> dataList = new ArrayList<It_MaraVO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
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



    //同步增量客户数据
    public void toAddKeh(ActionEvent actionEvent) {
        log.info("点击了同步客户表-同步增量");

        String pa = kehKeyList.getValue();//获取同步参数
        if(null == pa || "".equals(pa)){
            CommonUtil._alertInformation("请选择同步参数，再点击同步");
            return;
        }
        String stDate = String.valueOf(startDateAssembly.getValue());
        String edDate = String.valueOf(endDateAssembly.getValue());

        if(null != stDate && !"null".equals(stDate) && null !=edDate && !"null".equals(edDate)){
            stDate = stDate.replaceAll("-","");
            edDate = edDate.replaceAll("-","");
            JSONSTR1 = "<![CDATA[[{\"ERSDA_FROM\":\""+stDate+"\",\"ERSDA_TO\":\""+edDate+"\"}]]]>";
        }

        String param = pa.substring(0,pa.indexOf("-"));

        if("SD132".equals(param)) {//SD132-客户一般视图查询接口
            List<It_But000VO> dataList = new ArrayList<It_But000VO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
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


        if("SD142".equals(param)) {//SD142-客户分配多角贸易代码接口
            List<It_Ztsd_002VO> dataList = new ArrayList<It_Ztsd_002VO>();
            String result = GetSapConfigTableInfo.sendRequest(param,JSONSTR1);
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
                        CommonUtil._alertInformation("客户分配多角贸易代码新增失败"+rs);
                    }else{
                        CommonUtil._alertInformation("客户分配多角贸易代码新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertInformation("客户分配多角贸易代码获取失败");
            }
            return;
        }


    }




    /**
     * @Author cy
     * @Description 同步返利策略-同步增量
     * @Date 2023/2/6 14:27
     */
    public void toAddFanl() {

        log.info("点击了同步返利策略-同步增量");

        String pa = fanlKeyList.getValue();//获取同步参数
        if(null == pa || "".equals(pa)){
            CommonUtil._alertInformation("请选择同步参数，再点击同步");
            return;
        }
        String stDate = String.valueOf(startDateAssembly.getValue());
        String edDate = String.valueOf(endDateAssembly.getValue());
        String  JsonStr = "";
        if(null != stDate && !"null".equals(stDate) && null !=edDate && !"null".equals(edDate)){
            stDate = stDate.replaceAll("-","");
            edDate = edDate.replaceAll("-","");
            JsonStr = "<![CDATA[[{\"ZDATE1\":\""+stDate+"\",\"ZDATE2\":\""+edDate+"\"}]]]>";
        }else{
            stDate = DateUtils.dateTime();
            edDate = DateUtils.dateTime();
            JsonStr = "<![CDATA[[{\"ZDATE1\":\""+stDate+"\",\"ZDATE2\":\""+edDate+"\"}]]]>";

        }
        String param = pa.substring(0,pa.indexOf("-"));

        if("SD140".equals(param)) {//SD140-返利策略接口

            List<Ztsd206_headVO> headList = new ArrayList<Ztsd206_headVO>();

            String result = GetSapConfigTableInfo.sendRequest(param,JsonStr);


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
                        CommonUtil._alertErrorMessage("出错了","返利策略新增失败："+rs);
                    }else{
                        CommonUtil._alertInformation("返利策略新增成功");
                    }
                }else{
                    CommonUtil._alertInformation("无可同步数据");
                }
            }else{
                CommonUtil._alertErrorMessage("出错了","返利策略获取失败："+result);
            }
            return;
        }
    }


}
