package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: It_mardVO
 * @Description:
 * @date 2022/5/2315:02
 */

@Data
@ApiModel("物料库存相关")
public class It_MardVO {
    @ApiModelProperty(value = "主键id")
    private  int id;
    @ApiModelProperty(value = "物料编号")
    private String matnr;
    @ApiModelProperty(value = "工厂")
    private String werks;
    @ApiModelProperty(value = "库存地点")
    private String lgort;
    @ApiModelProperty(value = "删除标志")
    private String lvorm;
    @ApiModelProperty(value = "非限制使用的估价的库存")
    private String labst;
    @ApiModelProperty(value = "创建/更改时间")
    private String create_time;
    @ApiModelProperty(value = "最后修改时间")
    private String last_modified_time;

}
