package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: RolesOrderVO
 * @Description:
 * @date 2022/4/2814:36
 */

@Data
@ApiModel("多角订单")
public class RolesOrderVO {

    @ApiModelProperty(value = "主键id")
    private Long id;
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    @ApiModelProperty(value = "合约订单号")
    private String contractOrderNo;
    @ApiModelProperty(value = "销售订单")
    private String saleOrderNo;
    @ApiModelProperty(value = "售达方")
    private String deliveryParty;
    @ApiModelProperty(value = "送达方")
    private String consignee;
    @ApiModelProperty(value = "总含税金额")
    private Float totalTaxInclusiveAmount;
    @ApiModelProperty(value = "优惠券")
    private String coupon;
    @ApiModelProperty(value = "运费")
    private Float freight;
    @ApiModelProperty(value = "价格类型")
    private String priceType;
    @ApiModelProperty(value = "销售组织")
    private String salesOrganization;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "创建人")
    private String createBy;
}
