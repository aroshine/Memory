package memory;

import javax.swing.JOptionPane;

/**
 * Project Svampish Memory game The main class for our program
 * 
 * @author Aroshine Munasinghe and Ludwig Sidenmark
 * @date 2013-05-08
 */
public class Memory {
	/**
	 * The main method of the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JOptionPane
				.showMessageDialog(
						null,
						"This is a memory game, In a memory game you are supposed to match the different pairs on the gameboard. \nIf you manage to match a pair its your turn again. If not the turn is given to the next player.\n The player with the most pairs win!\n GOOD LUCK!");
		Layout Panel = new Layout();
		Panel.jPanel();
	}
}
