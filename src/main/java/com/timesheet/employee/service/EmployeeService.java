package com.timesheet.employee.service;
import java.util.List;

import com.timesheet.employee.Employee;

public interface EmployeeService {
	
	List<Employee> getEmployees();

	List<Employee> logWork(int id, int workDays) throws Exception;

	List<Employee> takeVacation(int id, float vacations) throws Exception;
}