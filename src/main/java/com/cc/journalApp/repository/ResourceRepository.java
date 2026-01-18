package com.cc.journalApp.repository;

import com.cc.journalApp.models.Resources;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resources, String> {
    Optional<Resources> getValueByKey(String key);
}
