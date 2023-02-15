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
@ApiModel("返利政策明细")
public class RebatePolicyDetailVO {

    @ApiModelProperty(value = "主键id")
    private int id;
    @ApiModelProperty(value = "序号")
    private Integer xuh;
    @ApiModelProperty(value = "返利单编号")
    private String rebateFormNo;
    @ApiModelProperty(value = "物料组")
    private String matkl_name;
    @ApiModelProperty(value = "物料组代码")
    private String matkl;
    @ApiModelProperty(value = "返利额度")
    private Float rebateLimit;
    @ApiModelProperty(value = "返利比例")
    private Float z005;
    @ApiModelProperty(value = "已使用额度")
    private Float usedQuota;
    @ApiModelProperty(value = "未使用额度")
    private Float unusedQuota;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private String create_time;
    @ApiModelProperty(value = "创建人")
    private String create_by;
    @ApiModelProperty(value = "审批状态")
    private String status;

    private Checkbox cb = new Checkbox();

    public Checkbox getCb() {
        return cb;
    }

    public void setSelected(Checkbox cb) {
        this.cb = cb;
    }

}
