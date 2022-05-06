package com.ldblock.carid.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Chang Han
 */
@Data
@TableName("app_authority")
public class AppAuthority {
    @TableId(type= IdType.AUTO)
    private Integer id;

    private String appId;

    private String authority;
}
