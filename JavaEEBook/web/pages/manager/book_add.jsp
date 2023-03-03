<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑图书</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }

        h1 a {
            color: red;
        }

        input {
            text-align: center;
        }
    </style>
</head>
<body>

<%
    request.setCharacterEncoding("UTF-8");
%>

<jsp:include page="header.jsp">
    <jsp:param name="msg" value="添加图书"/>
</jsp:include>


<div id="main">
    <form action="BookServlet?action=add" enctype="multipart/form-data" method="post" onsubmit="return checkdata()">
        <table>
            <tr>
                <td>名称</td>
                <td><input name="name" type="text"/></td>
            </tr>
            <tr>
                <td>价格</td>
                <td>
                    <input id="price" name="price" type="text" onblur="checkprice()"/><br/>
                    <span id="pricespan"></span>
                </td>
            </tr>
            <tr>
                <td>作者</td>
                <td><input name="author" type="text"/></td>
            </tr>
            <tr>
                <td>销量</td>
                <td>
                    <input id="sales" name="sales" type="text" onblur="checksales()"/><br/>
                    <span id="salesspan"></span>
                </td>
            </tr>
            <tr>
                <td>库存</td>
                <td>
                    <input id="stock" name="stock" type="text" onblur="checkstock()"/><br/>
                    <span id="stockspan"></span>
                </td>
            </tr>
            <tr>
                <td>封面</td>
                <td>
                    <input name="imgpath" type="file" id="file_input" onchange="show_image()"/>
                    <img src="" alt="" id="show_img" width="100px" height="100px" style="display: none;">
                </td>
            </tr>

            <tr>
                <td colspan="2"><input type="submit" value="提交"/></td>
            </tr>
        </table>
    </form>


</div>

<%--<%@include file="/pages/common/bottom.jsp"%>--%>
<jsp:include page="/pages/common/bottom.jsp"></jsp:include>
</body>
</html>
<script>
    function show_image() {
        //抓取到上传图片的input标签的信息
        file_input = document.getElementById("file_input");
        //抓取到需要展示预览图的img标签信息
        show_img = document.getElementById("show_img");
        //回去预览图的src属性信息
        show_img.src = window.URL.createObjectURL(file_input.files[0]);
        //改变style属性中block的值
        show_img.style.display = 'block';
    }

    function checkdata() {
        if (checkprice() && checksales() && checkstock()) {
            return true;
        }
        return false;
    }

    function checkprice() {
        var pricevalue = document.getElementById("price").value;
        var patt = /^([1-9][0-9]*)+(.[0-9]{1,2})?$/;
        var pricespanobj = document.getElementById("pricespan");
        if (patt.test(pricevalue)) {
            pricespanobj.innerHTML = "√";
            pricespanobj.style.color = "green";
            return true;
        } else {
            pricespanobj.innerHTML = "价格必须为数字,且最多输入两位小数";
            pricespanobj.style.color = "red";
            return false;
        }
    }

    function checksales() {
        var salesvalue = document.getElementById("sales").value;
        var patt = /^[0-9]*$/;
        var salesspanobj = document.getElementById("salesspan");
        if (patt.test(salesvalue)) {
            salesspanobj.innerHTML = "√";
            salesspanobj.style.color = "green";
            return true;
        } else {
            salesspanobj.innerHTML = "输入错误销量必须为纯数字";
            salesspanobj.style.color = "red";
            return false;
        }
    }

    function checkstock() {
        var stockvalue = document.getElementById("stock").value;
        var patt = /^[0-9]*$/;
        var stockspanobj = document.getElementById("stockspan");
        if (patt.test(stockvalue)) {
            stockspanobj.innerHTML = "√";
            stockspanobj.style.color = "green";
            return true;
        } else {
            stockspanobj.innerHTML = "输入错误库存必须为纯数字";
            stockspanobj.style.color = "red";
            return false;
        }
    }
</script>