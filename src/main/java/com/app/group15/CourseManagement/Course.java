package com.app.group15.CourseManagement;

import com.app.group15.Persistence.Persistence;

public class Course extends Persistence {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
