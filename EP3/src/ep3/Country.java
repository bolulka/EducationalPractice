package ep3;

import javax.swing.*;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Country {
    private String name;
    private String capital;
    private ImageIcon flag;
    private final int CONSTFORCALCUTALIN1 = 15;
    private final int CONSTFORCALCUTALIN2 = 25;

    public Country(String name) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.capital = "Capital of " + name;
    }

    public Country(Path filePath) {
        flag = new ImageIcon(filePath.toString());
        String regex = "[a-z]+[.]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(filePath.toString());
        if (matcher.find()) {
            name = matcher.group(0).substring(0, matcher.group(0).length() - 1);
        }
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        capital = "Capital of " + name;
    }

    public int calcPrice() {
        return capital.length() * CONSTFORCALCUTALIN1 + name.length() * CONSTFORCALCUTALIN2;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public ImageIcon getFlag() {
        return flag;
    }

    public void setFlag(ImageIcon flag) {
        this.flag = flag;
    }
}