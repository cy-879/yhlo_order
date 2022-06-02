package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: TvkwzVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("销售机构")
public class TvkwzVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "销售组织代码")
    private String vkorg;
    @ApiModelProperty(value = "分销渠道")
    private String vtweg;
    @ApiModelProperty(value = "工厂代码")
    private String werks;


}
