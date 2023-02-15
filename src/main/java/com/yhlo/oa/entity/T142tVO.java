package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: T142tVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("存储条件")
public class T142tVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "存储条件代码")
    private String raube;
    @ApiModelProperty(value = "描述")
    private String rbtxt;

}
