package com.comp303.lab2.controller;

import com.comp303.lab2.model.Enrollment;
import com.comp303.lab2.model.Program;
import com.comp303.lab2.model.Student;
import com.comp303.lab2.repository.EnrollmentRepository;
import com.comp303.lab2.repository.ProgramRepository;
import com.comp303.lab2.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Optional;

@Controller
public class AppController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("student", new Student());
        return "index";
    }

    @PostMapping("/register")
    public String register(@Valid Student student, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        studentRepository.save(student);
        model.addAttribute("student", new Student());
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {

        Student student = studentRepository.findByUsernameAndPassword(username, password);
        if (student != null) {
            model.addAttribute("student", student);
            model.addAttribute("program", new Program());
            return "program";
        } else {
            model.addAttribute("student", new Student());
            return "index";
        }
    }

    @PostMapping("/insertProgram")
    public String insertProgram(@Valid Program program, BindingResult bindingResult,
                                @RequestParam("studentId") int studentId, Model model) {
        if (bindingResult.hasErrors()) {
            Student student = studentRepository.findById(studentId).orElseThrow();
            model.addAttribute("student", student);
            model.addAttribute("program", program);
            return "program";
        }

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Invalid student id"));

        Optional<Program> optionalProgram = programRepository.findById(program.getProgramCode());
        if (optionalProgram.isPresent()) {
            program = optionalProgram.get();
            // proceed with using foundProgram
        } else {
            programRepository.save(program);
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setProgram(program);
        enrollment.setStartDate(new Date(System.currentTimeMillis()));
        enrollment.setAmountPaid(program.getFee());
        enrollmentRepository.save(enrollment);

        model.addAttribute("enrollment", enrollment);
        return "receipt";
    }

    @GetMapping("/editProfile/{id}")
    public String editStudentPage(@PathVariable("id") int id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid StudentId"));
        model.addAttribute("student", student);
        return "profile";
    }

    @PostMapping("/editProfile/{id}")
    public String editStudentPagePost(@PathVariable("id") int id, @Valid Student student,
                                      BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "profile";
        }

        studentRepository.save(student);
        model.addAttribute("student", student);
        model.addAttribute("program", new Program());
        return "program";
    }
}
