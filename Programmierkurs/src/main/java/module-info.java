module com.example.programmierkurs {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.programmierkurs to javafx.fxml;
    exports com.example.programmierkurs;
}