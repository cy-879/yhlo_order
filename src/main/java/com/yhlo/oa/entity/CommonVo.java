package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: CommonVo
 * @Description:
 * @date 2023/1/514:49
 */
@Data
@ApiModel("公用的实体类")
public class CommonVo {
    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "字段1")
    private String field1;
    @ApiModelProperty(value = "字段2")
    private String field2;
    @ApiModelProperty(value = "字段3")
    private String field3;
    @ApiModelProperty(value = "字段4")
    private String field4;
    @ApiModelProperty(value = "字段5")
    private String field5;
}
