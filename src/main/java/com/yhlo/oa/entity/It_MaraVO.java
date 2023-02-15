package com.yhlo.oa.entity;

import com.yhlo.oa.util.poi.Excel;
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
    @Excel(name = "主键id")
    @ApiModelProperty(value = "主键id")
    private int id;
    @Excel(name = "物料编号")
    @ApiModelProperty(value = "物料编号")
    private String matnr;
    @Excel(name = "物料描述")
    @ApiModelProperty(value = "物料描述")
    private String maktx;
    @Excel(name = "物料类型")
    @ApiModelProperty(value = "物料类型")
    private String mtart;
    @Excel(name = "物料组")
    @ApiModelProperty(value = "物料组")
    private String matkl;
    @Excel(name = "存储条件")
    @ApiModelProperty(value = "存储条件")
    private String raube;
    @Excel(name = "旧物料号")
    @ApiModelProperty(value = "旧物料号")
    private String bismt;
    @Excel(name = "产品组")
    @ApiModelProperty(value = "产品组")
    private String spart;
    @Excel(name = "产品层次")
    @ApiModelProperty(value = "产品层次")
    private String prdha;
    @Excel(name = "基本计量单位")
    @ApiModelProperty(value = "基本计量单位")
    private String meins;
    @Excel(name = "跨工厂物料状态")
    @ApiModelProperty(value = "跨工厂物料状态")
    private String mstae;
    @Excel(name = "批次管理需求标识")
    @ApiModelProperty(value = "批次管理需求标识")
    private String xchpf;
    @Excel(name = "外部物料组")
    @ApiModelProperty(value = "外部物料组")
    private String extwg;
    @Excel(name = "行业领域")
    @ApiModelProperty(value = "行业领域")
    private String mbrsh;
    @Excel(name = "最短剩余货架寿命")
    @ApiModelProperty(value = "最短剩余货架寿命")
    private String mhdrz;
    @Excel(name = "总货架寿命")
    @ApiModelProperty(value = "总货架寿命")
    private String mhdhb;
    @Excel(name = "常规项目类别组")
    @ApiModelProperty(value = "常规项目类别组")
    private String mtpos_mara;
    @Excel(name = "规格型号")
    @ApiModelProperty(value = "规格型号")
    private String zggxh;
    @Excel(name = "助记码")
    @ApiModelProperty(value = "助记码")
    private String zcus01;
    @Excel(name = "生产产商")
    @ApiModelProperty(value = "生产产商")
    private String zcus02;
    @Excel(name = "生产厂商简称")
    @ApiModelProperty(value = "生产厂商简称")
    private String zcus02_1;
    @Excel(name = "生产厂商备注")
    @ApiModelProperty(value = "生产厂商备注")
    private String zcus02_2;
    @Excel(name = "批准文号")
    @ApiModelProperty(value = "批准文号")
    private String zcus03;
    @Excel(name = "医疗器械生产许可证号/生产备案凭证号")
    @ApiModelProperty(value = "医疗器械生产许可证号/生产备案凭证号")
    private String zcus04;
    @Excel(name = "生产许可证/生产备案凭证发证日期")
    @ApiModelProperty(value = "生产许可证/生产备案凭证发证日期")
    private String zcus05;
    @Excel(name = "生产许可证/生产备案凭证有效期")
    @ApiModelProperty(value = "生产许可证/生产备案凭证有效期")
    private String zcus06;
    @Excel(name = "UDI商品码")
    @ApiModelProperty(value = "UDI商品码")
    private String zcus07;
    @Excel(name = "HS-CODE")
    @ApiModelProperty(value = "HS-CODE")
    private String zcus08;
    @Excel(name = "报关名称")
    @ApiModelProperty(value = "报关名称")
    private String zcus09;
    @Excel(name = "是否回小样")
    @ApiModelProperty(value = "是否回小样")
    private String zcus10;
    @Excel(name = "税收分类编码")
    @ApiModelProperty(value = "税收分类编码")
    private String zcus11;
    @Excel(name = "物料等级")
    @ApiModelProperty(value = "物料等级")
    private String zcus12;
    @Excel(name = "包装类别")
    @ApiModelProperty(value = "包装类别")
    private String zcus13;
    @Excel(name = "是否有授权")
    @ApiModelProperty(value = "是否有授权")
    private String zcus14;
    @Excel(name = "材质")
    @ApiModelProperty(value = "材质")
    private String zcus15;
    @Excel(name = "成份")
    @ApiModelProperty(value = "成份")
    private String zcus16;
    @Excel(name = "功能")
    @ApiModelProperty(value = "功能")
    private String zcus17;
    @Excel(name = "原理")
    @ApiModelProperty(value = "原理")
    private String zcus18;
    @Excel(name = "售后物料等级")
    @ApiModelProperty(value = "售后物料等级")
    private String zcus19;
    @Excel(name = "售后物料属性")
    @ApiModelProperty(value = "售后物料属性")
    private String zcus20;
    @Excel(name = "通用性")
    @ApiModelProperty(value = "通用性")
    private String zcus21;
    @Excel(name = "品牌")
    @ApiModelProperty(value = "品牌")
    private String zcus22;
    @Excel(name = "是否危险品")
    @ApiModelProperty(value = "是否危险品")
    private String zcus23;
    @Excel(name = "型号")
    @ApiModelProperty(value = "型号")
    private String zcus24;
    @Excel(name = "用途")
    @ApiModelProperty(value = "用途")
    private String zcus25;
    @Excel(name = "检验对象")
    @ApiModelProperty(value = "检验对象")
    private String zcus26;
    @Excel(name = "包装规格")
    @ApiModelProperty(value = "包装规格")
    private String zcus27;
    @Excel(name = "品牌类型")
    @ApiModelProperty(value = "品牌类型")
    private String zcus28;
    @Excel(name = "加工工艺")
    @ApiModelProperty(value = "加工工艺")
    private String zcus29;
    @Excel(name = "是否配定剂量或零售包装")
    @ApiModelProperty(value = "是否配定剂量或零售包装")
    private String zcus30;
    @Excel(name = "是否配有试剂,试纸,有无电脑端口的打印机")
    @ApiModelProperty(value = "是否配有试剂,试纸,有无电脑端口的打印机")
    private String zcus31;
    @Excel(name = "用于体内诊断还是体外诊断")
    @ApiModelProperty(value = "用于体内诊断还是体外诊断")
    private String zcus32;
    @Excel(name = "产品简称")
    @ApiModelProperty(value = "产品简称")
    private String zcus33;
    @Excel(name = "产地")
    @ApiModelProperty(value = "产地")
    private String zcus34;
    @Excel(name = "医疗器械分类代码")
    @ApiModelProperty(value = "医疗器械分类代码")
    private String zcus35;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;

    @ApiModelProperty(value = "下拉框显示名称")
    private String showName;

    private Checkbox cb = new Checkbox();

    public Checkbox getCb() {
        return cb;
    }

    public void setSelected(Checkbox cb) {
        this.cb = cb;
    }
}
