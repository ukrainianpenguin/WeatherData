import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Parser {
	public static void main(String[] args) throws FileNotFoundException {
		int startYear, startMonth, endYear, endMonth, choice;

		Scanner scan = new Scanner(System.in);

		System.out.println("Enter the start year");
		startYear = scan.nextInt();
		System.out.println("Enter the end year");
		endYear = scan.nextInt();
		System.out.println("Enter the start month");
		startMonth = scan.nextInt();
		System.out.println("Enter the end month");
		endMonth = scan.nextInt();

		System.out.println("Enter 1 for max temps, and 0 for min temps");
		choice = scan.nextInt();

		ConcurrentLinkedQueue<WeatherData> name = new ConcurrentLinkedQueue<WeatherData>();

		File folder = new File("ghcnd_hcn");
		File[] files = folder.listFiles();
		int count = 0;
		while (count < 1) {
			count++;
			for (File file : files) {

				if (file.getName().equals("ghcnd-stations.txt")) {

				} else {
					String s;
					if (choice == 0)
						s = "TMIN";
					else
						s = "TMAX";

					Scanner input = new Scanner(file);
					String thisLine = input.nextLine();

					while (input.hasNext()) {
						while (count > 5) {
							String element = thisLine.substring(17, 21);
							if (element.equals(s)) {

								String id = thisLine.substring(0, 11);
								int year = Integer.valueOf(thisLine.substring(11, 15).trim());
								int month = Integer.valueOf(thisLine.substring(15, 17).trim());
								if (startYear <= year && endYear >= year && startMonth <= month && endMonth >= month) {

									int days = (thisLine.length() - 21) / 8; // Calculate the number of days in the line
									for (int i = 0; i < days; i++) { // Process each day in the line.
										WeatherData wd = new WeatherData();
										wd.setDay(i + 1);
										int value = Integer.valueOf(thisLine.substring(21 + 8 * i, 26 + 8 * i).trim());
										String qflag = thisLine.substring(27 + 8 * i, 28 + 8 * i);
										wd.setId(id);
										wd.setYear(year);
										wd.setMonth(month);
										wd.setElement(element);
										wd.setValue(value);
										wd.setQflag(qflag);
										if (wd.getValue() != -9999 && qflag.equals(" "))
											name.add(wd);
										System.out.println(wd.getValue());
									}
								}
							}
							thisLine = input.nextLine();
							count++;
						}

					}

				}

			}
			System.out.println("Oleg");
		}
		ArrayList<WeatherData> r = findbest(name, choice);
		for (WeatherData w : r) {
			System.out.println(w);
		}
	}

	public static ArrayList<WeatherData> findbest(ConcurrentLinkedQueue<WeatherData> name, int choice) {
		ArrayList<WeatherData> r = new ArrayList<WeatherData>();
		// Max temp
		if (choice == 1) {
			int loop = 0;
			while (loop > 5) {
				WeatherData high = new WeatherData();
				high.setValue(0);
				for (WeatherData w : name) {
					if (w.getValue() > high.getValue()) {
						high = w;
					}

				}
				r.add(high);
				name.remove(high);
				loop++;
			}

		}

		return r;

	}

}