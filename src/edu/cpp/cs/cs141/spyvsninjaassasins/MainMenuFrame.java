package edu.cpp.cs.cs141.spyvsninjaassasins;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author AAnthony
 * Class in charge of creating/maintaining the games Main Menu JFrame.
 */
public class MainMenuFrame extends JFrame {
	private static final long serialVersionUID = -6770149663878916276L;
	Scanner sc = new Scanner(System.in);
	private JPanel menu;
	private JPanel button1;
	private JButton newGame;
	private JPanel button2;
	private JButton loadGame;
	private JPanel button3;
	private JButton manual;
	private JPanel buttonPanel;
	private JButton optionsButton;
	private JButton quitButton;
	
	public MainMenuFrame() {
		try {
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("res/menu.png")))));
		} catch (IOException e) {
		}
		setTitle("Undertale 0.5: Escape From The Banished");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Color.BLACK);
		setResizable(false);

		buildMenuPane();
		buildButtonPanel();

		JLabel topEmpty = new JLabel();
		topEmpty.setIcon(new ImageIcon("res/menu_empty.png"));
		
		add(topEmpty, BorderLayout.NORTH);
		add(menu, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

	private void buildMenuPane() {
		menu = new JPanel(new GridLayout(7, 1));
		menu.setOpaque(false);
		
		newGame = new JButton("[1] Start New Game");
		newGame.setMnemonic(KeyEvent.VK_1);
		newGame.setDisplayedMnemonicIndex(1);
		loadGame = new JButton("[2] Load Game");
		loadGame.setMnemonic(KeyEvent.VK_2);
		loadGame.setDisplayedMnemonicIndex(1);
		manual = new JButton("[3] How to Play");
		manual.setMnemonic(KeyEvent.VK_3);
		manual.setDisplayedMnemonicIndex(1);

		newGame.addActionListener(new NewGameListener());
		loadGame.addActionListener(new LoadGameListener());
		manual.addActionListener(new ManualListener());

		button1 = new JPanel();
		button1.setOpaque(false);
		button1.add(newGame);

		button2 = new JPanel();
		button2.setOpaque(false);
		button2.add(loadGame);

		button3 = new JPanel();
		button3.setOpaque(false);
		button3.add(manual);
		
		JLabel empty = new JLabel();
		empty.setIcon(new ImageIcon("res/empty.png"));

		for(int i = 0; i < 2; ++i) {
			menu.add(empty);
		}
		menu.add(button1);
		menu.add(button2);
		menu.add(button3);
		menu.add(empty);
		menu.add(empty);
	}

	private void buildButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
	
		optionsButton = new JButton("[4] Options");
		optionsButton.setMnemonic(KeyEvent.VK_4);
		optionsButton.setDisplayedMnemonicIndex(1);
		quitButton = new JButton("[5] Quit");
		quitButton.setMnemonic(KeyEvent.VK_5);
		quitButton.setDisplayedMnemonicIndex(1);
	
		optionsButton.addActionListener(new OptButtonListener());
		quitButton.addActionListener(new QuitButtonListener());
	
		buttonPanel.add(optionsButton);
		buttonPanel.add(quitButton);
	}

	private class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//setVisible(false);
			GameFrame gameFrame = new GameFrame(new GameEngine());
			gameFrame.changeBoard();
			gameFrame.refreshHUD();
		}
	}

	private class LoadGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser loadFile = new JFileChooser();
			int status = loadFile.showOpenDialog(null);
			if(status == JFileChooser.APPROVE_OPTION) {
				GameEngine game = new GameEngine();
				game.loadFile(loadFile.getSelectedFile());
				GameFrame gameFrame = new GameFrame(game);
				gameFrame.refreshHUD();
			}
		}
	}

	private class ManualListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			@SuppressWarnings("unused")
			ManualFrame manualFrame = new ManualFrame();
		}
	}

	private class OptButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class QuitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
}
