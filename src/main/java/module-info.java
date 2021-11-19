module at.htlhl {
    requires javafx.controls;
    requires javafx.fxml;

    opens at.htlhl.javafxtetris to javafx.fxml, javafx.graphics;
    exports at.htlhl.javafxtetris.grid;
    opens at.htlhl.javafxtetris.grid to javafx.fxml;
    exports at.htlhl.javafxtetris.graphics;
    opens at.htlhl.javafxtetris.graphics to javafx.fxml;
}
