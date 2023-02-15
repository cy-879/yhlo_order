package com.yhlo.oa.entity;

import com.yhlo.oa.util.poi.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: 订单导入模板
 * @Description:
 * @date 2022/10/2610:54
 */
@Data
@ApiModel("订单导入模板")
public class OrderImportTemplateVO {

    @Excel(name = "物料号")
    @ApiModelProperty(value = "物料号")
    private String materialNo;

    @Excel(name = "订单数量")
    @ApiModelProperty(value = "订单数量")
    private Integer orderQty;

    //@Excel(name = "已交货数量（基本单位）")
    @ApiModelProperty(value = "已交货数量（基本单位）")
    private Integer qtyDelivered;

    //@Excel(name = "未清数量（基本单位）")
    @ApiModelProperty(value = "未清数量（基本单位）")
    private Integer openQty;

    @Excel(name = "订单单位")
    @ApiModelProperty(value = "订单单位")
    private String orderUnit;

    @Excel(name = "定价日期")
    @ApiModelProperty(value = "定价日期")
    private String pricingDate;

    @Excel(name = "手工价")
    @ApiModelProperty(value = "手工价")
    private Float manualPrice;

    @Excel(name = "行项目类型")
    @ApiModelProperty(value = "行项目类型")
    private String lineItemType;

    @Excel(name = "行项目文本")
    @ApiModelProperty(value = "行项目文本")
    private String lineItemText;

}
