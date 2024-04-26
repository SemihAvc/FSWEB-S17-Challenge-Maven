package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.entity.*;
import com.workintech.spring17challenge.exception.ApiException;
import com.workintech.spring17challenge.validation.CourseValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.PagedResultsControl;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private List<Course> courses;

private CourseGpa highCourseGpa ;
private CourseGpa lowCourseGpa;
private CourseGpa mediumCourseGpa;
@Autowired
    public CourseController(@Qualifier("highCourseGpa") CourseGpa highCourseGpa,
                            @Qualifier("lowCourseGpa")  CourseGpa lowCourseGpa,
                            @Qualifier("mediumCourseGpa")   CourseGpa mediumCourseGpa) {
        this.highCourseGpa = highCourseGpa;
    this.lowCourseGpa = lowCourseGpa;
    this.mediumCourseGpa = mediumCourseGpa;
}

    @PostConstruct
    public  void  init()
    {
        courses = new ArrayList<>();    }

    @GetMapping
    public  List<Course>getAll(){
    return this.courses;
    }
    @GetMapping("/{name}")
    public Course getByName(@PathVariable("name") String name) {
        CourseValidation.checkName(name);
return  courses.stream().filter(course ->
        course.getName().equalsIgnoreCase(name)).findFirst().orElseThrow(()->
        new ApiException("name not found with name:"+name , HttpStatus.NOT_FOUND));
    }
@PostMapping
   public ResponseEntity<ApiResponse> create(@RequestBody Course course){
    CourseValidation.checkName(course.getName());

CourseValidation.checkCredit(course.getCredit());
CourseValidation.CheckNameExist(courses,course.getName());
courses.add(course);

ApiResponse apiResponse =new ApiResponse();
return  new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
}

private  Integer getTotalGpa(Course course){
    if (course.getCredit()<=2){
        return  course.getGrade().getCoefficient()*course.getCredit()* lowCourseGpa.getGpa();
    }
    else if (course.getCredit()<=3){
        return  course.getGrade().getCoefficient()*course.getCredit()* mediumCourseGpa.getGpa();
    }
else {
      return  course.getGrade().getCoefficient() * course.getCredit() * highCourseGpa.getGpa();

    }}



@PutMapping("/{id}")
    public  ResponseEntity<ApiResponse> update(@PathVariable Integer id , @RequestBody Course course){

    CourseValidation.checkId(id);
    CourseValidation.checkCredit(course.getCredit());
    CourseValidation.checkName(course.getName());
    Course existingCourse = courses.stream() .filter(c->c.getName().equalsIgnoreCase(course.getName())).findAny().orElseThrow(()->new ApiException("ewcord not foubd width id"+id,HttpStatus.NOT_FOUND));

int indexOfExistingCourse = courses.indexOf(existingCourse);
course.setId(id);

courses.set(indexOfExistingCourse,course);
Integer totalGpa = getTotalGpa(course);
ApiResponse apiResponse = new ApiResponse(courses.get(indexOfExistingCourse), totalGpa);
return  new ResponseEntity<>(apiResponse, HttpStatus.OK);





}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        CourseValidation.checkId(id);
        Course course = courses.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow(() -> new ApiException("record not found with id" + id, HttpStatus.NOT_FOUND));
        courses.remove(course);
    }


}
