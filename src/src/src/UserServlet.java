
package com.codegym.controller;

import com.codegym.dao.UserDAO;
import com.codegym.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    // Xử lý các yêu cầu GET
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                // Hiển thị danh sách User
                listUsers(request, response);

            } else if (action.equals("search")) {
                // Tìm kiếm User theo country
                searchUsers(request, response);

            } else if (action.equals("sort")) {
                // Sắp xếp User theo name
                sortUsers(request, response);

            } else if (action.equals("create")) {
                // Hiển thị form thêm User
                request.getRequestDispatcher("/user/create.jsp")
                        .forward(request, response);

            } else if (action.equals("edit")) {
                // Hiển thị form sửa User
                showEditForm(request, response);

            } else if (action.equals("delete")) {
                // Hiển thị trang xác nhận xóa
                showDeleteForm(request, response);

            } else {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Không tìm thấy chức năng"
                );
            }

        } catch (SQLException e) {
            throw new ServletException(
                    "Lỗi khi truy cập cơ sở dữ liệu", e
            );
        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID không hợp lệ"
            );
        }
    }

    // Xử lý các yêu cầu POST
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                // Thêm User
                User user = new User(
                        request.getParameter("name"),
                        request.getParameter("email"),
                        request.getParameter("country")
                );

                userDAO.insertUser(user);

            } else if ("update".equals(action)) {
                // Cập nhật User
                int id = Integer.parseInt(
                        request.getParameter("id")
                );

                User user = new User(
                        id,
                        request.getParameter("name"),
                        request.getParameter("email"),
                        request.getParameter("country")
                );

                userDAO.updateUser(user);

            } else if ("delete".equals(action)) {
                // Xóa User
                int id = Integer.parseInt(
                        request.getParameter("id")
                );

                userDAO.deleteUser(id);

            } else {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Hành động không hợp lệ"
                );
                return;
            }

            // Quay lại danh sách sau khi xử lý
            response.sendRedirect(
                    request.getContextPath() + "/users?action=list"
            );

        } catch (SQLException e) {
            throw new ServletException(
                    "Lỗi khi lưu dữ liệu User", e
            );
        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID không hợp lệ"
            );
        }
    }

    // Hiển thị danh sách User
    private void listUsers(HttpServletRequest request,
                           HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<User> users = userDAO.selectAllUsers();

        request.setAttribute("listUser", users);

        request.getRequestDispatcher("/user/list.jsp")
                .forward(request, response);
    }

    // Tìm kiếm User theo country
    private void searchUsers(HttpServletRequest request,
                             HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String country = request.getParameter("country");

        if (country == null) {
            country = "";
        }

        List<User> users =
                userDAO.searchUsersByCountry(country.trim());

        request.setAttribute("listUser", users);
        request.setAttribute("country", country);

        request.getRequestDispatcher("/user/list.jsp")
                .forward(request, response);
    }

    // Sắp xếp User theo name tăng dần A-Z
    private void sortUsers(HttpServletRequest request,
                           HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<User> users = userDAO.sortUsersByName();

        request.setAttribute("listUser", users);

        request.getRequestDispatcher("/user/list.jsp")
                .forward(request, response);
    }

    // Hiển thị form sửa User
    private void showEditForm(HttpServletRequest request,
                              HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        User user = userDAO.selectUser(id);

        if (user == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Không tìm thấy User"
            );
            return;
        }

        request.setAttribute("user", user);

        request.getRequestDispatcher("/user/edit.jsp")
                .forward(request, response);
    }

    // Hiển thị trang xác nhận xóa User
    private void showDeleteForm(HttpServletRequest request,
                                HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        User user = userDAO.selectUser(id);

        if (user == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Không tìm thấy User"
            );
            return;
        }

        request.setAttribute("user", user);

        request.getRequestDispatcher("/user/delete.jsp")
                .forward(request, response);
    }
}
