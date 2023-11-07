package com.example.employee_management_system_backend.mapper;

import com.example.employee_management_system_backend.dto.EmployeeDto;

import com.example.employee_management_system_backend.entity.Employee;
import com.example.employee_management_system_backend.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeMapper {

    @Autowired
    static Department department;

   public static EmployeeDto mapToEmployeeDto(Employee employee){
       return new EmployeeDto(
               employee.getId(),
               employee.getFirstName(),
               employee.getLastName(),
               employee.getEmail(),
               employee.getDepartment().getDepartment_code()
       );
   }

   public static Employee mapToEmployee(EmployeeDto employeeDto){
       Employee employee = new Employee();
               employee.setId(employeeDto.getId());
               employee.setFirstName(employeeDto.getFirstName());
               employee.setLastName(employeeDto.getLastName());
               employee.setEmail(employeeDto.getEmail());
               //employee.setDepartment_code(employeeDto.getDepartment_code());
               //employee.setDepartment(employeeDto.getDepartment_code());

               // Create a new Department object and set its department_code
               department.setDepartment_code(employeeDto.getDepartment_code());

               // Set the department for the employee
               employee.setDepartment(department);

       return  employee;
}


}
