package org.kainos.ea.cli;

public class SalesEmployee extends Employee {
    private double monthlySales;
    private float commissionRate;

    public SalesEmployee(int employeeId, String name, double salary, double monthlySales, float commissionRate) {
        super(employeeId, name, salary);
        this.setMonthlySales(monthlySales);
        this.setCommissionRate(commissionRate);
    }

    @Override
    public double calcPay() {
        return this.getSalary()/12 + (getMonthlySales()*getCommissionRate());
    }

    public double getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(double monthlySales) {
        this.monthlySales = monthlySales;
    }

    public float getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(float commissionRate) {
        this.commissionRate = commissionRate;
    }
}
