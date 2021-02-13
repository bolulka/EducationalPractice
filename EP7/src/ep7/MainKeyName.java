package ep7;

import javafx.scene.text.Text;

public class MainKeyName implements Observer {
    private Text text;
    private TextLogger logger;

    public MainKeyName(TextLogger logger, Text text) {
        this.logger = logger;
        this.text = text;
        logger.registerObserver(this);
    }

    public void setText(Text text) {
        this.text = text;
    }

    @Override
    public void update(String s) {
        text.setText("            "+s);
    }
}
