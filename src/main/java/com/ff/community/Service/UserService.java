package com.ff.community.Service;

import com.ff.community.exception.CustomizeExcepCode;
import com.ff.community.exception.CustomizeException;
import com.ff.community.mapper.UserMapper;
import com.ff.community.model.User;
import com.ff.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(userExample);
        if(dbUsers.size() == 0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            User dbUser = new User();
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvertUrl(user.getAvertUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUsers.get(0).getId());
            int updateInt = userMapper.updateByExampleSelective(dbUser,example );
            if (updateInt != 1){
                throw new CustomizeException(CustomizeExcepCode.QUESTION_NOT_FOUND);
            }

        }
        user.getAccountId();
    }
}
