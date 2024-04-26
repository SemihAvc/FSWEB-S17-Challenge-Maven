package com.workintech.spring17challenge.entity;

import org.springframework.stereotype.Component;

@Component
public class HightCourseGpa implements  CourseGpa{

    @Override
    public int getGpa() {
        return 10;
    }
}
