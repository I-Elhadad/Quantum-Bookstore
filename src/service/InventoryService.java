package service;
import model.*;
import java.util.HashMap;
import java.util.Vector;

public class InventoryService {
    private HashMap<String, Book> stock = null;
    private static InventoryService inventory = null;

    private InventoryService() {
        stock = new HashMap<>();

    }

    public static InventoryService getInventory() {
        if (inventory == null)
            inventory = new InventoryService();
        return inventory;
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (stock.containsKey(book.getISBN())) {
            throw new IllegalArgumentException("Book with ISBN " + book.getISBN() + " already exists.");
        }
        stock.put(book.getISBN(), book);
    }

    public void removeBook(String ISBN) {
        if (ISBN == null || ISBN.isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        if (!stock.containsKey(ISBN)) {
            throw new IllegalArgumentException("Book with ISBN " + ISBN + " does not exist.");
        }
        stock.remove(ISBN);
    }

    public Book getBook(String ISBN) {
        return stock.get(ISBN);
    }

    public Vector<Book> removeOutdatedBooks() {
        Vector<String> outdatedBooks = new Vector<>();
        Vector<Book> removedBooks = new Vector<>();
        for (String ISBN : stock.keySet()) {
            Book book = stock.get(ISBN);
            if (book.isOutdated()) {
                outdatedBooks.add(ISBN);
                removedBooks.add(book);
            }
        }
        for (String ISBN : outdatedBooks) {
            removeBook(ISBN);
        }
        return removedBooks;
    }

    public void clearInventory() {
        stock.clear();
    }

}
