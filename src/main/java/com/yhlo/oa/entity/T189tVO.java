package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: T189tVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("价格清单")
public class T189tVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "语言")
    private String spras;
    @ApiModelProperty(value = "价格清单")
    private String pltyp;
    @ApiModelProperty(value = "描述")
    private String ptext;


}
