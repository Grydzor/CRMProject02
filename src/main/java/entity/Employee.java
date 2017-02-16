package entity;

import com.sun.istack.internal.NotNull;
import enum_types.Position;
import enum_types.Sex;

import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEES")
public class Employee {

    @Id
    @Column(name = "EMPLOYEE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "SEX")
    @Enumerated
    private Sex sex;

    @Column(name = "POSITION")
    @Enumerated
    private Position position;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Employee() {}

    public Employee(String name, String surname, Integer age, Sex sex, Position position) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.sex = sex;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", position=" + position +
                '}';
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!name.equals(employee.name)) return false;
        if (!surname.equals(employee.surname)) return false;
        if (age != null ? !age.equals(employee.age) : employee.age != null) return false;

        return sex == employee.sex
                && position == employee.position;
    }*/
}
