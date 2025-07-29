package com.diffchecker.java_fundamentals.swing_and_awt.pos_system;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CustomersPanel extends JPanel {
  // Widgets
  JLabel search_Lbl;
  JLabel name_Lbl;
  JLabel mobile_number_Lbl;

  JTextField name_Field;
  JTextField search_Field;
  JTextField mobile_number_Field;
  JTextField search_table_Field;

  JButton save_Btn;
  JButton search_Btn;
  JButton update_Btn;
  JButton delete_Btn;
  JButton search_table_Btn;

  // Table
  JTable table;
  DefaultTableModel defaultTableModel;

  // Database
  DB database;

  public CustomersPanel() {

    database = new DB();

    // Widgets Initialization
    search_Lbl = new JLabel("Search by Mobile Number: ");
    name_Lbl = new JLabel("Name: ");
    mobile_number_Lbl = new JLabel("Mobile Number: ");

    name_Field = new JTextField();
    search_Field = new JTextField();
    mobile_number_Field = new JTextField();
    search_table_Field = new JTextField();

    save_Btn = new JButton("Save");
    search_Btn = new JButton("Search");
    update_Btn = new JButton("Update");
    delete_Btn = new JButton("Delete");
    search_table_Btn = new JButton("Search Table");

    // Left Panel: Grid
    JPanel leftJPanel = new JPanel();
    leftJPanel.setPreferredSize(new Dimension(200, 300));
    leftJPanel.setLayout(new GridLayout(8, 2));

    leftJPanel.add(search_Lbl);
    leftJPanel.add(search_Field);
    leftJPanel.add(name_Lbl);
    leftJPanel.add(name_Field);
    leftJPanel.add(mobile_number_Lbl);
    leftJPanel.add(mobile_number_Field);

    leftJPanel.add(save_Btn);
    leftJPanel.add(search_Btn);
    leftJPanel.add(update_Btn);
    leftJPanel.add(delete_Btn);
    leftJPanel.add(search_table_Field);
    leftJPanel.add(search_table_Btn);

  }
}
