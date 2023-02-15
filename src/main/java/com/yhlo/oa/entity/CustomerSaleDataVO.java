package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: CustomerSaleDataVO
 * @Description:
 * @date 2023/1/617:30
 */
@Data
@ApiModel("客户销售数据视图关联数据")
public class CustomerSaleDataVO {

    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "客户编号")
    private String kunnr;
    @ApiModelProperty(value = "名称1")
    private String name1;
    @ApiModelProperty(value = "名称2")
    private String name2;
    @ApiModelProperty(value = "销售组织")
    private String vkorg;
    @ApiModelProperty(value = "分销渠道")
    private String vtweg;
    @ApiModelProperty(value = "产品组")
    private String spart;
    @ApiModelProperty(value = "创建对象的人员名称")
    private String ernam;
    @ApiModelProperty(value = "记录创建日期")
    private String erdat;
    @ApiModelProperty(value = "权限组")
    private String begru;
    @ApiModelProperty(value = "客户的删除标记(销售级别)")
    private String loevm;
    @ApiModelProperty(value = "统计组")
    private String versg;
    @ApiModelProperty(value = "客户订单冻结（销售范围）")
    private String aufsd;
    @ApiModelProperty(value = "用于定价过程确定的客户分类")
    private String kalks;
    @ApiModelProperty(value = "客户组")
    private String kdgrp;
    @ApiModelProperty(value = "销售地区")
    private String bzirk;
    @ApiModelProperty(value = "客户价格组")
    private String konda;
    @ApiModelProperty(value = "价格清单类型")
    private String pltyp;
    @ApiModelProperty(value = "国际贸易条款（第 1 部分）")
    private String inco1;
    @ApiModelProperty(value = "国际贸易条款（第 2 部分）")
    private String inco2;
    @ApiModelProperty(value = "客户交货冻结（销售范围）")
    private String lifsd;
    @ApiModelProperty(value = "订单组合标识")
    private String kzazu;
    @ApiModelProperty(value = "装运条件")
    private String vsbed;
    @ApiModelProperty(value = "冻结客户出具发票(销售和分销)")
    private String faksd;
    @ApiModelProperty(value = "货币码")
    private String waers;
    @ApiModelProperty(value = "客户分类（ABC分析）")
    private String klabc;
    @ApiModelProperty(value = "此客户的账户分配组")
    private String ktgrd;
    @ApiModelProperty(value = "收付条件代码")
    private String zterm;
    @ApiModelProperty(value = "从工厂")
    private String vwerk;
    @ApiModelProperty(value = "销售组")
    private String vkgrp;
    @ApiModelProperty(value = "销售办事处")
    private String vkbur;
    @ApiModelProperty(value = "定价标识相关")
    private String prfre;
    @ApiModelProperty(value = "信用控制范围")
    private String kkber;
    @ApiModelProperty(value = "与POD处理相关")
    private String podkz;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;

}
