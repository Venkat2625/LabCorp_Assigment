package com.timesheet.employee;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.timesheet.employee.service.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeTest {

	private EmployeeServiceImpl employeeService;

	@BeforeEach
	void setUp() {
		employeeService = new EmployeeServiceImpl();
	}

	@BeforeEach
	public void setUp1() {
		employee = new Employee();
	}

	@Test
	public void TakeVacationTest() {
		float initialVacationDays = 10.0f;
		float vacationDaysToTake = 5.0f;
		employee.setVacationDays(initialVacationDays);
		employee.takeVacation(vacationDaysToTake);
		Assertions.assertEquals(initialVacationDays - vacationDaysToTake, employee.getVacationDays());
	}

	@Test
	void ValidateWorkDaysTest() {
		int id = 1;
		int workDays = 100;
		Employee employee = employeeService.getEmployees().stream().filter(emp -> emp.getId() == id).findFirst()
				.orElse(null);
		Assertions.assertNotNull(employee);
		employee.validateWorkDays(workDays);
		Assertions.assertEquals(workDays, employee.getWorkDays());
	}

	@Test
	void testValidateWorkDaysInvalid() {
		int id = 1;
		int workDays = 300;
		Employee employee = employeeService.getEmployees().stream().filter(emp -> emp.getId() == id).findFirst()
				.orElse(null);
		Assertions.assertNotNull(employee);
		Assertions.assertThrows(RuntimeException.class, () -> employee.validateWorkDays(workDays));
	}

	@Test
	void testLogWork() throws Exception {
		int id = 1;
		int workDays = 20;
		List<Employee> employees = employeeService.logWork(id, workDays);
		Employee employee = employees.stream().filter(emp -> emp.getId() == id).findFirst().orElse(null);
		Assertions.assertNotNull(employee);
	}

	@Test
	void testLogWorkInvalid() {
		int id = 1;
		int workDays = 300;
		Assertions.assertThrows(RuntimeException.class, () -> employeeService.logWork(id, workDays));
	}

	@Test
	void testTakeVacationInsufficientBalance() {
		int id = 1;
		float vacations = 10.0f;
		Assertions.assertThrows(RuntimeException.class, () -> employeeService.takeVacation(id, vacations));
	}

	private Employee employee;

	@Test
	public void testSetAndGetId() {
		int id = 1;
		employee.setId(id);
		Assertions.assertEquals(id, employee.getId());
	}

	@Test
	public void testSetAndGetName() {
		String name = "John";
		employee.setName(name);
		Assertions.assertEquals(name, employee.getName());
	}

	@Test
	public void testSetAndGetVacationDays() {
		float vacationDays = 5.0f;
		employee.setVacationDays(vacationDays);
		Assertions.assertEquals(vacationDays, employee.getVacationDays());
	}

	@Test
	public void testSetAndGetWorkDays() {
		int workDays = 100;
		employee.setWorkDays(workDays);
		Assertions.assertEquals(workDays, employee.getWorkDays());
	}

	@Test
	public void testSetAndGetEmployeeType() {
		EmployeeType type = EmployeeType.SALARIED;
		employee.setType(type);
		Assertions.assertEquals(type, employee.getType());
	}

	@Test
	public void testTakeVacationThrowsException() {
		float initialVacationDays = 5.0f;
		float vacationDaysToTake = 10.0f;
		employee.setVacationDays(initialVacationDays);
		Assertions.assertThrows(RuntimeException.class, () -> {
			employee.takeVacation(vacationDaysToTake);
		});
	}

	@Test
	public void testValidateWorkDays() {
		int initialWorkDays = 100;
		int numberOfDaysWorked = 10;
		employee.setWorkDays(initialWorkDays);
		employee.validateWorkDays(numberOfDaysWorked);
		Assertions.assertEquals(initialWorkDays + numberOfDaysWorked, employee.getWorkDays());
	}

	@Test
	public void testValidateWorkDaysThrowsException() {
		int initialWorkDays = 260;
		int numberOfDaysWorked = 1;
		employee.setWorkDays(initialWorkDays);
		Assertions.assertThrows(RuntimeException.class, () -> {
			employee.validateWorkDays(numberOfDaysWorked);
		});
	}

	@Test
	public void testLogWorkThrowsException() throws Exception {
		employee.setWorkDays(Employee.MAX_WORK_DAYS);
		Assertions.assertThrows(RuntimeException.class, () -> {
			employee.validateWorkDays(1);
		});
	}
}
