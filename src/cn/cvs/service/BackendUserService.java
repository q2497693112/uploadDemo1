package cn.cvs.service;

import cn.cvs.pojo.BackendUser;

public interface BackendUserService {
    BackendUser LoginUser(String userCode,String userPassword);
}
