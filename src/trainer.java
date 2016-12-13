import java.io.*;
import java.util.*;
import weka.core.converters.ArffLoader;
import weka.core.Instances;
import weka.core.Instance;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.Evaluation;
import weka.core.FastVector;
import weka.classifiers.bayes.*;
import weka.core.*;

public class trainer {

	public static weka.classifiers.Classifier c;
	
	
public static void trainClassifier()
{
	try{
			// load data
			ArffLoader loader = new ArffLoader();
			loader.setFile(new File("/home/akkisinghpanchaal/Desktop/nose_v4.arff"));
			Instances structure = loader.getDataSet();
			structure.setClassIndex(structure.numAttributes() - 1);
			System.out.println("Number fo attributres = " + structure.numAttributes());
			System.out.println("Number fo instances = " + structure.numInstances());
			
	 
			// train NaiveBayes
			MultilayerPerceptron mlpc = new MultilayerPerceptron();
			mlpc.setOptions(Utils.splitOptions("-L 0.5 -M 0.2 -N 1000 -V 0 -S 0 -E 20 -H 4")); 
			//NaiveBayes mlpc = new NaiveBayes();
			//nb.buildClassifier(structure);
			//mlpc.buildClassifier(structure);
			Evaluation eval = new Evaluation(structure);
			eval.crossValidateModel(mlpc, structure, 10, new Random(1));
			Object[] predictions = eval.predictions().toArray();
			
			
		/*	System.out.println("\n=================================\nError Rate\n============================\n");
			System.out.println(eval.errorRate());
			System.out.println("\n=================================\nCorrect\n============================\n");
			System.out.println(eval.correct());
			System.out.println("\n=================================\nF-Measure\n============================\n");
			for(int i = 0 ; i < 10; i++)
				System.out.println("class " + i+1 + "\t" + eval.fMeasure(i));
			System.out.println("\n=================================\nIncorrect\n============================\n");
			System.out.println(eval.incorrect());
			System.out.println("\n=================================\nKappa\n============================\n");
			System.out.println(eval.kappa());
			System.out.println("\n=================================\nPredictions\n============================\n");*/
			System.out.println(predictions.length);
			for(Object prediction : predictions)
			{
				System.out.println(prediction);
			}
			/*for(int i =0 ; i < structure.numInstances(); i++)
			{
				double pred = mlpc.classifyInstance(structure.instance(i));
				System.out.print("ID: " + structure.instance(i).value(0));
				System.out.print(", actual: " + structure.classAttribute().value((int) structure.instance(i).classValue()));
				System.out.println(", predicted: " + structure.classAttribute().value((int) pred));
			}*/
			
		}
	catch(Exception e){
		System.out.println(e);
		e.printStackTrace();
	}
	
}




public static void main(String[] args) {
		
	trainClassifier();

	}

}
