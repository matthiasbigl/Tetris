module at.htlhl {
    requires javafx.controls;
    requires javafx.fxml;

    opens at.htlhl to javafx.fxml;
    exports at.htlhl;
    exports at.htlhl.javafxtetris;
    opens at.htlhl.javafxtetris to javafx.fxml;
    exports at.htlhl.javafxtetris.grid;
    opens at.htlhl.javafxtetris.grid to javafx.fxml;
    exports at.htlhl.javafxtetris.block;
    opens at.htlhl.javafxtetris.block to javafx.fxml;
    exports at.htlhl.javafxtetris.graphics;
    opens at.htlhl.javafxtetris.graphics to javafx.fxml;
}
