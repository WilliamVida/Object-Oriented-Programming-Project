package ie.gmit.sw;

/**
 * @author William Vida
 * @version 1.0
 * 
 * Runner class that starts the program and calls languageMenu() from Menu.
 */
public class Runner {
	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.languageMenu();
	}

}
