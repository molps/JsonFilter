import java.util.Scanner;

public class MainActivity {

	public static void main(String[] args) {
		String url;

		String saveType = "";
		Scanner scanner = new Scanner(System.in);
		System.out.print("Paste URL to fetch data: ");
		url = scanner.nextLine();
		int numberOfKeys = 0;
		int i = 0;
		System.out.print("Number of keys for filtration: ");
		numberOfKeys = scanner.nextInt();
		scanner.nextLine();
		String[] key = new String[numberOfKeys];
		while (i < numberOfKeys) {
			System.out.print("Enter " + (i + 1) + ". key: ");
			key[i] = scanner.nextLine();
			i++;
		}
		System.out.print("Loading data, please wait...");

		ExtractUtil.extractValues(url, key);
		if (key.length > 1)
			while (true) {

				System.out.print(
						"Do you want to save data separately or to combine with regex?\nType \"sep\" or \"com\": ");
				saveType = scanner.nextLine();
				if (saveType.contains("sep") | saveType.contains("com"))
					break;

			}

		ExtractUtil.printValues(saveType);
		scanner.close();

	}
}
