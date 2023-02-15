package com.yhlo.oa.mapper;


import com.yhlo.oa.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSynMapper {

   public List<Item> getConfigItem(@Param("ttype")String ttype);

   public int insertMara(It_MaraVO im);
   public int updatetMara(It_MaraVO im);
   public void deleteMara(It_MaraVO im);
   public List<It_MaraVO> queryMaraListByParam(It_MaraVO im);
   public List<It_MaraVO> checkMaraExist(It_MaraVO im);

   public int insertMvke(It_MvkeVO im);
   public int updateMvke(It_MvkeVO im);
   public void deleteMvke(It_MvkeVO im);
   public List<It_MvkeVO> queryMvkeListByParam(It_MvkeVO im);
   public List<It_MvkeVO> checkExist(It_MvkeVO im);
   public List<It_MvkeVO> queryMvkeList();

   public int insertMbew(It_MbewVO im);
   public int updateMbew(It_MbewVO im);
   public void deleteMbew(It_MbewVO im);
   public List<It_MbewVO> queryMbewListByParam(It_MbewVO im);
   public List<It_MbewVO> checkMbewExist(It_MbewVO im);

   public int insertMarm(It_MarmVO im);
   public int updateMarm(It_MarmVO im);
   public void deleteMarm(It_MarmVO im);
   public List<It_MarmVO> queryMarmListByParam(It_MarmVO im);
   public List<It_MarmVO> checkMarmExist(It_MarmVO im);

   public int insertMard(It_MardVO im);
   public int updateMard(It_MardVO im);
   public void deleteMard(It_MardVO im);
   public List<It_MardVO> queryMardListByParam(It_MardVO im);
   public List<It_MardVO> checkMardExist(It_MardVO im);

   public int insertMarc(It_MarcVO im);
   public int updateMarc(It_MarcVO im);
   public void deleteMarc(It_MarcVO im);
   public List<It_MarcVO> queryMarcListByParam(It_MarcVO im);
   public List<It_MarcVO> checkMarcExist(It_MarcVO im);

   public int insertBut000(It_But000VO ib);
   public int updateBut000(It_But000VO ib);
   public void deleteBut000(It_But000VO ib);
   public List<It_But000VO> queryBut000ListByParam(It_But000VO ib);
   public List<It_But000VO> checkBut000Exist(It_But000VO ib);

   public int insertKna1(It_Kna1VO ik);
   public int updateKna1(It_Kna1VO ik);
   public void deleteKna1(It_Kna1VO ik);
   public List<It_Kna1VO> queryKna1ListByParam(It_Kna1VO ik);
   public List<It_Kna1VO> checkKna1Exist(It_Kna1VO ik);

   public int insertKnb1(It_Knb1VO ik);
   public int updateKnb1(It_Knb1VO ik);
   public void deleteKnb1(It_Knb1VO ik);
   public List<It_Knb1VO> queryKnb1ListByParam(It_Knb1VO ik);
   public List<It_Knb1VO> checkKnb1Exist(It_Knb1VO ik);

   public int insertKnvv(It_KnvvVO ik);
   public int updateKnvv(It_KnvvVO ik);
   public void deleteKnvv(It_KnvvVO ik);
   public List<It_KnvvVO> queryKnvvListByParam(It_KnvvVO ik);
   public List<It_KnvvVO> checkKnvvExist(It_KnvvVO ik);

   public int insertKnvp(It_KnvpVO ik);
   public int updateKnvp(It_KnvpVO ik);
   public void deleteKnvp(It_KnvpVO ik);
   public List<It_KnvpVO> queryKnvpListByParam(It_KnvpVO ik);
   public List<It_KnvpVO> checkKnvpExist(It_KnvpVO ik);

   public int insertAdrc(It_AdrcVO ia);
   public int updateAdrc(It_AdrcVO ia);
   public void deleteAdrc(It_AdrcVO ia);
   public List<It_AdrcVO> queryAdrcListByParam(It_AdrcVO ia);
   public List<It_AdrcVO> checkAdrcExist(It_AdrcVO ia);

   public int insertZtsd_012(It_Ztsd_012VO iz);
   public int updateZtsd_012(It_Ztsd_012VO iz);
   public void deleteZtsd_012(It_Ztsd_012VO iz);
   public List<It_Ztsd_012VO> queryZtsd_012ListByParam(It_Ztsd_012VO iz);
   public List<It_Ztsd_012VO> checkZtsd_012Exist(It_Ztsd_012VO iz);


   public int insertZtsd_002(It_Ztsd_002VO iz);
   public int updateZtsd_002(It_Ztsd_002VO iz);
   public void deleteZtsd_002(It_Ztsd_002VO iz);
   public List<It_Ztsd_002VO> queryZtsd_002ListByParam(It_Ztsd_002VO iz);
   public List<It_Ztsd_002VO> checkZtsd_002Exist(It_Ztsd_002VO iz);


   public int insertZtsd206_head(Ztsd206_headVO zt);
   public List<Ztsd206_headVO> checkZtsd206_headExist(Ztsd206_headVO zt);

   public int insertZtsd206_item(Ztsd206_ItemVO zi);
   public List<Ztsd206_ItemVO> checkZtsd206_itemExist(Ztsd206_ItemVO zi);

   public int insertZtsd206_kunnr_ec(Ztsd206_kunnr_ecVO zk);
   public List<Ztsd206_kunnr_ecVO> checkZtsd206_kunnr_ecExist(Ztsd206_kunnr_ecVO zk);





}
