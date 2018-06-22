package domain.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DbManager;
import domain.login.Login;



public class UserDaoImpl implements UserDao {

	static Connection conn;
	static PreparedStatement ps;
	DbManager db = new DbManager();
	
	@Override
	public int register(User u) {
		int status = 0;
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("insert into Users values(?,?,?,?)");
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getName());
			ps.setInt(4, u.getUsertype());
			status = ps.executeUpdate();
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return status;
	}

	@Override
	public User validateUser(Login login) {
		User u = new User();
		try{
			conn = db.getConnection();
			ps =conn.prepareStatement("select * from Users where Username=? and Password=?");
			ps.setString(1, login.getUsername());
			ps.setString(2, login.getPassword());

			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				u.setUsername(rs.getString(1));
				u.setPassword(rs.getString(2));
				u.setName(rs.getString(3));
				u.setUsertype(rs.getInt(4));
			}
			conn.close();
		}catch(Exception e){
			System.out.println(e);
		}
		return u;
	}

}
