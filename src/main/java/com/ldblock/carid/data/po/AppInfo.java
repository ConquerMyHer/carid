package com.ldblock.carid.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("app_info")
public class AppInfo {
    @TableId(type= IdType.AUTO)
    private Integer id;

    private String appId;

    private String appName;

    private String appIcon;

    private String appDesc;

}

