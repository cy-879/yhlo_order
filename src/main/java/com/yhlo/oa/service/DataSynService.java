package com.yhlo.oa.service;


import com.yhlo.oa.entity.*;

import java.util.List;

public interface DataSynService {

    public List<Item> getConfigItem(String ttype);

    public String insertMara(List<It_MaraVO> list);
    public String deleteMara(It_MaraVO im);
    public List<It_MaraVO> queryMaraListByParam(It_MaraVO im);
    public List<It_MaraVO> checkMaraExist(It_MaraVO im);

    public String insertMvke(List<It_MvkeVO> list);
    public String deleteMvke(It_MvkeVO im);
    public List<It_MvkeVO> queryMvkeListByParam(It_MvkeVO im);
    public List<It_MvkeVO> checkExist(It_MvkeVO im);
    public List<It_MvkeVO> queryMvkeList();

    public String insertMbew(List<It_MbewVO> list);
    public String deleteMbew(It_MbewVO im);
    public List<It_MbewVO> queryMbewListByParam(It_MbewVO im);
    public List<It_MbewVO> checkMbewExist(It_MbewVO im);

    public String insertMarm(List<It_MarmVO> list);
    public String deleteMarm(It_MarmVO im);
    public List<It_MarmVO> queryMarmListByParam(It_MarmVO im);
    public List<It_MarmVO> checkMarmExist(It_MarmVO im);

    public String insertMard(List<It_MardVO> list);
    public String deleteMard(It_MardVO im);
    public List<It_MardVO> queryMardListByParam(It_MardVO im);
    public List<It_MardVO> checkMardExist(It_MardVO im);

    public String insertMarc(List<It_MarcVO> list);
    public String deleteMarc(It_MarcVO im);
    public List<It_MarcVO> queryMarcListByParam(It_MarcVO im);
    public List<It_MarcVO> checkMarcExist(It_MarcVO im);

    public String insertBut000(List<It_But000VO> list);
    public String deleteBut000(It_But000VO ib);
    public List<It_But000VO> queryBut000ListByParam(It_But000VO ib);
    public List<It_But000VO> checkBut000Exist(It_But000VO ib);

    public String insertKna1(List<It_Kna1VO> list);
    public String deleteKna1(It_Kna1VO ik);
    public List<It_Kna1VO> queryKna1ListByParam(It_Kna1VO ik);
    public List<It_Kna1VO> checkKna1Exist(It_Kna1VO ik);

    public String insertKnb1(List<It_Knb1VO> list);
    public String deleteKnb1(It_Knb1VO ik);
    public List<It_Knb1VO> queryKnb1ListByParam(It_Knb1VO ik);
    public List<It_Knb1VO> checkKnb1Exist(It_Knb1VO ik);

    public String insertKnvv(List<It_KnvvVO> list);
    public String deleteKnvv(It_KnvvVO ik);
    public List<It_KnvvVO> queryKnvvListByParam(It_KnvvVO ik);
    public List<It_KnvvVO> checkKnvvExist(It_KnvvVO ik);

    public String insertKnvp(List<It_KnvpVO> list);
    public String deleteKnvp(It_KnvpVO ik);
    public List<It_KnvpVO> queryKnvpListByParam(It_KnvpVO ik);
    public List<It_KnvpVO> checkKnvpExist(It_KnvpVO ik);

    public String insertAdrc(List<It_AdrcVO> list);
    public String deleteAdrc(It_AdrcVO ia);
    public List<It_AdrcVO> queryAdrcListByParam(It_AdrcVO ia);
    public List<It_AdrcVO> checkAdrcExist(It_AdrcVO ia);

    public String insertZtsd_012(List<It_Ztsd_012VO> list);
    public String deleteZtsd_012(It_Ztsd_012VO iz);
    public List<It_Ztsd_012VO> queryZtsd_012ListByParam(It_Ztsd_012VO iz);
    public List<It_Ztsd_012VO> checkZtsd_012Exist(It_Ztsd_012VO iz);




}
