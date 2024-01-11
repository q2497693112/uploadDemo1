package cn.cvs.mapper;

import cn.cvs.pojo.BackendUser;
import org.apache.ibatis.annotations.Param;

public interface BackendUserMapper {
    BackendUser LoginUser(@Param("userCode")String userCode, @Param("userPassword")String userPassword);
}
