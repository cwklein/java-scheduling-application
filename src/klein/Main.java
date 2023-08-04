package klein;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import klein.helper_controllers.JDBC;

import java.io.IOException;
import java.time.ZoneId;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/klein/view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 342, 290);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) throws Exception {
        JDBC.openConnection();
        System.out.println(ZoneId.systemDefault());
        launch();
        JDBC.closeConnection();
    }
}