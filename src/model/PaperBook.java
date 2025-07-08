package model;
import java.time.LocalDate;

public class PaperBook extends Book {
    int quantity;

    public PaperBook(String ISBN, String title, LocalDate date, double price, int quantity) {
        super(ISBN, title, date, price);
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }
}
