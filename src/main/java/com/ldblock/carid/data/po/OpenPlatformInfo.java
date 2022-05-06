package com.ldblock.carid.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Chang Han
 */
@Data
@TableName("open_platform_info")
public class OpenPlatformInfo {
    @TableId(type= IdType.AUTO)

    private Integer id;

    private String appId;

    private String userId;

    private String openId;

    private String scope;

}
