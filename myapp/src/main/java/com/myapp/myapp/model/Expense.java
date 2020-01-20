package com.myapp.myapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double amount;
    private String note;
    //TODO
    //Zastawiam się nie zmienić tego pola na Integer padiById
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User paidBy;
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "expense",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> paidFor = new HashSet<>();


}
