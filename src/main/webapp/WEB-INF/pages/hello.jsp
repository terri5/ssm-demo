<%@ page language="java" contentType="text/html; charset=utf-8" 
    pageEncoding="utf-8"%>
 <%@page import="java.io.*"%>    
 <%@ taglib prefix="st" uri="http://www.springframework.org/tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<body>  
<h2>文件上传实例</h2>  

<form action="<st:url value="/fileUpload/"></st:url>"  method="post" enctype="multipart/form-data">  
    选择文件:<input type="file" name="file">  
    <input type="submit" value="提交">   
</form>  
 
  
</body>  

</body>
</html>