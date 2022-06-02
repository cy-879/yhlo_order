package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: TspatVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("产品组")
public class TspatVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "语言")
    private String spras;
    @ApiModelProperty(value = "产品组")
    private String spart;
    @ApiModelProperty(value = "产品组描述")
    private String vtxtk;


}
