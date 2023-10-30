package com.example.employee_management_system_backend.service;

import com.example.employee_management_system_backend.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto getEmployeeById(Long id);
    EmployeeDto updateEmployeeById(Long id, EmployeeDto employeeDto);
    void deleteEmployeeById(Long id);
}
