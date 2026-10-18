
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xóa User</title>
</head>
<body>
    <h1>Xác nhận xóa User</h1>

    <p>
        Bạn có chắc muốn xóa User:
        <strong><c:out value="${user.name}"/></strong>?
    </p>

    <p>Email: <c:out value="${user.email}"/></p>
    <p>Quốc gia: <c:out value="${user.country}"/></p>

    <form action="${pageContext.request.contextPath}/users" method="post">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="id" value="${user.id}">

        <button type="submit">Xác nhận xóa</button>
        <a href="${pageContext.request.contextPath}/users">Hủy</a>
    </form>
</body>
</html>
