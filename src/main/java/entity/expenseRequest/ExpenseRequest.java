package entity.expenseRequest;

import entity.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class ExpenseRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;
    private int submitId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
    private String imageUrl;
    private String name;
    private String description;
    private Float amount;
    private String status;
    private Timestamp date;


    public ExpenseRequest() {
    }

    public ExpenseRequest(int id, int submitId, String imageUrl, String name, String description, Float amount, String status, Timestamp date) {
        this.id = id;
        this.submitId = submitId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubmitId() {
        return submitId;
    }

    public void setSubmitId(int submitId) {
        this.submitId = submitId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
