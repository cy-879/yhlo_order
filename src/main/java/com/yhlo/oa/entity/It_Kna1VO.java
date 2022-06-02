package com.yhlo.oa.entity;

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

    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "客户编号")
    private String kunnr;
    @ApiModelProperty(value = "国家/地区代码")
    private String land1;
    @ApiModelProperty(value = "名称1")
    private String name1;
    @ApiModelProperty(value = "名称2")
    private String name2;
    @ApiModelProperty(value = "城市")
    private String ort01;
    @ApiModelProperty(value = "邮政编码")
    private String pstlz;
    @ApiModelProperty(value = "地区（省/自治区/直辖市、市、县）")
    private String regio;
    @ApiModelProperty(value = "街道和房屋号")
    private String stras;
    @ApiModelProperty(value = "第一个电话号")
    private String telf1;
    @ApiModelProperty(value = "传真号")
    private String telfx;
    @ApiModelProperty(value = "地址")
    private String adrnr;
    @ApiModelProperty(value = "记录创建日期")
    private String erdat;
    @ApiModelProperty(value = "创建对象的人员名称")
    private String ernam;
    @ApiModelProperty(value = "客户帐户组")
    private String ktokd;
    @ApiModelProperty(value = "语言代码")
    private String spras;
    @ApiModelProperty(value = "第二个电话号")
    private String telf2;
    @ApiModelProperty(value = "税号5")
    private String stcd5;
    @ApiModelProperty(value = "客户企业类型")
    private String kukla;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;


}
