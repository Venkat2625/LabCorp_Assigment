package com.timesheet.controller;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.timesheet.employee.Employee;
import com.timesheet.employee.service.EmployeeService;

@RestController
@RequestMapping("/api/v1")
public class TimesheetController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getEmployees() {
		try {
			List<Employee> employees = employeeService.getEmployees();
			return new ResponseEntity<List<Employee>>(employees, null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Employee>>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/work")
	public ResponseEntity<List<Employee>> logWork(@RequestParam("id") int id, @RequestParam("workDays") int workDays) {
		try {
			List<Employee> employees = employeeService.logWork(id, workDays);
			return new ResponseEntity<List<Employee>>(employees, null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Employee>>(null, null, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/vacation")
	public ResponseEntity<List<Employee>> takeVacation(@RequestParam("id") int id,
			@RequestParam("vacationDays") float vacations) {
		try {
			List<Employee> employees = employeeService.takeVacation(id, vacations);
			return new ResponseEntity<List<Employee>>(employees, null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Employee>>(null, null, HttpStatus.BAD_REQUEST);
		}
	}

}
