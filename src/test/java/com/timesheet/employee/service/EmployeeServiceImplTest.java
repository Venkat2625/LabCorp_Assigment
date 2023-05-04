package com.timesheet.employee.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.timesheet.employee.Employee;
import com.timesheet.employee.EmployeeType;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

	private EmployeeServiceImpl employeeService;

	private List<Employee> employees;

	@BeforeEach
	void setUp() {
		employees = new ArrayList<>();
		Employee employee1 = new Employee();
		employee1.setId(1);
		employee1.setName("John Doe");
		employee1.setType(EmployeeType.HOURLY);
		employee1.setWorkDays(100);
		employee1.setVacationDays(15.5f);
		employees.add(employee1);

		Employee employee2 = new Employee();
		employee2.setId(2);
		employee2.setName("Jane Smith");
		employee2.setType(EmployeeType.SALARIED);
		employee2.setWorkDays(150);
		employee2.setVacationDays(10.0f);
		employees.add(employee2);
	}

	@BeforeEach
	public void setup1() {
		employeeService = new EmployeeServiceImpl();
	}

	@Test
	public void testGetEmployees() {
		assertNotNull(employeeService.getEmployees());
		assertEquals(10, employeeService.getEmployees().size());
	}

	@Test
	public void getEmployeesTest() {
		List<Employee> employees = employeeService.getEmployees();
		assertNotNull(employees);
		assertEquals(10, employees.size());
	}

	@Test
	void testLogWorkSuccess() throws Exception {
		int id = 1;
		int workDays = 5;
		List<Employee> updatedEmployees = new ArrayList<>(employees);
		updatedEmployees.get(0).logWork(workDays);
		Employee employee = updatedEmployees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
		assertNotNull(employee);
		assertEquals(15.5f + ((float) workDays / Employee.MAX_WORK_DAYS) * 10, employee.getVacationDays(), 0.01f);
	}

	@Test
	void testLogWork() throws Exception {
		int id = 1;
		int workDays = 20;
		List<Employee> employees = employeeService.logWork(id, workDays);
		Employee employee = employees.stream().filter(emp -> emp.getId() == id).findFirst().orElse(null);
		Assertions.assertNotNull(employee);
		Assertions.assertEquals(workDays, employee.getWorkDays());
	}

	@Test
	void testLogWorkInvalidId() throws Exception {
		int id = 100;
		int workDays = 20;
		Assertions.assertThrows(RuntimeException.class, () -> employeeService.logWork(id, workDays));
	}

	@Test
	void testLogWorkInvalidWorkDays() throws Exception {
		int id = 1;
		int workDays = 300;
		Assertions.assertThrows(RuntimeException.class, () -> employeeService.logWork(id, workDays));
	}

	@Test
	void testTakeVacationInvalidId() throws Exception {
		int id = 100;
		float vacations = 5.0f;
		Assertions.assertThrows(RuntimeException.class, () -> employeeService.takeVacation(id, vacations));
	}

	@Test
	void testTakeVacationInsufficientVacationDays() throws Exception {
		int id = 1;
		float vacations = 20.0f;
		Assertions.assertThrows(RuntimeException.class, () -> employeeService.takeVacation(id, vacations));
	}

	@Test
	void testGetEmployeeNotFound() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			employeeService.logWork(11, 5);
		});
	}

	@Test
	void testTakeVacationNotEnoughBalance() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			employeeService.takeVacation(1, 10.0f);
		});
	}

	@Test
	void testValidateWorkDaysLessThanOne() {
		Employee employee = new Employee();
		Assertions.assertThrows(RuntimeException.class, () -> {
			employee.validateWorkDays(-5);
		});
	}

	@Test
	void testValidateWorkDaysGreaterThanMax() {
		Employee employee = new Employee();
		Assertions.assertThrows(RuntimeException.class, () -> {
			employee.validateWorkDays(300);
		});
	}

	@Test
	void testLogWorkHourlyEmployee() throws Exception {
		int id = 1;
		int workDays = 5;
		List<Employee> employees = employeeService.logWork(id, workDays);
		Employee employee = employees.stream().filter(emp -> emp.getId() == id).findFirst().orElse(null);
		Assertions.assertNotNull(employee);
		Assertions.assertEquals(5, employee.getWorkDays());
	}

	@Test
	void testLogWorkSalariedEmployee() throws Exception {
		int id = 5;
		int workDays = 5;
		List<Employee> employees = employeeService.logWork(id, workDays);
		Employee employee = employees.stream().filter(emp -> emp.getId() == id).findFirst().orElse(null);
		Assertions.assertNotNull(employee);
		Assertions.assertEquals(5, employee.getWorkDays());
	}

}
