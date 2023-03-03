<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
    <span class="wel_word">订单详情</span>
    <div>
        <c:if test="${empty user}">
            <a href="pages/user/login.jsp">登录</a> |
            <a href="pages/user/regist.jsp">注册</a> &nbsp;&nbsp;
        </c:if>
        <c:if test="${not empty user}">
            <span>欢迎<span class="um_span">${user.username}</span>光临网上书城</span>
            <a href="OrderServlet?action=listOrder">我的订单</a> |
            <a href="UserServlet?action=logout">注销</a>
        </c:if>
        <a href="index.jsp">返回</a>
    </div>
</div>

<div id="main">

    <table >
        <tr style="height: 60px; font-size: 15px;">
            <td>商品名称</td>
            <td>数量</td>
            <td>单价</td>
            <td>金额</td>
        </tr>
        <c:forEach items="${page.items}" var="entry">
            <tr style="height: 60px; font-size: 15px">
                <td>${entry.name}</td>
                <td>${entry.count}</td>
                <td>${entry.price}</td>
                <td>${entry.totalPrice}</td>
            </tr>
        </c:forEach>
    </table>
    <jsp:include page="/pages/common/page.jsp"></jsp:include>
</div>

<jsp:include page="/pages/common/bottom.jsp"></jsp:include>
</body>
</html>
<script>

    function changeCount(id, obj) {
        if (obj.value > 0) {
            window.location.href = "CartServlet?action=updateCount&id=" + id + "&count=" + obj.value;
        }else {
            obj.value = 1;
            window.location.href = "CartServlet?action=updateCount&id=" + id + "&count=" + obj.value;
        }
    }
</script>