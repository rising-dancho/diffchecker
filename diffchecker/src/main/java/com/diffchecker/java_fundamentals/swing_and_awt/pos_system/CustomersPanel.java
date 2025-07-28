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

  JTextField search_Field;
  JTextField name_Field;
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
  DB db;

  public CustomersPanel() {

  }

}
