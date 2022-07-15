package ab.persistencelayer.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "employees")
public class Employee extends Person{

    private BigDecimal salary;

    @OneToMany(mappedBy = "employee")
    private List<Order> orders;

    public Employee(String name, String surname, BigDecimal salary, List<Order> orders) {
        super(name, surname);
        this.salary = salary;
        this.orders = orders;
    }

    public Employee() {
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
