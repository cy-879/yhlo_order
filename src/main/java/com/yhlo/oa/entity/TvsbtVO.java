package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: TvsbtVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("装运条件")
public class TvsbtVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "语言")
    private String spras;
    @ApiModelProperty(value = "装运条件代码")
    private String vsbed;
    @ApiModelProperty(value = "装运条件描述")
    private String vtext;


}
