package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author cy
 * @ClassName: Ztsd206_headVO
 * @Description:
 * @date 2022/2/6
 */

@Data
@ApiModel("返利策略头表")
public class Ztsd206_headVO {

    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "返利编号")
    private String zzbname;
    @ApiModelProperty(value = "返利编号描述")
    private String zzbname_txt;
    @ApiModelProperty(value = "合同编号")
    private String zcont;
    @ApiModelProperty(value = "合同描述")
    private String zcont_txt;
    @ApiModelProperty(value = "客户编号")
    private String kunnr;
    @ApiModelProperty(value = "客户名称")
    private String name1;
    @ApiModelProperty(value = "销售组织代码")
    private String vkorg;
    @ApiModelProperty(value = "销售组织描述")
    private String vtext;
    @ApiModelProperty(value = "返利总额度")
    private Float zrebate;
    @ApiModelProperty(value = "已用返利")
    private Float zrebate_used;
    @ApiModelProperty(value = "剩余返利")
    private Float zrebate_last;
    @ApiModelProperty(value = "开始生效日期")
    private String datab;
    @ApiModelProperty(value = "有效截至日期")
    private String datbi;
    @ApiModelProperty(value = "批准状态")
    private String zkfrst;
    @ApiModelProperty(value = "折扣率（%）")
    private Float zzkl;
    @ApiModelProperty(value = "创建人")
    private String zname;
    @ApiModelProperty(value = "最后修改人")
    private String zname1;
    @ApiModelProperty(value = "审核人")
    private String zsh;
    @ApiModelProperty(value = "审核日期")
    private String zdate_sh;
    @ApiModelProperty(value = "创建日期")
    private String zdate;
    @ApiModelProperty(value = "修改日期")
    private String zdate1;
    @ApiModelProperty(value = "创建时间")
    private String ztime;
    @ApiModelProperty(value = "修改时间")
    private String ztime1;
    @ApiModelProperty(value = "审核时间")
    private String ztime_sh;
    @ApiModelProperty(value = "备注")
    private String zbase;
    @ApiModelProperty(value = "来源")
    private String zly;
    @ApiModelProperty(value = "来源流程编号")
    private String zoa;
    @ApiModelProperty(value = "删除标识")
    private String zdel_h;
    @ApiModelProperty(value = "返利策略物料组行")
    private List<Ztsd206_ItemVO> ziList;
    @ApiModelProperty(value = "返利策略终端行")
    private List<Ztsd206_kunnr_ecVO> zkuList;

}
