import java.io.File;
import java.util.*;
public class GenFields // Used just during development, unnecessary for program operation
{
	
	public static void main(String[] args) throws Exception
	{
		List<String> skillNames = Main.readFile(new File("skills.txt"));
		List<String> lines = new ArrayList<String>();
		
		for (int i = 0; i < skillNames.size() - 1; i++)
		{
			String gennedString = "jCheckBox" + String.valueOf(i+1) + ".setText(skillNames.get(" + String.valueOf(i) + "));";
			lines.add(gennedString);
		}
		
		Main.printFile(new File("lines.java"), lines);
	}
	
}
