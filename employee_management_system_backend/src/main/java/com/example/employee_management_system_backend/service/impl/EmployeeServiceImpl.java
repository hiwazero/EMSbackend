package com.example.employee_management_system_backend.service.impl;


import com.example.employee_management_system_backend.dto.EmployeeDto;
import com.example.employee_management_system_backend.entity.Employee;
import com.example.employee_management_system_backend.exception.ResourceNotFoundException;
import com.example.employee_management_system_backend.mapper.EmployeeMapper;
import com.example.employee_management_system_backend.repository.EmployeeRepository;
import com.example.employee_management_system_backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.saveAndFlush(employee);
        return  EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees(Integer page,Integer size,String sortBy) {
        List<Employee> employees = employeeRepository.findAll();

        // Add logic to handle pagination and sorting
        if(page == null){
            page = 1 ; // Default page number
        }

        if(size == null){
            size = 10;
        }

        if(sortBy == null){
            sortBy = "ascend";
        }

        // Pagination Logic
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, employees.size());

        // Return the sublist based on the pagination parameters
        List<Employee> subList;
        if (startIndex <= endIndex) {
            subList = employees.subList(startIndex, endIndex);
        } else {
            subList = new ArrayList<>(); // Return an empty list if no items match the criteria
        }

        // Sorting logic
        if(sortBy.equals("ascend")){
            subList.sort(Comparator.comparing(Employee::getId)); // invoke getter for Id
        } else if (sortBy.equals("descend")) {
            subList.sort(Comparator.comparing(Employee::getId).reversed());
        }

        // Convert the sublist to EmployeeDto objects

        // return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
        // .collect(Collectors.toList());

        return subList.stream().map(EmployeeMapper::mapToEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee does not exist with id: " + id));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public EmployeeDto updateEmployeeById(Long id, EmployeeDto updatedEmployee) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with id: " + id));

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObj = employeeRepository.saveAndFlush(employee);

        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with id: " + id));
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDto> getEmployeesByDepartment(String department_code) {
        List<Employee> employees = employeeRepository.findEmployeesByDepartment(department_code);
        return employees.stream().map(EmployeeMapper::mapToEmployeeDto).collect(Collectors.toList());
    }

}
