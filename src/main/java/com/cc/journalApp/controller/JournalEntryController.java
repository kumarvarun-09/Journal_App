package com.cc.journalApp.controller;

import com.cc.journalApp.dto.JournalDTO;
import com.cc.journalApp.exceptions.ResourceNotFoundException;
import com.cc.journalApp.models.JournalEntry;
import com.cc.journalApp.request.JournalRequest;
import com.cc.journalApp.service.IJournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/journals")
@RequiredArgsConstructor
public class JournalEntryController {
    private final IJournalService journalService;

    @GetMapping
    public ResponseEntity<?> getAllJournalsOfUser() {
        try {
            List<JournalDTO> journalEntries = new ArrayList<>();
            journalService.getAllJournalsForUser()
                    .forEach(journal -> {
                        journalEntries.add(new JournalDTO(journal));
                    });
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable(name = "id") Long journalId) {
        try {
            JournalDTO journalEntry = new JournalDTO(journalService.getJournalEntryById(journalId));
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addJournalEntry(@RequestBody JournalRequest journalEntry) {
        try {
            JournalDTO insertedEntry = new JournalDTO(
                    journalService.saveJournalEntry(journalEntry));
            return new ResponseEntity<>(insertedEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/byId/{id}")
    public ResponseEntity<?> updateJournalById(@PathVariable Long id, @RequestBody JournalRequest journalRequest) {
        try {
            JournalEntry journalEntry = new JournalEntry(journalRequest);
            journalEntry.setId(id);
            JournalDTO insertedEntry = new JournalDTO(journalService.updateJournalEntry(journalEntry));
            return new ResponseEntity<>(insertedEntry, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/byId/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable Long id) {
        try {
            journalService.deleteJournalEntryById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
