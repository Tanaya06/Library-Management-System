package library;
import java.sql.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
public class User {
	//public static final Logger logger = Logger.getLogger(User.class);
	public User() {
		
	}
	public boolean userExist(Connection conn, String userName) {
		try {
			PreparedStatement st=conn.prepareStatement("select count(*) from user where user_name=?");
			st.setString(1, userName);
			ResultSet rs=st.executeQuery();
			int countUser=0;
			while(rs.next()) {
				countUser=rs.getInt(1);
			}
			if(countUser>0)
				return true;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("in userExist()");
			//logger.fatal(e.getMessage());
		}
		return false;
	}
	public void addUser(Connection conn, String userName, String phoneNumber) {
		try {
			PreparedStatement st=conn.prepareStatement("insert into user(user_name,phone) values(?,?)");
			st.setString(1,userName);
			st.setString(2,phoneNumber);
			
			int rows=st.executeUpdate();  
			System.out.println(rows+" user added successfully");
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("in addUser()");
			//logger.fatal(e.getMessage());
		}
	}
}
