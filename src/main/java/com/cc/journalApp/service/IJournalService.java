package com.cc.journalApp.service;

import com.cc.journalApp.JournalAppApplication;
import com.cc.journalApp.models.JournalEntry;
import com.cc.journalApp.request.JournalRequest;

import java.util.List;

public interface IJournalService {
    List<JournalEntry> getAllJournalsForUser(String userName) throws Exception;
    JournalEntry getJournalEntryById(Long id) throws Exception;
    JournalEntry saveJournalEntry(String userName, JournalRequest journalRequest) throws Exception;
    JournalEntry updateJournalEntry(JournalEntry journalEntry) throws Exception;
    JournalEntry deleteJournalEntryById(Long id);
}
