import java.util.*;
import java.text.DecimalFormat;
import java.lang.Math;

public class Main {

	public static ArrayList<ArrayList<Float>> trainingSamples2 = new ArrayList<ArrayList<Float>>();
	public static ArrayList<Float> cutenessOfSample = new ArrayList<Float>();
	public static ArrayList<ArrayList<Float>> weightsInputToHidden = new ArrayList<ArrayList<Float>>();
	public static ArrayList<Float> weightsHiddenToOutput = new ArrayList<Float>();
	public static ArrayList<Float> netJ = new ArrayList<Float>();
	public static float deltaK;
	public static float netK;
	public static ArrayList<Float> sgm_netJ = new ArrayList<Float>();
	public static ArrayList<Float> deltaJ = new ArrayList<Float>();
	public static ArrayList<ArrayList<Float>> updatedWeightsInputToHidden = new ArrayList<ArrayList<Float>>();
	public static ArrayList<Float> updatedWeightsHiddenToOutput = new ArrayList<Float>();
	public static ArrayList<ArrayList<Float>> hiddenLayerOutput = new ArrayList<ArrayList<Float>>();
	public static float output;
	public static float learningRate = (float) 0.4;
	public static int noOfHiddenNeurons = 6;
	public static int noOfFeatures = 64;
	public static int yoyo = 0;
	public static DecimalFormat df = new DecimalFormat("#,###,##0.0000000000");
	
	public static void show_matrix_2(ArrayList<ArrayList<Float>> matrix)
	{
		for(ArrayList<Float> row : matrix)
		{
			System.out.println(row);
		}
	}
	
	public static void loadEyeData(String eyeData)
	{
		int i = 0;
		int j = 0;
		
		/*
		 * assigning the value of feature vectors to the training sample array
		 */
		
		for(String featureVector : eyeData.trim().split("\n"))
		{		
				String[] features = featureVector.trim().split(" ");
				ArrayList<Float> row = new ArrayList<Float>();
				for(int feature_index = 0; feature_index < features.length-1; feature_index++)
				{
					row.add(Float.parseFloat(features[feature_index].trim()));
					//System.out.println(double.parsedouble(feature.trim()));
				}
				cutenessOfSample.add((float)Float.parseFloat(features[features.length-1].trim()));
				row.add((float) 1);  //this is the bias input
				trainingSamples2.add(row);
				i++;
				j=0;
		}
		
	}
	
	public static ArrayList<ArrayList<Float>> makeTest(String testSet)
	{
		int i = 0;
		int j = 0;
		ArrayList<ArrayList<Float>> testSamples = new ArrayList<ArrayList<Float>>();
		
		/*
		 * assigning the value of feature vectors to the test sample array
		 */
		
		for(String featureVector : testSet.trim().split("\n"))
		{		
				String[] features = featureVector.trim().split(" ");
				ArrayList<Float> row = new ArrayList<Float>();
				for(int feature_index = 0; feature_index < features.length; feature_index++)
				{
					row.add(Float.parseFloat(features[feature_index].trim()));
					//System.out.println(double.parsedouble(feature.trim()));
				}
				//row.add();  //this is the bias input
				testSamples.add(row);
				i++;
				j=0;
		}
		
		return testSamples;
	}
	/*
	 * trainClassifier() is required to train the DL network with the sample data supplied
	 */
	public static void trainClassifier(int noOfEpochs)
	{
		/*
		 * evaluating Input-Hidden
		 */
		int sample_index;
		int neuron_index;
		int feature_index;
		float temp_netJ = (float) 0;
		//int noOfEpochs = 200;
		int epoch_index;
		netJ.clear();
		sgm_netJ.clear();
		
		for(epoch_index = 0; epoch_index < noOfEpochs ; epoch_index++)
		{
			netJ.clear();
			sgm_netJ.clear();
			netK = 0;
			output = 0;
			System.out.println("Epoch: " + epoch_index + "=========================================================================================");
			for(sample_index = 0; sample_index < trainingSamples2.size(); sample_index++)
			{
				//System.out.println("sample_index = " + sample_index);
				for(neuron_index = 0; neuron_index < noOfHiddenNeurons; neuron_index++)
				{
					//System.out.println("neuron_index = " +neuron_index);
					for(feature_index = 0; feature_index <= noOfFeatures; feature_index++)
					{
						temp_netJ += trainingSamples2.get(sample_index).get(feature_index) * weightsInputToHidden.get(neuron_index).get(feature_index);
						//System.out.println(trainingSamples2.get(sample_index).get(noOfFeatures));
						//System.out.println(sample_index+"\t"+neuron_index+"\t"+feature_index+"\t"+"\t"+trainingSamples2.get(sample_index).get(feature_index)+"\t"+weightsInputToHidden.get(neuron_index).get(feature_index)+"\t"+temp_netJ);
					}
					netJ.add(temp_netJ); //adding the netj into netJ
					sgm_netJ.add(sigmoid(temp_netJ)); 	//evaluation netj through sigmoid function and saving into the sgm_netJ
					//System.out.println(netJ);
					//System.out.println(sgm_netJ);
					temp_netJ = 0;
				}
				//System.out.println("\n===============================\n"+netJ+"\n================================\n");
				
				//we will start the calculation of Hidden and output side now.
				//float temp_output;
				netK = 0;
				int hidden_index;
			    //System.out.println(sgm_netJ.size());
				//System.out.println(netJ.size());
				for(hidden_index = 0; hidden_index < noOfHiddenNeurons; hidden_index++)
				{
					netK += sgm_netJ.get(hidden_index) * weightsHiddenToOutput.get(hidden_index);
				}
				netK += weightsHiddenToOutput.get(hidden_index);
				output = (float)(sigmoid(netK)*10);
				//System.out.println(netK + "\t" + (int)Math.ceil(output) + "\t" +cutenessOfSample.get(sample_index));
				//System.out.println(netK + "\t" + df.format(output) + "\t" + cutenessOfSample.get(sample_index));
				
				/*
				 * start back propagation from here
				 */
				
				startBackPropagationHiddenOutput(cutenessOfSample.get(sample_index));
				//System.exit(0);
				startBackPropagationInputHidden();
				netJ.clear();
				sgm_netJ.clear();
				netK = 0;
				output = 0;
			}
			
		}
	}
	
	/*
	 * startBackPropagationInputHidden() updates the weights of input layer using the linear regression 
	 */
	public static void startBackPropagationInputHidden()
	{
		int neuron_index;
		int feature_index;
		float update;
		float delta;
		
		for(ArrayList<Float> updateVector : updatedWeightsInputToHidden)
			updateVector.clear();														//flush previous updates before starting
		
		deltaJ.clear();				//flush previous values of DELTA_Js
		ArrayList<Float> row = new ArrayList<Float>();
		for(neuron_index = 0; neuron_index < noOfHiddenNeurons; neuron_index++)
		{
			for(feature_index = 0; feature_index <= noOfFeatures; feature_index++)
			{
				delta = deltaK * weightsHiddenToOutput.get(neuron_index) * derivative_sigmoid(netJ.get(neuron_index));
				deltaJ.add(delta);
				
				update = (float)(learningRate * delta * trainingSamples2.get(neuron_index).get(feature_index));
				row.add(update);
				//updatedWeightsInputToHidden.get(neuron_index).set(feature_index, update);
				
				/*
				 * now we will update the weights
				 */
				float prevWeight = weightsInputToHidden.get(neuron_index).get(feature_index);
				float updatedWeight = weightsInputToHidden.get(neuron_index).get(feature_index) + update;
				weightsInputToHidden.get(neuron_index).set(feature_index, updatedWeight);
				//System.out.println("W[" + neuron_index +","+feature_index+"]: " + prevWeight + "\t" + "dW[" + neuron_index +","+feature_index+"]: "+ df.format((double)update) + "\t" + "ndW: "+updatedWeight);
			}
			updatedWeightsInputToHidden.add(row);
			row.clear();
		}
	}
	
	/*
	 * startBackPropagationHiddenOutput() updates the weights of output layer using the linear regression
	 */
	
	public static void startBackPropagationHiddenOutput(float target)
	{
		int neuron_index;
		float update;
		
		//System.out.println("## "+(target - output) +"\t"+derivative_sigmoid(netK));
		deltaK = (float) (target - output) * derivative_sigmoid(netK); //this is the value of -DELTA_K
		//System.out.println("deltaK: "+deltaK +"\t"+ "error: "+(-(target-output))+"\t"+"dsgmnetK: "+ netK);
		updatedWeightsHiddenToOutput.clear();
		
		for (neuron_index = 0; neuron_index < noOfHiddenNeurons; neuron_index++ )
		{
			//System.out.println(">> "+learningRate +"\t"+deltaK+"\t"+sgm_netJ.get(neuron_index));
			update = (float)(learningRate * deltaK * sgm_netJ.get(neuron_index));
			//System.out.println("updated thing is: "+ update);
			updatedWeightsHiddenToOutput.add(update);
			/*
			 * now we will update the weight between hidden and output layer using the updates we calculated above
			 */
		//	System.out.print("prev: " + round(weightsHiddenToOutput.get(neuron_index)) + "\t");
			float prevWeight = weightsHiddenToOutput.get(neuron_index);
			float updatedWeight = weightsHiddenToOutput.get(neuron_index) + update;
			weightsHiddenToOutput.set(neuron_index, updatedWeight);
			//System.out.println("W[0," + neuron_index +"]: " + prevWeight + "\t" + "dW[" + neuron_index +"]: " + df.format((double)update) + "\t" + "ndW: "+updatedWeight);
			//System.out.print("updated: "+round(update) + "\t" + round(weightsHiddenToOutput.get(neuron_index)));
			//System.out.println();
		}
		
		updatedWeightsHiddenToOutput.add(learningRate * deltaK); //calculating the weight of the bias
		weightsHiddenToOutput.set(neuron_index, weightsHiddenToOutput.get(neuron_index) + learningRate * deltaK); //updating the weight of the bias
		
		//System.out.println(updatedWeightsHiddenToOutput.size() + "\n" + updatedWeightsHiddenToOutput);
		//System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@\n"+weightsHiddenToOutput+"\n"+weightsHiddenToOutput.size()+"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");
		
	}
	
	/*
	 * generateRandomNumber() generates the random weights between 1 to 10
	 */
	public static float generateRandomNumber()
	{
		//return (double) (-0.5 + (Math.round(Math.random()*100)/100));
		//return (float) (-0.5 + Math.random());
		//return (float) (Math.random());
		//return (float) (1 + Math.random()*9);
		return (float) (Math.random()*0.25);
		
	}
	
	/*
	 * derivative_sigmoid() gives the value of the derivative of the signum function at netk and netj
	 */
	public static float derivative_sigmoid(float net)
	{
		return (float) (sigmoid(net)*(1.0 - sigmoid(net)));
	}
	
	
	/*
	 * sigmoid() evaluates the linear sum of samples and weights at any neuron 
	 */
	public static float sigmoid(float net)
	{
		 return (float)( (float) 1 / (1 + Math.exp(-net)));
	}
	
	/*
	 * round function rounds off the digits to 3 places of decimal
	 */
	
	public static float round(float number)
	{
		return (float)((float)Math.round(number*1000)/1000);
	}
	
	
	/*
	 * ceil(x) function outputs the ceiling of a any real number x
	 */
	public static int ceil(float x)
	{
		return (int)Math.ceil(x);
	}
	
	
	/*
	 * assignWeightsInputHidden() assigns the weights from input to hidden layer
	 */
	public static void assignWeightsInputHidden()
	{
		int i,j;
		ArrayList<Float> row = new ArrayList<Float>();
		for(i = 0; i < noOfHiddenNeurons; i++)
		{
			for(j = 0; j <= noOfFeatures; j++) //here the additional one weight/vector is used for bias
			{
				//row.add(generateRandomNumber());
				row.add((float)1/(noOfFeatures+1));
			}
			weightsInputToHidden.add(new ArrayList<Float>(row));
			//System.out.println(weightsInputToHidden);
			row.clear();
		}
		
		//System.out.println(weightsInputToHidden);
		
		/*
		 * adding bias row
		 */
/*		ArrayList<Float> biasRow = new ArrayList<Float>();
		float bias;
		
		for(i = 0;i < noOfHiddenNeurons;i++)
		{
			bias = generateRandomNumber();
			biasRow.add(bias);
		}
		
		weightsInputToHidden.add(biasRow);*/
	}
	
	/*
	 * assignWeightsHiddenOutput() assigns the weights from hidden to output layer
	 */
	public static void assignWeightsHiddenOutput()
	{
		int i,j;
		
			for(j = 0; j < noOfHiddenNeurons; j++) //here one extra weight/neuron is take as bias
			{
				//weightsHiddenToOutput.add(generateRandomNumber());
				weightsHiddenToOutput.add((float)1/(noOfHiddenNeurons)+1);
			}
		
		/*
		 * adding bias row
		 */
		
		//weightsHiddenToOutput.add(generateRandomNumber());
			weightsHiddenToOutput.add((float)1/(noOfHiddenNeurons+1));
	}
	
	
	public static void testClassifier(String testSet)
	{
		int sample_index;
		int neuron_index;
		int feature_index;
		float temp_netJ = (float) 0;
		int correctlyClassified = 0;
		float accuracy = 0;
		//int noOfEpochs = 200;
		netJ.clear();
		sgm_netJ.clear();
		netK = 0;
		output = 0;
		ArrayList<ArrayList<Float>> testSamples = makeTest(testSet);
		show_matrix_2(testSamples);
		//System.exit(0);
		int totalSamples = testSamples.size();
		
		//System.out.println("======================================================");
		//show_matrix_2(weightsInputToHidden);
		//System.out.println("======================================================");
		//System.out.println(weightsHiddenToOutput);
		//System.exit(0);
		
		for(sample_index = 0; sample_index < testSamples.size(); sample_index++)
		{
			//System.out.println("sample_index = " + sample_index);
			for(neuron_index = 0; neuron_index < noOfHiddenNeurons; neuron_index++)
			{
				//System.out.println("neuron_index = " +neuron_index);
				for(feature_index = 0; feature_index < noOfFeatures; feature_index++)
				{
					float wi = weightsInputToHidden.get(neuron_index).get(feature_index);
					float xi = testSamples.get(sample_index).get(feature_index);
					temp_netJ += xi * wi;
					System.out.println(xi + "\t" + wi + "\t" + temp_netJ);
					//System.out.println(sample_index+"\t"+neuron_index+"\t"+feature_index+"\t"+"\t"+trainingSamples2.get(sample_index).get(feature_index)+"\t"+weightsInputToHidden.get(neuron_index).get(feature_index)+"\t"+temp_netJ);
				}
				temp_netJ += weightsInputToHidden.get(neuron_index).get(noOfFeatures);
				netJ.add(temp_netJ); //adding the netj into netJ
				sgm_netJ.add(sigmoid(temp_netJ)); 	//evaluation netj through sigmoid function and saving into the sgm_netJ
				System.out.println("sgm_NetJ: "+sigmoid(temp_netJ) + "\t" + "netJ: " + temp_netJ);
				//System.out.println(netJ);
				//System.out.println(sgm_netJ);
				temp_netJ = 0;
			}
				//System.out.println("\n===============================\n"+netJ+"\n================================\n");
				
				//we will start the calculation of Hidden and output side now.
				//float temp_output;
			netK = 0;
			int hidden_index;
			//System.out.println(sgm_netJ.size());
			//System.out.println(netJ.size());
			for(hidden_index = 0; hidden_index < noOfHiddenNeurons ; hidden_index++)
			{
				//System.out.println(hidden_index);
				//System.out.println(sgm_netJ.get(hidden_index) + "\t" + weightsHiddenToOutput.get(hidden_index));
				netK += sgm_netJ.get(hidden_index) * weightsHiddenToOutput.get(hidden_index);
			}
			netK += weightsHiddenToOutput.get(hidden_index);
			//System.out.println("bias weight\t" +weightsHiddenToOutput.get(hidden_index));
			output = (float)(sigmoid(netK)*10);
			//System.out.println(netK + "\t" + (int)Math.ceil(output) + "\t" +cutenessOfSample.get(sample_index));
			//int expected = ceil(testSamples.get(sample_index).get(noOfFeatures));
			//int observed = ceil(output);
			int expected = Math.round(testSamples.get(sample_index).get(noOfFeatures));
			int observed = Math.round(output);
			System.out.println(netK + "\t" + observed + "\t" + expected);
			
			if (expected == observed)
			{
				correctlyClassified++;
			}
			netK = 0;
			output = 0;
			netJ.clear();
			sgm_netJ.clear();
		}
		
		accuracy  = (float) ((float)correctlyClassified / totalSamples) * 100;
		accuracy = (float)Math.round(accuracy * 10000) / 10000;
		System.out.println("\n"+accuracy);
		
	}
	
	
	public static void main(String[] args) {
		
		//Reading the eye SURF features
		//String eyeData = Global.file_read("/media/akkisinghpanchaal/CA081D76081D62AD/akshay/SEM-VI/DIP/codes/data_files/eyes_v2.txt");
		String eyeData = dataDivider.divideCorpus("data_files/eyes_v2.txt")[0];
		String testSet = dataDivider.divideCorpus("data_files/eyes_v2.txt")[1];
		//Reading nose SURF features
		String noseData = Global.file_read("/media/akkisinghpanchaal/CA081D76081D62AD/akshay/SEM-VI/DIP/codes/data_files/nose_v2.txt");
		
		//Reading mouth SURF features
		String mouthData = Global.file_read("/media/akkisinghpanchaal/CA081D76081D62AD/akshay/SEM-VI/DIP/codes/data_files/mouth_v2.txt");
		/*		
		System.out.println(eyeData.split("\n").length);
		System.out.println(eyeData);
		System.out.println(noseData.split("\n").length);
		System.out.println(noseData);
		System.out.println(mouthData.split("\n").length);
		System.out.println(mouthData);
		*/
		
		loadEyeData(eyeData);
		//System.out.println(trainingSamples2.size() + "\t" + trainingSamples2.get(0).size());
		//show_matrix_2(trainingSamples2);
		
//		System.out.println("=====================================================================");
	//	System.out.println("=====================================================================");
		
		assignWeightsInputHidden();
		assignWeightsHiddenOutput();
		
	//	System.out.println(weightsInputToHidden.size() + "\t" + weightsInputToHidden.get(0).size());
		//show_matrix_2(weightsInputToHidden);
		
	//	System.out.println("=====================================================================");
	//	System.out.println("=====================================================================");
		
	//	System.out.println(weightsHiddenToOutput.size());
	//	System.out.println(weightsHiddenToOutput);
		
		trainClassifier(200);
		for(int lines = 0; lines < 4 ; lines ++)
			System.out.println("===================================================================================================");
		testClassifier(testSet);
		
	//	System.out.println(netJ);
	//	System.out.println(cutenessOfSample);
		
		
	}

}
