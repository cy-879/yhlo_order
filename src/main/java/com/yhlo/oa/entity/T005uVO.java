package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: T005uVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("中国城市")
public class T005uVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "语言")
    private String spras;
    @ApiModelProperty(value = "国家")
    private String land1;
    @ApiModelProperty(value = "地区")
    private String bland;
    @ApiModelProperty(value = "地区描述")
    private String bezei;


}
