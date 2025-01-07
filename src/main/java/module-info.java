module br.ufrn.imd.cambio_imd {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.management;

    opens br.ufrn.imd.cambio_imd to javafx.fxml;
    opens br.ufrn.imd.cambio_imd.controllers to javafx.fxml;
    exports br.ufrn.imd.cambio_imd;
}