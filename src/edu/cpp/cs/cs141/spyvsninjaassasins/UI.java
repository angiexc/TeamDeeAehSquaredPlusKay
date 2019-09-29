package edu.cpp.cs.cs141.spyvsninjaassasins;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author AAnthony The UI class is in charge of all text inputted by the user
 *         or displayed on the screen. The UI will be constructed with a new
 *         GameEngine to run the code that controls the internal game mechanics
 *         and a Scanner that will be used throughout the class to allow the
 *         user to input their choices/moves in the game.
 */
public class UI {
	private GameEngine game = null;
	private Scanner sc = null;

	public UI(GameEngine game) {
		sc = new Scanner(System.in);
		this.game = game;
	}

	/**
	 * This method is used to initiate the game. The method will not reach its
	 * end unless the boolean quit becomes true, which will be at the control of
	 * the user. Otherwise, the method will return back to opening the main menu
	 * while the boolean remains false.
	 */
	public void startGame() {
		boolean quit = false;
		while (!quit) {
			int choice = openMainMenu();
			sc.nextLine();
			switch (choice) {
			case 1:
				int mode = promptMode();
				sc.nextLine();
				switch (mode) {
				case 1:
					runGame(mode);
					break;
				case 2:
					game.setHardMode(true);
					runGame(mode);
					break;
				case 3:
					runGame(mode);
					break;
				case 4:
					runGame(mode);
					break;
				}
				break;
			case 2:
				loadFile();
				break;
			case 3:
				displayHowToPlay();
				break;
			case 4:
				quitGame();
				quit = true;
				break;
			}
		}
	}

	/**
	 * Prompts the user which mode he/she wants to start the game with. Returns
	 * the the int that the user types.
	 */
	private int promptMode() {
		boolean valid = false;
		int choice = 0;
		while (!valid) {
			System.out.println("Choose the mode: ");
			System.out.println("[1] Normal Mode");
			System.out.println("[2] Hard Mode");
			System.out.println("[3] Debug Mode");
			System.out.println("[4] God Mode");
			System.out.println("[5] Back to Main Menu");
			try {
				choice = sc.nextInt();
			} catch (InputMismatchException e) {
				sc.nextLine();
				displayError(2);
			}

			if (!(choice < 0) && !(choice > 5))
				valid = true;
			else
				displayError(2);
		}
		return choice;
	}

	/**
	 * Method in charge of running a new game. Resets the board and internal
	 * fields, generates a new board. If the player opted to start a debug or
	 * god mode game, it will run the appropriate GameEngine method. From there,
	 * until the player quits, the game will continuously run the exexuteTurn()
	 * method. If the player wins, the UI will print a message saying so and
	 * will generate the next level.
	 */
	private void runGame(int mode) {
		boolean quitToMenu = false;
		game.gameReset();
		game.generateBoard();
		if (mode == 3) {
			game.executeDebug();
			game.setPreDebugBullets(1);
			game.setPreDebugLives(3);
		}
		if (mode == 4) {
			game.executeGod();
			game.setPreDebugBullets(1);
			game.setPreDebugLives(3);
		}
		while (true) {
			displayBoard();
			while (!game.checkWin()) {
				quitToMenu = executeTurn();
				// sc.nextLine(); // For testing moving enemies.
				// game.moveEnemies(); // For testing killing player.
				// displayBoard(); // For testing moving enemies.
				if (quitToMenu || gameOver()) {
					return;
				}
				game.endTurnFunctions();
			}
			displayEndLevel();
		}
	}

	/**
	 * Method used to run a previously saved game. Skips the process of
	 * restarting any game fields.
	 */
	private void runLoadedGame() { // WIP
		boolean quitToMenu = false;
		while (true) {
			displayBoard();
			while (!game.checkWin()) {
				quitToMenu = executeTurn();
				// sc.nextLine(); // For testing moving enemies.
				if (quitToMenu || gameOver()) {
					return;
				}
				game.endTurnFunctions();
			}
			displayEndLevel();
		}
	}

	/**
	 * Displays the main menu to the user and returns whatever choice the user
	 * inputs, represented by some integer.
	 */
	private int openMainMenu() {
		int choice = 0;
		boolean valid = false;
		while (!valid) {
			System.out.println("Select one of the following options:");
			System.out.println("[1] Start New Game");
			System.out.println("[2] Load File");
			System.out.println("[3] How-To-Play");
			System.out.println("[4] Quit Game");
			try {
				choice = sc.nextInt();
			} catch (InputMismatchException e) {
				sc.nextLine();
				displayError(2);
			}

			if (!(choice < 0) && !(choice > 4))
				valid = true;
			else
				displayError(2);
		}
		return choice;
	}

	/**
	 * Displays the map to the user. Only shows the rooms, the player, and
	 * anything within the players range; if debug mode is on, everything on the
	 * map will be visible. Also displays a small HUD for the player to know how
	 * the current status of the game's important fields.
	 */
	private void displayBoard() {
		System.out.println("\n===========================");
		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 9; ++j) {
				if (!game.checkExists(i, j))
					System.out.print("[ ]");
				else if (game.isVisible(i, j))
					System.out.print("[" + game.getType(i, j) + "]");
				else
					System.out.print("[ ]");
			}
			System.out.println("");
		}
		System.out.println("===========================");
		System.out.println("Lives: " + game.getLives() + "   Bullets: " + game.getBullets());
		System.out.println("Level: " + game.getLevel() + "   Enemies: " + game.getEnemies());
		System.out.println("===========================");
		if (game.checkInvincibility()) {
			System.out.println("You are invincible for " + (game.getInvincibility() - 2) + " more turns!");
			System.out.println("===========================");
		}
		// System.out.println("X: " + game.getX() + " Y: " + game.getY()); //
		// For
		// testing
		// player
		// movement
	}

	/**
	 * This method is in charge of going through the process of a player's turn.
	 * It will call to multiple methods throughout the UI and the GameEngine
	 * that will account for functions such as moving the player, looking in a
	 * direction, shooting, etc.
	 */
	private boolean executeTurn() {
		boolean valid = false;
		while (!valid) {
			System.out.println("Type one of the following options:");
			System.out.print("[Look] [Move] [Fire] [Save] [Quit] : ");
			char choice = sc.next().toLowerCase().charAt(0);
			sc.nextLine();
			switch (choice) {
			case 'l':
				promptLook();
				valid = true;
				break;
			case 'm':
				promptMove();
				valid = true;
				break;
			case 'f':
				if (promptFire() != 0)
					valid = true;
				break;
			case 's':
				saveNewFile();
				valid = true;
				break;
			case 'd':
				if (game.getGodMode())
					game.executeGod();
				game.executeDebug();
				displayBoard();
				if (game.getDebugMode()) {
					System.out.println("Debug Mode has been activated. Good luck testing.");
				} else {
					System.out.println("Debug Mode has been de-activated. Prepare yourself.");
				}
				System.out.println("===========================");
				valid = true;
				break;
			case 'g':
				if (game.getDebugMode())
					game.executeDebug();
				game.executeGod();
				displayBoard();
				if (game.getGodMode()) {
					System.out.println("Have fun playing God.");
				} else {
					System.out.println("God Mode has been de-activated. You are now mortal.");
				}
				System.out.println("===========================");
				valid = true;
				break;
			case 'h':
				displayBoard();
				if (!game.getHardMode()) {
					game.setHardMode(true);
					System.out.println("Hard Mode has been activated. Prepare yourself.");
				} else {
					game.setHardMode(false);
					System.out.println("Normal Mode has been activated. Take a breather.");
				}
				System.out.println("===========================");
				break;
			case 'x':
				game.unstuck();
				break;
			case 'q':
				System.out.println("\n\n");
				return true;
			default:
				displayError(2);
			}
		}

		return false;
	}

	/**
	 * If the player chooses to look, this method will prompt the user to input
	 * which direction, and from there, call the method in the GameEngine that
	 * actually checks what is there. Prints the result of the look once the
	 * process finished.
	 */
	private void promptLook() {
		boolean valid = false;
		while (!valid) {
			System.out.print("Which way do you want to look? [Up, Down, Left, or Right] : ");
			char direction = sc.next().toLowerCase().charAt(0);
			System.out.println("");
			sc.nextLine();

			String result = game.look(direction);
			if (!result.equals("")) {
				displayBoard();

				if (result.equals("A room is in the way."))
					System.out.println(result);
				else {
					System.out.println("There is " + result + " at this space.");
					valid = true;
				}

				if (!result.equals("a wall") && !result.equals("nothing"))
					game.endLook(direction);
			} else
				displayError(2);
		}
		promptPartTwo();
	}

	/**
	 * If the user decides to look, then after, the user is given the choice to
	 * either move or fire. This method is in charge of executing said "Phase 2"
	 * of the user's turn.
	 */
	private void promptPartTwo() {
		boolean valid = false;
		while (!valid) {
			System.out.print("What do you want to do next? [Move] [Fire] : ");
			char choice = sc.next().toLowerCase().charAt(0);
			System.out.println("");
			sc.nextLine();

			switch (choice) {
			case 'm':
				promptMove();
				valid = true;
				break;
			case 'f':
				if (promptFire() != 0)
					valid = true;
				break;
			case 'x':
				game.unstuck();
				break;
			default:
				displayError(2);
				break;
			}
		}
	}

	/**
	 * Method in charge of prompting the user on which direction to fire. If the
	 * player does not have any bullets, the method returns to the executeTurn()
	 * method to prompt the user to choose a different action, as they cannot
	 * shoot. Otherwise, the method will call to the GameEngine to execute the
	 * actual shooting, then account for and display the result of said shot.
	 */
	private int promptFire() {
		if (game.getPlayer().getBullets() == 0) {
			System.out.println("Can't shoot if your clip's empty.. Better find some ammo.\n");
			return 0;
		}

		String result = "";
		boolean valid = false;
		while (!valid) {
			System.out.print("Which way do you want to shoot? [Up, Down, Left, or Right] : ");
			char direction = sc.next().toLowerCase().charAt(0);
			System.out.println("");
			sc.nextLine();

			result = game.shoot(direction);
			game.getPlayer().setBullets(game.getBullets() - 1);

			if (!game.shoot(direction).equals(""))
				valid = true;
			if (!valid)
				displayError(1);
		}
		game.moveEnemies();
		displayBoard();
		System.out.println(result);
		return 1;
	}

	/**
	 * Method in charge of prompting the player to move. If the player is stuck, he/she
	 * has the option to type 'x' in order to run the GameEngine's unstuck method that
	 * respawns the player in the original spawn point with one less life.
	 */
	private void promptMove() {
		boolean valid = false;
		while (!valid) {
			System.out.print("Which way do you want to move? [Up, Down, Left, or Right] : ");
			char direction = sc.next().toLowerCase().charAt(0);
			if (direction == 'x') {
				game.unstuck();
				sc.nextLine();
			} else {
				sc.nextLine();
				valid = game.movePlayer(direction);
				if (!valid)
					displayError(1);
			}
		}
		game.moveEnemies();
		displayBoard();
	}

	/**
	 * Prompts the user to input a new name for the game progress they want to
	 * save. Loops until the user inputs a proper file name. If the file exists already, it
	 * will be overwritten.
	 * [Note: The process of actually saving the data will be in the GameEngine's saveFile() method.]
	 */
	private void saveNewFile() {
		System.out.println("What would you like to name your save file?");
		String fileName = sc.next();
		sc.nextLine();
		File file = new File(fileName);

		System.out.println(game.saveFile(file));
	}

	/**
	 * Prompts the user to input the name of the file he/she wants to load. The game
	 * then takes the file name, creates a File object with it, and sends the file
	 * object to be loaded in through the GameEngine.
	 */
	private void loadFile() {
		boolean valid = false;
		while (!valid) {
			System.out.println("Please input the name of the saved file you want to load.");
			System.out.println("[Type \"Back\" to go back to the main menu.]");
			String fileName = sc.next();
			sc.nextLine();

			File file = new File(fileName);
			String result = game.loadFile(file);
			if (result.equals("back"))
				return;
			else if (!result.equals(""))
				System.out.println(result);
			else {
				valid = true;
				runLoadedGame();
			}
		}
	}

	/**
	 * Displays that the user has finished the level and waits until the user is
	 * ready to generate a new board for the next level. Increases the amount of enemies
	 * by one.
	 */
	private void displayEndLevel() {
		System.out.println("Congratulations! You found the briefcase!");
		System.out.println("[Press ENTER to go to the next level.]");
		sc.nextLine();
		game.increaseLevel();
		int lives = game.getLives();
		game.levelReset();
		game.generateBoard(lives);
	}

	/**
	 * Displays that the user has lost all of his/her lives and has now lost the
	 * game; whenever the user presses enter, it will return them to the main
	 * menu.
	 */
	private boolean gameOver() {
		if (game.getLives() == 0) {
			System.out.println("You're out of lives! Better luck next time!");
			System.out.println("[Press ENTER to continue.]");
			sc.nextLine();
			return true;
		} else
			return false;
	}

	/**
	 * Opens a how-to-play manual for users new to the game.
	 */
	private void displayHowToPlay() {
		System.out.println("===================================================================");
		System.out.println("Goal: Find the briefcase hidden in one of the 9 rooms (labeled 'R').");
		System.out.println("Every turn, a player is able to look two spaces forward in any ");
		System.out.println("direction. After looking, the player must either move or shoot in ");
		System.out.println("whatever direction they please.");
		System.out.println("After the player completes his/her turn, the enemies will move.");
		System.out.println("Rooms can only be entered through the north side of the space.");
		System.out.println("They will also block the line of sight/fire, so be careful not to");
		System.out.println("waste look strategies or bullets on rooms that are in the way!");
		System.out.println("===================================================================");
		System.out.println("Map Legend:");
		System.out.println("[P] - Player  ;  [E] - Enemies  ;  [R] - Room  ;  [E] - Briefcase");
		System.out.println("[A] - Ammo [Gives the player and additional bullet.]");
		System.out.println("[L] - Locator [Shows the player which room has the briefcase.]");
		System.out.println("[S] - Shield [Makes player safe from death for 5 turns.]");
		System.out.println("===================================================================");
		System.out.println("If the player is ever trapped in with nowhere to move, type 'x' to");
		System.out.println("respawn in the lower left corner! This will decrease the lives");
		System.out.println("counter by one, so please do not abuse the function!");
		System.out.println("===================================================================");
		System.out.print("[Press ENTER when you finish reading.]");
		sc.nextLine();
		System.out.println("");
	}

	/**
	 * This method is called upon with an int parameter to determine which error
	 * to display to the user.
	 */
	private void displayError(int errorCode) {
		switch (errorCode) {
		case 1:
			System.out.println("Invalid move! Try again! \n");
			break;
		case 2:
			System.out.println("That option doesn't exist! Try again!\n");
			break;
		}
	}

	/**
	 * Displays a message for when the user quits the game.
	 */
	private void quitGame() {
		System.out.println("Thanks for playing!");
		System.out.println("Press ENTER to continue.");
		sc.nextLine();
	}
}
