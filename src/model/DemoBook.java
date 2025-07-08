package model;
import java.time.LocalDate;

public class DemoBook extends Book {
    public DemoBook(String ISBN, String title, LocalDate date, double price) {
        super(ISBN, title, date, price);
    }
}
