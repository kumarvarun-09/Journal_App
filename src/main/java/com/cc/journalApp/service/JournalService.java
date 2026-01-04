package com.cc.journalApp.service;

import com.cc.journalApp.exceptions.ResourceNotFoundException;
import com.cc.journalApp.models.JournalEntry;
import com.cc.journalApp.models.User;
import com.cc.journalApp.repository.JournalRepository;
import com.cc.journalApp.request.JournalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalService implements IJournalService {

    private final JournalRepository journalRepository;
    private final IUserService userService;

    @Override
    public List<JournalEntry> getAllJournalsForUser(String userName) throws Exception {
        try {
            userName = userName.trim();
            User user = userService.getUserByUserName(userName);
            return journalRepository.findByUserId(user.getId());
        } catch (ResourceNotFoundException e) {
            throw e;
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
    public JournalEntry saveJournalEntry(String userName, JournalRequest journalRequest) throws Exception {
        try {
            User user = userService.getUserByUserName(userName);
            JournalEntry journalEntry = new JournalEntry(journalRequest);
            journalEntry.setUser(user);
            journalEntry.setTimestamp(LocalDateTime.now());
            return journalRepository.save(journalEntry);
        } catch (ResourceNotFoundException e) {
            throw e;
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
                    journalEntry.setTitle(title.trim());
                }
                if (content != null && !content.isEmpty()) {
                    journalEntry.setContent(content.trim());
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
    public void deleteJournalEntryById(Long id) {
        try {
            JournalEntry journalEntry = journalRepository.findById(id).orElse(null);
            if (journalEntry != null) {
                journalRepository.deleteById(id);
            }
            throw new ResourceNotFoundException("Journal Entry with id " + id + " was not found");
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }
}
