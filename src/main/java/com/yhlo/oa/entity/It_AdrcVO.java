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
@ApiModel("客户主数据地址信息")
public class It_AdrcVO {

    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "地址号码")
    private String addrnumber;
    @ApiModelProperty(value = "名称1")
    private String name1;
    @ApiModelProperty(value = "名称2")
    private String name2;
    @ApiModelProperty(value = "名称3")
    private String name3;
    @ApiModelProperty(value = "名称4")
    private String name4;
    @ApiModelProperty(value = "街道2")
    private String str_suppl1;
    @ApiModelProperty(value = "街道3")
    private String str_suppl2;
    @ApiModelProperty(value = "街道4")
    private String str_suppl3;
    @ApiModelProperty(value = "街道5")
    private String location;
    @ApiModelProperty(value = "地址注释")
    private String remark;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;


}
