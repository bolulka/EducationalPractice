package ep7;

import javafx.scene.text.Text;

public class KeysLog implements Observer {
    private Text text;
    private TextLogger logger;

    public KeysLog(TextLogger logger, Text text) {
        this.logger = logger;
        this.text = text;
        logger.registerObserver(this);
    }

    public void setText(Text text) {
        this.text = text;
    }

    @Override
    public void update(String s) {
        text.setText(text.getText()+s);
    }
} 