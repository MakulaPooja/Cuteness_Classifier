import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Distribution {

	public static HashMap<Integer, Integer> eyeFeaturesFreq = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> noseFeaturesFreq = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> mouthFeaturesFreq = new HashMap<Integer, Integer>();
	public static ArrayList<Integer> allF = new ArrayList<Integer>(); 
	public static ArrayList<Integer> noneF = new ArrayList<Integer>();
	public static ArrayList<Integer> eyesF = new ArrayList<Integer>();
	public static ArrayList<Integer> noseF = new ArrayList<Integer>();
	public static ArrayList<Integer> mouthF = new ArrayList<Integer>();
	public static ArrayList<Integer> eyesNnoseF = new ArrayList<Integer>();
	public static ArrayList<Integer> noseNmouthF = new ArrayList<Integer>();
	public static ArrayList<Integer> eyesNmouthF = new ArrayList<Integer>();
	public static int noOfImages = 117;
	
	//-------------------------------------------------------------------------
	public static void init()
	{
		for(int i = 1; i <= noOfImages; i++)
		{
			eyeFeaturesFreq.put(i, 0);
			noseFeaturesFreq.put(i, 0);
			mouthFeaturesFreq.put(i, 0);
		}
	}
	//-------------------------------------------------------------------------
	
	
	//-------------------------------------------------------------------------
	public static void showDistribution()
	{
		/*
		 * checking which images' what features are absent in the whole corpus
		 */
		int all = 0;
		int none = 0;
		int eyes = 0;
		int nose = 0;
		int mouth = 0;
		int eyesNnose = 0;
		int noseNmouth = 0;
		int eyesNmouth = 0;
		
		for (int i = 1; i <= noOfImages; i++)
		{
			if((eyeFeaturesFreq.get(i) > 0) && (noseFeaturesFreq.get(i) > 0) && (mouthFeaturesFreq.get(i) > 0))
			{
				all++;
				allF.add(i);
				continue;
			}
			
			else if((eyeFeaturesFreq.get(i) > 0) && (noseFeaturesFreq.get(i) > 0) && (mouthFeaturesFreq.get(i) == 0))
			{
				eyesNnose++;
				eyesNnoseF.add(i);
				continue;
			}
			else if((eyeFeaturesFreq.get(i) == 0) && (noseFeaturesFreq.get(i) > 0) && (mouthFeaturesFreq.get(i) > 0))
			{
				noseNmouth++;
				noseNmouthF.add(i);
				continue;
			}
			else if((eyeFeaturesFreq.get(i) > 0) && (noseFeaturesFreq.get(i) == 0) && (mouthFeaturesFreq.get(i) > 0))
			{
				eyesNmouth++;
				eyesNmouthF.add(i);
				continue;
			}
			else if((eyeFeaturesFreq.get(i) > 0) && (noseFeaturesFreq.get(i) == 0) && (mouthFeaturesFreq.get(i) == 0))
			{
				eyes++;
				eyesF.add(i);
				continue;
			}
			else if((eyeFeaturesFreq.get(i) == 0) && (noseFeaturesFreq.get(i) > 0) && (mouthFeaturesFreq.get(i) == 0))
			{
				nose++;
				noseF.add(i);
				continue;
			}
			else if((eyeFeaturesFreq.get(i) == 0) && (noseFeaturesFreq.get(i) == 0) && (mouthFeaturesFreq.get(i) > 0))
			{
				mouth++;
				mouthF.add(i);
				continue;
			}
			else if((eyeFeaturesFreq.get(i) == 0) && (noseFeaturesFreq.get(i) == 0) && (mouthFeaturesFreq.get(i) == 0))
			{
				none++;
				noneF.add(i);
			}
		}
		
		System.out.println("Only eyes : " + eyes + "\n" + eyesF);
		System.out.println("Only nose : " + nose + "\n" + noseF);
		System.out.println("Only mouth : " + mouth + "\n" + mouthF);
		System.out.println("eyes and nose : " + eyesNnose + "\n" + eyesNnoseF);
		System.out.println("nose and mouth : " + noseNmouth + "\n" + noseNmouthF);
		System.out.println("eyes and mouth : " + eyesNmouth + "\n" + eyesNmouthF);
		System.out.println("all : " + all + "\n" + allF);
		System.out.println("none : " + none + "\n" + noneF);
		
	}
	//-------------------------------------------------------------------------
	
	
	//-------------------------------------------------------------------------
	public static void storeFrequencies(String[] eyeVectors, String[] noseVectors, String[] mouthVectors)
	{
		int numEyeFeatures = eyeVectors.length;					//number of eye vectors obtained from eyes of 100 images
		int numNoseFeatures = noseVectors.length;				//number of nose vectors obtained from noses of 100 images
		int numMouthFeatures = mouthVectors.length;				//number of mouth vectors obtained from mouths of 100 images

		System.out.println("Eyes Features");
		for(int i = 0; i < numEyeFeatures; i++)
		{
			int len = eyeVectors[i].trim().split(" ").length;
			int key = (int)Float.parseFloat(eyeVectors[i].trim().split(" ")[len -1]);
			System.out.print(key + " ");
			eyeFeaturesFreq.put(key, eyeFeaturesFreq.get(key) + 1);
		}
		
		System.out.println("\nNose Features");
		for(int i = 0; i < numNoseFeatures; i++)
		{
			int len = noseVectors[i].trim().split(" ").length;			
			int key = (int)Float.parseFloat(noseVectors[i].trim().split(" ")[len -1]);
			System.out.print(key + " ");
			noseFeaturesFreq.put(key, noseFeaturesFreq.get(key) + 1);
		}
		
		System.out.println("\nMouth Features");
		for(int i = 0; i < numMouthFeatures; i++)
		{
			int len = mouthVectors[i].trim().split(" ").length;			
			int key = (int)Float.parseFloat(mouthVectors[i].trim().split(" ")[len -1]);
			System.out.print(key + " ");
			mouthFeaturesFreq.put(key, mouthFeaturesFreq.get(key) + 1);
		}
		System.out.println();
	}
	//-------------------------------------------------------------------------
	
	public static void main(String[] args) {
	
		String eyesPath = "data_files/eyes_v4.txt";
		String nosePath = "data_files/nose_v4.txt";
		String mouthPath = "data_files/mouth_v4.txt";
		
		String eyeData = Global.file_read(eyesPath);
		String noseData = Global.file_read(nosePath);
		String mouthData = Global.file_read(mouthPath);
		
		/*
		 * Initializing all the feature frequencies to 0.  
		 */
		init();
		
		String[] eyeVectors = eyeData.trim().split("\n");		//contains feature vectors obtained from eyes of 100 images
		String[] noseVectors = noseData.trim().split("\n");		//contains feature vectors obtained from noses of 100 images
		String[] mouthVectors = mouthData.trim().split("\n");	//contains feature vectors obtained from mouths of 100 images
		
		storeFrequencies(eyeVectors,noseVectors,mouthVectors);
		showDistribution();
	}

}