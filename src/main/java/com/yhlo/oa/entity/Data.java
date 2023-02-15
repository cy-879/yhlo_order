package com.yhlo.oa.entity;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * @author cy
 * @ClassName: Data
 * @Description:
 * @date 2022/11/1810:50
 */
public class Data {
    private String name;
    private SimpleBooleanProperty isSelected = new SimpleBooleanProperty();

    public Data(String name, Boolean isSelected) {
        this.name = name;
        this.isSelected = new SimpleBooleanProperty(isSelected);
    }

    public Data(String name){
        this.name = name;
        this.isSelected = new SimpleBooleanProperty(false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsSelected() {
        return isSelected.get();
    }

    public SimpleBooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
    }
}
