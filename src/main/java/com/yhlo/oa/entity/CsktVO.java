package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: CsktVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("成本中心描述")
public class CsktVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "语言")
    private String spras;
    @ApiModelProperty(value = "成本中心")
    private String kostl;
    @ApiModelProperty(value = "成本中心描述")
    private String ltext;


}
