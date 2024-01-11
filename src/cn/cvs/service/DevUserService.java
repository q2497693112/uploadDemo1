package cn.cvs.service;

import cn.cvs.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DevUserService {
    DevUser LoginDev(String devCode, String devPassword);
    Page getPage(Integer devId, String softwareName, String apkName, Integer status, Integer flatformId, Integer categoryLevel1, Integer categoryLevel2, Integer categoryLevel3, Integer pageNo,Integer pageSize);
    List<AppCategory> appCategoryList(Integer parentId);
    List<DataDictionary> flatformList();
    List<DataDictionary> statusList();
    int addInfo(AppInfo appInfo);
    int checkApkName(String apkName);
    AppInfo appInfoById(Integer id);
    int updateInfo(AppInfo appInfo);
    List<AppVersion> versionById(Integer appId);
    boolean delAppInfo(Integer appId);
    int upOrDownStatus(Integer status, Integer id);
    boolean addVersion(AppVersion appVersion);
}
