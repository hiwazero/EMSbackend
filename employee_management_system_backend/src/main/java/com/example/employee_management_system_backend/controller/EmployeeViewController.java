package com.example.employee_management_system_backend.controller;

import com.example.employee_management_system_backend.dto.EmployeeDto;
import com.example.employee_management_system_backend.entity.Employee;
import com.example.employee_management_system_backend.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Controller
public class EmployeeViewController {

    private EmployeeService employeeService;


    // Create VIEW for All List Employee
    @GetMapping("/")
    public String viewHomePage(
            Model model,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy
    ){
        model.addAttribute("allemplist", employeeService.getAllEmployees(page,size,sortBy));
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

    // Export Data
    @GetMapping("/export")
    public void exportToExcel(
            HttpServletResponse response,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy
            ) throws IOException{

        List<EmployeeDto> employeeDtoList = employeeService.getAllEmployees(page,size,sortBy);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        // Add headers
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("First Name");
        headerRow.createCell(1).setCellValue("Last Name");
        headerRow.createCell(2).setCellValue("Email");

        // Add data to the sheet
        int rowNum = 1;
        for (EmployeeDto employeeDto : employeeDtoList){
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(employeeDto.getFirstName());
            dataRow.createCell(1).setCellValue(employeeDto.getLastName());
            dataRow.createCell(2).setCellValue(employeeDto.getEmail());
        }

        // Set the content type and attachment header
        response.setHeader("Content-Disposition", "attachment; filename=EmployeeData.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // Write the workbook to the response
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
