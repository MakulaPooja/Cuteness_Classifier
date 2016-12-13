import java.io.*;
import java.util.ArrayList;

public class appendAnnotation {
	
	
	public static ArrayList<String> storeAll(String[] arr)
	{
/*		System.out.println(arr.length);
		System.exit(0);*/
		ArrayList<String> res = new ArrayList<String>();
		for(String word : arr)
		{
			res.add(word);
		}
		return new ArrayList<String>(res);
	}
	
	public static String listToString(ArrayList<String> list)
	{
		String res = "";
		for(String e : list)
		{
			res += e.trim() + " ";
		}
		return new String(res.trim());
	}
	
	public static String appendAnnot(String vector, String annot)
	{
		ArrayList<String> words = new ArrayList<String>();
		words = storeAll(vector.trim().split(" "));
		words.add(words.size() - 1, annot);
		String res = new String(listToString(words));
		return res;
	}
	
	public static void appendAllAnnot(String oldFileName, String annotFileName) throws IOException
	{
		String[] oldFile = Global.file_read(oldFileName).split("\n"); //reading the object file
		String[] annots = Global.file_read(annotFileName).split("\n");	//reading the annotations file
		String tempFileName = "data_files/temp.txt";	//this is the temporary file	
		String line;
		
		for(int i = 0 ; i < oldFile.length ; i++)
		{
			line = oldFile[i];
			String newLine = appendAnnot(line.trim(), annots[i]);
			System.out.println(newLine);
			Global.file_append(tempFileName, newLine);
		}
		
		/*
		 * deleting the old file after appending the annotations
		 */
		File old = new File(oldFileName);
		old.delete();
		
		/*
		 * renaming the temp file as the old file
		 */
		File newFile = new File(tempFileName);
		newFile.renameTo(old);
	}
	
	public static void main(String[] args) throws IOException {
		
		String eyesP = "data_files/eyes_hog_features_median_v1_128x128.txt";
		String noseP = "data_files/nose_hog_features_median_v1_128x128.txt";
		String mouthP = "data_files/mouth_hog_features_median_v1_128x128.txt";
//		String avgValuesPath = "data_files/final_avg_values.txt";
		String medianValuesPath = "data_files/final_median_values.txt";
		String test  = "data_files/akkad.txt";
		
		System.out.println("updating for eyes...");
//		appendAllAnnot(eyesP,avgValuesPath);
		appendAllAnnot(eyesP,medianValuesPath);
		
		System.out.println("updating for nose...");
//		appendAllAnnot(noseP,avgValuesPath);
		appendAllAnnot(noseP,medianValuesPath);
		
		System.out.println("updating for mouth...");
//		appendAllAnnot(mouthP,avgValuesPath);
		appendAllAnnot(mouthP,medianValuesPath);
		
	}

}
