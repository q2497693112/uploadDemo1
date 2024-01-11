package cn.cvs.service;

import cn.cvs.mapper.*;
import cn.cvs.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class DevUserServiceImpl implements DevUserService {
    @Autowired
    DevUserMapper devUserMapper;
    @Autowired
    AppInfoMapper appInfoMapper;
    @Autowired
    AppCategoryMapper categoryMapper;
    @Autowired
    DataDictionaryMapper dataDictionaryMapper;
    @Autowired
    AppVersionMapper versionMapper;
    @Override
    public DevUser LoginDev(String devCode, String devPassword) {
        return devUserMapper.LoginDev(devCode, devPassword);
    }
    @Override
    public Page getPage(Integer devId, String softwareName, String apkName, Integer status, Integer flatformId, Integer categoryLevel1, Integer categoryLevel2, Integer categoryLevel3, Integer pageNo,Integer pageSize) {
        Page page=new Page();
        page.setPageDataCount(appInfoMapper.getPageDataCount(devId, softwareName, apkName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3));
        page.setAppInfoList(appInfoMapper.getAppInfoList(devId, softwareName, apkName, status, flatformId, categoryLevel1, categoryLevel2, categoryLevel3, (pageNo - 1) * pageSize, pageSize));
        return page;
    }
    @Override
    public List<AppCategory> appCategoryList(Integer parentId) {
        return categoryMapper.appCategoryList(parentId);
    }
    @Override
    public List<DataDictionary> flatformList() {
        return dataDictionaryMapper.flatformList();
    }
    @Override
    public List<DataDictionary> statusList() {
        return dataDictionaryMapper.statusList();
    }
    @Override
    public int addInfo(AppInfo appInfo) {
        return appInfoMapper.addInfo(appInfo);
    }
    @Override
    public int checkApkName(String apkName) {
        return appInfoMapper.checkApkName(apkName);
    }
    @Override
    public AppInfo appInfoById(Integer id) {
        return appInfoMapper.appInfoById(id);
    }
    @Override
    public int updateInfo(AppInfo appInfo) {
        return appInfoMapper.updateInfo(appInfo);
    }
    @Override
    public List<AppVersion> versionById(Integer appId) {
        return versionMapper.versionById(appId);
    }
    @Override
    public boolean delAppInfo(Integer appId) {
        for (AppVersion version : versionMapper.versionById(appId)) {
            if (version != null) versionMapper.delVersion(version.getAppId());
        }
        return appInfoMapper.delAppInfo(appId) > 0;
    }
    @Override
    public int upOrDownStatus(Integer status, Integer id) {
        return appInfoMapper.upOrDownStatus(status, id);
    }
    @Override
    public boolean addVersion(AppVersion appVersion) {
        if (versionMapper.addVersion(appVersion)<=0)return false;
//        versionMapper.versionById(appVersion.getAppId())
        return  appInfoMapper.upInfoByVersionId(appVersion.getId(),appVersion.getAppId())>0;
    }
}
