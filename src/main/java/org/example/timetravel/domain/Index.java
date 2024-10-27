package org.example.timetravel.domain;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Index implements Serializable {

    private List<Document> documents = new ArrayList<>();
    private Map<String, Set<Long>> textIndex = new HashMap<>();
    private Map<LocalDate, Set<Long>> dateIndex = new TreeMap<>();


    public Index() {
    }

    public Index(List<Document> documents, Map<String, Set<Long>> textIndex, Map<LocalDate, Set<Long>> dateIndex) {
        this.documents = documents;
        this.textIndex = textIndex;
        this.dateIndex = dateIndex;
    }

    public Index(String filePath) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Index loadedIndex = (Index) ois.readObject();
            this.documents = loadedIndex.documents;
            this.textIndex = loadedIndex.textIndex;
            this.dateIndex = loadedIndex.dateIndex;
        }
    }

    public void indexDocument(Document document) {
        documents.add(document);

        String[] words = document.getText().toLowerCase().split("\\W+");
        for (String word : words) {
            textIndex.computeIfAbsent(word, k -> new HashSet<>()).add(document.getId());
        }

        dateIndex.computeIfAbsent(document.getDate(), k -> new HashSet<>()).add(document.getId());
    }

    public void saveIndexToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
        }
    }

    public List<Document> search(Query query) {
        Set<Long> resultIds = new HashSet<>(documents.stream().map(Document::getId).collect(Collectors.toSet()));

        if (query.getText() != null) {
            String[] queryWords = query.getText().toLowerCase().split("\\W+");
            Set<Long> textResultIds = Arrays.stream(queryWords)
                    .filter(textIndex::containsKey)
                    .flatMap(word -> textIndex.get(word).stream())
                    .collect(Collectors.toSet());
            resultIds.retainAll(textResultIds);
        }

        if (query.getEndDate() != null && query.getDate() != null) {
            Set<Long> dateRangeResultIds = dateIndex.entrySet().stream()
                    .filter(entry -> !entry.getKey().isBefore(query.getDate()) && !entry.getKey().isAfter(query.getEndDate()))
                    .flatMap(entry -> entry.getValue().stream())
                    .collect(Collectors.toSet());
            resultIds.retainAll(dateRangeResultIds);
        } else if (query.getDate() != null) {
            Set<Long> exactDateResultIds = dateIndex.getOrDefault(query.getDate(), Collections.emptySet());
            resultIds.retainAll(exactDateResultIds);
        }

        return documents.stream()
                .filter(doc -> resultIds.contains(doc.getId()))
                .collect(Collectors.toList());
    }
}
