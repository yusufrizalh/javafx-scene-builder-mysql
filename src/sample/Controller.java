package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.io.IOException;

public class Controller {
    // 1. inisialisasi komponen dalam layout
    @FXML
    private TextField txt_nama;
    @FXML
    private TextField txt_email;
    @FXML
    private PasswordField txt_password;
    @FXML
    private Button btn_register;

    @FXML
    public Button btn_login;

    // method untuk proses registrasi
    public void prosesRegistrasi(ActionEvent event) {
        Window window = btn_register.getScene().getWindow();

        // validasi bahwa field tidak boleh kosong
        if (txt_nama.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Nama Tidak Boleh Kosong");
            return;
        }
        if (txt_email.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Email Tidak Boleh Kosong");
            return;
        }
        if (txt_password.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Password Tidak Boleh Kosong");
            return;
        }

        // jika field sudah valid, sudah terisi semua
        String nama = txt_nama.getText();
        String email = txt_email.getText();
        String password = txt_password.getText();

        // membuat class untuk konek ke database
        KoneksiJdbc koneksiJdbc = new KoneksiJdbc();
        // memanggil method untuk insert data
        koneksiJdbc.insertData(nama, email, password);

//        JOptionPane.showMessageDialog(null,
//                "Registrasi Peserta Training Berhasil");

        tampilkanAlert(Alert.AlertType.INFORMATION, window,
                "Registrasi", "Selamat Datang : " + txt_nama.getText());
    }

    // method untuk menampilkan Alert
    private static void tampilkanAlert(Alert.AlertType alertType,
                                       Window window,
                                       String title,
                                       String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(window);
        alert.show();
    }

    public void bukaFormLogin() {
        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(Controller.this.getClass()
                            .getResource("login.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Form Login Peserta");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(root, 300, 275));
                    stage.show();
                    // menyembunyikan form registrasi
                    // form registrasi hide()
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
