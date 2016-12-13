

/*
 * The following code helps collecting the predictions of different feature points coming from a particular image
 */
import java.util.*;


public class makeData {

	public static int orgStart = 0;
	public static int visStart = 0;
	public static ArrayList<String> orgVectors = new ArrayList<String>();
	public static ArrayList<String> visVectors = new ArrayList<String>();
	public static ArrayList<FeatureVector> Vectors = new ArrayList<FeatureVector>();
	public static ArrayList<String> extractedVisVectors = new ArrayList<String>();
	
	
	/*
	 * This method stores the vectors from an arff file to an array 
	 */
	
	public static ArrayList<String> storeVectors(String arff)
	{
		String[] lines = arff.split("\n");
		int vectorStarting = 0;
		ArrayList<String> vectors = new ArrayList<String>();
		
		//finding the point from where the instances start
		vectorStarting = starting(lines);
		System.out.println("Start Point : " + vectorStarting);
		for(int i = vectorStarting; i < lines.length; i++)
		{
			vectors.add(lines[i].trim());
		}
		
		return new ArrayList<String>(vectors);
	}
	
	public static int starting(String[] lines)
	{
		int vectorStarting = 0;
		
		for(int i = 0 ; i < lines.length; i++)
		{
			if(lines[i].toLowerCase().equals("@data"))
			{
				System.out.println("found : " + lines[i]);
				vectorStarting = i+1;	//storing the line number of the first instance
				break;
			}
		}
		
		return vectorStarting;
	}
	
	
	public static void extractFromVisualised()
	{
		for(String vctr : visVectors)
		{
			extractedVisVectors.add(vctr.trim().substring(0, vctr.length()-4));
			//System.out.println(vctr);
			//System.out.println(vctr.substring(0, vctr.length()-4));
		}
	}
	
	
	public static void print(ArrayList<String> list)
	{
		for(String vector : list)
			System.out.println(vector);
	}
	
	public static void matchNput(String matFile)
	{
		String[] matLines = matFile.split("\n");
		String visvctr = "";	//this is the visualised vector
		String vctr = "";	//this is the actual value of the 64D vector
		int vctrPred = 0;	//this is the predicted value of the vector
		int vctrAct = 0;	//this the actual value
		int vctrFilenum = 0;	
		//Vectors.clear();
		
		for (int i = 0; i < orgVectors.size(); i++)
		{
			System.out.println("==============================================================\n i = " + i);
			vctr = new String(orgVectors.get(i).trim());
			vctr = vctr.substring(0, vctr.length()-2);
			System.out.println(vctr);
			
			//finding the predicted value of the vector
			int vctrInd = extractedVisVectors.indexOf(vctr);
			System.out.println("vctrInd : " + vctrInd);
			String visVec = new String(visVectors.get(vctrInd).trim());
			System.out.println(visVec);
			vctrPred = Integer.parseInt(visVec.substring(visVec.length()-3, visVec.length()-2));
			
			//finding the actual value of the vector
			vctrAct = Integer.parseInt(visVec.substring(visVec.length()-1));
			System.out.println("vctrPred : " + vctrPred + "\t" + "vctrAct : " + vctrAct);
			
			//finding the file number of the vector to which it it is associated
			String matVctr = matLines[i];
			System.out.println("matVctr : " + matVctr);
			String[] broken = matVctr.trim().split(" ");
			System.out.println(broken[broken.length - 1]);
			vctrFilenum = Integer.parseInt(broken[broken.length - 1]);
			System.out.println("fileNum : " + vctrFilenum);
			
			//making the feature vector
			FeatureVector featureVector = new FeatureVector();
			featureVector.setValue(vctr);	//assigning value
			featureVector.setActual(vctrAct);	//assigning actual class 
			featureVector.setPredicted(vctrPred);	//assigning predicted class
			featureVector.setFileNumber(vctrFilenum);	//assigning file number
			
			Vectors.add(featureVector);	//vector added to the list
			
			vctr = "";	//this is the actual value of the 64D vector
			vctrPred = 0;	//this is the predicted value of the vector
			vctrAct = 0;	//this the actual value
			vctrFilenum = 0;	
			//System.exit(0);
		}
	}
	
	
	public static ArrayList<FeatureVector> returnVector(String orgArff, String visArff, String matFile)
	{
		orgVectors.clear();
		visVectors.clear();
		extractedVisVectors.clear();
		Vectors.clear();
		
		orgVectors = storeVectors(orgArff);
		System.out.println("==========original==========");
		System.out.println(orgVectors.size());
		
		visVectors = storeVectors(visArff);
		System.out.println("==========visualised==========");
		System.out.println(visVectors.size());
		
		extractFromVisualised();	//only the vector part of each of the vector in visualised arff file
		
		System.out.println("extracted vis vectors size : " + extractedVisVectors);
		//if(visVectors.size() == 106){
		//int ind = extractedVisVectors.indexOf("0.000124,-0.000203,0.000128,0.000215,-0.004111,-0.00418,0.007793,0.005006,-0.002098,0.005351,0.002878,0.0064,0.001151,0.00048,0.001151,0.00048,-0.000467,-0.00061,0.001195,0.002103,-0.167655,-0.227884,0.221453,0.236445,0.196999,-0.233856,0.277791,0.2836,-0.003501,0.00036,0.011881,0.003497,-0.00246,-0.003077,0.003605,0.004118,-0.199762,0.235896,0.265369,0.303986,0.271062,0.268199,0.282948,0.278636,-0.008698,0.003174,0.012133,0.004063,-0.005384,-0.003205,0.005409,0.003568,0.022559,-0.013634,0.026709,0.016273,0.000659,0.000605,0.001784,0.001704,-0.000814,0.000433,0.000834,0.000512");
		//System.out.println("index  =  " + ind);
		//System.exit(0);
		//}
		matchNput(matFile);
		
		System.out.println("***********&&&&&&&&&&&&***********"+Vectors.size());
		return Vectors;
	}
	
	
	
	public static void main(String[] args) {
	
	String pathToOrg = "data_files/eyes_v5.arff";						//path to the original arff file
	String pathToVis = "data_files/visualised/vis_eyes_v5_med.arff";	//path to the visualised(predictions added) file
	String pathToMat = "data_files/eyes_v5.txt";						//path to the matlab generated file
	
	String orgArff = Global.file_read(pathToOrg);
	String visArff = Global.file_read(pathToVis);
	String matFile = Global.file_read(pathToMat);
	
	orgVectors = storeVectors(orgArff);
	System.out.println("==========original==========");
	System.out.println(orgVectors.size());
	//print(orgVectors);
	
	visVectors = storeVectors(visArff);
	System.out.println("==========visualised==========");
	System.out.println(visVectors.size());
	//print(visVectors);
	
	extractFromVisualised();	//only the vector part of each of the vector in visualised arff file
	
	matchNput(matFile);
	
	//System.out.println(Vectors.size());
	
	}

}

class FeatureVector {
	
	String value;
	int actual;
	int predicted;
	int fileNum;
	
	public String value()
	{
		return this.value;
	}
	
	public int actual()
	{
		return this.actual;
	}
	
	public int predicted()
	{
		return this.predicted;
	}
	
	public int fileNum()
	{
		return this.fileNum;
	}
	
	public void setValue(String vectorValue)
	{
		this.value = vectorValue;
	}
	
	public void setActual(int actualClass)
	{
		this.actual = actualClass;
	}
	
	public void setPredicted(int predictedClass)
	{
		this.predicted = predictedClass;
	}
	
	public void setFileNumber(int fileNumber)
	{
		this.fileNum = fileNumber;
	}
}