package memory;

import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
/**
* Project Svampish Memory game
* The Pictures class creates and holds an array of 16 images and one blank image
* @author Aroshine Munasinghe and Ludwig Sidenmark
* @date 2013-05-08
*/
public class Pictures {

	private int amount;
	private ArrayList<ImageIcon> images;
	private ImageIcon[] blank;
	private String[] imageNames = { "gary.jpg", "larry.jpg", "mrcrab.jpg",
			"patrick.jpg", "plankton.jpg", "sandy.jpg", "spongebob.jpg",
			"squidward.jpg", "baby.jpg", "fisk.jpg", "flying.jpg", "grupp.jpg",
			"karen.jpg", "mrspuff.jpg", "patchy.jpg", "pearlkrabs.jpg", };

	/**
	* Creats the lists containing the pictures.
	*/
	public Pictures(int n) {
		images = new ArrayList<ImageIcon>();
		blank = new ImageIcon[1];
		amount = n;
		setPictures();
		blank[0] = (createImageIcon("blank.jpg")); //The picture for all the unflipped cards which is a blank.jpg
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path) {
		URL pic = getClass().getResource("images/" + path);
		if (pic != null) {
			return new ImageIcon(pic);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	* Sets pictures and creates icons depending on the Amount of Cards chosen in the start of the game.
	*/
	public void setPictures() {
		for (int i = 0; i < (amount / 2); i++) {
			images.add(createImageIcon(imageNames[i]));
			images.add(createImageIcon(imageNames[i]));

		}
	}
	/**
	 * Returns the list containing all the images
	 * @return
	 */
	public ArrayList<ImageIcon> getImages() {
		return images;
	}
	/**
	 * returns the array containing the blank picture
	 * @return
	 */
	public ImageIcon[] getBlank() {
		return blank;
	}

}
