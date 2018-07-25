package domain.user;

import java.util.List;

import domain.login.Login;
public interface UserDao {

	public int register(User u);
	
	public User validateUser(Login login);
	
	public List<User> getUserList();
	
	public User getTypeDB(String username);
}

