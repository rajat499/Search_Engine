import java.io.*;
import java.util.*;

public class Checker{
	public static void main ( String args [])
	{
		BufferedReader br = null;
		SearchEngine s = new SearchEngine();

		try {
			String actionString;
			br = new BufferedReader(new FileReader("actions.txt"));
			FileOutputStream fs = new FileOutputStream("out.txt", false);
			PrintStream p = new PrintStream(fs);

			while ((actionString = br.readLine()) != null) {
				String str = s.performAction(actionString);
				p.println(str);
			}
			System.out.println("Output written to file out.txt");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}
