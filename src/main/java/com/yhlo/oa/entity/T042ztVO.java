package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: T042ztVO
 * @Description:
 * @date 2022/5/20
 */

@Data
@ApiModel("付款方式")
public class T042ztVO {
    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "表名")
    private String zsap_tabel;
    @ApiModelProperty(value = "表描述")
    private String zsap_tabletxt;
    @ApiModelProperty(value = "语言")
    private String spras;
    @ApiModelProperty(value = "付款方式")
    private String zlsch;
    @ApiModelProperty(value = "付款方式描述")
    private String text2;


}
