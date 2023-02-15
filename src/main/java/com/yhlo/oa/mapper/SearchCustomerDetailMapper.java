package com.yhlo.oa.mapper;


import com.yhlo.oa.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchCustomerDetailMapper {


   public List<It_Knb1VO> queryKnb1List(@Param("kunnr")String kunnr);

   public List<It_KnvvVO> queryKnvvList(@Param("kunnr")String kunnr);

   public List<It_KnvpVO> queryKnvpList(@Param("kunnr")String kunnr);

   public List<It_AdrcVO> queryAdrcList(@Param("addrnumber")String addrnumber);

   public List<It_Ztsd_012VO> queryZtsdList(@Param("kunnr")String kunnr);
}
