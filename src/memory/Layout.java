package memory;

/**
 * Project Svampish Memory game
 * The Layout class makes the window and handles menu events.
 * @author Aroshine Munasinghe and Ludwig Sidenmark
 * @date 2013-05-08
 */
import java.util.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Layout {
	private JButton[] buttons;
	private boolean firstCardup = false;
	private ImageIcon firstCard;
	private JButton firstButton;
	private int Players, nrTurns, totalScore, CardAmount;
	private int currentPlayer = 1;
	private JLabel[] scoreBoard;
	private JLabel turn;
	private int[] scores;
	private JFrame frame;
	private JPanel panel;
	private ArrayList<ImageIcon> images;
	private ImageIcon[] blank;

	/**
	 * The panel shows subwindows of options that can be chosen by the player,
	 * such as the amount of players and the amount of cards
	 */
	public Layout() {
	}

	public void jPanel() {
		String NrPlay = JOptionPane.showInputDialog(null,
				"Choose the amount of players, (1-4)");
		if (NrPlay == null) {
			System.exit(0);
		}
		boolean checkPlayer = true;

		while (checkPlayer == true) {
			if (!(NrPlay.matches("[1-4]"))) {
				NrPlay = JOptionPane.showInputDialog(null,
						"Choose the amount of players, (1-4)");
			} else
				checkPlayer = false;
		}

		Players = Integer.parseInt(NrPlay);

		frame = new JFrame();

		chooseCards(); // choose the amount of cards.

		Pictures pic = new Pictures(CardAmount);
		images = pic.getImages();
		blank = pic.getBlank();
		JPanel north = new JPanel();
		buttons = new JButton[CardAmount];
		scores = new int[Players];
		scoreBoard = new JLabel[Players];

		Collections.shuffle(images); // randomizes the order of the pictures

		turn = new JLabel("Turns: " + nrTurns);
		JButton playAgain = new JButton("Play Again");
		playAgain.addActionListener(new ExitListener());

		// the total score of each player will be shown on top panel of the
		// window
		for (int i = 0; i < Players; i++) {
			scoreBoard[i] = new JLabel(newScore((i + 1), scores[i]));
			north.add(scoreBoard[i]);

		}

		north.add(turn);
		north.add(playAgain);

		frame.add(north, BorderLayout.NORTH);
		frame.add(panel);
		frame.setTitle("Memory Game");

		for (int i = 0; i < CardAmount; i++) {
			buttons[i] = new JButton();
			buttons[i].setIcon(blank[0]);
			buttons[i].setName(String.valueOf(i));
			panel.add(buttons[i]);
			buttons[i].addActionListener(new ButtonListener());

		}
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * The class for the playagain button. Creates a new game and closes the old
	 * one.
	 */
	private class ExitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			restart();
			frame.dispose();
		}

	}

	/**
	 * When buttons are pressed an image will turn up and compare it to each
	 * other In multiple game: if the images are not pairs, the next player gets
	 * its turn if the images are pairs the pairs will remain turned up and the
	 * player can continue next move.
	 * 
	 * In single game:
	 * 
	 */
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			JButton b = (JButton) event.getSource();
			int number = Integer.parseInt(b.getName());
			ImageIcon image = images.get(number);
			b.setIcon(image);

			if (firstCardup == false) { // if it is the first card to be flipped
				firstCard = image;
				firstButton = b;
				firstCardup = true;
				firstButton.removeActionListener(this);
				nrTurns++;
				turn.setText("Turns: " + nrTurns);

			} else { // if it is the second card to be flipped
				if (firstCard.getImage() == image.getImage()) { // if it is a
																// pair
					firstCardup = false;
					b.removeActionListener(this);
					JOptionPane.showMessageDialog(null, "It was a match!\n"
							+ returnTurn(true));

					scores[currentPlayer - 1]++;
					scoreBoard[currentPlayer - 1].setText(newScore(
							currentPlayer, scores[currentPlayer - 1]));
					totalScore++;
					checkFinish();
				} else { // if it is not a pair
					JOptionPane.showMessageDialog(null, "It was not a match!\n"
							+ returnTurn(false));
					firstButton.setIcon(blank[0]);
					b.setIcon(blank[0]);
					firstCardup = false;
					firstButton.addActionListener(new ButtonListener());
				}

			}
		}

	}

	/**
	 * In multiple game, when the first player's images are false then the
	 * second player gets its turn
	 * 
	 * @param pair
	 * @return string and currentplayer
	 */
	private String returnTurn(boolean pair) {
		if (pair == false)
			currentPlayer++;
		if (currentPlayer > Players)
			currentPlayer = 1;
		return "Player " + currentPlayer + "'s turn";

	}
	/**
	 * Checks if the game has ended
	 */
	private void checkFinish(){
		if(totalScore == (CardAmount/2)){

			JOptionPane.showMessageDialog(null, "Congratulations! The game has ended\n" + "Turns: " + nrTurns + printScores());

	}
	}
	/**
	 * Prints each players scores
	 * @return Final score
	 */
	private String printScores(){
		String scoresFinish = "\nFinal Score:";
		for(int i = 0; i < Players; i++){
			scoresFinish += "\n Player " + (i + 1) + ": " + scores[i];
		}
		
		return scoresFinish;
	}


	/**
	 * When pressed Play Again the game will restartand creates a new game in a
	 * new thread.
	 * 
	 */
	private void restart() {

		Thread t = new Thread() {
			public void run() {
				String[] args = {};
				Memory.main(args);
			}
		};
		t.run();
	}

	/**
	 * This method lets the player choose the amount of cards he or she wants in
	 * the game. The Jpanel then makes a suitable size for the game window
	 * depending on the amount of cards chosen. The player can choose between
	 * 16, 24 or 32 cards.
	 */
	private void chooseCards() {

		Object[] options = { "8", "12", "16" };
		CardAmount = JOptionPane.showOptionDialog(null,
				"Choose amount of pairs", "Cards", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, 0);

		if (CardAmount == 0) {
			CardAmount = 16;

			frame.setSize(800, 800);
			panel = new JPanel(new GridLayout(4, 4));
		} else if (CardAmount == 1) {
			CardAmount = 24;
			frame.setSize(1200, 800);
			panel = new JPanel(new GridLayout(4, 6));
		} else {
			CardAmount = 32;
			frame.setSize(1600, 800);
			panel = new JPanel(new GridLayout(4, 8));
		}
	}
	/**
	 * returns the new score for the choosen player
	 * @param player
	 * @param score
	 * @return New score
	 */
	public String newScore(int player, int score) {

		return "Player " + player + ": " + score + " points |";

	}

}
