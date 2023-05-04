package com.timesheet.employee;

import java.text.DecimalFormat;

public class Employee {
	public static final int MAX_WORK_DAYS = 260;
	public static final int MIN_WORK_DAYS = 1;

	private int id;
	private String name;
	private float vacationDays;
	private int workDays;
	private EmployeeType type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getVacationDays() {
		return vacationDays;
	}

	public void setVacationDays(float vacationDays) {
		this.vacationDays = vacationDays;
	}

	public int getWorkDays() {
		return workDays;
	}

	public void setWorkDays(int workDays) {
		this.workDays = workDays;
	}

	public EmployeeType getType() {
		return type;
	}

	public void setType(EmployeeType type) {
		this.type = type;
	}

	/**
	 * 
	 * This method updates the vacation days. If the number of vacation days requested is greater
	 * than the employee's current vacation balance, a RuntimeException is thrown
	 * 
	 * @param days The number of vacation days to be taken.
	 * @throws RuntimeException if the number of vacation days requested is greater
	 *                          than the employee's current vacation balance.
	 */
	public void takeVacation(float days) throws RuntimeException {
		if (days > vacationDays) {
			throw new RuntimeException("Vacations balance is not enough to take vacations.");
		}
		float vacations = this.vacationDays - days;
		this.setVacationDays(vacations);
		DecimalFormat df = new DecimalFormat(".00");
		this.setVacationDays(Float.valueOf(df.format(this.getVacationDays())));
	}

	/**
	 * 
	 * This method adds the specified number of days worked to the employee's
	 * current work days count.
	 * 
	 * @param numberOfDaysWorked The number of days worked to be added to the
	 *                           employee's work days count.
	 * @throws RuntimeException if the updated work days count is less than 1 or
	 *                          greater than 260.
	 */
	public void validateWorkDays(int numberOfDaysWorked) throws RuntimeException {
		this.workDays += numberOfDaysWorked;
		if (this.workDays < MIN_WORK_DAYS || this.workDays > MAX_WORK_DAYS) {
			this.workDays -= numberOfDaysWorked;
			throw new RuntimeException("Work days can not be less than 1 or greater than 260 days.");
		}
	}

	/**
	 * 
	 * This method logs the number of days worked by the employee and updates their
	 * vacation days balance accordingly.
	 * 
	 * @param numberOfDaysWorked The number of days worked by the employee to be
	 *                           logged.
	 * @throws Exception if the validateWorkDays method throws an Exception.
	 */
	public void logWork(int numberOfDaysWorked) throws Exception {
		validateWorkDays(numberOfDaysWorked);
		int count = 0;
		if (this.type == EmployeeType.HOURLY) {
			count = 10;
		} else if (this.type == EmployeeType.SALARIED) {
			count = 15;
		} else if (this.type == EmployeeType.MANAGER) {
			count = 30;
		}
		float vacations = (Float.valueOf(numberOfDaysWorked) / MAX_WORK_DAYS) * count;
		this.setVacationDays(this.getVacationDays() + vacations);
		DecimalFormat df = new DecimalFormat(".00");
		this.setVacationDays(Float.valueOf(df.format(this.getVacationDays())));
	}

}