// Part of glib: https://github.com/gnmmarechal/glib
package glib.utils.net;
import java.io.*;
import java.net.*;

public class Network{
	public static void downloadFile(final String urlString, final String fileName) throws Exception {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(new URL(urlString).openStream());
			fout = new FileOutputStream(fileName);
	
			final byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (fout != null) {
				fout.close();
			}
		}
    }
    public static String getText(String urlString) throws Exception {
        URL website = new URL(urlString);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            response.append(inputLine + "\n");

        in.close();

        return response.toString();
    }
}
