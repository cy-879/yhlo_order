package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cy
 * @Description
 * * @param null
 * @Return 
 * @Date 2022/5/27 8:59
 */

@Data
@ApiModel("客户主数据公司代码数据")
public class It_Knb1VO {

    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "客户编号")
    private String kunnr;
    @ApiModelProperty(value = "公司代码")
    private String bukrs;
    @ApiModelProperty(value = "记录创建日期")
    private String erdat;
    @ApiModelProperty(value = "创建对象的人员名称")
    private String ernam;
    @ApiModelProperty(value = "总帐中的统驭科目")
    private String akont;
    @ApiModelProperty(value = "考虑的付款方式清单")
    private String zwels;
    @ApiModelProperty(value = "收付条件代码")
    private String zterm;
    @ApiModelProperty(value = "对公司代码过帐冻结")
    private String sperr;
    @ApiModelProperty(value = "主记录删除标记(公司代码级)")
    private String loevm;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;


}
