@Override
public void addUserTransaction(User user, int[] permissionIds)
        throws SQLException {

    String sqlUser =
            "INSERT INTO users (name, email, country) VALUES (?, ?, ?)";

    String sqlPermission =
            "INSERT INTO user_permission (user_id, permission_id) VALUES (?, ?)";

    try (Connection connection = getConnection()) {

        boolean oldAutoCommit = connection.getAutoCommit();

        try {
            // Bắt đầu transaction
            connection.setAutoCommit(false);

            int userId;

            // Bước 1: Thêm User
            try (PreparedStatement pstmt = connection.prepareStatement(
                    sqlUser, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, user.getName());
                pstmt.setString(2, user.getEmail());
                pstmt.setString(3, user.getCountry());

                pstmt.executeUpdate();

                // Lấy ID User vừa tạo
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (!rs.next()) {
                        throw new SQLException("Không lấy được ID User.");
                    }

                    userId = rs.getInt(1);
                }
            }

            // Bước 2: Gán quyền cho User
            if (permissionIds != null && permissionIds.length > 0) {

                try (PreparedStatement pstmt =
                        connection.prepareStatement(sqlPermission)) {

                    for (int permissionId : permissionIds) {
                        pstmt.setInt(1, userId);
                        pstmt.setInt(2, permissionId);
                        pstmt.executeUpdate();
                    }
                }
            }

            // Bước 3: Tất cả thành công thì commit
            connection.commit();

        } catch (SQLException e) {

            // Có lỗi thì rollback
            try {
                connection.rollback();
            } catch (SQLException rollbackError) {
                e.addSuppressed(rollbackError);
            }

            // Báo lỗi cho Servlet xử lý
            throw e;

        } finally {
            // Khôi phục auto-commit
            connection.setAutoCommit(oldAutoCommit);
        }
    }
}