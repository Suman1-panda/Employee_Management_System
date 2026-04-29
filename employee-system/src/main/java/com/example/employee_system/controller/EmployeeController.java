package com.example.employee_system.controller;


import com.example.employee_system.entity.Employee;
import com.example.employee_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // Home page
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("employees", service.getAllEmployees());
        return "index";
    }

    // Save employee with image
    @PostMapping("/save")
    public String saveEmployee(@RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("image") MultipartFile image) throws IOException {

        String imageName = image.getOriginalFilename();

        String uploadDir = "src/main/resources/static/images/";
        image.transferTo(new File(uploadDir + imageName));

        Employee emp = new Employee();
        emp.setName(name);
        emp.setEmail(email);
        emp.setImageName(imageName);

        service.saveEmployee(emp);

        return "redirect:/";
    }

    // delete employee
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return "redirect:/";
    }
}

