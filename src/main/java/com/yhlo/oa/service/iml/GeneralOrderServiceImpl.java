package com.yhlo.oa.service.iml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.*;
import com.yhlo.oa.enums.OrderStatuEnum;
import com.yhlo.oa.mapper.GeneralOrderMapper;
import com.yhlo.oa.service.GeneralOrderService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;

/**
 * @create: 2022-04-24 16:25
 * @description:
 **/
@Service
public class GeneralOrderServiceImpl implements GeneralOrderService {

    @Resource
    private GeneralOrderMapper orderMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;



    @Override
    public List<GeneralOrderVO> checkDdsSalesOrderNoExists(String nowDate) {
        return orderMapper.checkDdsSalesOrderNoExists(nowDate);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insertOrder(GeneralOrderVO order, List<GeneralOrderDetailVO> detailList) {

        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GeneralOrderMapper mapper = session.getMapper(GeneralOrderMapper.class);

        String rs = "ok，操作成功";

        try {
            orderMapper.insertOrder(order);
            rs = String.valueOf(order.getId());
            for (int i = 0; i < detailList.size(); i++) {
                detailList.get(i).setDdsSalesOrderNo(order.getDdsSalesOrderNo());
                detailList.get(i).setMultiAngleTradeCode(order.getMultiAngleTradeCode());
                detailList.get(i).setCreateBy(order.getCreateBy());
                mapper.insertOrderDetails(detailList.get(i));
                if (i % 50 == 0 || i == detailList.size() - 1) {
                    session.commit();
                    session.clearCache(); //清理缓存，防止溢出
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败："+e.getMessage();
            e.printStackTrace();
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rs;
    }


    /**
     * @Author cy
     * @Description 在反审后更新订单数据时，先将原来的数据插入历史数据表
     * @Date 2023/1/14 15:08
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insertOrderHistory(GeneralOrderHistoryVO orderHistory) {
        String rs = "ok，操作成功";
        try {
            orderMapper.insertOrderHistory(orderHistory);

            GeneralOrderDetailHistoryVO orderDetailsHis = new GeneralOrderDetailHistoryVO();
            orderDetailsHis.setDdsSalesOrderNo(orderHistory.getDdsSalesOrderNo());
            orderDetailsHis.setOrderDataId(orderHistory.getId());
            orderDetailsHis.setUpdateBy(orderHistory.getUpdateBy());
            orderDetailsHis.setOperatRemark(orderHistory.getOperatRemark());
            orderMapper.insertOrderDetailsHistory(orderDetailsHis);
        }catch (Exception e){
            rs = "error,操作失败："+e.getMessage();
            e.printStackTrace();
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rs;
    }


    /**
     * @Author cy
     * @Description 新增页面保存后，在本页面修改订单
     * @Return
     * @Date 2023/1/14 14:44
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateOrder(GeneralOrderVO order, List<GeneralOrderDetailVO> detailList) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GeneralOrderMapper mapper = session.getMapper(GeneralOrderMapper.class);

        String rs = "ok，操作成功";

        try {
            orderMapper.deleteOrderDetails(order);
            orderMapper.updateOrder(order);
            for (int i = 0; i < detailList.size(); i++) {
                detailList.get(i).setDdsSalesOrderNo(order.getDdsSalesOrderNo());
                detailList.get(i).setMultiAngleTradeCode(order.getMultiAngleTradeCode());
                detailList.get(i).setCreateBy(order.getCreateBy());
                mapper.insertOrderDetails(detailList.get(i));
                if (i % 50 == 0 || i == detailList.size() - 1) {
                    session.commit();
                    session.clearCache(); //清理缓存，防止溢出
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败："+e.getMessage();
            e.printStackTrace();
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rs;
    }

    /**
     * @Author cy
     * @Description 在反审后更新订单数据
     * @Return
     * @Date 2023/1/14 14:43
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateOrderByReApproval(GeneralOrderVO order, List<GeneralOrderDetailVO> detailList) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        GeneralOrderMapper mapper = session.getMapper(GeneralOrderMapper.class);

        String rs = "ok，操作成功";

        try {
            orderMapper.updateOrder(order);
            for (int i = 0; i < detailList.size(); i++) {
                detailList.get(i).setDdsSalesOrderNo(order.getDdsSalesOrderNo());
                detailList.get(i).setMultiAngleTradeCode(order.getMultiAngleTradeCode());
                detailList.get(i).setCreateBy(order.getCreateBy());
                detailList.get(i).setUpdateBy(order.getUpdateBy());
                if(detailList.get(i).getId()>0){//明细id大于0表示是本身就存在的数据，此时执行修改
                    mapper.updateOrderdetails(detailList.get(i));
                }else{//否则就是新增的明细，新增明细时，行数据id=0，此时执行新增明细
                    mapper.insertOrderDetails(detailList.get(i));
                }

                if (i % 50 == 0 || i == detailList.size() - 1) {
                    session.commit();
                    session.clearCache(); //清理缓存，防止溢出
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e){
            rs = "error,操作失败："+e.getMessage();
            e.printStackTrace();
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return rs;
    }


    @Override
    public PageInfo<GeneralOrderApprovalVO> getGeneralOrderApprovalList(Integer currentPage, Integer pageSize, String ddsSalesOrderNo, String customerName,String approvalStatus) {
        PageHelper.startPage(currentPage, pageSize);
        List<GeneralOrderApprovalVO> list = orderMapper.getGeneralOrderApprovalList(ddsSalesOrderNo,customerName,approvalStatus);
        return new PageInfo<>(list);
    }

    @Override
    public String updateOrderApprovalStatu(GeneralOrderApprovalVO goa) {
        String rs = "ok，操作成功";
        try {
            orderMapper.updateOrderApprovalStatu(goa);
        }catch (Exception e){
            rs = "error,操作失败："+e.getMessage();
            e.printStackTrace();
        }
        return rs;
    }


    @Override
    public String updateOrderApprovalStatuAndOrderNo(GeneralOrderApprovalVO goa) {
        String rs = "ok，操作成功";
        try {
            orderMapper.updateOrderApprovalStatuAndOrderNo(goa);
        }catch (Exception e){
            rs = "error,操作失败："+e.getMessage();
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<GeneralOrderDetailVO> getOrderListByDdsSalesOrderNo(String ddsSalesOrderNo) {
        return orderMapper.getOrderListByDdsSalesOrderNo(ddsSalesOrderNo);
    }

    @Override
    public List<GeneralOrderVO> getOrderList(String ddsSalesOrderNo) {
        return orderMapper.getOrderList(ddsSalesOrderNo);
    }

    @Override
    public List<GeneralOrderVO> getOrderStatus(String ddsSalesOrderNo) {
        return orderMapper.getOrderStatus(ddsSalesOrderNo);
    }

    @Override
    public List<GeneralOrderApprovalVO> getAllGeneralOrderApprovalList() {
        return orderMapper.getAllGeneralOrderApprovalList();
    }


}
