package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: It_maraVO
 * @Description:
 * @date 2022/5/2313:44
 */
@Data
@ApiModel("物料主数据")
public class It_MaraVO extends  BaseEntity{

    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "物料编号")
    private String matnr;
    @ApiModelProperty(value = "物料描述")
    private String maktx;
    @ApiModelProperty(value = "物料类型")
    private String mtart;
    @ApiModelProperty(value = "物料组")
    private String matkl;
    @ApiModelProperty(value = "存储条件")
    private String raube;
    @ApiModelProperty(value = "旧物料号")
    private String bismt;
    @ApiModelProperty(value = "产品组")
    private String spart;
    @ApiModelProperty(value = "产品层次")
    private String prdha;
    @ApiModelProperty(value = "基本计量单位")
    private String meins;
    @ApiModelProperty(value = "跨工厂物料状态")
    private String mstae;
    @ApiModelProperty(value = "批次管理需求标识")
    private String xchpf;
    @ApiModelProperty(value = "外部物料组")
    private String extwg;
    @ApiModelProperty(value = "行业领域")
    private String mbrsh;
    @ApiModelProperty(value = "最短剩余货架寿命")
    private String mhdrz;
    @ApiModelProperty(value = "总货架寿命")
    private String mhdhb;
    @ApiModelProperty(value = "常规项目类别组")
    private String mtpos_mara;
    @ApiModelProperty(value = "规格型号")
    private String zggxh;
    @ApiModelProperty(value = "助记码")
    private String zcus01;
    @ApiModelProperty(value = "生产产商")
    private String zcus02;
    @ApiModelProperty(value = "生产厂商简称")
    private String zcus02_1;
    @ApiModelProperty(value = "生产厂商备注")
    private String zcus02_2;
    @ApiModelProperty(value = "批准文号")
    private String zcus03;
    @ApiModelProperty(value = "医疗器械生产许可证号/生产备案凭证号")
    private String zcus04;
    @ApiModelProperty(value = "生产许可证/生产备案凭证发证日期")
    private String zcus05;
    @ApiModelProperty(value = "生产许可证/生产备案凭证有效期")
    private String zcus06;
    @ApiModelProperty(value = "UDI商品码")
    private String zcus07;
    @ApiModelProperty(value = "HS-CODE")
    private String zcus08;
    @ApiModelProperty(value = "报关名称")
    private String zcus09;
    @ApiModelProperty(value = "是否回小样")
    private String zcus10;
    @ApiModelProperty(value = "税收分类编码")
    private String zcus11;
    @ApiModelProperty(value = "物料等级")
    private String zcus12;
    @ApiModelProperty(value = "包装类别")
    private String zcus13;
    @ApiModelProperty(value = "是否有授权")
    private String zcus14;
    @ApiModelProperty(value = "材质")
    private String zcus15;
    @ApiModelProperty(value = "成份")
    private String zcus16;
    @ApiModelProperty(value = "功能")
    private String zcus17;
    @ApiModelProperty(value = "原理")
    private String zcus18;
    @ApiModelProperty(value = "售后物料等级")
    private String zcus19;
    @ApiModelProperty(value = "售后物料属性")
    private String zcus20;
    @ApiModelProperty(value = "通用性")
    private String zcus21;
    @ApiModelProperty(value = "品牌")
    private String zcus22;
    @ApiModelProperty(value = "是否危险品")
    private String zcus23;
    @ApiModelProperty(value = "型号")
    private String zcus24;
    @ApiModelProperty(value = "用途")
    private String zcus25;
    @ApiModelProperty(value = "检验对象")
    private String zcus26;
    @ApiModelProperty(value = "包装规格")
    private String zcus27;
    @ApiModelProperty(value = "品牌类型")
    private String zcus28;
    @ApiModelProperty(value = "加工工艺")
    private String zcus29;
    @ApiModelProperty(value = "是否配定剂量或零售包装")
    private String zcus30;
    @ApiModelProperty(value = "是否配有试剂,试纸,有无电脑端口的打印机")
    private String zcus31;
    @ApiModelProperty(value = "用于体内诊断还是体外诊断")
    private String zcus32;
    @ApiModelProperty(value = "产品简称")
    private String zcus33;
    @ApiModelProperty(value = "产地")
    private String zcus34;
    @ApiModelProperty(value = "医疗器械分类代码")
    private String zcus35;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;


    private Checkbox cb = new Checkbox();

    public Checkbox getCb() {
        return cb;
    }

    public void setSelected(Checkbox cb) {
        this.cb = cb;
    }
}
