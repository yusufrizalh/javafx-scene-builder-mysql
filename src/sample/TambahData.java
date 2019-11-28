package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TambahData {
    // inisialisasi komponen dalam layout
    @FXML
    private TextField txt_nama;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_password;
    @FXML
    private Button btn_tambah_data;
    @FXML
    private VBox tambahDataLayout;

    private Connection connection;
    private Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);

    public void initialize() throws SQLException {
        KoneksiJdbc koneksiJdbc = new KoneksiJdbc();
        connection = koneksiJdbc.getConnection();
    }

    // method untuk tambah data
    public void tambahData(ActionEvent event) throws SQLException {
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
            String nama = txt_nama.getText();
            String email = txt_email.getText();
            String password = txt_password.getText();

            // simpan ke tabel
            simpanDB(nama, email, password);
            alertInformation.setTitle("INSERT");
            alertInformation.setHeaderText(null);
            alertInformation.setContentText(nama + " Berhasil disimpan!");
            alertInformation.showAndWait();

            clearText();
        }
    }

    private void clearText() {
        txt_nama.clear();
        txt_email.clear();
        txt_password.clear();
        txt_nama.requestFocus();
    }

    private void simpanDB(String nama, String email, String password) throws SQLException {
        String q_insert = "INSERT INTO registrasi(nama, email, password) " +
                "VALUES(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(q_insert);
        statement.setString(1, nama);
        statement.setString(2, email);
        statement.setString(3, password);
        statement.executeUpdate();
    }

    private boolean isInputEmpty(TextField txt_nama, TextField txt_password, TextField txt_email) {
        return isTextFieldEmpty(txt_nama) || isTextFieldEmpty(txt_email)
                || isTextFieldEmpty(txt_password);
    }

    private boolean isTextFieldEmpty(TextField txt_field) {
        return txt_field.getText().equals("");
    }
}
