import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;


public class Cuteness_Predictor {

	public static weka.classifiers.Classifier c;
	
	 public static MultilayerPerceptron loadModel(String path) throws Exception 
		{
	    	MultilayerPerceptron classifier;
		    FileInputStream fis = new FileInputStream(path);
		    ObjectInputStream ois = new ObjectInputStream(fis);
		    classifier = (MultilayerPerceptron) ois.readObject();
		    ois.close();
		    return classifier;
		}
	 
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
			c = loadModel("Models/MP_mouthfeatures.model");
			Instances unlabeled = new Instances(new BufferedReader(new FileReader("data_files/unlabeled.arff")));

		    // set class attribute
		    unlabeled.setClassIndex(unlabeled.numAttributes() - 1);

		    Instances labeled = new Instances(unlabeled);
		    
		    // label instances
		    String tag="";
		    int ins_count = 0;
		    double wekaOutput;
		    double clsLabel = 0;
		    for (int i = 0; i < unlabeled.numInstances(); i++) 
		    {
		    	ins_count++;
		      clsLabel = c.classifyInstance(unlabeled.instance(i));
		      
		      
		      if(clsLabel==0.0)
		    	  tag="1";
		      else if(clsLabel==1.0)
		    	  tag="2";
		      else if(clsLabel==2.0)
		    	  tag="3";
		      else if(clsLabel==3.0)
		    	  tag="4";
		      else if(clsLabel==4.0){
		    	  //System.out.println("beee");
		      	  tag="5";}
		      else if(clsLabel==5.0)
		    	  tag="6";
		      else if(clsLabel==6.0)
		    	  tag="7";
		      else if(clsLabel==7.0)
		    	  tag="8";
		      else if(clsLabel==8.0)
		    	  tag="9";
		      else if(clsLabel==9.0)
		    	  tag="10";
		      
		      System.out.println("tag: "+tag + "\t"+ "class label: " + clsLabel);
		    }
		
	}

}
