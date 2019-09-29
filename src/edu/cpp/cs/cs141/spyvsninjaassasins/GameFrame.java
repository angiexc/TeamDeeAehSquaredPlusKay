package edu.cpp.cs.cs141.spyvsninjaassasins;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author AAnthony
 * Class in charge of creating/maintaining the JFrame that displays the actual game.
 */
public class GameFrame extends JFrame implements KeyListener {
	private static final long serialVersionUID = -4799335665804330813L;
	private GameEngine game = null;
	private boolean looked = false;
	private boolean holdingLook = false;
	private Color normal = Color.WHITE;
	private Color frisk = new Color(255, 204, 0);
	private Color sans = new Color(0, 255, 255);

	private final Set<Character> pressed = new HashSet<Character>();

	//// Board //////////////////
	private BoardPanel board;
	
	//// HUD ////////////////////
	private JPanel hud;
	private JLabel level;
	private JLabel lives;
	private JLabel bullets;
	private JLabel shield;
	private JLabel enemies;
	
	//// HUD Part II ////////////
	private JLabel found;
	private JPanel foundIcons;
	private JLabel foundAmmo;
	private JLabel foundShield;
	private JLabel foundLocator;

	//// Text Box ///////////////
	private JPanel textField;
	private JLabel text;
	private JLabel text2;

	//// Empty Panel ////////////
	private JPanel empty;

	//// Top Menu Bar ///////////
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenu modeMenu;

	//// Game Menu //////////////
	private JMenuItem newNormal;
	private JMenuItem newHard;
	private JMenuItem newDebug;
	private JMenuItem newGod;
	private JMenuItem unstuck;
	private JMenuItem exit;

	//// Mode Menu //////////////
	private JCheckBoxMenuItem hardMode;
	private JRadioButtonMenuItem playMode;
	private JRadioButtonMenuItem debugMode;
	private JRadioButtonMenuItem godMode;
	
	private int postDeath;
	private boolean shot = false;

	public GameFrame(GameEngine game) {
		this.game = game;

		try {
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("res/base.png")))));
		} catch (IOException e) {
		}

		setTitle("Undertale 0.5: Escape From the Banished");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));
		setResizable(false);

		board = new BoardPanel(game);
		buildHUD();
		buildTextField();
		buildEmpty();

		JLabel image = new JLabel();
		image.setIcon(new ImageIcon("res/field_resized.png"));

		addKeyListener(this);
		setFocusable(true);

		add(empty, BorderLayout.NORTH);
		add(empty, BorderLayout.WEST);
		add(board, BorderLayout.CENTER);
		add(hud, BorderLayout.EAST);
		add(textField, BorderLayout.SOUTH);

		buildMenuBar();
		setJMenuBar(menuBar);
		
		pack();
		setVisible(true);
	}

	public void keyPressed(KeyEvent e) {
		postDeath = game.getLives() - 1;
		String resultLook = "";
		pressed.add(e.getKeyChar());
		if (!game.checkWin()) {
			if (game.getLives() != 0) {
				if (pressed.size() == 1) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						dispose();
					} else if (e.getKeyCode() == KeyEvent.VK_SEMICOLON) {
						JFileChooser pickSave = new JFileChooser();
						int status = pickSave.showSaveDialog(null);
						if (status == JFileChooser.APPROVE_OPTION) {
							String result = game.saveFile(pickSave.getSelectedFile());
							text.setText("  " + result);
							text.setForeground(normal);
							text2.setText("");
							pressed.remove(e.getKeyChar());
						}
						if (status == JFileChooser.CANCEL_OPTION){ 
							text.setText("  Game save has been cancelled.");
							text.setForeground(normal);
							text2.setText("");
							pressed.remove(e.getKeyChar());
						}
					} else if (e.getKeyCode() == KeyEvent.VK_W) {
						if (game.movePlayer('u')) {
							game.endTurnFunctions();
							game.moveEnemies();
							if (game.getLives() == postDeath) {
								text.setText("  You've died and lost a life!");
								text.setForeground(normal);
								text2.setText("");
							}
							looked = false;
						}
					} else if (e.getKeyCode() == KeyEvent.VK_A) {
						if (game.movePlayer('l')) {
							game.endTurnFunctions();
							game.moveEnemies();
							if (game.getLives() == postDeath) {
								text.setText("  You've died and lost a life!");
								text.setForeground(normal);
								text2.setText("");
							}
							looked = false;
						}
					} else if (e.getKeyCode() == KeyEvent.VK_S) {
						if (game.movePlayer('d')) {
							game.endTurnFunctions();
							game.moveEnemies();
							if (game.getLives() == postDeath) {
								text.setText("  You've died and lost a life!");
								text.setForeground(normal);
								text2.setText("");
							}
							looked = false;
						}
					} else if (e.getKeyCode() == KeyEvent.VK_D) {
						if (game.movePlayer('r')) {
							game.endTurnFunctions();
							game.moveEnemies();
							if (game.getLives() == postDeath) {
								text.setText("  You've died and lost a life!");
								text.setForeground(normal);
								text2.setText("");
							}
							looked = false;
						}
					}
				} else if (pressed.size() == 2) {
					if (pressed.contains('l')) {
						if (!holdingLook) {
							if (!looked) {
								if (pressed.contains('w')) {
									resultLook = game.look('u');
									looked = true;
								} else if (pressed.contains('a')) {
									resultLook = game.look('l');
									looked = true;
								} else if (pressed.contains('s')) {
									resultLook = game.look('d');
									looked = true;
								} else if (pressed.contains('d')) {
									resultLook = game.look('r');
									looked = true;
								}

								text.setText("  There is " + resultLook + " at this space.");
								text.setForeground(normal);
								text2.setText(" ");
								holdingLook = true;
								if (resultLook.equals("A room is in the way."))
									text.setText("  " + resultLook);
							} else {
								text.setText(" You've already looked this turn! Move or shoot before");
								text.setForeground(normal);
								text2.setText(" looking again!");
							}
						}
					} else if (pressed.contains('k')) {
						if (!shot) {
							if (game.getBullets() == 0) {
								text.setText("  \"Can't shoot if my clip's empty.. Better find some ");
								text.setForeground(normal);
								text2.setText("  ammo.\"");
							} else if (pressed.contains('w')) {
								String result = game.shoot('u');
								text.setText("  " + result);
								text.setForeground(normal);
								text2.setText("");
								game.getPlayer().setBullets(game.getBullets() - 1);
								game.moveEnemies();
								if (game.getLives() == postDeath) {
									text.setText("  You've died and lost a life!");
									text2.setText("");
								}
								looked = false;
								game.endTurnFunctions();
							} else if (pressed.contains('a')) {
								String result = game.shoot('l');
								text.setText("  " + result);
								text.setForeground(normal);
								text2.setText("");
								game.getPlayer().setBullets(game.getBullets() - 1);
								game.moveEnemies();
								if (game.getLives() == postDeath) {
									text.setText("  You've died and lost a life!");
									text2.setText("");
								}
								looked = false;
								game.endTurnFunctions();
							} else if (pressed.contains('s')) {
								String result = game.shoot('d');
								text.setText("  " + result);
								text.setForeground(normal);
								text2.setText("");
								game.getPlayer().setBullets(game.getBullets() - 1);
								game.moveEnemies();
								if (game.getLives() == postDeath) {
									text.setText("  You've died and lost a life!");
									text2.setText("");
								}
								looked = false;
								game.endTurnFunctions();
							} else if (pressed.contains('d')) {
								String result = game.shoot('r');
								text.setText("  " + result);
								text.setForeground(normal);
								text2.setText("");
								game.getPlayer().setBullets(game.getBullets() - 1);
								game.moveEnemies();
								if (game.getLives() == postDeath) {
									text.setText("  You've died and lost a life!");
									text2.setText("");
								}
								looked = false;
								game.endTurnFunctions();
							}
							shot = true;
						}
					}
				}
			}
		}
		board.setBoard(game);
		refreshHUD();

		if (game.checkWin()) {
			displayEndLevel();
			if (e.getKeyChar() == KeyEvent.VK_ENTER)
				levelUp();
		}

		if (game.getLives() == 0) {
			text.setText("  \"geeettttttt dunked on!!!\"");
			text.setForeground(sans);
			text2.setText("  You've lost all your lives! [Press ENTER to continue.]");
			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
				dispose();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		pressed.remove(e.getKeyChar());
		if (pressed.contains('l'))
			if (!text.getText().equals("  There is a wall at this space.")
					&& !text.getText().equals("  There is nothing at this space."))
				game.endLook(getDirection(e.getKeyChar()));
		if (!game.checkWin() && game.getLives() != 0 && !(postDeath == game.getLives())) {
			generateText();
		}
		if (e.getKeyChar() == 'k')
			shot = false;
		if (e.getKeyChar() == 'l')
			holdingLook = false;
	}

	public void keyTyped(KeyEvent e) {
	}
	
	private void generateText() {
		Random rng = new Random();
		switch(rng.nextInt(200)) {
		case 1:
			text.setText("  \"are you ready to have a bad time?\" ");
			text.setForeground(sans);
			text2.setText(" ");
			text2.setForeground(normal);
			break;
		case 2:
			text.setText("  \"where are you going? don't you know how to greet");
			text.setForeground(sans);
			text2.setText("   a new pal?\" ");
			text2.setForeground(sans);
			break;
		case 3:
			text.setText("  * (You called out to your friends for help...)");
			text.setForeground(normal);
			text2.setText("    (... but nobody came.)");
			text2.setForeground(normal);
			break;
		case 4: case 100: case 104: case 44:
			text.setText("  \"if you step forward, you're really not going to");
			text.setForeground(sans);
			text2.setText("   like what happens next.\"");
			text2.setForeground(sans);
			break;
		case 5: 
			text.setText("  \"birds are singing, flowers are blooming. on days like");
			text.setForeground(sans);
			text2.setText("   these, kids like you... should be burning in hell.\"");
			text2.setForeground(sans);
			break;
		case 6:
			text.setText("  * (The feeling of your socks squishing as you step)");
			text.setForeground(frisk);
			text2.setText("    (gives you determination.)");
			text2.setForeground(frisk);
			break;
		case 7: case 132: case 93:
			text.setText("  * (A feeling of dread hangs over you...)");
			text.setForeground(frisk);
			text2.setText("    (But you stay determined.)");
			text2.setForeground(frisk);
			break;
		case 8:
			text.setText("  * (The sound of muffled rain on the cave top...)");
			text.setForeground(frisk);
			text2.setText("    (fills you with determination.)");
			text2.setForeground(frisk);
			break;
		case 9:
			text.setText("  * (There's a lone quiche on the floor. You leave it)");
			text.setForeground(normal);
			text2.setText("    (on the ground and tell it you'll be right back.)");
			text2.setForeground(normal);
			break;
		case 10:
			text.setText("  * (You accidentally step in DogResidu...)");
			text.setForeground(normal);
			text2.setText("    (Eww...)");
			text2.setForeground(normal);
			break;
		case 11:
			text.setText("  * (You found a Rock Candy on the ground.)");
			text.setForeground(normal);
			text2.setText("    (... Oh. Nevermind. It's just a rock.)");
			text2.setForeground(normal);
			break;
		case 12:
			if(game.enemyIsVisible() && game.getLives() == 1) {
			text.setText("  \"that expression.. that's the expression of someone who's");
			text.setForeground(sans);
			text2.setText("   died twice. how 'bout we make it a third?\"");
			text2.setForeground(sans);
			} else {
				text.setText(" ");
				text.setForeground(normal);
				text2.setText(" ");
				text2.setForeground(normal);
				break;
			}
			break;
		default:
			text.setText(" ");
			text.setForeground(normal);
			text2.setText(" ");
			text2.setForeground(normal);
			break;
		}
	}

	private char getDirection(char key) {
		switch (key) {
		case 'w':
			return 'u';
		case 's':
			return 'd';
		case 'a':
			return 'l';
		case 'd':
			return 'r';
		default:
			return 'o';
		}
	}

	private void buildMenuBar() {
		menuBar = new JMenuBar();

		buildGameMenu();
		buildModeMenu();

		menuBar.add(gameMenu);
		menuBar.add(modeMenu);
	}

	private void buildGameMenu() {
		newNormal = new JMenuItem("Start New Game [Normal] ");
		newNormal.addActionListener(new NormalListener());

		newHard = new JMenuItem("Start New Game [Hard]");
		newHard.addActionListener(new HardListener());

		newDebug = new JMenuItem("Start New Game [Debug]");
		newDebug.addActionListener(new DebugListener());

		newGod = new JMenuItem("Start New Game [God]");
		newGod.addActionListener(new GodListener());

		unstuck = new JMenuItem("Unstuck");
		unstuck.addActionListener(new UnstuckListener());
		
		exit = new JMenuItem("Exit Game");
		exit.addActionListener(new ExitListener());

		gameMenu = new JMenu("Game");
		gameMenu.add(newNormal);
		gameMenu.add(newHard);
		gameMenu.add(newDebug);
		gameMenu.add(newGod);
		gameMenu.addSeparator();
		gameMenu.add(unstuck);
		gameMenu.add(exit);
	}

	private void buildModeMenu() {
		boolean checkHardMode = game.getHardMode();
		hardMode = new JCheckBoxMenuItem("Hard Mode", checkHardMode);
		hardMode.addActionListener(new HardModeListener());

		ButtonGroup group = new ButtonGroup();

		playMode = new JRadioButtonMenuItem("Play Mode", game.checkMode("Play"));
		playMode.addActionListener(new ModeListener());

		debugMode = new JRadioButtonMenuItem("Debug Mode", game.checkMode("Debug"));
		debugMode.addActionListener(new ModeListener());

		godMode = new JRadioButtonMenuItem("God Mode", game.checkMode("God"));
		godMode.addActionListener(new ModeListener());

		group.add(playMode);
		group.add(debugMode);
		group.add(godMode);

		modeMenu = new JMenu("Mode");

		modeMenu.add(hardMode);
		modeMenu.addSeparator();
		modeMenu.add(playMode);
		modeMenu.add(debugMode);
		modeMenu.add(godMode);
	}

	private void buildEmpty() {
		empty = new JPanel();
		empty.setBackground(Color.BLACK);
	}

	private void buildHUD() {
		hud = new JPanel();
		hud.setBackground(Color.BLACK);
		hud.setLayout(new GridLayout(2, 1));
	
		JPanel gameInfoGroup = new JPanel();
		gameInfoGroup.setBackground(Color.BLACK);
		Border outer = BorderFactory.createLineBorder(Color.BLACK, 5);
		Border innerOuter = BorderFactory.createLineBorder(Color.WHITE, 2);
		Border innerInner = BorderFactory.createLineBorder(Color.BLACK, 2);
		Border inner = BorderFactory.createCompoundBorder(innerOuter, innerInner);
		gameInfoGroup.setBorder(BorderFactory.createCompoundBorder(outer, inner));
	
		JLabel empty = new JLabel("");
		gameInfoGroup.setLayout(new GridLayout(8, 1));
	
		Font font = new Font("DialogInput", Font.BOLD, 16);
	
		level = new JLabel("   Level: " + 0 + "  ", SwingConstants.CENTER);
		level.setFont(font);
		level.setForeground(Color.WHITE);
		level.setBackground(Color.BLACK);
	
		lives = new JLabel(" Lives: " + 0 + "  ", SwingConstants.LEFT);
		lives.setFont(font);
		lives.setForeground(Color.WHITE);
		lives.setBackground(Color.BLACK);
		lives.setIcon(new ImageIcon("res/lives_icon.png"));
	
		bullets = new JLabel(" Bullets: " + 0 + "  ", SwingConstants.LEFT);
		bullets.setFont(font);
		bullets.setForeground(Color.WHITE);
		bullets.setBackground(Color.BLACK);
		bullets.setIcon(new ImageIcon("res/bullets_icon.png"));
	
		enemies = new JLabel(" Enemies: " + 0 + "  ", SwingConstants.LEFT);
		enemies.setFont(font);
		enemies.setForeground(Color.WHITE);
		enemies.setBackground(Color.BLACK);
		enemies.setIcon(new ImageIcon("res/enemy_icon.png"));
	
		shield = new JLabel(" Shield: " + 0 + "  ", SwingConstants.LEFT);
		shield.setFont(font);
		shield.setForeground(Color.WHITE);
		shield.setBackground(Color.BLACK);
		shield.setIcon(new ImageIcon("res/shield_icon.png"));
	
		gameInfoGroup.add(level);
		gameInfoGroup.add(lives);
		gameInfoGroup.add(bullets);
		gameInfoGroup.add(enemies);
		gameInfoGroup.add(shield);
		gameInfoGroup.add(empty);
		gameInfoGroup.add(empty);
		gameInfoGroup.add(empty);
	
		foundIcons = new JPanel();
		foundIcons.setLayout(new FlowLayout());
		foundIcons.setBackground(Color.BLACK);
		foundIcons.setBorder(BorderFactory.createCompoundBorder(outer, inner));
		foundIcons.setLayout(new GridLayout(4, 1));
	
		found = new JLabel("  Power-ups Found:  ", SwingConstants.CENTER);
		found.setFont(font);
		found.setForeground(Color.WHITE);
		found.setBackground(Color.BLACK);
	
		ImageIcon notFound = new ImageIcon("res/powerup_not_found.png");
		foundAmmo = new JLabel("", SwingConstants.CENTER);
		foundAmmo.setIcon(notFound);
		foundLocator = new JLabel("", SwingConstants.CENTER);
		foundLocator.setIcon(notFound);
		foundShield = new JLabel("", SwingConstants.CENTER);
		foundShield.setIcon(notFound);
	
		foundIcons.add(found);
		foundIcons.add(foundAmmo);
		foundIcons.add(foundLocator);
		foundIcons.add(foundShield);
	
		hud.add(gameInfoGroup);
		hud.add(foundIcons);
	}

	public void refreshHUD() {
		level.setText(" Level: " + game.getLevel() + "  ");
		lives.setText(" Lives: " + game.getLives() + "  ");
		bullets.setText(" Bullets: " + game.getBullets() + "  ");
		enemies.setText(" Enemies: " + game.getEnemies() + "  ");
		shield.setText(" Shield: " + game.getInvincibility() + "  ");
		if (!game.scanFor("Ammo"))
			foundAmmo.setIcon(new ImageIcon("res/bullets_found.png"));
		if (!game.scanFor("Locator"))
			foundLocator.setIcon(new ImageIcon("res/map_found.png"));
		if (!game.scanFor("Shield"))
			foundShield.setIcon(new ImageIcon("res/artifact_found.png"));
	}

	private void buildTextField() {
		textField = new JPanel();
		textField.setLayout(new GridLayout(2, 1));
		textField.setBackground(Color.BLACK);
		Border outerOuter = BorderFactory.createLineBorder(Color.BLACK, 10);
		Border outerInner = BorderFactory.createLineBorder(Color.WHITE, 3);
		Border innerOuter = BorderFactory.createLineBorder(Color.BLACK, 5);
		Border innerInner = BorderFactory.createLineBorder(Color.WHITE, 2);
	
		Border outsideBorder = BorderFactory.createCompoundBorder(outerOuter, outerInner);
		Border insideBorder = BorderFactory.createCompoundBorder(innerOuter, innerInner);
		textField.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
	
		text = new JLabel("  ");
		text.setFont(new Font("DialogInput", Font.BOLD, 16));
		text.setForeground(normal);
	
		text2 = new JLabel("  ");
		text2.setFont(new Font("DialogInput", Font.BOLD, 16));
		text2.setForeground(normal);
	
		textField.add(text);
		textField.add(text2);
	}

	private void levelUp() {
		game.increaseLevel();
		int lives = game.getLives();
		game.levelReset();
		game.generateBoard(lives);
		board.setBoard(game);
		refreshHUD();
	}

	private void displayEndLevel() {
		text.setText("  You've made it out alive! Congrats!  ");
		text.setForeground(normal);
		text2.setText("  [Press Enter to Continue]");
	}

	public boolean gameOver() {
		if (game.getLives() == 0) {
			return true;
		} else
			return false;
	}

	public boolean quitToMenu() {
		return false;
	}

	private class HardModeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (hardMode.isSelected())
				game.setHardMode(true);
			else
				game.setHardMode(false);
		}
	}

	private class ModeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (playMode.isSelected()) {
				if (game.getDebugMode()) {
					game.executeDebug();
				} else if (game.getGodMode()) {
					game.executeGod();
				}
			} else if (debugMode.isSelected()) {
				if(!game.getDebugMode()) {
					if (game.getGodMode()) {
						game.executeGod();
					}
					game.executeDebug();
				}
			} else if (godMode.isSelected()) {
				if(!game.getGodMode()) {
					if (game.getDebugMode()) {
						game.executeDebug();
					}
				}
				game.executeGod();
			}
			refreshHUD();
			board.setBoard(game);
		}
	}

	private class NormalListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			changeBoard();
			refreshHUD();
			board.setBoard(game);
			game.setHardMode(false);
			game.setDebugMode(false);
			game.setGodMode(false);
			hardMode.setSelected(false);
			playMode.setSelected(true);
		}
	}

	private class HardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			changeBoard();
			game.setHardMode(true);
			game.setDebugMode(false);
			game.setGodMode(false);
			refreshHUD();
			board.setBoard(game);
			hardMode.setSelected(true);
			playMode.setSelected(true);
		}
	}

	private class DebugListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			changeBoard();
			game.setHardMode(false);
			game.executeDebug();
			game.setGodMode(false);
			refreshHUD();
			board.setBoard(game);
			hardMode.setSelected(false);
			debugMode.setSelected(true);
		}
	}

	private class GodListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			changeBoard();
			game.setHardMode(false);
			game.setDebugMode(false);
			game.executeGod();
			refreshHUD();
			board.setBoard(game);
			hardMode.setSelected(false);
			godMode.setSelected(true);
		}
	}
	
	private class UnstuckListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			game.unstuck();
			refreshHUD();
			board.setBoard(game);
		}
	}

	private class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	public void changeBoard() {
		game.gameReset();
		game.generateBoard();
		board.setBoard(game);
	}
}