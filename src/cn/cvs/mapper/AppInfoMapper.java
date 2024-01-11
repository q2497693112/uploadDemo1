package cn.cvs.mapper;

import cn.cvs.pojo.AppInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface AppInfoMapper {
    List<AppInfo> getAppInfoList(@Param("devId") Integer devId, @Param("softwareName") String softwareName, @Param("apkName") String apkName, @Param("status") Integer status, @Param("flatformId") Integer flatformId, @Param("categoryLevel1") Integer categoryLevel1, @Param("categoryLevel2") Integer categoryLevel2, @Param("categoryLevel3") Integer categoryLevel3, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
    int getPageDataCount(@Param("devId") Integer devId, @Param("softwareName") String softwareName, @Param("apkName") String apkName, @Param("status") Integer status, @Param("flatformId") Integer flatformId, @Param("categoryLevel1") Integer categoryLevel1, @Param("categoryLevel2") Integer categoryLevel2, @Param("categoryLevel3") Integer categoryLevel3);
    int addInfo(AppInfo appInfo);
    int checkApkName(@Param("apkName")String apkName);
    AppInfo appInfoById(@Param("id")Integer id);
    int updateInfo(AppInfo appInfo);
    int delAppInfo(@Param("id")Integer id);
    int upOrDownStatus(@Param("status")Integer status,@Param("id")Integer id);
    int upInfoByVersionId(@Param("versionId")Integer versionId,@Param("id")Integer id);
}
