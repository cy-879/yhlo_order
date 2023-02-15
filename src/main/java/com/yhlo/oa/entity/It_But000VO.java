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
@ApiModel("客户一般数据")
public class It_But000VO {

    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "业务伙伴编号")
    private String partner;
    @ApiModelProperty(value = "业务伙伴分组")
    private String bu_group;
    @ApiModelProperty(value = "组织名称1")
    private String name_org1;
    @ApiModelProperty(value = "组织名称2")
    private String name_org2;
    @ApiModelProperty(value = "组织名称3")
    private String name_org3;
    @ApiModelProperty(value = "组织名称4")
    private String name_org4;
    @ApiModelProperty(value = "业务伙伴的搜索词1")
    private String bu_sort1;
    @ApiModelProperty(value = "业务伙伴的搜索词2")
    private String bu_sort2;
    @ApiModelProperty(value = "执业许可证号")
    private String zynum;
    @ApiModelProperty(value = "执业许可证发证日期")
    private String zyfzdat;
    @ApiModelProperty(value = "执业许可证有效期")
    private String zyyxdat;
    @ApiModelProperty(value = "第二类医疗器械经营备案凭证")
    private String zelnum;
    @ApiModelProperty(value = "第二类医疗器械经营备案凭证发证日期")
    private String zelfzdat;
    @ApiModelProperty(value = "医疗器械经营许可证/生产许可证")
    private String zjynum;
    @ApiModelProperty(value = "医疗器械经营许可证/生产许可证日期")
    private String zjyfzdat;
    @ApiModelProperty(value = "医疗器械经营许可证/生产许可证有效期")
    private String zjyyxdat;
    @ApiModelProperty(value = "营业执照发证日期")
    private String zyyfzdat;
    @ApiModelProperty(value = "生产备案凭证")
    private String zscbapz;
    @ApiModelProperty(value = "生产备案日期")
    private String zscbarq;
    @ApiModelProperty(value = "法人")
    private String zfr;
    @ApiModelProperty(value = "创建对象的用户")
    private String crusr;
    @ApiModelProperty(value = "对象创建日期")
    private String crdat;
    @ApiModelProperty(value = "对象创建时间")
    private String crtim;
    @ApiModelProperty(value = "生最后更改对象的用户")
    private String chusr;
    @ApiModelProperty(value = "最后更改对象的日期")
    private String chdat;
    @ApiModelProperty(value = "对象最后更改的时间")
    private String chtim;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;


    private Checkbox cb = new Checkbox();

    public Checkbox getCb() {
        return cb;
    }

    public void setSelected(Checkbox cb) {
        this.cb = cb;
    }

}
