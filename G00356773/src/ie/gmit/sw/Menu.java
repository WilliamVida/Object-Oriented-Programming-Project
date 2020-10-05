package ie.gmit.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author William Vida
 * @version 1.0
 * 
 *  Menu UI lets users input file query file and language data set
 *  and gets the most common language of the query file from an ArrayList.
 */
public class Menu {
	public static String content;

	public String getFileData() {
		return this.content;
	}

	public void languageMenu() {
		Scanner in = new Scanner(System.in);

		String languageMode = "";
		String languageDataSetFile = "";
		String queryFile = "";
		int menuOption = 0;
		int nGramSize = 0;
		int count = 0;
		int maxCount = 0;

		Parser p = new Parser(languageDataSetFile, nGramSize);
		Database db = new Database();

		System.out.println("Enter query file");
		queryFile = in.nextLine();

		try {
			content = new Scanner(new File(queryFile)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Thread t2 = new Thread(content);

		// language data set file
		System.out.println("Name of the language data set file, including file type:");
		languageDataSetFile = in.nextLine();
		// wili-2018-Large-117500-Edited.txt
		// wili-2018-Small-11750-Edited.txt

		System.out.println("N-Gram size:");
		nGramSize = in.nextInt();

		p = new Parser(languageDataSetFile, nGramSize);

		p.setDb(db);
		Thread t1 = new Thread(p);

		t1.start();
		t2.start();

		db.resize(300);

		// language doesn't print if the below doesn't output
		System.out.println("Enter an integer to get the language");
		menuOption = in.nextInt();

		// https://www.javatpoint.com/java-program-to-find-the-most-repeated-word-in-a-text-file
		for (int i = 0; i < p.getLanguageList().size(); i++) {
			count = 1;
			i++;
			// Count each word in the file and store it in variable count
			for (int j = i + 1; j < p.getLanguageList().size(); j++) {
				if (p.getLanguageList().get(i).equals(p.getLanguageList().get(j))) {
					count++;
				}
			}
			// If maxCount is less than count then store value of count in maxCount
			// and corresponding word to variable word
			if (count > maxCount) {
				maxCount = count;
				languageMode = p.getLanguageList().get(i);
			}
		}

		System.out.println("Lanuguage of the file seems to be: " + languageMode);
		Thread.currentThread().interrupt();
		System.out.println("End");
		in.close();
	}

}
