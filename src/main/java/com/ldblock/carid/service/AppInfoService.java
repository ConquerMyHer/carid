package com.ldblock.carid.service;

import com.ldblock.carid.dao.AppInfoMapper;
import com.ldblock.carid.dao.CarOwnerMapper;
import com.ldblock.carid.data.bo.AppInfoRegisterBo;
import com.ldblock.carid.data.bo.AppInfoUpdateBo;
import com.ldblock.carid.data.po.AppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AppInfoService {
    @Autowired
    AppInfoMapper appInfoMapper;

    public AppInfo getAppInfo(String appId) {
        return appInfoMapper.selectByAppId(appId);
    }

    public AppInfo updateAppInfo(AppInfoUpdateBo appInfoUpdateBo) {
        AppInfo appInfoToBeUpdated = appInfoMapper.selectByAppId(appInfoUpdateBo.getAppId());
        if (appInfoToBeUpdated == null) {
            return null;
        }
        appInfoToBeUpdated.setAppName(appInfoUpdateBo.getAppName() == null ? appInfoToBeUpdated.getAppName() : appInfoUpdateBo.getAppName());
        appInfoToBeUpdated.setAppDesc(appInfoUpdateBo.getAppDesc() == null ? appInfoToBeUpdated.getAppDesc() : appInfoUpdateBo.getAppDesc());
        appInfoToBeUpdated.setAppIcon(appInfoUpdateBo.getAppIcon() == null ? appInfoToBeUpdated.getAppIcon() : appInfoUpdateBo.getAppIcon());
        appInfoMapper.updateById(appInfoToBeUpdated);
        return appInfoToBeUpdated;
    }

    public AppInfo registerAppInfo(AppInfoRegisterBo appInfoRegisterBo) {
        AppInfo appInfo = new AppInfo();
        appInfo.setAppIcon(appInfoRegisterBo.getAppIcon());
        appInfo.setAppDesc(appInfoRegisterBo.getAppDesc());
        appInfo.setAppName(appInfoRegisterBo.getAppName());
        appInfo.setAppId(UUID.randomUUID().toString());
        appInfoMapper.insert(appInfo);
        return appInfo;
    }
}
