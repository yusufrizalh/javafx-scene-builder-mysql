package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UbahData {
    private Connection connection;
    private LihatData lihatDataController;
    private ObservableList<Peserta> listOfPeserta;
    private Peserta peserta;
    private Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    private TextField txt_nama;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_password;
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
    @FXML
    private Button btn_ubah_data;

    public void initialize() throws SQLException {
        KoneksiJdbc koneksiJdbc = new KoneksiJdbc();
        connection = koneksiJdbc.getConnection();

        disabledField();

        lihatDataController = new LihatData();
        listOfPeserta = lihatDataController.bacaDB(connection);

        // diisi kedalam tabel peserta
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        tabel_peserta.setItems(listOfPeserta);
    }

    // method untuk ubah data
    public void ubahData(ActionEvent event) throws SQLException {
        // validasi text field tidak boleh kosong
        boolean textFieldValid = isInputEmpty(
                txt_nama, txt_password, txt_email
        );

        if (textFieldValid) {
            alertInformation.setTitle("WARNING!");
            alertInformation.setHeaderText(null);
            alertInformation.setContentText("Text Field tidak boleh kosong!");
            alertInformation.showAndWait();
        } else {
            // membuat query update
            String q_update = "UPDATE registrasi SET " +
                    "nama = ?, email = ?, password = ? " +
                    "WHERE id = ?";

            int id = peserta.getId();
            String nama = txt_nama.getText();
            String email = txt_email.getText();
            String password = txt_password.getText();

            alertInformation.setTitle("UPDATE");
            alertInformation.setHeaderText(null);
            alertInformation.setContentText("Peserta berhasil diubah");
            alertInformation.showAndWait();

            tabel_peserta.refresh();
            tabel_peserta.getSelectionModel().select(null);

            clearText();

            // jika tidak ada field yg ingin diubah
            disabledField();

            // perintah update dijalankan
            PreparedStatement statement = connection.prepareStatement(q_update);
            statement.setString(1, nama);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setInt(4, id);
            statement.executeUpdate();
            statement.close();

            listOfPeserta = lihatDataController.bacaDB(connection);
            tabel_peserta.setItems(listOfPeserta);
        }
    }

    // method untuk mengambil baris dari tabel menggunakan event mouse clicked
    public void tabelPesertaMouseClicked(MouseEvent event) {
        // memilih 1 baris
        peserta = tabel_peserta.getSelectionModel().getSelectedItem();
        enabledField();

        txt_nama.setText(peserta.getNama());
        txt_email.setText(peserta.getEmail());
        txt_password.setText(peserta.getPassword());
    }

    private void enabledField() {
        txt_nama.setDisable(false);
        txt_email.setDisable(false);
        txt_password.setDisable(false);
        btn_ubah_data.setDisable(false);
    }

    private void disabledField() {
        txt_nama.setDisable(true);
        txt_email.setDisable(true);
        txt_password.setDisable(true);
        btn_ubah_data.setDisable(true);
    }

    private void clearText() {
        txt_nama.clear();
        txt_email.clear();
        txt_password.clear();
        txt_nama.requestFocus();
    }

    private boolean isInputEmpty(TextField txt_nama, TextField txt_password, TextField txt_email) {
        return isTextFieldEmpty(txt_nama) || isTextFieldEmpty(txt_email)
                || isTextFieldEmpty(txt_password);
    }

    private boolean isTextFieldEmpty(TextField txt_field) {
        return txt_field.getText().equals("");
    }
}
