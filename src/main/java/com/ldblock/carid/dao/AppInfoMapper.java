package com.ldblock.carid.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldblock.carid.data.po.AppInfo;
import com.ldblock.carid.data.po.CarOwner;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AppInfoMapper extends BaseMapper<AppInfo> {

    @Select("select * from app_info where app_id = #{appId} limit 1")
    AppInfo selectByAppId(@Param("appId") String appId);

    @Select("select * from app_info where app_id = #{appId} and app_secret = #{secret} limit 1")
    AppInfo selectByAppIdAndSecret(@Param("appId") String appId, @Param("secret") String secret);
}
