package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: T001lVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("工厂")
public class T001lVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "工厂")
    private String werks;
    @ApiModelProperty(value = "仓位")
    private String lgort;
    @ApiModelProperty(value = "仓位描述")
    private String lgobe;


}
