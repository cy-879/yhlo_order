package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: T001VO
 * @Description:
 * @date 2022/5/1911:17
 */

@Data
@ApiModel("公司代码")
public class T001VO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "公司代码")
    private String bukrs;
    @ApiModelProperty(value = "公司代码描述")
    private String butxt;


}
