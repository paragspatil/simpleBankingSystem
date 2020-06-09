package banking;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedHashMap<Long, Integer> userDetails = new LinkedHashMap<>();


        LinkedHashMap<Long, Integer> userBalance = new LinkedHashMap<>();
        boolean exitflag = true;

        //switch statement
        while (exitflag) {
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

    for(int i = 0; i < 10; i++){
        BIN = BIN + random.nextInt(9);
    }
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

}

