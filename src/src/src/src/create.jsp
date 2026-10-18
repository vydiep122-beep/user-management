
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm User</title>
</head>
<body>
    <h1>Thêm User mới</h1>

    <form action="${pageContext.request.contextPath}/users" method="post">
        <input type="hidden" name="action" value="create">

        <p>
            Họ tên:
            <input type="text" name="name" required maxlength="120">
        </p>

        <p>
            Email:
            <input type="email" name="email" required maxlength="120">
        </p>

        <p>
            Quốc gia:
            <input type="text" name="country" required maxlength="120">
        </p>

        <button type="submit">Thêm</button>
        <a href="${pageContext.request.contextPath}/users">Quay lại</a>
    </form>
</body>
</html>
