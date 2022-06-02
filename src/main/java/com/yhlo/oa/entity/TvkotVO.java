package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: TvkotVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("销售组织")
public class TvkotVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "语言")
    private String spras;
    @ApiModelProperty(value = "销售组织代码")
    private String vkorg;
    @ApiModelProperty(value = "销售组织描述")
    private String vtxtk;


}
