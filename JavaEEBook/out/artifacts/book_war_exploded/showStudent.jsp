<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hrj.bean.Student" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生信息</title>
    <style type="text/css">

        table {
            border: 1px blue solid;
            width: 600px;
            border-collapse: collapse;
        }
        td, tr, th {
            border: 1px blue solid;
        }
    </style>
</head>
<body>

<table>

<c:forEach items="${studentList}" var="stu">
    <tr>
        <td>${stu.id}</td>
        <td>${stu.username}</td>
        <td>${stu.password}</td>
        <td>${stu.age}</td>
        <td>${stu.phone}</td>
    </tr>
</c:forEach>

</table>
</body>
</html>