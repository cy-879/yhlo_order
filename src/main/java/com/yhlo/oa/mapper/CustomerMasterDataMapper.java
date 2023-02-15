package com.yhlo.oa.mapper;


import com.yhlo.oa.entity.It_Kna1VO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMasterDataMapper {

   public List<It_Kna1VO> queryKna1List(@Param("kunnr")String kunnr, @Param("name1")String name1);


}
