package com.yhlo.oa.entity;

import com.yhlo.oa.util.poi.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cy
 * @Description
 * * @param null
 * @Return 
 * @Date 2022/5/27 8:59
 */

@Data
@ApiModel("客户一般数据2")
public class It_Kna1VO {

    @Excel(name = "主键id")
    @ApiModelProperty(value = "主键id")
    private int id;
    @Excel(name = "客户编号")
    @ApiModelProperty(value = "客户编号")
    private String kunnr;
    @Excel(name = "国家/RebatePolicyVO")
    @ApiModelProperty(value = "国家/地区代码")
    private String land1;
    @Excel(name = "名称1")
    @ApiModelProperty(value = "名称1")
    private String name1;
    @Excel(name = "名称2")
    @ApiModelProperty(value = "名称2")
    private String name2;
    @Excel(name = "城市")
    @ApiModelProperty(value = "城市")
    private String ort01;
    @Excel(name = "邮政编码")
    @ApiModelProperty(value = "邮政编码")
    private String pstlz;
    @Excel(name = "地区（省/自治区/直辖市、市、县）")
    @ApiModelProperty(value = "地区（省/自治区/直辖市、市、县）")
    private String regio;
    @Excel(name = "街道和房屋号")
    @ApiModelProperty(value = "街道和房屋号")
    private String stras;
    @Excel(name = "第一个电话号")
    @ApiModelProperty(value = "第一个电话号")
    private String telf1;
    @Excel(name = "传真号")
    @ApiModelProperty(value = "传真号")
    private String telfx;
    @Excel(name = "地址")
    @ApiModelProperty(value = "地址")
    private String adrnr;
    @Excel(name = "记录创建日期")
    @ApiModelProperty(value = "记录创建日期")
    private String erdat;
    @Excel(name = "创建对象的人员名称")
    @ApiModelProperty(value = "创建对象的人员名称")
    private String ernam;
    @Excel(name = "客户帐户组")
    @ApiModelProperty(value = "客户帐户组")
    private String ktokd;
    @Excel(name = "语言代码")
    @ApiModelProperty(value = "语言代码")
    private String spras;
    @Excel(name = "第二个电话号")
    @ApiModelProperty(value = "第二个电话号")
    private String telf2;
    @Excel(name = "税号5")
    @ApiModelProperty(value = "税号5")
    private String stcd5;
    @Excel(name = "客户企业类型")
    @ApiModelProperty(value = "客户企业类型")
    private String kukla;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;


    @ApiModelProperty(value = "业务员凭拼接")
    private String salesman;


    private Checkbox cb = new Checkbox();

    public Checkbox getCb() {
        return cb;
    }

    public void setSelected(Checkbox cb) {
        this.cb = cb;
    }

}
