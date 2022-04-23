package com.ldblock.carid.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldblock.carid.data.po.AppAuthority;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AppAuthMapper extends BaseMapper<AppAuthority> {

    @Select("select authority from app_authority where app_id = #{appId} and authority = #{authority} limit 1")
    String selectAuth(@Param("appId") String appId, @Param("authority") String authority);
}
