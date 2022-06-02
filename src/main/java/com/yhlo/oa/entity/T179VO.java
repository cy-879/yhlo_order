package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: T179VO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("产品组层次")
public class T179VO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "产品层级")
    private String stufe;
    @ApiModelProperty(value = "层号")
    private String prodh;
    @ApiModelProperty(value = "描述")
    private String vtext;


}
