package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: TwewtVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("外部物料组")
public class TwewtVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "语言")
    private String spras;
    @ApiModelProperty(value = "外部物料组")
    private String extwg;
    @ApiModelProperty(value = "外部物料组描述")
    private String ewbez;


}
