package com.cc.journalApp.repository;

import com.cc.journalApp.models.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByUserId(Long id);
}
