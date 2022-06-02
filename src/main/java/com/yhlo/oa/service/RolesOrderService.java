package com.yhlo.oa.service;

import com.yhlo.oa.entity.Item;
import com.yhlo.oa.entity.RolesOrderVO;

import java.util.List;

public interface RolesOrderService {
    public void saveOrder(RolesOrderVO order);

    public List<RolesOrderVO> queryOrderList();

    public List<Item> getOrderItem();
}
