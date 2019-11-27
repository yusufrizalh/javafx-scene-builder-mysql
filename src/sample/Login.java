package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class Login {
    // inisialisasi komponen dalam layout
    @FXML
    private TextField txt_email;
    @FXML
    private PasswordField txt_password;
    @FXML
    private Button btn_login;

    public void prosesLogin(ActionEvent event) throws IOException {
        Window window = btn_login.getScene().getWindow();

        // validasi bahwa email dan password tidak boleh kosong
        if (txt_email.getText().isEmpty()) {
            tampilkanAlert(Alert.AlertType.ERROR, window,
                    "Form Error", "Tuliskan Email Anda");
            return;
        }
        if (txt_password.getText().isEmpty()) {
            tampilkanAlert(Alert.AlertType.ERROR, window,
                    "Form Error", "Tuliskan Password Anda");
            return;
        }

        // jika email dan password sudah terisi
        String email = txt_email.getText();
        String password = txt_password.getText();

        KoneksiJdbc koneksiJdbc = new KoneksiJdbc();
        boolean checker = koneksiJdbc.selectData(email, password);
        if (!checker) {
            tampilkanAlert(Alert.AlertType.ERROR, window,
                    "LOGIN GAGAL", "Email atau Password Salah!");
        } else {
            tampilkanAlert(Alert.AlertType.INFORMATION, window,
                    "LOGIN BERHASIL", "Selamat Datang: " + email);
            // diarahkan ke dashboard
            // memangil layout dashboard
            panggilLayout("dashboard");
        }
    }

    // method untuk memanggil layout
    private void panggilLayout(String layout) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(layout + ".fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
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

}
