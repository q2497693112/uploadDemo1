package cn.cvs.service;

import cn.cvs.mapper.BackendUserMapper;
import cn.cvs.pojo.BackendUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BackendUserServiceImpl implements BackendUserService {
    @Autowired
    BackendUserMapper backendUserMapper;
    @Override
    public BackendUser LoginUser(String userCode, String userPassword) {
        return backendUserMapper.LoginUser(userCode, userPassword);
    }
}
