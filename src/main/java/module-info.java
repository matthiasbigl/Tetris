module at.htlhl {
    requires javafx.controls;
    requires javafx.fxml;

    opens at.htlhl to javafx.fxml;
    exports at.htlhl;
}
