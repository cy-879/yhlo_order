package com.yhlo.oa.mapper;


import com.yhlo.oa.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchMaterDetailMapper {


   public List<It_MvkeVO> queryMvkeList(@Param("matnr")String matnr);

   public List<It_MbewVO> queryMbewList(@Param("matnr")String matnr);

   public List<It_MarmVO> queryMarmList(@Param("matnr")String matnr);

   public List<It_MardVO> queryMardList(@Param("matnr")String matnr);

   public List<It_MarcVO> queryMarcList(@Param("matnr")String matnr);



}
