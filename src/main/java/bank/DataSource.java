package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataSource {

  public static Connection connect() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("We're connected");
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return connection;
  }

  public static Customer getCustomer(String username) {
    String sql = "SELECT * FROM customers where username = ?";
    Customer customer = new Customer();
    try (Connection connection = connect(); PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, username);
      try (ResultSet rs = statement.executeQuery()) {
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setAccountId(rs.getInt("account_id"));
        customer.setUsername(rs.getString("username"));
        customer.setPassword(rs.getString("password"));
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return customer;
  }

  public static Accounts getAccount(int accountId){
    String sql = "SELECT * FROM ACCOUNTS WHERE id =?"; 
    Accounts account = new Accounts();

    try(Connection connection = connect(); PreparedStatement statement = connection.prepareStatement(sql)){

      statement.setInt(1, accountId);
      try(ResultSet rs = statement.executeQuery()){
        account.setId(rs.getInt("id"));
        account.setType(rs.getString("type"));
        account.setBalance(rs.getDouble("balance"));
      }
    }
    catch(SQLException ex){
      ex.printStackTrace();
    }
    return account;
  }
  public static void main(String[] args) {
    //Customer customer = getCustomer("twest8o@friendfeed.com");
    //System.out.println(customer.getName());
    Accounts account = getAccount(10385);
    System.out.println(account.getBalance());
  }
}
