package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author William Vida
 * @version 1.0
 * 
 * Used to parse the database and the language data set file 
 * and to add the language of the kmer to an ArrayList.
 */
public class Parser implements Runnable {
	private Database db = null;
	private String file;
	private int k;
	private ArrayList<String> languageList = new ArrayList<>();
	Menu menu = new Menu();

	public ArrayList<String> getLanguageList() {
		return languageList;
	}

	public Parser(String file, int k) {
		this.file = file;
		this.k = k;
	}

	public void setDb(Database db) {
		this.db = db;
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;

			while ((line = br.readLine()) != null) {
				String[] record = line.trim().split("@");
				if (record.length != 2)
					continue;
			parse(record[0], record[1]);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parse(String text, String lang, int... ks) {
		Language language = Language.valueOf(lang);
		String queryFile = menu.getFileData();

		for (int i = 0; i < text.length() - k; i++) {
			CharSequence kmer = text.substring(i, i + k);
			db.add(kmer, language);

			// add the language of the kmer to an arraylist
			if (queryFile.toLowerCase().contains(kmer.toString().toLowerCase())) {
				languageList.add(lang);
			}
		}
	}

}

