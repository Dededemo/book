<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>
<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
    <span class="wel_word">${param.msg}</span>
    <div>
        <a href="BookServlet?action=page&pageNo=${page.pageNo}">图书管理</a>
        <a href="pages/manager/order_manager.jsp">订单管理</a>
        <a href="index.jsp">返回商城</a>
    </div>
</div>
</body>
</html>
