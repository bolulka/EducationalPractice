package ep7;

import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class TextLogger implements Observable {
    private List<Observer> observers = new ArrayList<>();
    private Text text;
    private String newText;

    @Override
    public void registerObserver(Observer o) {
        if (o != null) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        if (o != null) {
            observers.remove(o);
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer elem : observers) {
            elem.update(newText);
        }
    }

    public void setText(String s){
        newText = s;
        notifyObservers();
    }
} 