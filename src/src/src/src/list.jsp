
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách User</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }
        th { background: #e8f0fe; }
        a, button { margin-right: 8px; }
    </style>
</head>
<body>
    <h1>Quản lý User</h1>

    <a href="${pageContext.request.contextPath}/users?action=create">
        Thêm User mới
    </a>

    <table>
        <tr>
            <th>ID</th>
            <th>Họ tên</th>
            <th>Email</th>
            <th>Quốc gia</th>
            <th>Thao tác</th>
        </tr>

        <c:forEach var="user" items="${listUser}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.country}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/users?action=edit&id=${user.id}">
                        Sửa
                    </a>
                    <a href="${pageContext.request.contextPath}/users?action=delete&id=${user.id}">
                        Xóa
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
