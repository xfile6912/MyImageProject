package com.example.MyImage.Model;

import lombok.Data;

@Data
public class User {
    private String id;
    private int authorization;//0:login이 안된 상태 1: 일반 유저, 2:관리자.
    private volatile static User loginUser;
    private User (){
    }
    public static User getLoginUser()
    {
        if(loginUser==null)
        {
            synchronized (User.class)
            {
                if(loginUser==null)
                    loginUser=new User();
            }
        }
        return loginUser;
    }
    public static void setUser(String id, int authorization)
    {
        if(loginUser==null)
        {
            synchronized (User.class)
            {
                if(loginUser==null)
                    loginUser=new User();
            }
        }
        loginUser.setId(id);
        loginUser.setAuthorization(authorization);
    }
}
