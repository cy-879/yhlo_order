package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: Ztsd206_ItemVO
 * @Description:
 * @date 2023/2/6
 */

@Data
@ApiModel("返利策略物料组行表")
public class Ztsd206_ItemVO {

    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "返利编号")
    private String zzbname;
    @ApiModelProperty(value = "行序号")
    private String zposnr;
    @ApiModelProperty(value = "折扣率（%）")
    private Float zzkl;
    @ApiModelProperty(value = "物料组")
    private String matkl;
    @ApiModelProperty(value = "物料组描述")
    private String wgbez;
    @ApiModelProperty(value = "物料编号")
    private String matnr;
    @ApiModelProperty(value = "删除标识")
    private String zdel;

}
