import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

	private static final String PROTOCOL_HTTPS = "https://";
	private static final String PROTOCOL_HTTP = "http://";

	public static String fetchData(String urlString) {
		if (!urlString.startsWith(PROTOCOL_HTTPS) && (!urlString.startsWith(PROTOCOL_HTTP))) {
			urlString = PROTOCOL_HTTPS + urlString;
		} else if (urlString.startsWith(PROTOCOL_HTTP))
			urlString = urlString.replace(PROTOCOL_HTTP, PROTOCOL_HTTPS);
		URL url = null;
		Scanner scanner = null;
		HttpURLConnection connection = null;
		String data = null;

		try {
			url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter("\\A");
			if (scanner.hasNext()) {
				data = scanner.next();
			} else
				return null;
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			scanner.close();
			connection.disconnect();

		}
		System.out.println("Done.");
		return data;
	}
}
