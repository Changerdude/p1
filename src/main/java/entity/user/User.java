package entity.user;

import entity.expenseRequest.ExpenseRequest;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)

    private int id;

    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private boolean isManager;
    @OneToMany(mappedBy = "submitId")
    private List<ExpenseRequest> expenseRequests;

    public User() {
    }

    public User(int id, String name, String email, String password, boolean isManager) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isManager = isManager;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword(){return password;}

    public boolean checkPassword(String attempt) {
        return Objects.equals(attempt, password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public List<ExpenseRequest> getExpenseRequests() {
        return expenseRequests;
    }

    public void setExpenseRequests(List<ExpenseRequest> expenseRequests) {
        this.expenseRequests = expenseRequests;
    }
}
