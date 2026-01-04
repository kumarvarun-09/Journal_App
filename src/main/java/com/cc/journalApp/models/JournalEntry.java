package com.cc.journalApp.models;

import com.cc.journalApp.request.JournalRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime timestamp;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public JournalEntry(JournalRequest journalRequest) {
        if (journalRequest.getTitle() != null) {
            this.title = journalRequest.getTitle().trim();
        }
        if (journalRequest.getContent() != null) {
            this.content = journalRequest.getContent().trim();
        }
    }
}
