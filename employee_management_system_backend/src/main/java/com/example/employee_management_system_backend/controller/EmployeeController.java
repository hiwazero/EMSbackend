package com.example.employee_management_system_backend.controller;

import com.example.employee_management_system_backend.dto.EmployeeDto;
import com.example.employee_management_system_backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    //Build Add Employee REST API
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    //Build Get All Employee REST API
    @GetMapping
    public  ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    //Build Get Employee By Id REST API
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long id){
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeDto);
    }

    //Build Update Employee By Id REST API
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDto> updateEmployeeById(@PathVariable("id") Long id, @RequestBody EmployeeDto updatedEmployee){
        EmployeeDto employeeDto = employeeService.updateEmployeeById(id, updatedEmployee);
        return ResponseEntity.ok(employeeDto);
    }

    //Build Delete Employee By Id REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long id){
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee deleted successfully!");
    }
}
