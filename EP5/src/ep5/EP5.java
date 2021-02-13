package ep5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EP5 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	//Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("firstTask.fxml"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("secondTask.fxml"));
        Scene scene = new Scene(root, 600, 375);
        //stage.setTitle("First Task");
        stage.setTitle("Second Task");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}