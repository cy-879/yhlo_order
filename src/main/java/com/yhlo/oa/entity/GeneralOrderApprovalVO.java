package com.yhlo.oa.entity;

import com.yhlo.oa.util.poi.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: GeneralOrderApprovalVO
 * @Description:
 * @date 2022/10/1314:54
 */

@Data
@ApiModel("订单审批列表")
public class GeneralOrderApprovalVO {
    @ApiModelProperty(value = "序号")
    private String xuh;
    @Excel(name = "主键id")
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @Excel(name = "订单类型")
    @ApiModelProperty(value = "订单类型")
    private String orderType;
    @Excel(name = "订单参考")
    @ApiModelProperty(value = "订单参考")
    private String orderReference;
    @Excel(name = "SAP销售订单号")
    @ApiModelProperty(value = "SAP销售订单号")
    private String sapSalesOrderNo;
    @Excel(name = "SAP采购订单号")
    @ApiModelProperty(value = "SAP采购订单号")
    private String sapPurchaseOrderNo;
    @Excel(name = "DDS销售订单号")
    @ApiModelProperty(value = "DDS销售订单号")
    private String ddsSalesOrderNo;
    @Excel(name = "销售组织")
    @ApiModelProperty(value = "销售组织")
    private String salesOrg;
    @Excel(name = "产品组")
    @ApiModelProperty(value = "产品组")
    private String productGroup;
    @Excel(name = "分销渠道")
    @ApiModelProperty(value = "分销渠道")
    private String distributionChannel;
    @Excel(name = "客户名称")
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @Excel(name = "客户代码")
    @ApiModelProperty(value = "客户代码")
    private String customerCode;
    @Excel(name = "送达方名称")
    @ApiModelProperty(value = "送达方名称")
    private String songdfmc;
    @Excel(name = "送达方代码")
    @ApiModelProperty(value = "送达方代码")
    private String songdfdm;
    @Excel(name = "销售部门")
    @ApiModelProperty(value = "销售部门")
    private String saleDept;
    @Excel(name = "销售小组")
    @ApiModelProperty(value = "销售小组")
    private String saleGroup;
    @Excel(name = "付款条件")
    @ApiModelProperty(value = "付款条件")
    private String termOfPayment;
    @Excel(name = "订单原因")
    @ApiModelProperty(value = "订单原因")
    private String orderReason;
    @Excel(name = "终端客户")
    @ApiModelProperty(value = "终端客户")
    private String endCustomer;
    @ApiModelProperty(value = "终端客户代码")
    private String endCustomerCode;
    @Excel(name = "业务员")
    @ApiModelProperty(value = "业务员")
    private String salesman;
    @Excel(name = "制单人")
    @ApiModelProperty(value = "制单人")
    private String preparedBy;
    @Excel(name = "凭证日期")
    @ApiModelProperty(value = "凭证日期")
    private String documentDate;
    @Excel(name = "总金额")
    @ApiModelProperty(value = "总金额")
    private Float totalAmount;
    @Excel(name = "未税金额")
    @ApiModelProperty(value = "未税金额")
    private Float amountNotTaxed;
    @Excel(name = "装运条件")
    @ApiModelProperty(value = "装运条件")
    private String shippingConditions;
    @Excel(name = "组合交货")
    @ApiModelProperty(value = "组合交货")
    private String combinationDelivery;
    @Excel(name = "POD")
    @ApiModelProperty(value = "POD")
    private String pod;
    @Excel(name = "审核状态")
    @ApiModelProperty(value = "审核状态")
    private String approvalStatus;
    @Excel(name = "交货状态")
    @ApiModelProperty(value = "交货状态")
    private String deliveryStatus;
    @Excel(name = "唛头/订单摘要")
    @ApiModelProperty(value = "唛头/订单摘要")
    private String orderSummary;
    @Excel(name = "发货信息备注")
    @ApiModelProperty(value = "发货信息备注")
    private String shippingRemarks;
    @Excel(name = "多角贸易代码")
    @ApiModelProperty(value = "多角贸易代码")
    private String multiAngleTradeCode;

    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "创建人")
    private String createBy;
    @ApiModelProperty(value = "修改时间")
    private String updateTime;
    @ApiModelProperty(value = "修改人")
    private String updateBy;


    @Excel(name = "明细主键id")
    @ApiModelProperty(value = "明细主键id")
    private int id_detail;
    @Excel(name = "行项目")
    @ApiModelProperty(value = "行项目")
    private String lineItem;
    @Excel(name = "助记码")
    @ApiModelProperty(value = "助记码")
    private String mnemonicCode;
    @Excel(name = "物料号")
    @ApiModelProperty(value = "物料号")
    private String materialNo;
    @Excel(name = "物料描述")
    @ApiModelProperty(value = "物料描述")
    private String materialDesc;
    @Excel(name = "规格型号")
    @ApiModelProperty(value = "规格型号")
    private String specification;
    @Excel(name = "订单数量")
    @ApiModelProperty(value = "订单数量")
    private Float orderQty;
    @Excel(name = "已交货数量（基本单位）")
    @ApiModelProperty(value = "已交货数量（基本单位）")
    private Float qtyDelivered;
    @Excel(name = "未清数量（基本单位）")
    @ApiModelProperty(value = "未清数量（基本单位）")
    private Float openQty;
    @Excel(name = "订单单位")
    @ApiModelProperty(value = "订单单位")
    private String orderUnit;
    @Excel(name = "基本单位")
    @ApiModelProperty(value = "基本单位")
    private String basicUnit;
    @Excel(name = "工厂")
    @ApiModelProperty(value = "工厂")
    private String factory;
    @Excel(name = "库存地点")
    @ApiModelProperty(value = "库存地点")
    private String inventoryLocation;
    @ApiModelProperty(value = "库存地点代码")
    private String inventoryLocationCode;
    @Excel(name = "装运点")
    @ApiModelProperty(value = "装运点")
    private String shippingPoint;
    @ApiModelProperty(value = "装运点代码")
    private String shippingPointCode;
    @Excel(name = "定价日期")
    @ApiModelProperty(value = "定价日期")
    private String pricingDate;
    @Excel(name = "标准价")
    @ApiModelProperty(value = "标准价")
    private Float standardPrice;
    @Excel(name = "手工价")
    @ApiModelProperty(value = "手工价")
    private Float manualPrice;
    @Excel(name = "订单基本单位数量")
    @ApiModelProperty(value = "订单基本单位数量")
    private Float orderBuomQty;
    @Excel(name = "折扣率")
    @ApiModelProperty(value = "折扣率")
    private Float discountRate;
    @Excel(name = "返利金额")
    @ApiModelProperty(value = "返利金额")
    private Float rebateAmount;
    @Excel(name = "折前金额")
    @ApiModelProperty(value = "折前金额")
    private Float amountBeforeDiscount;
    @Excel(name = "折后金额")
    @ApiModelProperty(value = "折后金额")
    private Float discountedAmount;
    @Excel(name = "存储温度")
    @ApiModelProperty(value = "存储温度")
    private String storageTemperature;
    @Excel(name = "税率")
    @ApiModelProperty(value = "税率")
    private String taxRate;
    @Excel(name = "货币")
    @ApiModelProperty(value = "货币")
    private String currency;
    @Excel(name = "是否批次/序列号管理")
    @ApiModelProperty(value = "是否批次/序列号管理")
    private String batch;
    @Excel(name = "生产许可/备案凭证")
    @ApiModelProperty(value = "生产许可/备案凭证")
    private String productionLicense;
    @Excel(name = "生产厂家全称")
    @ApiModelProperty(value = "生产厂家全称")
    private String fullNameOfManufacturer;
    @Excel(name = "批准文号")
    @ApiModelProperty(value = "批准文号")
    private String approvalNo;
    @Excel(name = "客户物料")
    @ApiModelProperty(value = "客户物料")
    private String customerMaterial;
    @Excel(name = "客户物料描述")
    @ApiModelProperty(value = "客户物料描述")
    private String customerMaterialDesc;
    @Excel(name = "省招标编码")
    @ApiModelProperty(value = "省招标编码")
    private String provincialBiddingCode;
    @Excel(name = "市招标编码")
    @ApiModelProperty(value = "市招标编码")
    private String cityBiddingCode;
    @Excel(name = "预留编码")
    @ApiModelProperty(value = "预留编码")
    private String reservedCode;
    @Excel(name = "品牌")
    @ApiModelProperty(value = "品牌")
    private String brand;
    @Excel(name = "物料组")
    @ApiModelProperty(value = "物料组")
    private String materialGroup;
    @Excel(name = "经营许可证/执业许可证")
    @ApiModelProperty(value = "经营许可证/执业许可证")
    private String businessLicense;
    @Excel(name = "生产备案凭证")
    @ApiModelProperty(value = "生产备案凭证")
    private String productionRecordCertificate;
    @Excel(name = "是否认领")
    @ApiModelProperty(value = "是否认领")
    private String claim;
    @Excel(name = "上一次单价")
    @ApiModelProperty(value = "上一次单价")
    private Float lastUnitPrice;
    @Excel(name = "多角贸易类型")
    @ApiModelProperty(value = "多角贸易类型")
    private String multiAngleTradeType;
    @Excel(name = "STO1单号")
    @ApiModelProperty(value = "STO1单号")
    private String sto1No;
    @Excel(name = "STO1行项目")
    @ApiModelProperty(value = "STO1行项目")
    private String sto1LineItem;
    @Excel(name = "行项目类型")
    @ApiModelProperty(value = "行项目类型")
    private String lineItemType;
    @Excel(name = "行项目文本")
    @ApiModelProperty(value = "行项目文本")
    private String lineItemText;

    @ApiModelProperty(value = "销售组织代码")
    private String salesOrgCode;
    @ApiModelProperty(value = "工厂代码")
    private String factoryCode;
    @ApiModelProperty(value = "基本计量单位转换分子")
    private String umrez;
    @ApiModelProperty(value = "转换为基本计量单位的分母")
    private String umren;
    @ApiModelProperty(value = "未使用额度")
    private Float unusedQuota;
    @ApiModelProperty(value = "物料组代码")
    private String materialGroupCode;
    @ApiModelProperty(value = "存储条件代码")
    private String storageTemperatureCode;
    @ApiModelProperty(value = "行项目类型代码")
    private String lineItemTypeCode;
    @ApiModelProperty(value = "产品组代码")
    private String productGroupCode;
    @ApiModelProperty(value = "分销渠道代码")
    private String distributionChannelCode;


    private Checkbox cb = new Checkbox();

    public Checkbox getCb() {
        return cb;
    }

    public void setSelected(Checkbox cb) {
        this.cb = cb;
    }
}
