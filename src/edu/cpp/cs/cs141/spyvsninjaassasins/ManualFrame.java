package edu.cpp.cs.cs141.spyvsninjaassasins;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author AAnthony
 * Class in charge of creating the game JFrame that displays the manual for new users.
 */
public class ManualFrame extends JFrame {
	private static final long serialVersionUID = 7263005645999798155L;
	@SuppressWarnings("rawtypes")
	private JList toc; //Table of Contents
	private JLabel manualPage;
	private String[] pages = {"1 - Story", "2 - Goal", "3 - Turn Order",
			"4 - Moving", "5 - Looking", "6 - Shooting",
			"7 - Rooms" , "8 - Power-Ups", "9 - Saving" };
	
	public ManualFrame() {
		setTitle("Undertale 0.5: Player Manual");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Color.BLACK);
		
		buildPage();
		buildTOC();
		
		add(toc, BorderLayout.WEST);
		add(manualPage, BorderLayout.EAST);
		
		pack();
		setVisible(true);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void buildTOC() {
		toc = new JList(pages);
		toc.setBorder(BorderFactory.createTitledBorder("Pages"));
		toc.setBackground(Color.BLACK);
		toc.setFont(new Font("DialogInput", Font.BOLD, 16));
		toc.setForeground(Color.WHITE);
		toc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		toc.addListSelectionListener(new ListListener());
	}
	
	private class ListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			String selection = (String)toc.getSelectedValue();
			switch(selection) {
			case "1 - Story":
				manualPage.setIcon(new ImageIcon("res/manual_1_story.png"));
				break;
			case "2 - Goal":
				manualPage.setIcon(new ImageIcon("res/manual_2_goal.png"));
				break;
			case "3 - Turn Order":
				manualPage.setIcon(new ImageIcon("res/manual_3_turn_order.png"));
				break;
			case "4 - Moving":
				manualPage.setIcon(new ImageIcon("res/manual_4_moving.png"));
				break;
			case "5 - Looking":
				manualPage.setIcon(new ImageIcon("res/manual_5_looking.png"));
				break;
			case "6 - Shooting":
				manualPage.setIcon(new ImageIcon("res/manual_6_shooting.png"));
				break;
			case "7 - Rooms":
				manualPage.setIcon(new ImageIcon("res/manual_7_rooms.png"));
				break;
			case "8 - Power-Ups":
				manualPage.setIcon(new ImageIcon("res/manual_8_powerups.png"));
				break;
			case "9 - Saving":
				manualPage.setIcon(new ImageIcon("res/manual_9_saving.png"));
				break;
			}
		}	
	}
	private void buildPage() {
		manualPage = new JLabel();
		manualPage.setIcon(new ImageIcon("res/manual_0_blank.png"));
	}
}
