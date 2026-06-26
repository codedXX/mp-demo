package com.itheima.mp.domain.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户详细信息")
public class UserInfo {

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("简介")
    private String intro;

    @ApiModelProperty("性别")
    private String gender;
}
