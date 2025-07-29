package com.diffchecker.java_fundamentals.swing_and_awt.pos_system;

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

  }
}
