package banking;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        boolean hasData = false;
        String createTable = "CREATE TABLE IF not EXISTS card (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "number TEXT," +
                "pin TEXT," +
                "balance INTEGER DEFAULT 0"+

        ");";

        //create New database
        String url =  "jdbc:sqlite:" + args[1];

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                //System.out.println("The driver name is " + meta.getDriverName());
                //System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //connect to database
        //create new table

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                //System.out.println("Connection is valid.");
                Statement createtable = con.createStatement();
                createtable.execute(createTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }











        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<Long, Integer> userDetails = new LinkedHashMap<>();
        LinkedHashMap<Long, Integer> userBalance = new LinkedHashMap<>();
        boolean exitflag = true;

        //switch statement
        while (exitflag) {
            String insertIntoTable = "INSERT INTO card (number, pin) VALUES (";
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            String choice = scanner.nextLine();
            boolean loginflag = true;
            switch (choice) {
                //1. Create an account
                case "1":
                    long cardNumber = generateCardNumber();
                    int cardPin = generateCardPin();
                    userDetails.put(cardNumber, cardPin);
                    userBalance.put(cardNumber, 0);
                    //adding to database
                    insertIntoTable = insertIntoTable + cardNumber + "," + cardPin + ");";
                    try (Connection con = dataSource.getConnection()) {
                        if (con.isValid(5)) {
                            //System.out.println("Connection is valid.");
                            Statement insert = con.createStatement();
                            insert.execute(insertIntoTable);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }





                    System.out.println("Your card has been created");
                    System.out.println("Your card number:");
                    System.out.println(cardNumber);
                    System.out.println("Your card PIN:");
                    System.out.println(cardPin);
                    break;

                //2. Log into account
                case "2":
                    System.out.println("Enter your card number:");
                    Long cardNum = Long.parseLong(scanner.nextLine());
                    System.out.println("Enter your PIN:");
                    int pin = Integer.parseInt(scanner.nextLine());
                    if (userDetails.containsKey(cardNum) && userDetails.containsValue(pin) && userDetails.get(cardNum).equals(pin)) {
                        System.out.println("You have successfully logged in!");
                       // scanner.nextLine();

                        while (loginflag && exitflag) {
                            System.out.println("1. Balance");
                            System.out.println("2. Log out");
                            System.out.println("0. Exit");

                            String userChoice = scanner.nextLine();
                            switch (userChoice){
                                //1. Balance
                                case  "1":
                                  System.out.println("Balance: " + userBalance.get(cardNum));
                                  break;
                                  //2. Log out
                                case "2":
                                    System.out.println("You have successfully logged out!");
                                    //scanner.nextLine();
                                    loginflag = false;
                                    break;

                                    //0. Exit
                                case "0":
                                    System.out.println("Bye!");
                                    exitflag=false;
                                    break;


                            }

                        }

                    } else {
                        System.out.println("Wrong card number or PIN!");
                    }
                    break;

                    // 0. exit
                case "0":
                    System.out.println("Bye!");
                    exitflag=false;
                    break;



            }


        }


    }
public static Long generateCardNumber(){
    Random random = new Random();
    String BIN = "400000";
    int[] Array = new int[15];
    Array[0] = 4;
    Array[1] = 0;
    Array[2] = 0;
    Array[3] = 0;
    Array[4] = 0;
    Array[5] = 0;
    int temp;

    for(int i = 0; i < 9; i++){
        temp = random.nextInt(9);
        BIN = BIN + temp;
        Array[i + 6] = temp;
    }
    //Luhn Algorithm in action
    //multily odd digits by 2;
    for(int i = 0;i<15;i++){
        if(i%2==0){
            Array[i] = Array[i]*2;
        }if(Array[i]>9){
            Array[i] = Array[i]-9;
        }
    }
    //sum of all elements
    int sum = 0;
    for(int num:Array){
        sum = sum + num;
    }
    int x = sum%10;
    int checksum = 10 - x;
    BIN = BIN + checksum;


    long cardNumber = Long.parseLong(BIN);

       return cardNumber;
    }
    public static int generateCardPin(){
        String pin = "";
        Random random = new Random();
        for(int i = 0; i < 4; i++) {
            if(i==0){
                pin = pin + (random.nextInt(9) + 1);
            }
            else{
                pin = pin + random.nextInt(9) ;
            }
        }
        int cardPin = Integer.parseInt(pin);
        return cardPin;
    }


    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                //System.out.println("The driver name is " + meta.getDriverName());
                //System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

