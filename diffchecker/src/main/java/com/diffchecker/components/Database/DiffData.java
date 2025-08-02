package com.diffchecker.components.Database;

public class DiffData {
  public String title;
  public String leftText;
  public String rightText;

  public DiffData(String title, String leftText, String rightText) {
    this.title = title;
    this.leftText = leftText;
    this.rightText = rightText;
  }
}
