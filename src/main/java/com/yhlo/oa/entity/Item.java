package com.yhlo.oa.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cy
 * @ClassName: Item
 * @Description:
 * @date 2022/5/516:30
 */

@Data
@ApiModel("值列表")
public class Item {
    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "标题key")
    private String itemKey;
    @ApiModelProperty(value = "标题")
    private String itemValue;


}
