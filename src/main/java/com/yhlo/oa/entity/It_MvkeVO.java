package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: It_mvkeVO
 * @Description:
 * @date 2022/5/2314:55
 */
@Data
@ApiModel("销售物料")
public class It_MvkeVO {

    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "物料编号")
    private String matnr;
    @ApiModelProperty(value = "销售组织")
    private String vkorg;
    @ApiModelProperty(value = "分销渠道")
    private String vtweg;
    @ApiModelProperty(value = "销售单位")
    private String vrkme;
    @ApiModelProperty(value = "来自物料主数据的项目类别组")
    private String mtpos;
    @ApiModelProperty(value = "交货工厂 (自有或外部)")
    private String dwerk;
    @ApiModelProperty(value = "物料的科目分配组")
    private String ktgrm;
    @ApiModelProperty(value = "删除的物料")
    private String lvorm;
    @ApiModelProperty(value = "指定分销链的物料状态")
    private String vmsta;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;
}
