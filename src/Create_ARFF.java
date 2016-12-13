import java.util.ArrayList;
import java.util.Locale;
import java.text.*;

public class Create_ARFF {

	public static ArrayList<ArrayList<String>> trainingSamples = new ArrayList<ArrayList<String>>();
	public static float min = (float)4.6;
	public static float max = (float)7;
	//DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
	//df.setMaximumFractionDigits(340); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
	public static DecimalFormat df = new DecimalFormat("###.######");
	public static double d = 1.0012100e5;
	
	
	public static float normalize(float input)
	{
		float normalized = (float)(1 + (float)(input - min) * (float)(10 - 1)/(max - min));
		return normalized;
	}
	
	
	public static void loadEyeData(String eyeData , String trainingPath)
	{
		int i = 0;
		int j = 0;
		
		/*
		 * assigning the value of feature vectors to the training sample array
		 */
		String str = new String(trainingPath);
		//str = str.replace(".txt", "_filenumSequence.txt");
		
		for(String featureVector : eyeData.trim().split("\n"))
		{		
				String[] features = featureVector.trim().split(" ");
				ArrayList<String> row = new ArrayList<String>();
				System.out.println(features.length);
				for(int feature_index = 0; feature_index < features.length-2; feature_index++)
				{
					if(df.format(Float.parseFloat(features[feature_index].trim())).equals("-0"))
						row.add("0");
					else
						row.add(df.format(Float.parseFloat(features[feature_index].trim())));
				}

				row.add(df.format(Float.parseFloat(features[features.length-2].trim())));
				System.out.println(row.size() +"\n"+ row);
			//	System.exit(0);
				//Global.file_append(str, features[features.length-1].trim());
				trainingSamples.add(row);
				i++;
				j=0;
		}
		
	}
	
	public static void make_arff(String path)
	{
		Global.file_append(path, "@relation Features");
		
		for(int i=0 ; i < 64; i++)
		{
			Global.file_append(path, "@ATTRIBUTE word" + i + " NUMERIC");
		}
		int i,j;
		Global.file_append(path, "@ATTRIBUTE class {1,2,3,4,5,6,7,8,9,10}");
		Global.file_append(path, "@Data");
		String str = "";
		for(i = 0; i < trainingSamples.size(); i++)
		{
			//System.out.println("i = " + i);
			for (j = 0; j < 64; j++)
			{
				str += trainingSamples.get(i).get(j) + ",";
				//Global.file_append(path, trainingSamples.get(i).get(j) + ",");
			}
			str += df.format(Math.round(Float.parseFloat(trainingSamples.get(i).get(j))));
			//System.out.println(trainingSamples.get(i).get(j));
			//System.exit(0);
			Global.file_append(path, str);
			str = "";
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(df.format(d));
		//System.exit(0);
		//String path = "data_files/eyes_v6_avg.txt";
		//String path = "data_files/nose_v6_avg.txt";
		String path = "data_files/mouth_v6_avg.txt";
		String eyeData = Global.file_read(path);
		loadEyeData(eyeData, path);
		//make_arff("data_files/eyes_v6_avg.arff");
		//make_arff("data_files/nose_v6_avg.arff");
		make_arff("data_files/mouth_v6_avg.arff");

	}

}
