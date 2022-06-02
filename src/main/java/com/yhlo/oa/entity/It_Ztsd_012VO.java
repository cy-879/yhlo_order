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
@ApiModel("客户物料")
public class It_Ztsd_012VO {

    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "客户编号")
    private String kunnr;
    @ApiModelProperty(value = "销售组织")
    private String vkorg;
    @ApiModelProperty(value = "分销渠道")
    private String vtweg;
    @ApiModelProperty(value = "物料编号")
    private String matnr;
    @ApiModelProperty(value = "客户名称")
    private String name1;
    @ApiModelProperty(value = "物料描述")
    private String arktx;
    @ApiModelProperty(value = "客户物料")
    private String kdmat;
    @ApiModelProperty(value = "客户物料描述")
    private String postx;
    @ApiModelProperty(value = "省招标编码")
    private String zkdmat_2;
    @ApiModelProperty(value = "市招标编码")
    private String zkdmat_3;
    @ApiModelProperty(value = "预留编码")
    private String zkdmat_4;
    @ApiModelProperty(value = "创建对象的人员名称")
    private String zernam1;
    @ApiModelProperty(value = "记录创建日期")
    private String zerdat1;
    @ApiModelProperty(value = "修改人")
    private String zernam2;
    @ApiModelProperty(value = "最后修改日期")
    private String zerdat2;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;


}
