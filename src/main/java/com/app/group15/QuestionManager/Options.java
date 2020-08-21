package com.app.group15.QuestionManager;

import com.app.group15.Persistence.Persistence;

public class Options extends Persistence{

    private String option;
    private String value;
    public boolean isSelected;
    private int id;
    private int index;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Options{" +
                "option='" + option + '\'' +
                ", value='" + value + '\'' +
                ", id=" + id +
                '}';
    }
}
