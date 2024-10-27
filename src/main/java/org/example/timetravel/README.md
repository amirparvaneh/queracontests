# Basic Full-Text Search System

This project implements a basic full-text search system in Java, simulating a prototype search engine similar to early database technology. It includes three main classes: `Document`, `Query`, and `Index`, with functionalities to index documents and retrieve them based on specified search criteria.

## Project Structure

- **`Document`**: Represents individual documents with unique IDs, textual content, and dates.
- **`Query`**: Defines search criteria, including optional text, specific date, or date range.
- **`Index`**: Manages the documents, indexing by text and date, and retrieves documents based on a query.

### Classes

1. **Document**
    - Properties: `id`, `text`, and `date`.
    - Serializable for saving and loading.
    - Includes getter methods for each property.

2. **Query**
    - Properties: `text`, `date`, and `endDate`.
    - Allows defining search criteria that can include specific text, exact date, or date range.
    - Includes getter methods for each property.

3. **Index**
    - Stores documents in a list and maintains two indexes:
        - **Text Index**: Maps words to sets of document IDs for fast text-based search.
        - **Date Index**: Maps dates to sets of document IDs for efficient date-based filtering.
    - Key Methods:
        - **`indexDocument(Document document)`**: Adds a document to both the text and date indexes.
        - **`saveIndexToFile(String filePath)`**: Saves the entire index to a file.
        - **`search(Query query)`**: Retrieves documents based on a query, prioritizing text, date range, and exact date in that order.

