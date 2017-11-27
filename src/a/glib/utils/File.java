// Part of glib: https://github.com/gnmmarechal/glib
package glib.utils;
import java.io.*;

public class File {

	public static void editFile(String filePath, int offset, int[] value) throws Exception
	{
		RandomAccessFile fileStore = new RandomAccessFile(filePath, "rw");
		fileStore.seek(offset);
		for (int i = 0; i < value.length; i++)
		{
			fileStore.write(value[i]);
		}	
		fileStore.close();
	}
}
