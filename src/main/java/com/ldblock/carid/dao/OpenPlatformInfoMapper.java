package com.ldblock.carid.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldblock.carid.data.po.OpenPlatformInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OpenPlatformInfoMapper extends BaseMapper<OpenPlatformInfo> {

    @Select("select open_id from open_platform_info where user_id = #{userId} and app_id = #{appId}")
    String getOpenIdByUserIdAndAppId(@Param("userId") String userId, @Param("appId") String appId);

    // 根据open_id和app_id查询open_platform_info表中的数据
    @Select("select * from open_platform_info where open_id = #{openId} and app_id = #{appId}")
    OpenPlatformInfo getOpenPlatformInfoByOpenIdAndAppId(@Param("openId") String openId, @Param("appId") String appId);

    @Select("select scope from open_platform_info where user_id = #{userId} and app_id = #{appId}")
    String getScopeByUserIdAndAppId(@Param("userId") String userId, @Param("appId") String appId);

    @Update("update open_platform_info set open_id = #{openId}, scope = #{scope} where user_id = #{userId} and app_id = #{appId}")
    boolean updateOpenPlatformInfo(@Param("userId") String userId, @Param("appId") String appId, @Param("openId") String openId, @Param("scope") String scope);

    @Insert("insert into open_platform_info (user_id, app_id, open_id, scope) values (#{userId}, #{appId}, #{openId}, #{scope})")
    boolean insertOpenPlatformInfo(@Param("userId") String userId, @Param("appId") String appId, @Param("openId") String openId, @Param("scope") String scope);
}
