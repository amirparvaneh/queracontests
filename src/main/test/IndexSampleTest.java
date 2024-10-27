import org.example.timetravel.domain.Document;
import org.example.timetravel.domain.Index;
import org.example.timetravel.domain.Query;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class IndexSampleTest {
    @Test
    public void testSearchReturnsExpectedDocuments() {
        Index index = new Index();
        index.indexDocument(new Document(1l, "Quera online coding contests", LocalDate.now()));
        index.indexDocument(new Document(2l, "Quera for programmers", LocalDate.now().minusDays(4)));
        index.indexDocument(new Document(3l, "Practice coding skills", LocalDate.now().minusMonths(11)));

        Query query = new Query("Quera", null, null);

        List<Document> result = index.search(query);
        assertEquals(2, result.size());

        List<Long> resultIds = result.stream().map(Document::getId).collect(Collectors.toList());
        assertTrue(resultIds.contains(1L));
        assertTrue(resultIds.contains(2L));
    }

    @Test
    public void testSearchReturnsNoResultsForUnknownQuery() {
        Index index = new Index();
        index.indexDocument(new Document(1l, "Quera online coding contests", LocalDate.now()));
        index.indexDocument(new Document(2l, "Quera for programmers", LocalDate.now().minusDays(4)));

        Query query = new Query("Not Exist", null, null);

        List<Document> result = index.search(query);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchIsCaseInsensitive() {
        Index index = new Index();
        index.indexDocument(new Document(1l, "Quera online coding contests", LocalDate.now()));
        index.indexDocument(new Document(3l, "Practice coding skills", LocalDate.now().minusMonths(11)));

        Query query = new Query("quera", null, null);

        List<Document> result = index.search(query);
        assertEquals(1, result.size());
        assertEquals(1l, result.get(0).getId().longValue());
    }
}
