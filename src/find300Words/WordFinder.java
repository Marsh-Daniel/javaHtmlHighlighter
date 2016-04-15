package find300Words;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WordFinder {
	private static final Path wordListPath = Paths.get("wordlist.txt");
	private static final File wordListFile = wordListPath.toFile();
	private static final Path urlPath = Paths.get("urlFile.txt");
	private static final File urlFile = urlPath.toFile();

	static List<String> wordImporter() throws FileNotFoundException {
		BufferedReader input = new BufferedReader(new FileReader(wordListFile));
		List<String> words = new ArrayList<String>();
		try {
			while (input.ready()) {

				words.add(input.readLine());

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("We have come across an issue try again.");
		}
		
		return words;
	}

	public static void saveHttpFile() throws FileNotFoundException {
		BufferedReader input = new BufferedReader(new FileReader(urlFile));

		try {
			textWriter(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("something went wrong try again" + e);
		}
	}

	private static void textWriter(BufferedReader input) throws IOException, MalformedURLException {
		while (input.ready()) {
			String urlName = input.readLine();
			List <String> words = new ArrayList<>();
			words=wordImporter();
			String fileName = getValidFileName(urlName);
			Paths.get(fileName);
			URL website = new URL(urlName.trim());
			URLConnection yc = (URLConnection) website.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine = "";
			PrintWriter out = new PrintWriter((new FileWriter(fileName + ".html")));
			while ((inputLine = in.readLine()) != null) {
				for (int i = 0; i < words.size(); i++) {
					if (inputLine.contains(" " + words.get(i) +" ") ||( inputLine.contains(" " + words.get(i) +"."))) {
						out.println(inputLine.replaceAll(words.get(i), "<mark>"+words.get(i)+"</mark>"));
					}
						
					
					
				}
				
			}
				out.close();
				in.close();

			
		}
	}

	private static String getValidFileName(String urlName) {
		String fileName = urlName.substring(30);
		return fileName;
	}

}
