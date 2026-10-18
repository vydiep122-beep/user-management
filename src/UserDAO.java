
@Override
public User getUserById(int id) throws SQLException {
    String sql = "{CALL get_user_by_id(?)}";

    try (Connection connection = getConnection();
         CallableStatement callableStatement =
                 connection.prepareCall(sql)) {

        callableStatement.setInt(1, id);

        try (ResultSet rs = callableStatement.executeQuery()) {
            if (rs.next()) {
                return new User(
                        id,
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("country")
                );
            }
        }
    }

    return null;
}

@Override
public void insertUserStore(User user) throws SQLException {
    String sql = "{CALL insert_user(?, ?, ?)}";

    try (Connection connection = getConnection();
         CallableStatement callableStatement =
                 connection.prepareCall(sql)) {

        callableStatement.setString(1, user.getName());
        callableStatement.setString(2, user.getEmail());
        callableStatement.setString(3, user.getCountry());

        callableStatement.executeUpdate();
    }
}
