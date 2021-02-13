package ep5;

import javafx.scene.control.TextArea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondTask {

	private final static Pattern DATE = Pattern
			.compile("([0-2][0-9]|(3)[0-1])((.)|(/)|(-))((01)|(03)|(05)|(07)|(10)|(12))((.)|(/)|(-))(\\d{4})"
					+ "|([0-2][0-9]|(30))((.)|(/)|(-))((04)|(06)|(08)|(09)|(11))((.)|(/)|(-))(\\d{4})"
					+ "|([0-2][0-9])((.)|(/)|(-))((02))((.)|(/)|(-))(\\d{4})");
	private final static String START_STRING = "";

	public TextArea inputArea;
	public TextArea outputArea;

	public void initialize() {
		inputArea.setText(START_STRING);

		inputArea.textProperty().addListener((observable, oldValue, newValue) -> {
			outputArea.setText("");
			String text = inputArea.getText();
			Matcher matcher = DATE.matcher(text);
			while (matcher.find()) {
				outputArea.appendText(matcher.group());
				outputArea.appendText("\n");
			}
		});
	}
}