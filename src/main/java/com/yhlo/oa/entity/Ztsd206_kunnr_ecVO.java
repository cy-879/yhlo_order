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
@ApiModel("返利策略终端行表")
public class Ztsd206_kunnr_ecVO {

    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "返利编号")
    private String zzbname;
    @ApiModelProperty(value = "客户")
    private String kunnr_ec;
    @ApiModelProperty(value = "客户名称")
    private String zzbname_txt;

}
