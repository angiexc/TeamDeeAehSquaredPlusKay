/**
 * 
 */
package edu.cpp.cs.cs141.spyvsninjaassasins;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The main class holds the main method which will basically function by starting off the code.
 * It will create a new interface for the user (text-based or graphical, depending on their
 * choice) and then will initiate the code by calling the interface's startGame() method.
 */
public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Pick an interface:");
		System.out.println("[1] Text-Based");
		System.out.println("[2] Graphic User Interface");
		
		boolean valid = false;
		while(!valid) {
			try {
				switch(sc.nextInt()) {
				case 1:
					UI ui = new UI(new GameEngine());
					ui.startGame();
					valid = true;
					break;
				case 2:
					@SuppressWarnings("unused")
					MainMenuFrame gui = new MainMenuFrame();
					valid = true;
					break;
				default:
					System.out.println("This option does not exist. Try again!");
				}
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("This option does not exist. Try again!");
				sc.nextLine();
			}
		}
		sc.close();
	}
}
