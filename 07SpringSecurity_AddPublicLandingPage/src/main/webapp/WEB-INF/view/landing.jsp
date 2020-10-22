<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Landing</title>
</head>
<body>
<h2>Spring Security</h2>
<hr/>
Welcome To Spring Security Home Page
      <hr/>
      <p>
         Welcome to the landing page! This page is open to the public ... no login required :-)
      </p>
      <hr/>
      <p>
         <a href="${pageContext.request.contextPath}/employees">Access Secure Site (requires login)</a>
      </p>
</body>
</html>