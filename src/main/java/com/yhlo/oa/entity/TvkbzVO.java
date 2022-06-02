package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: TvkbzVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("销售范围分配销售办公室")
public class TvkbzVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "销售组织代码")
    private String vkorg;
    @ApiModelProperty(value = "销售办公室")
    private String vkbur;


}
