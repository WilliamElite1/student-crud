package com.quiambao.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Controller
@RequestMapping(path="/quiambao")
public class MainController {

    @Autowired
    private StudentRepository studentRepository;


    @PostMapping(path="/student")
    public ResponseEntity createStudent(@RequestBody Student student) throws URISyntaxException {
        Student savedStudent = studentRepository.save(student);
        return ResponseEntity.created(new URI("/student/" + savedStudent.getId())).body(savedStudent);
    }

    @PutMapping("/student/{id}")
    public ResponseEntity updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        Student currentStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        currentStudent.setFirstName(student.getFirstName());
        currentStudent.setLastName(student.getLastName());
        currentStudent.setCourse(student.getCourse());
        currentStudent.setStudentNumber(student.getStudentNumber());
        currentStudent = studentRepository.save(currentStudent);
        return ResponseEntity.ok(currentStudent);
    }

    @GetMapping(path="/student")
    public @ResponseBody Iterable<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping(path="/student/{id}")
    public @ResponseBody Optional<Student> getStudent(@PathVariable Integer id) {
        return studentRepository.findById(id);
    }

    @DeleteMapping(path="/student/{id}")
    public @ResponseBody String deleteStudent(@PathVariable Integer id) {
        try {
            studentRepository.deleteById(id);
            return "Deleted";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
