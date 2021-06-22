package view;

import java.util.Scanner;

public class Keypad {
   private Scanner inputInt; // reads data from the command line
   private Scanner inputString; // reads data from the command line
                         
   public Keypad() {
	   inputInt = new Scanner(System.in);  
	   inputString = new Scanner(System.in);
   } 

   public int getInput() {
      return inputInt.nextInt(); // user enters an integer
   } 
   
   public String getInputString() {
	  return inputString.nextLine(); // user enters an string
   }
} 