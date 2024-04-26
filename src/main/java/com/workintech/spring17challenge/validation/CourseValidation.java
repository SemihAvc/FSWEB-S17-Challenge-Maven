package com.workintech.spring17challenge.validation;

import com.workintech.spring17challenge.entity.Course;
import com.workintech.spring17challenge.exception.ApiException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class CourseValidation {
    public static void checkName(String name) {
if (name  == null || name.isEmpty()){
    throw  new ApiException("name cannot be null or empity!! "+ HttpStatus.BAD_REQUEST, HttpStatus.NOT_FOUND);
}
    }

    public  static void  checkCredit(Integer credit){
        if (credit == null || credit<0 || credit>4){
            throw  new ApiException("credit is null or not between !", HttpStatus.BAD_REQUEST);
        }
    }
    public  static  void CheckNameExist(List<Course> courses,String name){
        Optional<Course> courseOptional = courses.stream().filter(c-> c.getName().equalsIgnoreCase(name)).findAny();
        if (courseOptional.isPresent()){
            throw  new ApiException("course already exist with name:"+name,HttpStatus.BAD_REQUEST);
        }
    }
    public  static  void checkId(Integer id){

        if (id ==null|| id<0){
            throw  new ApiException("id cannot be null or less then zero!!!",HttpStatus.BAD_REQUEST);
        }
    }}


