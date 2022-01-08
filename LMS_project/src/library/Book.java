package library;
import java.sql.*;

public class Book {
	
	public BookLendingInfo bookLendingInfoObj=new BookLendingInfo();
	public Book() {
		
		
	}
	
	/**
	 * This method checks if book already exist in the table by bookId
	 * 
	 * @param conn - Connection object for "Library" database
	 * @param bookId - bookId(int) entered by the user 
	 * @return - boolean value
	 */
	public boolean bookExist(Connection conn,int bookId) {
		try {
			PreparedStatement st=conn.prepareStatement("select count(*) from book where book_id=?");
			st.setInt(1, bookId);
			ResultSet rs=st.executeQuery();
			int countBook=0;
			while(rs.next()) {
				countBook=rs.getInt(1);
			}
			if(countBook>0)
				return true;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("In bookExist()");
			
		}
		return false;
	}
	
	/**
	 * This method checks if book already exist in the table by bookName
	 * 
	 * @param conn - Connection object for "Library" database
	 * @param bookName - bookName(string) entered by user
	 * @return - boolean value 
	 */
	public boolean bookExist(Connection conn,String bookName) {
		try {
			PreparedStatement st=conn.prepareStatement("select count(*) from book where book_name=?");
			st.setString(1, bookName);
			ResultSet rs=st.executeQuery();
			int countBook=0;
			while(rs.next()) {
				countBook=rs.getInt(1);
			}
			if(countBook>0)
				return true;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("In bookExist()");
			
		}
		return false;
	}
	
	/**
	 * This method checks the quantity of book in "book" table by bookName
	 * 
	 * @param conn - Connection object for "Library" database
	 * @param bookName - bookName(string) entered by user
	 * @return - quantity(int value) 
	 */
	public int checkQuantity(Connection conn, String bookName) {
		int quantity=0;
		try {
			PreparedStatement st=conn.prepareStatement("select quantity from book where book_name=?");
			st.setString(1,bookName);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				quantity=rs.getInt(1);
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println("In checkQuantity()");
			
		}
		return quantity;
	}
	
	/**
	 * This method checks the quantity of book in "book" table by bookName and author
	 * 
	 * @param conn - Connection object for "Library" database
	 * @param bookName - bookName(string) entered by user
	 * @param author - author(string) entered by user
	 * @return - quantity(int value)
	 */
	public int checkQuantity(Connection conn, String bookName, String author) {
		int quantity=0;
		try {
			PreparedStatement st=conn.prepareStatement("select quantity from book where book_name=? AND author=?");
			st.setString(1,bookName);
			st.setString(2, author);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				quantity=rs.getInt(1);
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println("In checkQuantity()");
			
		}
		return quantity;
	}
	
	
	/**
	 * This method checks if two or more books of same name exist in "book" table
	 * 
	 * @param conn - Connection object for "Library" database
	 * @param bookName - bookName(string) entered by user
	 * @return bookCount(int value)
	 */
	public int checkDuplicateBookName(Connection conn, String bookName) {
		int bookCount=0;
		try {
			PreparedStatement st=conn.prepareStatement("select count(book_name) from book where book_name=?");
			st.setString(1, bookName);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				bookCount=rs.getInt(1);
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("In checkDuplicateBookName()");
			
		}
		return bookCount;
	}
	
	/**
	 * This method updates the quantity of the book in "book" table by bookName
	 * 
	 * @param conn - Connection object for "Library" database
	 * @param bookName - bookName(string) entered by user
	 */
	public void addBook(Connection conn,String bookName) {
		try {
			PreparedStatement st=conn.prepareStatement("update book set quantity=quantity+1 where book_name=?");
			st.setString(1, bookName);
			int rows=st.executeUpdate();  
			System.out.println(rows+" records updated");
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("In addBook(1)");
			
		}
	}

	/**
	 * This method updates the quantity of the book in "book" table by bookName and author
	 * 
	 * @param conn - Connection object for "Library" database
	 * @param bookName - bookName(string) entered by user
	 * @param author - author(string) entered by user
	 */
	public void addBook(Connection conn,String bookName,String author) {
		try {
			PreparedStatement st=conn.prepareStatement("update book set quantity=quantity+1 where book_name=? AND author=?");
			st.setString(1, bookName);
			st.setString(2, author);
			int rows=st.executeUpdate();  
			System.out.println(rows+" records updated");
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("In addBook(2)");
			
		}
	}
	
	/**
	 * This methods adds a new book to the "book" table
	 * 
	 * @param conn - Connection object for "Library" database
	 * @param bookName - bookName(string) entered by user
	 * @param author - author(string) entered by user
	 * @param genre - genre(string) entered by user
	 * @param isbn - isbn(string) entered by user
	 */
	public void addBook(Connection conn,String bookName, String author, String genre, String isbn) {
		try{
			PreparedStatement st=conn.prepareStatement("insert into book(book_name,author,genre,isbn,quantity) values(?,?,?,?,?)");
			st.setString(1,bookName);
			st.setString(2,author);
			st.setString(3,genre);
			st.setString(4,isbn);
			st.setInt(5,1);
			
			int rows=st.executeUpdate();  
			System.out.println(rows+" book added successfully"); 
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("In addBook(5)");
			
		}
		
	}
	
	/**
	 * This method searches the keyword in "book" table
	 * 
	 * @param conn - Connection object for "Library" database
	 * @param keyword - keyword(string) to search from the table
	 */
	public void searchBook(Connection conn, String keyword) {
		try {
			String searchQuery="select * from book where book_name like '%"+keyword+"%' OR author like '%"+keyword+"%' OR genre like '%"+keyword+"%' OR isbn like '%"+keyword+"%'";
			String format = "| %-5s| %-30s| %-30s| %-30s| %-20s| %-10s|";
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery(searchQuery);
			
			if(rs.next()==false) {
				System.out.println("\nNo result found");
			}
			else {
				System.out.println("\nSearch Results:");
				System.out.println(String.format(format, "ID  ", "Book Name", "Author Name", "Genre", "ISBN Number","Quantity"));
				do {
					System.out.println(String.format(format, rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getInt(6)));
				}while(rs.next());
			}
			
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("In searchBook()");
			
		}
	}
	
	/**
	 * This method displays the details of a book from "book" table by BookId 
	 * @param conn - Connection object for "Library" database
	 * @param bookId - bookId(int) entered by book 
	 */
	public void viewBookDetails(Connection conn,int bookId) {
		try {
			int quantity=0;
			String bookName="", author="", genre="", isbn="", status="", lendedBy="", dateOfReturn="";
			PreparedStatement st=conn.prepareStatement("select book_name, author, genre, isbn,quantity from book where book_id=?;");
			st.setInt(1, bookId);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				bookName=rs.getString(1);
				author=rs.getString(2);
				genre=rs.getString(3);
				isbn=rs.getString(4);
				quantity=rs.getInt(5);
			}
			
			System.out.println("Book ID: "+bookId);
			System.out.println("Book Name: "+bookName);
			System.out.println("Author: "+author);
			System.out.println("Genre: "+genre);
			System.out.println("ISBN: "+isbn);
			
			if(quantity>0) {
				status="Available";
				System.out.println("Status: "+status);
			}
			else {
				status="Unavailable";
				lendedBy=bookLendingInfoObj.getLender(conn,bookId);
				dateOfReturn=bookLendingInfoObj.getReturnDate(conn, bookId);
				System.out.println("Status: "+status);
				System.out.println("Lended By: "+lendedBy);
				System.out.println("Expected Date of Return: "+dateOfReturn);
			}
			
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("In viewBookDetails()");
			
		}
		
	}
	
	/**
	 * This method displays total count of books by genre 
	 * 
	 * @param conn - Connection object for "Library" database
	 */
	public void countBooksByGenre(Connection conn) {
		try {
			String format = "| %-30s| %-15s|";
			PreparedStatement st=conn.prepareStatement("select genre, count(*) from book group by genre");
			ResultSet rs=st.executeQuery();
			System.out.println(String.format(format,"Genre ","Total Count"));
			while(rs.next()) {
				System.out.println(String.format(format,rs.getString(1),rs.getInt(2)));
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("In countBooksByGenre()");
			
		}
	}
	
	/**
	 * This method displays details of all the books in "book" table
	 * 
	 * @param conn - Connection object for "Library" database
	 */
	public void allBookDetails(Connection conn) {
		try {
			String format="| %-5s| %-30s| %-30s| %-30s| %-20s| %-10s|";
			PreparedStatement st=conn.prepareStatement("select * from book");
			ResultSet rs=st.executeQuery();
			System.out.println(String.format(format, "ID  ", "Book Name", "Author Name", "Genre", "ISBN Number","Quantity"));
			while(rs.next()) {
				System.out.println(String.format(format, rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getInt(6)));
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("In allBookDetails()");
			
		}
	}
}
