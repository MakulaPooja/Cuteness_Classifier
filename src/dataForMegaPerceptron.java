/*
 * this code collects the info about eyes, nose and mouth feature vectors from the visualised arff and matlab generated files and
 * then generates the data for MegaPerceptron that predicts the final cuteness value of an image. 
 */

import java.util.*;
import java.io.*;
import org.apache.commons.math.*;

public class dataForMegaPerceptron {

	//public static int vectorStarting = 0;
	
	public static ArrayList<FeatureVector> eyeVectors = new ArrayList<FeatureVector>();
	public static ArrayList<FeatureVector> noseVectors = new ArrayList<FeatureVector>();
	public static ArrayList<FeatureVector> mouthVectors = new ArrayList<FeatureVector>();
	public static int numOfImages = 117;
	
	
	public static float median(ArrayList<Integer> numArray)
	{
		float median = 0;
		Collections.sort(numArray);
		int middle = ((numArray.size()) / 2);
		System.out.println("numArray : " + numArray);
		if(numArray.size() % 2 == 0){
			int medianA = numArray.get(middle);
			int medianB = numArray.get(middle-1);
			median = (float)(medianA + medianB) / 2;
		} 
		else{
			median = numArray.get(middle);
		}
		
		return median;
	}
	
	public static float average(ArrayList<Integer> arr)
	{
		int sum = 0;
		float avg = 0;
		
		for(Integer a : arr)
		{
			sum += a;
		}
		return (float) sum/arr.size();
	}
			
	public static void storeData(String path, float eye, float nose, float mouth, int overall )
	{
		String str = Math.round(eye) + "," + Math.round(nose) + "," + Math.round(mouth) + "," + overall;
		Global.file_append(path, str);
	}
	
	//this method will calculate the median values of the predictions
	public static void calculateMedian()
	{
		int count = 0;
		float eyeCuteness = 0;
		float noseCuteness = 0;
		float mouthCuteness = 0;
		int overall = 0;
		
		String outPath = "data_files/hog_MegaPerceptronData_median_128_v1.txt";
		
		ArrayList<Integer> eyeValues = new ArrayList<Integer>();
		ArrayList<Integer> noseValues = new ArrayList<Integer>();
		ArrayList<Integer> mouthValues = new ArrayList<Integer>();
		
		for(int img_ind = 1; img_ind <= numOfImages; img_ind++)
		{
			/*
			for(int i = 0 ; i < eyeVectors.size(); i++)
			{
				System.out.println("eye\t"+"filenum : "+eyeVectors.get(i).fileNum() + "\t" + "img_ind : " + img_ind);
				if(eyeVectors.get(i).fileNum() == img_ind)
				{
					System.out.println("eye match");
					eyeValues.add(eyeVectors.get(i).predicted());
					overall = eyeVectors.get(i).actual();
				}
			}
			*/
			System.out.println("test eye : "+eyeVectors.size());
			for (FeatureVector v : eyeVectors)
			{
				System.out.println("eye\t"+"filenum : "+ v.fileNum() + "\t" + "img_ind : " + img_ind);
				if(v.fileNum() == img_ind)
				{
					System.out.println("eye match");
					eyeValues.add(v.predicted());
					overall = v.actual();
				}
			}
			
			/*
			for(int i = 0 ; i < noseVectors.size(); i++)
			{
				System.out.println("nose\tfilenum : "+noseVectors.get(i).fileNum() + "\t" + "img_ind : " + img_ind);
				if(noseVectors.get(i).fileNum() == img_ind)
				{
					System.out.println("nose match");
					noseValues.add(noseVectors.get(i).predicted());
					overall = noseVectors.get(i).actual();
				}
			}
			*/
			System.out.println("test nose : "+eyeVectors.size());
			for (FeatureVector v : noseVectors)
			{
				System.out.println("nose\tfilenum : "+ v.fileNum() + "\t" + "img_ind : " + img_ind);
				if(v.fileNum() == img_ind)
				{
					System.out.println("nose match");
					noseValues.add(v.predicted());
					overall = v.actual();
				}
			}
			
			/*
			for(int i = 0 ; i < mouthVectors.size(); i++)
			{
				System.out.println("mouth\tfilenum : "+mouthVectors.get(i).fileNum() + "\t" + "img_ind : " + img_ind);
				if(mouthVectors.get(i).fileNum() == img_ind)
				{
					System.out.println("mouth match");
					mouthValues.add(mouthVectors.get(i).predicted());
					overall = mouthVectors.get(i).actual();
				}
			}	
			*/
			System.out.println("test mouth : "+eyeVectors.size());
			for (FeatureVector v : mouthVectors)
			{
				System.out.println("mouth\tfilenum : "+ v.fileNum() + "\t" + "img_ind : " + img_ind);
				if(v.fileNum() == img_ind)
				{
					System.out.println("mouth match");
					mouthValues.add(v.predicted());
					overall = v.actual();
				}
			}
			
			
			System.out.println(eyeValues);
			System.out.println(noseValues);
			System.out.println(mouthValues);
			
			
			if(eyeValues.size() > 0)
				eyeCuteness = average(eyeValues);
					//eyeCuteness = median(eyeValues);
				else eyeCuteness = 0;
				
				if (noseValues.size() > 0)
					noseCuteness = average(noseValues);
					//noseCuteness = median(noseValues);
				else noseCuteness = 0;
				
				if (mouthValues.size() > 0)
					mouthCuteness = average(mouthValues);
					//mouthCuteness = median(mouthValues);
				else mouthCuteness = 0;
				
				if(mouthValues.size() == 0  && noseValues.size() == 0  && eyeValues.size() == 0 ){
					continue;}
				
				count++;
				storeData(outPath, eyeCuteness, noseCuteness, mouthCuteness, overall);
				
				eyeValues.clear();
				noseValues.clear();
				mouthValues.clear();
				eyeCuteness = 0;
				mouthCuteness = 0;
				noseCuteness = 0;
				overall = 0;
			
		}
		
		System.out.println("Count : " + count);
	}
	
	
	public static void main(String[] args) {
		
		String pathToOrgEyes = "data_files/arff_files/eyes_hog_features_median_v1_128x128.arff";						//path to the original arff file
		String orgArffEyes = Global.file_read(pathToOrgEyes);
		String pathToVisEyes = "data_files/visualised/vis_eyes_hog_features_median_v1_128x128.arff";	//path to the visualised(predictions added) file
		String visArffEyes = Global.file_read(pathToVisEyes);
		String pathToMatEyes = "data_files/eyes_hog_features_median_v1_128x128.txt";						//path to the matlab generated file		
		String matFileEyes = Global.file_read(pathToMatEyes);
		
		String pathToOrgNose = "data_files/arff_files/nose_hog_features_median_v1_128x128.arff";						//path to the original arff file
		String orgArffNose = Global.file_read(pathToOrgNose);
		String pathToVisNose = "data_files/visualised/vis_nose_hog_features_median_v1_128x128.arff";	//path to the visualised(predictions added) file
		String visArffNose = Global.file_read(pathToVisNose);
		String pathToMatNose = "data_files/nose_hog_features_median_v1_128x128.txt";						//path to the matlab generated file		
		String matFileNose = Global.file_read(pathToMatNose);
		
		String pathToOrgMouth = "data_files/arff_files/mouth_hog_features_median_v1_128x128.arff";						//path to the original arff file
		String orgArffMouth = Global.file_read(pathToOrgMouth);
		String pathToVisMouth = "data_files/visualised/vis_mouth_hog_features_median_v1_128x128.arff";	//path to the visualised(predictions added) file
		String visArffMouth = Global.file_read(pathToVisMouth);
		String pathToMatMouth = "data_files/mouth_hog_features_median_v1_128x128.txt";						//path to the matlab generated file		
		String matFileMouth = Global.file_read(pathToMatMouth);
		
		eyeVectors = new ArrayList<FeatureVector>(makeData.returnVector(orgArffEyes, visArffEyes, matFileEyes));
		//System.out.println("DONE CALCULATION================\n===========\n============");
		
		System.out.println("eyeSize : " +eyeVectors.size());
		for(FeatureVector vec : eyeVectors)
			System.out.println(vec.fileNum() + "\t" + vec.predicted());
		
		noseVectors = new ArrayList<FeatureVector>(makeData.returnVector(orgArffNose, visArffNose, matFileNose));
		//System.out.println("DONE CALCULATION================\n===========\n============");
		
		System.out.println("noseSize : " +noseVectors.size());
		for(FeatureVector vec : noseVectors)
			System.out.println(vec.fileNum() + "\t" + vec.predicted());
		
		mouthVectors = new ArrayList<FeatureVector>(makeData.returnVector(orgArffMouth, visArffMouth, matFileMouth));
		//System.out.println("DONE CALCULATION================\n===========\n============");
		
		System.out.println("mouthSize : " +mouthVectors.size());
		for(FeatureVector vec : mouthVectors)
			System.out.println(vec.fileNum() + "\t" + vec.predicted());
		
		System.out.println("eyeSize : " +eyeVectors.size());
		System.out.println("noseSize : " +noseVectors.size());
		System.out.println("mouthSize : " +mouthVectors.size());
		
		calculateMedian();
		
		
		
		
		
		
		
		/*=======================================================================
		 * String arffPath = "data_files/visualised/testfile.arff";
		String arff = Global.file_read(arffPath);
		
		String[] lines = arff.split("\n");
		for(int i = 0 ; i < lines.length; i++)
		{
		//	System.out.println(lines[i]);
			//if(lines[i].equals("")) System.out.println("null found");
			if(lines[i].toLowerCase().equals("@data"))
			{
				System.out.println("found : " + lines[i]);
				vectorStarting = i+1;
				break;
			}
		}
		
		for (int i = vectorStarting; i < lines.length; i++)
		{
			String[] features = lines[i].trim().split(",");
			int target = (int)Float.parseFloat(features[features.length - 3]);
			int predicted = (int)Float.parseFloat(features[features.length - 2]);
			System.out.println("target : " + target + "\t" + "predicted : " + predicted);
		}
		
		//System.out.println("total vectors found : " + (lines.length - vectorStarting + 1));
		 * ==================================================================================
		 * */
		
		
	}

}
