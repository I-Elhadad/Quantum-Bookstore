package model;
import java.time.LocalDate;
abstract public class Book {
    private String ISBN;
    private String title;
    private LocalDate date;
    private double price;

    public Book() {
        this.ISBN = "";
        this.title = "";
        this.date = LocalDate.now();
        this.price = 0.0;
    }

    public Book(String ISBN, String title, LocalDate date, double price) {
        if (ISBN == null || ISBN.isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.ISBN = ISBN;
        this.title = title;
        this.date = date;
        this.price = price;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public void setISBN(String ISBN) {
        if (ISBN == null || ISBN.isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        this.ISBN = ISBN;
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }

    public void setDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.date = date;
    }

    public boolean isOutdated() {
        // Assuming a book is outdated if it was published before 100 years ago
        int years = LocalDate.now().getYear() - date.getYear();
        return years > 100;
    }
}
