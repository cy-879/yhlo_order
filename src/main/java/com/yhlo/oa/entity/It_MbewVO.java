package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: It_mbewVO
 * @Description:
 * @date 2022/5/2315:02
 */

@Data
@ApiModel("物料评估类型")
public class It_MbewVO {
    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "物料编号")
    private String matnr;
    @ApiModelProperty(value = "评估范围")
    private String bwkey;
    @ApiModelProperty(value = "评估类型")
    private String bklas;
    @ApiModelProperty(value = "评估类型")
    private String lvorm;
    @ApiModelProperty(value = "价格控制指示符")//S:标准价; V:移动平均价
    private String vprsv;
    @ApiModelProperty(value = "移动平均价格/周期单价")//V:移动平均价
    private String verpr;
    @ApiModelProperty(value = "标准价格")//S:标准价
    private String stprs;
    @ApiModelProperty(value = "价格单位")//S:标准价
    private String peinh;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;
}
