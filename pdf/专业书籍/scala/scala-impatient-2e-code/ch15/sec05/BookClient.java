import java.io.*;

public class BookClient { // This is Java
   public static void main(String[] args) {
      Book book = new Book();
      try {
         book.read("war-and-peace.txt");
         System.out.println(book.text().substring(0, 100));
      } catch(IOException ex) {
         System.out.println("Can't open file");
      }
   }
}
