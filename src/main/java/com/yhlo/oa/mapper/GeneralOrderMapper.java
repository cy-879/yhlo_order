package com.yhlo.oa.mapper;

import com.yhlo.oa.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneralOrderMapper {

    public void insertOrder(GeneralOrderVO orderVO);

    public void insertOrderHistory(GeneralOrderHistoryVO orderHistory);

    public void updateOrder(GeneralOrderVO orderVO);

    public void updateOrderdetails(GeneralOrderDetailVO orderDetails);

    public void insertOrderDetails(GeneralOrderDetailVO orderDetails);

    public void insertOrderDetailsHistory(GeneralOrderDetailHistoryVO orderDetailsHis);

    public void deleteOrderDetails(GeneralOrderVO orderVO);

    public List<GeneralOrderVO> checkDdsSalesOrderNoExists(@Param("ddsSalesOrderNo") String ddsSalesOrderNo);


    public List<GeneralOrderApprovalVO> getGeneralOrderApprovalList(
                @Param("ddsSalesOrderNo") String ddsSalesOrderNo,
                @Param("customerName") String customerName,
                @Param("approvalStatus") String approvalStatus);

    public void updateOrderApprovalStatu(GeneralOrderApprovalVO orderApprovaL);

    public void updateOrderApprovalStatuAndOrderNo(GeneralOrderApprovalVO orderApprovaL);

    public List<GeneralOrderDetailVO> getOrderListByDdsSalesOrderNo(@Param("ddsSalesOrderNo") String ddsSalesOrderNo);

    public List<GeneralOrderVO> getOrderList(@Param("ddsSalesOrderNo") String ddsSalesOrderNo);

    public List<GeneralOrderVO> getOrderStatus(@Param("ddsSalesOrderNo") String ddsSalesOrderNo);

    public List<GeneralOrderApprovalVO> getAllGeneralOrderApprovalList();
}
