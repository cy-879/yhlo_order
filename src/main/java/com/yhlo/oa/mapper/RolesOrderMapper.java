package com.yhlo.oa.mapper;

import com.yhlo.oa.entity.Item;
import com.yhlo.oa.entity.RolesOrderVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesOrderMapper {

   public void insertOrder(RolesOrderVO orderVO);

   public List<RolesOrderVO> queryOrderList();

   public List<Item> getOrderItem();


}
