import { EmployeeType } from './employee.enum';

export class Employee {
  id?: number;
  name?: string;
  type?: EmployeeType;
  vacationDays?: number;
  workDays?: number;
}
