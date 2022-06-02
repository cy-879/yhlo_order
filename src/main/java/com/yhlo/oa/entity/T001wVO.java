package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: T001wVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("工厂")
public class T001wVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "工厂代码")
    private String werks;
    @ApiModelProperty(value = "工厂描述")
    private String name1;


}
