package org.example.timetravel.domain;


import java.io.Serializable;
import java.time.LocalDate;

public class Document implements Serializable {
    private Long id;
    private String text;
    private LocalDate date;

    public Document(Long id, String text, LocalDate date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDate getDate() {
        return date;
    }
}
