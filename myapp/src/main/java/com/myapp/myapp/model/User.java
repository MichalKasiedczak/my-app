package com.myapp.myapp.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String login;
    private String password;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @ManyToMany(fetch = FetchType.EAGER,
            mappedBy = "users",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private Set<Role> roles = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER,
            mappedBy = "users",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private Set<Crew> crews = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "paidBy",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private Set<Expense> expenses = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private Expense expense;


}
