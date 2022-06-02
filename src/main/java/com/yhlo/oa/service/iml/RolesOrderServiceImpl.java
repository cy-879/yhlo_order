package com.yhlo.oa.service.iml;

import com.yhlo.oa.entity.Item;
import com.yhlo.oa.entity.RolesOrderVO;
import com.yhlo.oa.mapper.RolesOrderMapper;
import com.yhlo.oa.service.RolesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cy
 * @ClassName: RolesOrderServiceImpl
 * @Description:
 * @date 2022/4/2816:19
 */

@Service
public class RolesOrderServiceImpl implements RolesOrderService {

    @Autowired
    private RolesOrderMapper orderMapper;

    @Override
    public void saveOrder(RolesOrderVO order) {
        orderMapper.insertOrder(order);
    }

    @Override
    public List<RolesOrderVO> queryOrderList() {
        return orderMapper.queryOrderList();
    }

    @Override
    public List<Item> getOrderItem() {
        return orderMapper.getOrderItem();
    }
}
