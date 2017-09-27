import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class ExtractUtil {

	private static final String QUOTES = "\"";
	private static final String COMMA = ",";
	private static final String COLON = ":";

	private static List<List<String>> data;
	private static String[] arrayOfKeys;

	public static void extractValues(String url, String... keys) {

		arrayOfKeys = keys;
		String response = NetworkUtils.fetchData(url);

		System.out.println("Filtering data...");
		data = new ArrayList<>();

		for (int i = 0; i < keys.length; i++) {
			keys[i] = "\"" + keys[i] + "\"";
			int lastKnownPosition = 0;
			int beforeLastKnownPosition = 0;
			List<String> values = new ArrayList<>();

			while (lastKnownPosition >= beforeLastKnownPosition) {
				String extractedValue;
				int positionOfColon = 0;
				int startPositionOfKey = 0;
				int endPositionOfKey = 0;

				int positionOfFirstQuote = 0;
				int positionOfComma = 0;
				int positionOfLastQuote = 0;

				/**
				 * 
				 * Example: key -> account_id
				 * 
				 * 'a' -> startPositionOfKey 'd' -> endPositionOfKey
				 * 
				 * in our case, we use keys with quotes for example "account_id" first " is
				 * startPositionOfKey last " is endPositionOf key
				 * 
				 */
				startPositionOfKey = response.indexOf(keys[i], lastKnownPosition);
				endPositionOfKey = startPositionOfKey + keys[i].length() - 1;

				positionOfColon = response.indexOf(COLON, endPositionOfKey);

				positionOfFirstQuote = response.indexOf(QUOTES, startPositionOfKey + keys[i].length());
				positionOfComma = response.indexOf(COMMA, startPositionOfKey + keys[i].length());

				if (positionOfComma > positionOfFirstQuote) {
					positionOfLastQuote = response.indexOf(QUOTES, positionOfFirstQuote + 1);
					if (positionOfLastQuote < lastKnownPosition)
						break;
					extractedValue = response.substring(positionOfFirstQuote + 1, positionOfLastQuote);

					// System.out.println(extractedValue);
					values.add(extractedValue);

				} else {
					extractedValue = response.substring(positionOfColon + 1, positionOfComma).trim();
					values.add(extractedValue);
					positionOfLastQuote = response.indexOf(QUOTES, positionOfFirstQuote + 1);

				}

				beforeLastKnownPosition = lastKnownPosition;
				lastKnownPosition = positionOfLastQuote;

			}
			data.add(values);
		}

		System.out.println("Done.");

	}

	public static void printValues(String saveType) {
		PrintWriter writter = null;
		if (saveType.contains("sep")) {
			for (int i = 0; i < data.size(); i++) {
				List<String> x = data.get(i);
				try {
					String nameOfFile = arrayOfKeys[i].replaceAll("\"", "") + ".txt";
					writter = new PrintWriter(nameOfFile);
					for (String a : x)
						writter.println(a);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					writter.close();
				}
			}
		} else {

			List<String> combinedValues = new ArrayList<>();
			for (int i = 0; i < data.size(); i++) {
				List<String> values = data.get(i);
				combinedValues.addAll(values);
			}

			try {
				String nameOfFile = "";
				for (int i = 0; i < arrayOfKeys.length; i++)
					nameOfFile = nameOfFile + arrayOfKeys[i].replaceAll("\"", "");
				writter = new PrintWriter(nameOfFile+".txt");
				for (int i = 0; i < combinedValues.size()/2; i++) {
					writter.println(combinedValues.get(i)+"#"+combinedValues.get((combinedValues.size()/2)+i));
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				writter.close();
			}

		}
		System.out.println("Saved.");

	}

}
