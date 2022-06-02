package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: T024eVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("采购组织")
public class T024eVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "采购组织")
    private String ekorg;
    @ApiModelProperty(value = "采购组织描述")
    private String ekotx;


}
