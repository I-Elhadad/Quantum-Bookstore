package model;
import java.time.LocalDate;

public class EBook extends Book {
   private String fileType;

    public EBook(String ISBN, String title, LocalDate date, double price, String fileType) {
        super(ISBN, title, date, price);
        if (fileType == null || fileType.isEmpty()) {
            throw new IllegalArgumentException("File type cannot be null or empty");
        }
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        if (fileType == null || fileType.isEmpty()) {
            throw new IllegalArgumentException("File type cannot be null or empty");
        }
        this.fileType = fileType;
    }

}
