package com.wei.demo.springbootcartsdemo.model.User;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;

@Data
public class UserDto {


    @Schema(description = "使用者ID")
    private String id;

    @Schema(description = "使用者帳號")
    private String userName;

    @Schema(description = "使用者信箱")
    private String Email;

    @Schema(description = "創建時間")
    private LocalTime createAt;

    @Schema(description = "更新時間")
    private LocalTime updateAt;

    @Schema(description = "刪除時間")
    private LocalTime deleteAt;


}
