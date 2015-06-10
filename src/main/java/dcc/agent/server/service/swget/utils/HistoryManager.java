package dcc.agent.server.service.swget.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class HistoryManager {

	File history_file;

	LinkedList<String> recent_look_ups;

	public HistoryManager() {
		recent_look_ups = readHistory();

	}

	public void addToHistory(String uri) {

		if (!recent_look_ups.contains(uri)) {
			if (recent_look_ups.size() < Constants.HISTORY_SIZE) {

				recent_look_ups.add(uri);

			} else {
				recent_look_ups.removeFirst();
				recent_look_ups.add(uri);
			}

			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(
						Constants.HISTORY_FILE, true));

				out.write(uri + "\n");
				out.close();
			} catch (IOException e) {
			}
		}

	}

	public LinkedList<String> readHistory() {
		LinkedList<String> res = new LinkedList<String>();

		history_file = new File(Constants.HISTORY_FILE);

		if (!history_file.exists()) {
			try {
				history_file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader(Constants.HISTORY_FILE));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String str;
			try {
				while ((str = in.readLine()) != null) {
					res.add(str);
				}

				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return res;
		}

		return res;
	}

	public LinkedList<String> getCurrentHistory() {
		return recent_look_ups;
	}

}
