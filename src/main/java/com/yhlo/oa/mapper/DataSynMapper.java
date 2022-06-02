package com.yhlo.oa.mapper;


import com.yhlo.oa.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSynMapper {

   public List<Item> getConfigItem(@Param("ttype")String ttype);

   public int insertMara(It_MaraVO im);
   public void deleteMara(It_MaraVO im);
   public List<It_MaraVO> queryMaraListByParam(It_MaraVO im);
   public List<It_MaraVO> checkMaraExist(It_MaraVO im);

   public int insertMvke(It_MvkeVO im);
   public void deleteMvke(It_MvkeVO im);
   public List<It_MvkeVO> queryMvkeListByParam(It_MvkeVO im);
   public List<It_MvkeVO> checkExist(It_MvkeVO im);
   public List<It_MvkeVO> queryMvkeList();

   public int insertMbew(It_MbewVO im);
   public void deleteMbew(It_MbewVO im);
   public List<It_MbewVO> queryMbewListByParam(It_MbewVO im);
   public List<It_MbewVO> checkMbewExist(It_MbewVO im);

   public int insertMarm(It_MarmVO im);
   public void deleteMarm(It_MarmVO im);
   public List<It_MarmVO> queryMarmListByParam(It_MarmVO im);
   public List<It_MarmVO> checkMarmExist(It_MarmVO im);

   public int insertMard(It_MardVO im);
   public void deleteMard(It_MardVO im);
   public List<It_MardVO> queryMardListByParam(It_MardVO im);
   public List<It_MardVO> checkMardExist(It_MardVO im);

   public int insertMarc(It_MarcVO im);
   public void deleteMarc(It_MarcVO im);
   public List<It_MarcVO> queryMarcListByParam(It_MarcVO im);
   public List<It_MarcVO> checkMarcExist(It_MarcVO im);

   public int insertBut000(It_But000VO ib);
   public void deleteBut000(It_But000VO ib);
   public List<It_But000VO> queryBut000ListByParam(It_But000VO ib);
   public List<It_But000VO> checkBut000Exist(It_But000VO ib);

   public int insertKna1(It_Kna1VO ik);
   public void deleteKna1(It_Kna1VO ik);
   public List<It_Kna1VO> queryKna1ListByParam(It_Kna1VO ik);
   public List<It_Kna1VO> checkKna1Exist(It_Kna1VO ik);

   public int insertKnb1(It_Knb1VO ik);
   public void deleteKnb1(It_Knb1VO ik);
   public List<It_Knb1VO> queryKnb1ListByParam(It_Knb1VO ik);
   public List<It_Knb1VO> checkKnb1Exist(It_Knb1VO ik);

   public int insertKnvv(It_KnvvVO ik);
   public void deleteKnvv(It_KnvvVO ik);
   public List<It_KnvvVO> queryKnvvListByParam(It_KnvvVO ik);
   public List<It_KnvvVO> checkKnvvExist(It_KnvvVO ik);

   public int insertKnvp(It_KnvpVO ik);
   public void deleteKnvp(It_KnvpVO ik);
   public List<It_KnvpVO> queryKnvpListByParam(It_KnvpVO ik);
   public List<It_KnvpVO> checkKnvpExist(It_KnvpVO ik);

   public int insertAdrc(It_AdrcVO ia);
   public void deleteAdrc(It_AdrcVO ia);
   public List<It_AdrcVO> queryAdrcListByParam(It_AdrcVO ia);
   public List<It_AdrcVO> checkAdrcExist(It_AdrcVO ia);

   public int insertZtsd_012(It_Ztsd_012VO iz);
   public void deleteZtsd_012(It_Ztsd_012VO iz);
   public List<It_Ztsd_012VO> queryZtsd_012ListByParam(It_Ztsd_012VO iz);
   public List<It_Ztsd_012VO> checkZtsd_012Exist(It_Ztsd_012VO iz);




}
