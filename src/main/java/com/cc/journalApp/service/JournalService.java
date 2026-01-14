package com.cc.journalApp.service;

import com.cc.journalApp.exceptions.ResourceNotFoundException;
import com.cc.journalApp.models.JournalEntry;
import com.cc.journalApp.models.User;
import com.cc.journalApp.repository.JournalRepository;
import com.cc.journalApp.request.JournalRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JournalService implements IJournalService {

    private final JournalRepository journalRepository;
    private final IUserService userService;

    @Override
    public List<JournalEntry> getAllJournalsForUser() throws Exception {
        final String METHOD_NAME = "getAllJournalsForUser";
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            log.info("{}, userName = {}", METHOD_NAME, userName);
            User user = userService.getUserByUserName(userName);
            log.debug("{}, Got user from DB {}", METHOD_NAME, user);
            return journalRepository.findByUserId(user.getId());
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JournalEntry getJournalEntryById(Long id) throws Exception {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByUserName(userName);
            JournalEntry journalEntry = journalRepository.findByUserIdAndJournalNumber(user.getId(), id)
                    .orElse(null);
            if (journalEntry != null) {
                return journalEntry;
            }
            throw new ResourceNotFoundException("Journal Entry with id " + id + " was not found");
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public JournalEntry saveJournalEntry(JournalRequest journalRequest) throws Exception {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByUserName(userName);
            Long journalNumber = journalRepository.findTopByUserIdOrderByJournalNumberDesc(user.getId())
                    .map(j -> (j.getJournalNumber() + 1))
                    .orElse(1L);
            JournalEntry journalEntry = new JournalEntry(journalRequest);
            journalEntry.setUser(user);
            journalEntry.setJournalNumber(journalNumber);
            journalEntry.setTimestamp(LocalDateTime.now());
            return journalRepository.save(journalEntry);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public JournalEntry updateJournalEntry(JournalEntry requestBody) throws Exception {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByUserName(userName);
            Long id = requestBody.getId();
            JournalEntry journalEntry = journalRepository.findByUserIdAndJournalNumber(user.getId(), id)
                    .orElse(null);
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
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void deleteJournalEntryById(Long id) {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getUserByUserName(userName);
            JournalEntry journalEntry = journalRepository.findByUserIdAndJournalNumber(user.getId(), id)
                    .orElse(null);
            if (journalEntry != null) {
                journalRepository.deleteById(id);
            } else {
                throw new ResourceNotFoundException("Journal Entry with id " + id + " was not found");
            }
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
