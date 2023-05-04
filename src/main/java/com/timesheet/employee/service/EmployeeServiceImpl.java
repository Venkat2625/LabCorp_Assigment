package com.timesheet.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.timesheet.employee.Employee;
import com.timesheet.employee.EmployeeType;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static List<Employee> employees;

	public EmployeeServiceImpl() {
		employees = new ArrayList<>();
		employees.add(getEmployee(1, "John", EmployeeType.HOURLY));
		employees.add(getEmployee(2, "Chris", EmployeeType.HOURLY));
		employees.add(getEmployee(3, "Symond", EmployeeType.HOURLY));
		employees.add(getEmployee(4, "Robert", EmployeeType.HOURLY));
		employees.add(getEmployee(5, "Stephen", EmployeeType.SALARIED));
		employees.add(getEmployee(6, "Beryl", EmployeeType.SALARIED));
		employees.add(getEmployee(7, "Medimi", EmployeeType.SALARIED));
		employees.add(getEmployee(8, "Lazaro", EmployeeType.MANAGER));
		employees.add(getEmployee(9, "James", EmployeeType.MANAGER));
		employees.add(getEmployee(10, "Harvard", EmployeeType.MANAGER));
	}

	/**
	 * returns list of employees
	 */
	@Override
	public List<Employee> getEmployees() {
		return employees;
	}

	/**
	 * 
	 * This method logs the number of work days for the employee with the specified
	 * ID and updates their vacation days balance accordingly.
	 * 
	 * @param id       The ID of the employee whose work days are to be logged.
	 * @param workDays The number of work days to be logged.
	 * @return The updated employees list.
	 * @throws Exception if the logWork method throws an Exception, or if the
	 *                   employee with the specified ID is not found.
	 */
	@Override
	public List<Employee> logWork(int id, int workDays) throws Exception {
		Optional<Employee> emp = employees.stream().filter(employee -> employee.getId() == id).findFirst();
		if (emp.isPresent()) {
			emp.get().logWork(workDays);
		} else {
			throw new RuntimeException("Employee not found");
		}
		return employees;
	}

	/**
	 * 
	 * This method allows the employee with the specified ID to take a certain
	 * number of vacation days and updates their vacation days balance accordingly.
	 * 
	 * @param id        The ID of the employee who is taking vacation.
	 * @param vacations The number of vacation days to be taken.
	 * @return The updated employees list.
	 * @throws Exception if the takeVacation method throws an Exception, or if the
	 *                   employee with the specified ID is not found.
	 */
	@Override
	public List<Employee> takeVacation(int id, float vacations) throws Exception {
		Optional<Employee> emp = employees.stream().filter(employee -> employee.getId() == id).findFirst();
		if (emp.isPresent()) {
			emp.get().takeVacation(vacations);
		} else {
			throw new RuntimeException("Employee not found");
		}
		return employees;
	}

	private Employee getEmployee(int id, String name, EmployeeType type) {
		Employee employee = new Employee();
		switch (type) {
		case HOURLY:
			employee.setType(EmployeeType.HOURLY);
			break;
		case SALARIED:
			employee.setType(EmployeeType.SALARIED);
			break;
		case MANAGER:
			employee.setType(EmployeeType.MANAGER);
			break;
		}
		employee.setId(id);
		employee.setName(name);
		return employee;
	}

}
