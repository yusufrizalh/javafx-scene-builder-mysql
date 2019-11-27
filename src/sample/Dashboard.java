package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Dashboard {
    // inisialisasi komponen dalam layout dashboard
    @FXML
    private Button btn_lihat_data;
    @FXML
    private Button btn_tambah_data;
    @FXML
    private Button btn_ubah_data;
    @FXML
    private Button btn_hapus_data;

    @FXML
    private AnchorPane anchorPaneLayout;

    // method untuk memanggil layout
    private void panggilLayout(String layout) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(layout + ".fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // method-method untuk masing-masing button
    public void lihatData(ActionEvent event) throws IOException {
        panggilLayout("lihat_data");
    }

    public void initialize() throws IOException {
        panggilLayout("lihat_data");
    }

    public void tambahData(ActionEvent event) throws IOException {
        panggilLayout("tambah_data");
    }

    public void ubahData(ActionEvent event) throws IOException {
        panggilLayout("ubah_data");
    }

    public void hapusData(ActionEvent event) throws IOException {
        panggilLayout("hapus_data");
    }
}
