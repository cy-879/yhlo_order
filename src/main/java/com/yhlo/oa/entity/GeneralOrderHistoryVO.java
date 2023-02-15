package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @create: 2022-08-11 10:56
 * @description:
 **/
@Data
@ApiModel("订单历史记录")
public class GeneralOrderHistoryVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "订单类型")
    private String orderType;
    @ApiModelProperty(value = "订单参考")
    private String orderReference;
    @ApiModelProperty(value = "SAP销售订单号")
    private String sapSalesOrderNo;
    @ApiModelProperty(value = "SAP采购订单号")
    private String sapPurchaseOrderNo;
    @ApiModelProperty(value = "DDS销售订单号")
    private String ddsSalesOrderNo;
    @ApiModelProperty(value = "销售组织")
    private String salesOrg;
    @ApiModelProperty(value = "产品组")
    private String productGroup;
    @ApiModelProperty(value = "分销渠道")
    private String distributionChannel;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "客户代码")
    private String customerCode;
    @ApiModelProperty(value = "送达方名称")
    private String songdfmc;
    @ApiModelProperty(value = "送达方代码")
    private String songdfdm;
    @ApiModelProperty(value = "销售部门")
    private String saleDept;
    @ApiModelProperty(value = "销售小组")
    private String saleGroup;
    @ApiModelProperty(value = "付款条件")
    private String termOfPayment;
    @ApiModelProperty(value = "订单原因")
    private String orderReason;
    @ApiModelProperty(value = "终端客户")
    private String endCustomer;
    @ApiModelProperty(value = "业务员")
    private String salesman;
    @ApiModelProperty(value = "制单人")
    private String preparedBy;
    @ApiModelProperty(value = "凭证日期")
    private String documentDate;
    @ApiModelProperty(value = "总金额")
    private Float totalAmount;
    @ApiModelProperty(value = "未税金额")
    private Float amountNotTaxed;
    @ApiModelProperty(value = "装运条件")
    private String shippingConditions;
    @ApiModelProperty(value = "组合交货")
    private String combinationDelivery;
    @ApiModelProperty(value = "POD")
    private String pod;
    @ApiModelProperty(value = "审核状态")
    private String approvalStatus;
    @ApiModelProperty(value = "交货状态")
    private String deliveryStatus;
    @ApiModelProperty(value = "唛头/订单摘要")
    private String orderSummary;
    @ApiModelProperty(value = "发货信息备注")
    private String shippingRemarks;
    @ApiModelProperty(value = "多角贸易代码")
    private String multiAngleTradeCode;
    @ApiModelProperty(value = "操作备注")
    private String operatRemark;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "创建人")
    private String createBy;
    @ApiModelProperty(value = "修改时间")
    private String updateTime;
    @ApiModelProperty(value = "修改人")
    private String updateBy;

}
