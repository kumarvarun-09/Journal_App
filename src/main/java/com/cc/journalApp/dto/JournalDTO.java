package com.cc.journalApp.dto;

import com.cc.journalApp.models.JournalEntry;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JournalDTO {
    private long journalNumber;
    private String title;
    private String content;
    private LocalDateTime timestamp;
    private Long userId;

    public JournalDTO(JournalEntry journalEntry) {
        this.journalNumber = journalEntry.getJournalNumber();
        this.title = journalEntry.getTitle();
        this.content = journalEntry.getContent();
        this.timestamp = journalEntry.getTimestamp();
        if (journalEntry.getUser() != null) {
            this.userId = journalEntry.getUser().getId();
        }
    }
}
