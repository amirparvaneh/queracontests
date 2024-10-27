package org.example.timetravel.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Query implements Serializable {
    private String text;
    private LocalDate date;
    private LocalDate endDate;

    public Query(String text, LocalDate date, LocalDate endDate) {
        this.text = text;
        this.date = date;
        this.endDate = endDate;
    }

    public String getText() {
        return text;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
