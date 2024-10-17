package com.example.issuems.Domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "IssueBook_data")
public class Issue {

    @Id
    private String isbn; //isbn as primary key
    private String custId; //customer ID
    private Integer noOfCopies;

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setCustId(String custId) {
        this.custId = custId;
    }
    public String getCustId() {
        return custId;
    }
    public void setNoOfCopies(Integer noOfCopies) {
        this.noOfCopies = noOfCopies;
    }
    public Integer getNoOfCopies() {
        return noOfCopies;
    }
}
