package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: V_024VO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("采购组")
public class V_024VO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "ekgrp")
    private String ekgrp;
    @ApiModelProperty(value = "eknam")
    private String eknam;


}
