package cn.cvs.mapper;

import cn.cvs.pojo.AppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppVersionMapper {
    List<AppVersion> versionById(@Param("appId") Integer appId);
    int delVersion(@Param("appId") Integer appId);
    int addVersion(AppVersion appVersion);
}
