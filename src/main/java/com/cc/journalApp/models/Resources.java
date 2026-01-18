package com.cc.journalApp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Resources {
    @Id
    private String key;
    @NonNull
    private String value;
}
