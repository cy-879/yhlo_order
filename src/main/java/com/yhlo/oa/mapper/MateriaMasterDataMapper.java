package com.yhlo.oa.mapper;


import com.yhlo.oa.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaMasterDataMapper {

   public List<Item> getConfigItem(@Param("ttype")String ttype);

   public List<It_MaraVO> queryMaraList(@Param("matnr")String matnr);



}
