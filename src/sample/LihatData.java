package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LihatData {
    // inisialisasi komponen dalam layout
    private ObservableList<Peserta> listOfPeserta = FXCollections.observableArrayList();

    @FXML
    private TableView<Peserta> tabel_peserta;
    @FXML
    private TableColumn<Peserta, Integer> col_id;
    @FXML
    private TableColumn<Peserta, String> col_nama;
    @FXML
    private TableColumn<Peserta, String> col_email;
    @FXML
    private TableColumn<Peserta, String> col_password;

    // pertama kali aplikasi dibuka lansung konek ke database
    public void initialize() throws SQLException {
        KoneksiJdbc koneksiJdbc = new KoneksiJdbc();
        Connection connection = koneksiJdbc.getConnection();

        listOfPeserta = bacaDB(connection);

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));

        tabel_peserta.setItems(listOfPeserta);
    }

    public ObservableList<Peserta> bacaDB(Connection connection) throws SQLException {
        String q_select = "SELECT * FROM registrasi";

        PreparedStatement statement = connection.prepareStatement(q_select);
        ResultSet resultSet = statement.executeQuery();
        ObservableList<Peserta> semuaPeserta = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Peserta peserta = new Peserta(
                    resultSet.getInt("id"),
                    resultSet.getString("nama"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );
            semuaPeserta.add(peserta);
        }
        return semuaPeserta;
    }
}
