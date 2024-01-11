package cn.cvs.mapper;

import cn.cvs.pojo.DevUser;
import org.apache.ibatis.annotations.Param;

public interface DevUserMapper {
    DevUser LoginDev(@Param("devCode")String devCode,@Param("devPassword")String devPassword);
}
