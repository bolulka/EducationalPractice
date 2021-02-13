package ep3;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Task1 extends JPanel {

    private JList<Country> list;
    private DefaultListModel<Country> listModel;
    private List<Country> countries;
    private JLabel label = new JLabel();

    public Task1(List<Country> countries) {
        this.countries = countries;
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setCellRenderer(new MyLabel());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane();
        list.setBounds(100, 100, 75, 75);
        list.addListSelectionListener(e -> {
            Country country = list.getSelectedValue();
            label.setIcon(country.getFlag());
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.BOTTOM);


           //JOptionPane.showMessageDialog(this, str);
        });

        scrollPane.setViewportView(list);
        scrollPane.setPreferredSize(new Dimension(250, 400));

        label.setPreferredSize(new Dimension(150,100));
        
        this.add(scrollPane);
        this.add(label);
        this.setSize(new Dimension(500, 500));
    }

    public void update() {
        for (Country country : countries) {
            listModel.add(listModel.size(), country);
        }
    }
}