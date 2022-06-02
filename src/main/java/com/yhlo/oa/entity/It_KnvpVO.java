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
@ApiModel("客户合作伙伴数据")
public class It_KnvpVO {

    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "客户编号")
    private String kunnr;
    @ApiModelProperty(value = "销售组织")
    private String vkorg;
    @ApiModelProperty(value = "分销渠道")
    private String vtweg;
    @ApiModelProperty(value = "产品组")
    private String spart;
    @ApiModelProperty(value = "合作伙伴职能")
    private String parvw;
    @ApiModelProperty(value = "合作伙伴计数器")
    private String parza;
    @ApiModelProperty(value = "业务伙伴的客户号")
    private String kunn2;
    @ApiModelProperty(value = "供应商或债权人的帐号")
    private String lifnr;
    @ApiModelProperty(value = "员工号")
    private String pernr;
    @ApiModelProperty(value = "联系人号码")
    private String parnr;
    @ApiModelProperty(value = "业务合作伙伴的客户描述(工厂，存储地点)")
    private String knref;
    @ApiModelProperty(value = "缺省合作伙伴")
    private String defpa;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;


}
