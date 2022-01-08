package library;
import java.util.*;
//import java.util.logging.Logger;
import java.util.regex.Pattern;
//import java.util.logging.Level;
import java.sql.*;
public class library {
	//public static final Logger logger = Logger.getLogger(Library.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner=new Scanner(System.in);
		try {
			
			int choice;
			String inputChoice="",exitChoice="0",choicePattern="\\d";
			
			//logger.info("Application Started");
			Class.forName("com.mysql.jdbc.Driver");
			//Establishing database connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library?characterEncoding=latin1", "root", "Vicky@99");
			
			do {
				System.out.print("\nSelect from below : \n1. Add a new book\n2. Search a book\n3. View Book Details \n4. Borrow a book \n5. List all the books that are supposed to be returned today \n6. Total number of books by genre \n7. View all book details\n8. Exit \n\nEnter your choice: ");
				inputChoice=scanner.nextLine();
				//Check if valid choice entered
				if(!Pattern.matches(choicePattern, inputChoice)){
					System.out.println("\nInvalid input\n - Enter number from 1-8");
					//logger.error("Invalid Choice");
					continue;
				}
				choice=Integer.parseInt(inputChoice);
				
				Book bookObj=new Book();
				User userObj=new User();
				BookLendingInfo bookLendingInfoObj=new BookLendingInfo();
				
				
				switch(choice) {
					case 1:
						//Add a new book
						{
							//logger.info("Selected Add New Book Option");
							String bookName, author, genre, isbn,confirmation="N",bookNamePattern="[A-Za-z0-9]{1}[A-Za-z0-9 ,-_:?!\'\"]{1,29}",authorPattern="[A-Za-z]{1}[A-Za-z .]{1,29}",genrePattern="[A-Za-z]{1}[A-Za-z ]{1,29}",isbnPattern="[0-9]{13}";
							//Check if valid book name entered
							do{
								System.out.print("\nEnter Book Name: ");
								bookName=scanner.nextLine();
								if(!Pattern.matches(bookNamePattern, bookName)){
									System.out.println("Invalid Input\n - Only alphanumberic input allowed\n - Maximum 30 characters\n - ,_:?!\'\"[space] allowed");
								}
							}while(!Pattern.matches(bookNamePattern, bookName));
							
							if(bookObj.bookExist(conn,bookName)) {
								System.out.println("Book name already exist.\nEnter 'Y' to update book quantity or 'N' to enter new details");
								confirmation=scanner.nextLine();
								if(confirmation.equals("Y")) {
									//Check if more than one book of same name exists
									if(bookObj.checkDuplicateBookName(conn, bookName)>1) {
										System.out.println("More than one book of same name exist");
										//Check if valid author name entered
										do{
											System.out.print("\nPlease Enter Book\'s Author Name: ");
											author=scanner.nextLine();
											if(!Pattern.matches(authorPattern, author)){
												System.out.println("Invalid Input\n - Only string input allowed\n - Maximum 30 characters\n - [space] allowed");
											}
										}while(!Pattern.matches(authorPattern, author));
										
										bookObj.addBook(conn, bookName, author);
									}
									else {
										bookObj.addBook(conn,bookName);
									}
									//logger.info(bookName+" quantity updated");
									System.out.println("\nEnter 0 to go to main menu or Enter anything else to exit.");
									exitChoice=scanner.nextLine();
								}
								else if(confirmation.equals("N")) {
									//Check if valid author name entered
									do{
										System.out.print("\nEnter Book\'s Author Name: ");
										author=scanner.nextLine();
										if(!Pattern.matches(authorPattern, author)){
											System.out.println("Invalid Input\n - Only string input allowed\n - Maximum 30 characters\n - [space] allowed");
										}
									}while(!Pattern.matches(authorPattern, author));
									
									//Check if valid genre entered
									do{
										System.out.print("\nEnter Book\'s Genre: ");
										genre=scanner.nextLine();
										if(!Pattern.matches(genrePattern, genre)){
											System.out.println("Invalid Input\n - Only string input allowed\n - Maximum 30 characters\n - [space] allowed");
										}
									}while(!Pattern.matches(genrePattern, genre));
									
									//Check if valid ISBN entered
									do{
										System.out.print("\nEnter Book\'s ISBN: ");
										isbn=scanner.nextLine();
										if(!Pattern.matches(isbnPattern, isbn)){
											System.out.println("Invalid Input\n - Only numeric input allowed\n - 13 digits required");
										}
									}while(!Pattern.matches(isbnPattern, isbn));
								
									bookObj.addBook(conn,bookName, author, genre, isbn);
									//logger.info("New book "+bookName+" added successfully");
									System.out.println("\nEnter 0 to go to main menu or Enter anything else to exit.");
									exitChoice=scanner.nextLine();
								}
								else {
									System.out.println("Invalid input");
								}
							}
							else {
								//Check if valid author name entered
								do{
									System.out.print("\nEnter Book\'s Author Name: ");
									author=scanner.nextLine();
									if(!Pattern.matches(authorPattern, author)){
										System.out.println("Invalid Input\n - Only string input allowed\n - Maximum 30 characters\n - [space] allowed");
									}
								}while(!Pattern.matches(authorPattern, author));

								//Check if valid genre entered
								do{
									System.out.print("\nEnter Book\'s Genre: ");
									genre=scanner.nextLine();
									if(!Pattern.matches(genrePattern, genre)){
										System.out.println("Invalid Input\n - Only string input allowed\n - Maximum 30 characters\n - [space] allowed");
									}
								}while(!Pattern.matches(genrePattern, genre));
								
								//Check if valid ISBN entered
								do{
									System.out.print("\nEnter Book\'s ISBN: ");
									isbn=scanner.nextLine();
									if(!Pattern.matches(isbnPattern, isbn)){
										System.out.println("Invalid Input\n - Only numeric input allowed\n - 13 digits required");
									}
								}while(!Pattern.matches(isbnPattern, isbn));
								
								bookObj.addBook(conn,bookName, author, genre, isbn);
								//logger.info("New book "+bookName+" added successfully");
								System.out.println("\nEnter 0 to go to main menu or Enter anything else to exit.");
								exitChoice=scanner.nextLine();
								
							}
							break;
						}
						
					case 2:
						//Search a book
						{
							//logger.info("Selected Search Book Option");
							String keyword;
							System.out.println("Enter search keyword: ");
							keyword=scanner.nextLine();
							bookObj.searchBook(conn,keyword);
							//logger.info("Searched new book using keyword "+keyword);
							System.out.println("\nEnter 0 to go to main menu or Enter anything else to exit.");
							exitChoice=scanner.nextLine();
							break;
						}
						
					case 3:
						//View Book Details
						{
							//logger.info("Select View Book Details Option");
							String inputBookId,bookIdPattern="[123456789]{1}[0-9]{2}";
							int bookId;
							
							//Check if valid book Id entered
							do{
								System.out.println("\nEnter Book ID: ");
								inputBookId=scanner.nextLine();
								if(!Pattern.matches(bookIdPattern,inputBookId)){
									System.out.println("Invalid Input\n - Only numeric input allowed\n - 3 digits required");
								}
							}while(!Pattern.matches(bookIdPattern, inputBookId));
							
							bookId=Integer.parseInt(inputBookId);
							//Check if book already exist 
							if(bookObj.bookExist(conn, bookId)) {
								bookObj.viewBookDetails(conn, bookId);
								//logger.info("Book Details Displayed");
							}
							else {
								System.out.println("No such book id in records");
								
							}
							System.out.println("\nEnter 0 to go to main menu or Enter anything else to exit.");
							exitChoice=scanner.nextLine();
							break;
						}
					
					case 4:
						//Borrow a book
						{
							//logger.info("Selected Borrow Book Option");
							String userName,phoneNumber,bookName,author,userNamePattern="[A-Za-z]{1}[A-Za-z0-9 ._]{1,29}",phoneNumberPattern="[9876]{1}[0-9]{9}",bookNamePattern="[A-Za-z0-9]{1}[A-Za-z0-9 ,-_:?!\'\"]{1,29}",authorPattern="[A-Za-z]{1}[A-Za-z .]{1,29}";
							//Check if valid user name entered
							do{
								System.out.print("\nEnter Username: ");
								userName=scanner.nextLine();
								if(!Pattern.matches(userNamePattern, userName)){
									System.out.println("Invalid Input\n - First character should be an alphabet\n - Rest can be alphanumeric \n - Maximum 30 characters \n - ._[space] allowed");
								}
							}while(!Pattern.matches(userNamePattern, userName));
							
							if(!userObj.userExist(conn, userName)) {
								//Check if valid phone number entered
								do{
									System.out.println("\nEnter Phone Number: ");
									phoneNumber=scanner.nextLine();
									if(!Pattern.matches(phoneNumberPattern, phoneNumber)){
										System.out.println("Invalid Input\n - Only numeric input allowed \n - 10 digits required");
									}
								}while(!Pattern.matches(phoneNumberPattern, phoneNumber));
								
								userObj.addUser(conn,userName,phoneNumber);
								//logger.info("New user "+userName+" added successfully");
							}
							//Check if valid book name entered
							do{
								System.out.print("\nEnter Book Name: ");
								bookName=scanner.nextLine();
								if(!Pattern.matches(bookNamePattern, bookName)){
									System.out.println("Invalid Input\n - Only alphanumberic input allowed\n - Maximum 30 characters\n - ,_:?!\'\"[space] allowed");
								}
							}while(!Pattern.matches(bookNamePattern, bookName));
							
							//Check if book already exist
							if(bookObj.bookExist(conn,bookName)) {
								//Check if more than one book of same name exists
								if(bookObj.checkDuplicateBookName(conn, bookName)>1) {
									System.out.println("More than one book of same name exist");
									//Check if valid author name entered
									do{
										System.out.print("\nPlease Enter Book\'s Author Name: ");
										author=scanner.nextLine();
										if(!Pattern.matches(authorPattern, author)){
											System.out.println("Invalid Input\n - Only string input allowed\n - Maximum 30 characters\n - [space] allowed");
										}
									}while(!Pattern.matches(authorPattern, author));
									
									//Check if book quantity is more than 0 for bookName and author
									if(bookObj.checkQuantity(conn,bookName,author)>0) {
										bookLendingInfoObj.borrowBook(conn, userName, bookName,author);
										//logger.info(bookName+" is borrowed by "+userName);
										
									}
									else {
										System.out.println(bookName+" is currently not available or Book Name and Author Incorrect");
										
									}
									
								}
								else {
									//Check if book quantity is more than 0 for bookName
									if(bookObj.checkQuantity(conn,bookName)>0) {
										bookLendingInfoObj.borrowBook(conn, userName, bookName);
										//logger.info(bookName+" is borrowed by "+userName);
										
									}
									else {
										System.out.println(bookName+" is currently not available");
										
									}		
								}
							} 
							else {
								System.out.println("No such book in record");
							}
							System.out.println("\nEnter 0 to go to main menu or Enter anything else to exit.");
							exitChoice=scanner.nextLine();
							break;
						}
						
					case 5:
						//List all the books that are supposed to be returned today
						{
							//logger.info("Selected List all the books that are supposed to be returned today Option");
							bookLendingInfoObj.booksToReturnToday(conn);
							//logger.info("All books that are supposed to be returned today are displayed");
							System.out.println("\nEnter 0 to go to main menu or Enter anything else to exit.");
							exitChoice=scanner.nextLine();
							break;
						}
						
					case 6:
						//Total number of books by genre
						{
							//logger.info("Selected Total number of books by genre Option");
							bookObj.countBooksByGenre(conn);
							//logger.info("Total number of books by genre are displayed");
							System.out.println("\nEnter 0 to go to main menu or Enter anything else to exit.");
							exitChoice=scanner.nextLine();
							break;
						}
					case 7:
						//Display all book details
						{
							//logger.info("Select View All Book Details Option");
							bookObj.allBookDetails(conn);
							//logger.info("All Book Details are displayed");
							System.out.println("\nEnter 0 to go to main menu or Enter anything else to exit.");
							exitChoice=scanner.nextLine();
							break;
						}
					case 8:
						//Exit Menu
						{
							exitChoice="x";
							break;
						}
					default:
						System.out.println("\nInvalid input\n - Enter number from 1-7");
						//logger.error("Invalid Choice");
				}
					
			}while(exitChoice.equals("0"));
				
		}
			
		catch(SQLException e) {
			System.out.println(e.getMessage());
			//logger.fatal(e.getMessage());
		}
		catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
			//logger.fatal(e.getMessage());
		}
		
		finally{
			scanner.close();
			System.out.println("\nThank you for using Library Application");
			//logger.info("Application closed");
		}

	}

}
