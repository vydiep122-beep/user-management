package com.codegym.dao;

import com.codegym.model.User;
import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {

    List<User> selectAllUsers() throws SQLException;

    User selectUser(int id) throws SQLException;

    void insertUser(User user) throws SQLException;

    boolean updateUser(User user) throws SQLException;

    boolean deleteUser(int id) throws SQLException;

    User getUserById(int id) throws SQLException;

    void insertUserStore(User user) throws SQLException;

    // Thêm User và phân quyền trong cùng transaction
    void addUserTransaction(User user, int[] permissionIds)
            throws SQLException;

    // Giữ lại các phương thức search/sort nếu file cũ đã có.
}