package com.diffchecker.java_fundamentals.swing_and_awt.pos_system;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CustomersPanel extends JPanel {

  // Widgets
  JLabel search_lbl;
  JLabel name_lbl;
  JLabel tp_number_lbl;

  JTextField search_field;
  JTextField name_field;
  JTextField tpNum_field;
  JTextField searchTable_field;

  JButton save_btn;
  JButton search_btn;
  JButton update_btn;
  JButton delete_btn;
  JButton search_table_btn;

  // Table
  JTable table;
  DefaultTableModel defaultTableModel;
  

  public CustomersPanel() {

  }
}