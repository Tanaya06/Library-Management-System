package library;
//import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
//import java.text.SimpleDateFormat;
//import java.util.logging.Level;
//import java.util.logging.Logger;

public class BookLendingInfo {
	//public static final Logger logger = Logger.getLogger(BookLendingInfo.class);
	public BookLendingInfo() {
		
	}
	
	/**
	 * This method is used add book lending details in "book_lending_info" table
	 * 
	 * @param conn - Connection object for "Library" database
	 * @param userName - userName(string) entered by user
	 * @param bookName - bookName(string) entered by user
	 * @param author - author(string) entered by user
	 */
	public void borrowBook(Connection conn, String userName, String bookName, String author) {
		try {
			PreparedStatement st1=conn.prepareStatement("insert into book_lending_info (user_id,book_id,date_of_return) select u.user_id, b.book_id, date_add(curdate(), INTERVAL 7 day) from user u, book b where u.user_name =? and b.book_name=? and b.author=?");
			st1.setString(1, userName);
			st1.setString(2, bookName);
			st1.setString(3, author);
			int rows1=st1.executeUpdate();
			System.out.println(rows1+" book lended successfully");
			
			PreparedStatement st2=conn.prepareStatement("update book set quantity=quantity-1 where book_name=? and author=?");
			st2.setString(1, bookName);
			st2.setString(2, author);
			int rows2=st2.executeUpdate();
			System.out.println(rows2+" book quantity updated");
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("in borrowBook()");
			//logger.fatal(e.getMessage());
		}
	}
	
	/**
	 * This method is used add book lending details in "book_lending_info" table
	 * 
	 * @param conn - Connection object for "Library" database
	 * @param userName - userName(string) entered by user
	 * @param bookName - bookName(string) entered by user
	 */
	public void borrowBook(Connection conn, String userName, String bookName) {
		try {
			PreparedStatement st1=conn.prepareStatement("insert into book_lending_info (user_id,book_id,date_of_return) select u.user_id, b.book_id, date_add(curdate(), INTERVAL 7 day) from user u, book b where u.user_name =? and b.book_name=?");
			st1.setString(1, userName);
			st1.setString(2, bookName);
			int rows1=st1.executeUpdate();
			System.out.println(rows1+" book lended successfully");
			
			PreparedStatement st2=conn.prepareStatement("update book set quantity=quantity-1 where book_name=?");
			st2.setString(1, bookName);
			int rows2=st2.executeUpdate();
			System.out.println(rows2+" book quantity updated");
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("in borrowBook()");
			//logger.fatal(e.getMessage());
		}
	}
	
	/**
	 * This method returns the lender of the book by bookId
	 * 
	 * @param conn - Connection object for "Library" database
	 * @param bookId - bookId(int) entered by user
	 * @return - userName(string value)
	 */
	public String getLender(Connection conn, int bookId) {
		String userName="";
		try {
			PreparedStatement st=conn.prepareStatement("select user_name from user where user_id= (select user_id from book_lending_info where book_id=? order by date_of_return LIMIT 1)");
			st.setInt(1,bookId);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				userName=rs.getString(1);
			}
			
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println("in getLender()");
			//logger.fatal(e.getMessage());
		}
		return userName;
		
	}
	
	/**
	 * This method returns the earliest date of return of the book by bookId 
	 * 
	 * @param conn - Connection object for "Library" database
	 * @param bookId - bookId(int) entered by user
	 * @return - formattedDate(string value)
	 */
	public String getReturnDate(Connection conn, int bookId) {
		String formattedDate="",returnDate="";
		try {
			PreparedStatement st=conn.prepareStatement("select date_of_return from book_lending_info where book_id=? order by date_of_return LIMIT 1;");
			st.setInt(1, bookId);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				returnDate=rs.getString(1);
			}
			
			Date date1=(Date) new SimpleDateFormat("yyyy-MM-dd").parse(returnDate);  
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");  
			formattedDate  = formatter.format(date1);  
			
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
			System.out.println("in getLender()");
			//logger.fatal(e.getMessage());
		} 
		catch (/*Parse*/Exception e) {
			e.printStackTrace();
		}
		return formattedDate;
	}
	
	/**
	 * This method displays data of all the books that supposed to be returned today
	 * 
	 * @param conn - Connection object for "Library" database
	 */
	public void booksToReturnToday(Connection conn) {
		try {
			String format = "| %-8s| %-30s| %-30s| %-10s|";
			PreparedStatement st=conn.prepareStatement("select b.book_id, b.book_name, u.user_name, u.phone from book b, user u where book_id IN (select book_id from book_lending_info where date_of_return=curdate()) AND user_id IN (select user_id from book_lending_info where date_of_return=curdate())");
			ResultSet rs=st.executeQuery();
			if(rs.next()==false) {
				System.out.println("\nNo book returning today");
			}
			else {
			
				System.out.println(String.format(format, "Book ID", "Book Name", "User Name", "Phone"));
				do{
				System.out.println(String.format(format, rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
				}while(rs.next());
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("in booksToReturnToday()");
			//logger.fatal(e.getMessage());
		}
	}
}
