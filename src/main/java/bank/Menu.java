package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountException;

public class Menu {
  private Scanner scan;

  public static void main(String[] args) {
    System.out.println("Welcome to Globe Bank Intl!");

    Menu menu = new Menu();
    menu.scan = new Scanner(System.in);

    Customer customer = menu.authenticateUser();
    if (customer != null) {
      Accounts account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }
    menu.scan.close();
  }

  private Customer authenticateUser() {
    System.out.println("Please enter your username");
    String username = scan.next();

    System.out.println("Please enter your password");
    String password = scan.next();

    Customer customer = new Customer();
    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException ex) {
      System.out.println("Sorry! User credentials not validated. Error Message: " + ex.getMessage());
    }

    return customer;

  }

  private void showMenu(Customer customer, Accounts account) {
    int selection = 0;

    while (selection != 4 && customer.isAuthenticated()) {
      System.out.println("===============================================");
      System.out.println("Please select one of the following options: ");
      System.out.println("1. Deposit");
      System.out.println("2. Withdrawal");
      System.out.println("3. Check Balance");
      System.out.println("4. Exit");
      System.out.println("===============================================");

      selection = scan.nextInt();
      double amount;

      switch (selection) {

        case 1:
          System.out.println("How much would you like to deposit?");
          amount = scan.nextDouble();
          try {
            account.deposit(amount);
          } catch (AmountException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Please try again.");
          }
          break;
        case 2:
          System.out.println("How much would you like to withdraw?");
          amount = scan.nextDouble();
          try {
            account.withdraw(amount);
          } catch (AmountException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Please try again.");
          }
          break;
        case 3:
          System.out.println("Current balance: " + account.getBalance());
          break;
        case 4:
          Authenticator.logout(customer);
          System.out.println("Thanks for banking with Gloabe Bank Intl. See you soon!");
          break;
        default:
          System.out.println("Invalid selection. Please try again.");
          break;

      }
    }
  }
}
