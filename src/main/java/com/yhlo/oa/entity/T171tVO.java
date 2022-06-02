package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: T171tVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("销售地区")
public class T171tVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "语言")
    private String spras;
    @ApiModelProperty(value = "销售地区代码")
    private String bzirk;
    @ApiModelProperty(value = "描述")
    private String bztxt;


}
