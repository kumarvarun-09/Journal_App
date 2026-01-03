package com.cc.journalApp.service;

import com.cc.journalApp.exceptions.ResourceNotFoundException;
import com.cc.journalApp.models.JournalEntry;
import com.cc.journalApp.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalService implements IJournalService {

    private final JournalRepository journalRepository;

    @Override
    public List<JournalEntry> getAllJournals() throws Exception {
        try {
            return journalRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public JournalEntry getJournalEntryById(Long id) throws Exception {
        try {
            JournalEntry journalEntry = journalRepository.findById(id).orElse(null);
            if (journalEntry != null) {
                return journalEntry;
            }
            throw new ResourceNotFoundException("Journal Entry with id " + id + " was not found");
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public JournalEntry saveJournalEntry(JournalEntry journalEntry) throws Exception {
        try {
            journalEntry.setTimestamp(LocalDateTime.now());
            return journalRepository.save(journalEntry);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public JournalEntry updateJournalEntry(JournalEntry requestBody) throws Exception {
        try {
            Long id = requestBody.getId();
            JournalEntry journalEntry = journalRepository.findById(id).orElse(null);
            if (journalEntry != null) {
                String title = requestBody.getTitle();
                String content = requestBody.getContent();
                if (title != null && !title.isEmpty()) {
                    journalEntry.setTitle(title);
                }
                if (content != null && !content.isEmpty()) {
                    journalEntry.setContent(content);
                }
                journalEntry.setTimestamp(LocalDateTime.now());
                return journalRepository.save(journalEntry);
            }
            throw new ResourceNotFoundException("Journal Entry with id " + id + " was not found");
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public JournalEntry deleteJournalEntryById(Long id) {
        try {
            JournalEntry journalEntry = journalRepository.findById(id).orElse(null);
            if (journalEntry != null) {
                journalRepository.deleteById(id);
                return journalEntry;
            }
            throw new ResourceNotFoundException("Journal Entry with id " + id + " was not found");
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }
}
