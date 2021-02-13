package ep3;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class MyLabel extends JLabel implements ListCellRenderer<Country>{
	public MyLabel() {
        setOpaque(true);
    }
    @Override
    public Component getListCellRendererComponent(
            JList<? extends Country> list, Country value, int index,
            boolean isSelected, boolean cellHasFocus) {
    
        Icon iconFlag = value.getFlag();
        String name = value.getName();
        this.setIcon(iconFlag);
        this.setText(name);
        //JLabel label = new JLabel(name , iconFlag, JLabel.LEFT);
        if(isSelected) setBackground(list.getSelectionBackground());
        else setBackground(list.getBackground());
        return this;
    }
}