
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Sửa User</title>
</head>
<body>
    <h1>Cập nhật User</h1>

    <form action="${pageContext.request.contextPath}/users" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<c:out value='${user.id}'/>">

        <p>
            Họ tên:
            <input type="text" name="name"
                   value="<c:out value='${user.name}'/>"
                   required maxlength="120">
        </p>

        <p>
            Email:
            <input type="email" name="email"
                   value="<c:out value='${user.email}'/>"
                   required maxlength="120">
        </p>

        <p>
            Quốc gia:
            <input type="text" name="country"
                   value="<c:out value='${user.country}'/>"
                   required maxlength="120">
        </p>

        <button type="submit">Lưu thay đổi</button>
        <a href="${pageContext.request.contextPath}/users">Hủy</a>
    </form>
</body>
</html>
