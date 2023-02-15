package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: RebatePolicyVO
 * @Description:
 * @date 2022/7/2017:32
 */

@Data
@ApiModel("返利政策")
public class RebatePolicyVO {

    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "返利单名称")
    private String rebateFormName;
    @ApiModelProperty(value = "返利单编号")
    private String rebateFormNo;
    @ApiModelProperty(value = "合同编号")
    private String contractNo;
    @ApiModelProperty(value = "返利策略")
    private String rebateStrategy;
    @ApiModelProperty(value = "客户名称")
    private String name1;
    @ApiModelProperty(value = "客户代码")
    private String kunnr;
    @ApiModelProperty(value = "销售组织")
    private String vkorg_name;
    @ApiModelProperty(value = "销售组织代码")
    private String vkorg;
    @ApiModelProperty(value = "物料组")
    private String matkl_name;
    @ApiModelProperty(value = "物料组代码")
    private String matkl;
    @ApiModelProperty(value = "终端客户")
    private String name_ec_name;
    @ApiModelProperty(value = "终端客户代码")
    private String name_ec;
    @ApiModelProperty(value = "返利额度")
    private Float rebateLimit;
    @ApiModelProperty(value = "返利比例")
    private Float z005;
    @ApiModelProperty(value = "已使用额度")
    private Float usedQuota;
    @ApiModelProperty(value = "未使用额度")
    private Float unusedQuota;
    @ApiModelProperty(value = "生效时间")
    private String takeEffectTime;
    @ApiModelProperty(value = "失效时间")
    private String failureTime;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private String create_time;
    @ApiModelProperty(value = "创建人")
    private String create_by;
    @ApiModelProperty(value = "审批状态")
    private String status;
    @ApiModelProperty(value = "审批时间")
    private String approvalTime;
    @ApiModelProperty(value = "审批人")
    private String approvalBy;

    private Checkbox cb = new Checkbox();

    public Checkbox getCb() {
        return cb;
    }

    public void setSelected(Checkbox cb) {
        this.cb = cb;
    }
}
