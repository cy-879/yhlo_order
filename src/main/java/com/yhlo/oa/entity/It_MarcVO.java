package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: It_MarcVO
 * @Description:
 * @date 2022/5/25 19:23
 */

@Data
@ApiModel("物料工厂相关")
public class It_MarcVO {
    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "物料编号")
    private String matnr;
    @ApiModelProperty(value = "工厂")
    private String werks;
    @ApiModelProperty(value = "删除标志")
    private String lvorm;
    @ApiModelProperty(value = "工厂的批次管理需求标识")
    private String xchpf;
    @ApiModelProperty(value = "批量管理标识(内部)")
    private String xchar;
    @ApiModelProperty(value = "装载组")
    private String ladgr;
    @ApiModelProperty(value = "序列号参数文件")
    private String sernp;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;

}
