module br.ufrn.imd.cambio_imd {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens br.ufrn.imd.cambio_imd to javafx.fxml;
    exports br.ufrn.imd.cambio_imd;
}