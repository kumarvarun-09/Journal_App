package com.cc.journalApp.service;

import com.cc.journalApp.JournalAppApplication;
import com.cc.journalApp.models.JournalEntry;

import java.util.List;

public interface IJournalService {
    List<JournalEntry> getAllJournals() throws Exception;
    JournalEntry getJournalEntryById(Long id) throws Exception;
    JournalEntry saveJournalEntry(JournalEntry journalEntry) throws Exception;
    JournalEntry updateJournalEntry(JournalEntry journalEntry) throws Exception;
    JournalEntry deleteJournalEntryById(Long id);
}
