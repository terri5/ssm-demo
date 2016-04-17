package com.terri.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.terri.inter.IBlogOperation;
import com.terri.inter.IUserOperation;
import com.terri.model.Blog;
import com.terri.model.User;
import com.terri.redis.util.RedisClient;
import com.terri.service.IUserService;
import com.terri.util.pagehelper.PageBean;

import static org.junit.Assert.*;







public class MybatisSpringTest {
    
    private static ApplicationContext ctx;  

    static 
    {  
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml"); 
    }        
      
    public static void main(String[] args)  
    {  
    	IBlogOperation mapper=(IBlogOperation)ctx.getBean("IBlogOperation");
//    	List<Blog> blogs = mapper.dynamicIfTest(new Blog());
//    	
//    	   for(Blog blog:blogs){
//               System.out.println(blog.getContent()+"--"+blog.getTitle());
//           }
//    	Blog t=new Blog();
//    	t.setId(1);
//    	Blog t2=mapper.dynamicChooseTest(t);
//    	 System.out.println(t2.getContent()+"--"+t2.getTitle());
//      	Blog t=new Blog();
//    	t.setId(1);
//    	Blog t3=mapper.dynamicTrimTest(t);
//    	 System.out.println(t3.getContent()+"--"+t3.getTitle());
    	
//    	Blog t=new Blog();
//    	t.setId(1);
//    	Blog t4=mapper.dynamicWhereTest(t);
//    	 System.out.println(t4.getContent()+"--"+t4.getTitle());
    	
    	
	     List<Integer> ids = new ArrayList<Integer>();
         ids.add(1);
         ids.add(3);
         ids.add(6);
    	 List<Blog> blogs = mapper.dynamicForeachTest(ids);
         for (Blog blog : blogs)
             System.out.println(blog.getId());
    	
        
    }  
    
    @Test
    public void ListBlog(){
    	 IBlogOperation mapper=(IBlogOperation)ctx.getBean("IBlogOperation");
    	 List<Integer> ids = new ArrayList<Integer>();
         ids.add(1);
         ids.add(3);
         ids.add(6);
    	 List<Blog> blogs = mapper.dynamicForeachTest(ids);
         for (Blog blog : blogs)
             System.out.println(blog.getId());
    }
    
    @Test
    public void ListUser(){
    	 IUserOperation userOperation=(IUserOperation) ctx.getBean("IUserOperation");          
         List<User> users = userOperation.selectUsers("%");
         for(User user:users){
             System.out.println(user.getId()+":"+user.getUserName()+":"+user.getUserAddress());
         }
    }
    @Test
    public void ListUserTestService(){
    	IUserService userService=(IUserService) ctx.getBean("userService");          
         List<User> users = userService.findAllUsers();
         for(User user:users){
             System.out.println(user.getId()+":"+user.getUserName()+":"+user.getUserAddress());
         }
    }
    
     
    @Test
    public void pageBlog(){
   	    IBlogOperation mapper=(IBlogOperation)ctx.getBean("IBlogOperation");
    	PageHelper.startPage(2, 3);
    	List<Blog> blogs=mapper.dynamicForeach2Test(new int[]{1,2,3,4,5,6});
    	System.out.println("size="+blogs.size());
    	assertTrue("获取blog数量异常",blogs.size()==3);
    	PageBean<Blog> p=new PageBean<Blog>(blogs);
    	System.out.println(p);
    
    }
    @Test
    public void TestRedis(){
    	RedisClient t=new RedisClient();
    	t.show();
    }
    
    

    
}