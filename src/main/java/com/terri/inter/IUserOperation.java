package com.terri.inter;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.terri.model.Article;
import com.terri.model.User;
import com.terri.util.page.PageInfo;

public interface IUserOperation {    
    public User selectUserByID(int id);
    public List<User> selectUsers(String userName);
    public void addUser(User user);
    public void deleteUser(int id);
    public void updateUser(User user);
    public List<Article> getUserArticles(int id);
    public List<Article> selectArticleListPage(@Param("page") PageInfo page,@Param("userid") int userid);
}