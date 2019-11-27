package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class Login {
    // inisialisasi komponen dalam layout
    @FXML
    private TextField txt_email;
    @FXML
    private PasswordField txt_password;
    @FXML
    private Button btn_login;

    public void prosesLogin(ActionEvent event) {
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
        }
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
