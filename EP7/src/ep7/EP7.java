package ep7;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class EP7 extends Application {

    Text text1 = new Text();
    Text text2 = new Text();

    @Override
    public void start(Stage primaryStage) {

        text1.setFont(Font.font("New Times Roman", FontWeight.EXTRA_BOLD, 80));

        BorderPane bp = new BorderPane();

        bp.setTop(text1);
        bp.setCenter(text2);

        TextLogger logger = new TextLogger();

        new MainKeyName(logger, text1);
        new KeysLog(logger, text2);
        
        Scene scene = new Scene(bp, 1000, 400);
        scene.getRoot().requestFocus();
        primaryStage.setTitle("Educational Practice 7");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getRoot().setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String s = e.getCode().getName();
                logger.setText(s);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}