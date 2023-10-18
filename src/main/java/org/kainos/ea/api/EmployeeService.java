package org.kainos.ea.api;

import org.kainos.ea.cli.Contractor;
import org.kainos.ea.cli.Employee;
import org.kainos.ea.cli.IPayable;
import org.kainos.ea.cli.SalesEmployee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    public List<IPayable> getEmployees() {
        Employee e = new Employee(1,"Stephen", 2000000);
        SalesEmployee se = new SalesEmployee(1, "Stephen", 200000, 1000, 0.01f);
        Contractor c = new Contractor("Stephen", 1000, 10);

        List<IPayable> employees = new ArrayList<>();
        employees.add(e);
        employees.add(se);
        employees.add(c);

        for (IPayable employee: employees) {
            System.out.println(employee.calcPay());
        }

        return employees;
    }
}
