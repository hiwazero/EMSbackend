package com.example.employee_management_system_backend.controller;

import com.example.employee_management_system_backend.dto.EmployeeDto;
import com.example.employee_management_system_backend.entity.Employee;
import com.example.employee_management_system_backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
public class EmployeeViewController {

    private EmployeeService employeeService;


    // Create VIEW for All List Employee
    @GetMapping("/")
    public String viewHomePage(Model model){
        model.addAttribute("allemplist", employeeService.getAllEmployees());
        return "index";
    }

    // Create VIEW for Adding Employee
    @GetMapping("/add")
    public String addNewEmployee(Model model){
        EmployeeDto employeeDto = new EmployeeDto(); // new entry so data should be blank first
        model.addAttribute("employee", employeeDto);
        return "addEmployee";
    }

    // Create LOGIC for Saving Employee
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") EmployeeDto employeeDto){
        employeeService.createEmployee(employeeDto);
        return "redirect:/";
    }

    // Create VIEW for Updating Employee
    @GetMapping("/updateform/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model){
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employeeDto);
        return "updateEmployee";
    }

    // Create LOGIC for Saving Updated Employee
    @PostMapping("/saveUpdate")
    public String updateEmployee(@ModelAttribute("employee") EmployeeDto employeeDto){
        employeeService.updateEmployeeById(employeeDto.id, employeeDto);
        return "redirect:/";
    }

    // Create VIEW for Deleting Employee
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id){
        employeeService.deleteEmployeeById(id);
        return  "redirect:/";
    }
}
