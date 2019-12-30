package com.ff.community.controller;


import com.ff.community.Service.UserService;
import com.ff.community.dto.AccessTokenDTO;
import com.ff.community.dto.GithubUser;
import com.ff.community.mapper.UserMapper;
import com.ff.community.model.User;
import com.ff.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Value("${github.client.id}")
    private String clientid;
    @Value("${github.client.sercet}")
    private String clientSercet;
    @Value("${github.client.redirecturi}")
    private String redirecturi;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientid);
        accessTokenDTO.setClient_secret(clientSercet);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirecturi);
        accessTokenDTO.setState(state);
        String githubtoken = gitHubProvider.getAccessToken(accessTokenDTO);
        System.out.println(githubtoken);
        GithubUser githubuser = gitHubProvider.getUser(githubtoken);
        if(githubuser != null){
            User user = new User();
            //用于前端与后端的记录
            String token = UUID.randomUUID().toString();
            user.setToken(token);

            user.setName(githubuser.getName());
            user.setAccountId(String.valueOf(githubuser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvertUrl(githubuser.getAvatarUrl());
            userService.createOrUpdate(user);

            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else
            return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        response.addCookie(cookie);
        return "redirect:/";
    }


}
