package com.polynomeer.hashcode;

public class Company {
    public String name;
    public int employees;
    public double salary;

    public Company(String name, int employees, double salary) {
        this.name = name;
        this.employees = employees;
        this.salary = salary;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Company)) return false;
        final Company other = (Company) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.name;
        final Object other$name = other.name;
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        if (this.employees != other.employees) return false;
        if (Double.compare(this.salary, other.salary) != 0) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Company;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.name;
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        result = result * PRIME + this.employees;
        final long $salary = Double.doubleToLongBits(this.salary);
        result = result * PRIME + (int) ($salary >>> 32 ^ $salary);
        return result;
    }
}
