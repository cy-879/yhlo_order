package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cy
 * @Description
 * @Return
 * @Date 2022/9/19 11:29
 */

@Data
@ApiModel("客户分配多角贸易代码")
    public class It_Ztsd_002VO {

    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "客户编号")
    private String kunnr;
    @ApiModelProperty(value = "销售组织")
    private String vkorg;
    @ApiModelProperty(value = "多角贸易代码")
    private String knumh;
    @ApiModelProperty(value = "客户名称")
    private String name1;
    @ApiModelProperty(value = "是否默认")
    private String c_default;
    @ApiModelProperty(value = "多角贸易名称")
    private String name2;
    @ApiModelProperty(value = "有效期自")
    private String datab;
    @ApiModelProperty(value = "有效截至日")
    private String datbi;
    @ApiModelProperty(value = "删除标识")
    private String loevm_ko;
    @ApiModelProperty(value = "创建者")
    private String zernam1;
    @ApiModelProperty(value = "创建日期")
    private String zerdat1;
    @ApiModelProperty(value = "修改人")
    private String zernam2;
    @ApiModelProperty(value = "最后修改日期")
    private String zerdat2;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;
    @ApiModelProperty(value = "下拉框显示名称")
    private String showName;


}
