package com.example.employee_management_system_backend.repository;

import com.example.employee_management_system_backend.dto.EmployeeDto;
import com.example.employee_management_system_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT * FROM Employees e WHERE e.department_code = :departmentCode", nativeQuery = true)
    List<Employee> findEmployeesByDepartment(@Param("departmentCode") String departmentCode);
}
