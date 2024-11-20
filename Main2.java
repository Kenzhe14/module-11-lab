import java.util.Date;

class Book{
    String Title;
    String Author;
    int ISBN;
    boolean IsAviable;

    public void MarkAsLoaned(){
        System.out.println("Is not Aviable " + Title);
        IsAviable = false;
    }

    public void MarkAsAvailable() {
        System.out.println("Is Aviable " + Title);
        IsAviable = true;
    }
}
class Reader{
    int Id;
    String Name;
    String Email;
    Book book;

    public void BorrowBook(){
        System.out.println(Name + " is borrow book " + book.Title);
        book.IsAviable = false;
    }
    public void ReturnBook(){
        System.out.println(Name + " is return book " + book.Title);
        book.IsAviable = true;
    }

}
class Librarian{
    int Id;
    String Name;
    String Position;
    Book book;

    public void AddBook(){
        System.out.println(Name + " in position " + Position + " add a book " + book.Title);
        book.IsAviable = true;
    }
    public void RemoveBook(){
        System.out.println(Name + " in position " + Position + " remove a book " + book.Title);
        book.IsAviable = false;
    }
}
class Loan{
    Book book;
    Reader reader;
    Date LoanDate;
    Date ReturnDate;

    public void IssueLoan(){
        System.out.println(book.Title + " is go to " + reader.Name + " by loan to this date " + LoanDate + " by this date " + ReturnDate);
    }
    public void CompleteLoan(){
        System.out.println(book.Title + " is return by " + reader.Name + " loan on this date " + LoanDate + " to this date " + ReturnDate);
    }
}
public class Main2{
    public static void main(String[] args) {
        Book lev = new Book();
        lev.Title = "Voina i mir ";
        lev.Author = "Lev Tolstoi";
        lev.ISBN = 1;

        Reader nari = new Reader();
        nari.Name = "Nariman";
        nari.Id = 1;
        nari.Email = "narikenzhe@gmail.com ";

        Librarian maks = new Librarian();
        maks.Name = "Maks";
        maks.Position = "main";
        maks.Id = 1;

        Loan loan1 = new Loan();
        loan1.reader = nari;
        loan1.book = lev;
        loan1.LoanDate = new Date();
        loan1.ReturnDate = new Date();

        loan1.IssueLoan();
        loan1.CompleteLoan();

    }
}
