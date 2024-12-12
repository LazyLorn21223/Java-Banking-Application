/*
**Lauren Auer
**4/28/24
**IFT201
*/


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PortfolioManager {

  private List < TransactionHistory > portfolioList = new ArrayList < > ();
  private double cashBalance = 0;
  public static void main(String[] args) {
    PortfolioManager manager = new PortfolioManager();
    Scanner scanner = new Scanner(System.in);
    boolean exit = false;
    while (!exit) {
      System.out.println("Lauren Auer Brokerage Account");
      System.out.println("=============================");
      System.out.println("0 - Exit");
      System.out.println("1 - Deposit Cash");
      System.out.println("2 - Withdraw Cash");
      System.out.println("3 - Buy Stock");
      System.out.println("4 - Sell Stock");
      System.out.println("5 - Display Transaction History");
      System.out.println("6 - Display Portfolio");
      System.out.println("Enter option (0 to 6):");
      int option = scanner.nextInt();
      switch (option) {
      case 0:
        exit = true;
        break;
      case 1:
        manager.depositCash(scanner);
        break;
      case 2:
        manager.withdrawCash(scanner);
        break;
      case 3:
        manager.buyStock(scanner);
        break;
      case 4:
        manager.sellStock(scanner);
        break;
      case 5:
        manager.displayTransactionHistory();
        break;
      case 6:
        manager.displayPortfolio();
        break;
      default:
        System.out.println("Invalid option. Please enter a number between 0 and 6.");
      }
    }
    scanner.close();
  }

  private void depositCash(Scanner scanner) {
    System.out.println("Enter amount to deposit:");
    double depositAmount = scanner.nextDouble();
    System.out.println("Enter transaction date (MM/dd/yyyy):");
    String transDate = scanner.next();
    cashBalance += depositAmount;
    TransactionHistory transaction = new TransactionHistory("CASH", transDate, "DEPOSIT", depositAmount, 1.0);
    portfolioList.add(transaction);
  }

  private void withdrawCash(Scanner scanner) {
    System.out.println("Enter amount to withdraw:");
    double withdrawAmount = scanner.nextDouble();
    if (cashBalance >= withdrawAmount) {
      System.out.println("Enter transaction date (MM/dd/yyyy):");
      String transDate = scanner.next();
      cashBalance -= withdrawAmount;
      TransactionHistory transaction = new TransactionHistory("CASH", transDate, "WITHDRAW", -withdrawAmount, 1.0);
      portfolioList.add(transaction);
    } else {
      System.out.println("Insufficient balance");
    }
  }

  private void buyStock(Scanner scanner) {
    System.out.println("Enter stock ticker:");
    String ticker = scanner.next().toUpperCase();
    System.out.println("Enter quantity to buy:");
    double quantity = scanner.nextDouble();
    System.out.println("Enter price per share:");
    double pricePerShare = scanner.nextDouble();
    System.out.println("Enter transaction date (MM/dd/yyyy):");
    String transDate = scanner.next();
    double totalCost = quantity * pricePerShare;
    if (cashBalance >= totalCost) {
      cashBalance -= totalCost;
      TransactionHistory buyTransaction = new TransactionHistory(ticker, transDate, "BUY", quantity, pricePerShare);
      portfolioList.add(buyTransaction);
      TransactionHistory withdrawTransaction = new TransactionHistory("CASH", transDate, "WITHDRAW", -totalCost, 1.0);
      portfolioList.add(withdrawTransaction);
    } else {
      System.out.println("Insufficient cash balance");
    }
  }

  private void sellStock(Scanner scanner) {
    System.out.println("Enter stock ticker:");
    String ticker = scanner.next().toUpperCase();
    System.out.println("Enter quantity to sell:");
    double quantity = scanner.nextDouble();
    System.out.println("Enter price per share:");
    double pricePerShare = scanner.nextDouble();
    System.out.println("Enter transaction date (MM/dd/yyyy):");
    String transDate = scanner.next();
    double totalGain = quantity * pricePerShare;
    cashBalance += totalGain;
    TransactionHistory sellTransaction = new TransactionHistory(ticker, transDate, "SELL", quantity, pricePerShare);
    portfolioList.add(sellTransaction);
    TransactionHistory depositTransaction = new TransactionHistory("CASH", transDate, "DEPOSIT", totalGain, 1.0);
    portfolioList.add(depositTransaction);
  }

  private void displayTransactionHistory() {
    System.out.println("Transaction History:");
    System.out.printf("%-15s%-10s%-15s%-15s%-15s%n", "Date", "Ticker", "Quantity", "Cost Basis", "Trans Type");
    System.out.println("==================================================================");
    for (TransactionHistory transaction: portfolioList) {
      System.out.printf("%-15s%-10s%-15.2f$%-15.2f%-15s%n", transaction.getTransDate(), transaction.getTicker(), transaction.getQty(), transaction.getCostBasis(), transaction.getTransType());
    }
    System.out.println();
  }

  private void displayPortfolio() {
    DateTimeFormatter dtf = DateTimeFormatter. ofPattern("MM/dd/yyyy HH:mm:ss");
    System.out.println("\nPortfolio as of: " + dtf.format(LocalDateTime.now()));
    System.out.println("================================+===");
    System.out.println("Ticker\tQuantity");
    System.out.println("================");
    List < String > displayedTickers = new ArrayList < > ();
    for (TransactionHistory transaction: portfolioList) {
      String ticker = transaction.getTicker();
      if (!displayedTickers.contains(ticker) && !ticker.equals("CASH")) {
        double totalQuantity = calculateTotalQuantity(ticker);
        System.out.printf("%-6s\t%.2f%n", ticker, totalQuantity);
        displayedTickers.add(ticker);
      }
    }
    System.out.printf("%-6s\t%.2f%n", "CASH", cashBalance);
  }

  private double calculateTotalQuantity(String ticker) {
    double totalQuantity = 0;
    for (TransactionHistory transaction: portfolioList) {
      if (totalQuantity > 0.0) {
        if (transaction.getTicker().equals(ticker)) {
            if (transaction.getTransType().equals("BUY")) {
            totalQuantity += transaction.getQty();
            } else if (transaction.getTransType().equals("SELL")) {
            totalQuantity -= transaction.getQty();
            }
        }
      }
    }
    return totalQuantity;
  }
}