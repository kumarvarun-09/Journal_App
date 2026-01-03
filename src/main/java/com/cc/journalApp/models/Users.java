package com.cc.journalApp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<JournalEntry> journalEntries;
}
