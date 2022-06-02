package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: It_mbrmVO
 * @Description:
 * @date 2022/5/2315:02
 */

@Data
@ApiModel("物料计量单位")
public class It_MarmVO {
    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "物料编号")
    private String matnr;
    @ApiModelProperty(value = "可选计量单位")
    private String meinh;
    @ApiModelProperty(value = "基本计量单位转换分子")
    private String umrez;
    @ApiModelProperty(value = "转换为基本计量单位的分母")
    private String umren;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;

}
