package com.example.employee_management_system_backend.repository;

import com.example.employee_management_system_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
