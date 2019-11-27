package sample;

import java.sql.*;

public class KoneksiJdbc {
    // konfigurasi untuk koneksi ke database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pelatihan?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    // konfigurasi query
    private static final String Q_INSERT = "INSERT INTO registrasi(nama, email, password) " +
            "VALUES (?, ?, ?)";
    private static final String Q_SELECT = "SELECT * FROM registrasi WHERE " +
            "email = ? AND password = ?";

    // method untuk q_select
    public boolean selectData(String email, String password) {
        try (Connection connection = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASS
        );
             PreparedStatement statement = connection.prepareStatement(Q_SELECT)) {
            statement.setString(1, email);
            statement.setString(2, password);

            System.out.println(statement);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    // method untuk q_insert
    public void insertData(String nama, String email, String password) {
        // test koneksi terhadap database
        try (Connection connection = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASS
        );
             PreparedStatement statement = connection.prepareStatement(Q_INSERT)) {
            statement.setString(1, nama);
            statement.setString(2, email);
            statement.setString(3, password);

            System.out.println(statement);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
