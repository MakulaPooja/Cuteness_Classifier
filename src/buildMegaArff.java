import java.util.*;
import java.io.*;

public class buildMegaArff {

	public static ArrayList<String> vects = new ArrayList<String>();
	public static int noOfFeatures = 3;
	
	public static void store(String[] file)
	{
		for(String str : file)
		{
			if(str.length() > 0)
			{
				vects.add(str);
			}
		}
	}
	
	public static void makeMegaArff(String path, String file)
	{
		String[] vectors = file.split("\n");
		store(vectors);
		
		Global.file_append(path, "@relation Cuteness");
		
		for(int i = 0; i<noOfFeatures ; i++)
		{
			Global.file_append(path, "@ATTRIBUTE word" + i + " NUMERIC");
		}
		
		Global.file_append(path, "@ATTRIBUTE class {1,2,3,4,5,6,7,8,9,10}");
		Global.file_append(path, "@Data");
		
		for(String str : vects)
			Global.file_append(path, str);
		
	}
	public static void main(String[] args) {
		
		String raw = Global.file_read("data_files/hog_MegaPerceptronData_median_128_v1.txt");
		//String path = "data_files/megaPerceptron_v1.arff";
		String path = "data_files/arff_files/hog_MegaPerceptronData_median_128_v1.arff";
		makeMegaArff(path,raw);

	}

}
