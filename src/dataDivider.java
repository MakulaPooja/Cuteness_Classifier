import java.io.BufferedReader;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;

public class dataDivider {
	
	public static ArrayList<String> StringArray= new ArrayList<String>();
	
	public static String[] divideCorpus(String fp)
	{
		String[] divided = new String[2]; 
		
		try (BufferedReader br = new BufferedReader(new FileReader(fp)))
		{

			String sCurrentLine;
			String store;
			
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				StringArray.add(sCurrentLine);
				//.out.println(sCurrentLine.length());
			}
			//System.out.println(StringArray);
			int len = StringArray.size();
			//System.out.println(len);
			//System.out.println(StringArray.get(2));
			Collections.shuffle(StringArray);
			System.out.println(StringArray);
			
			String trainEyeData = "";
			String testEyeData = "";
			
			int top70 = (int) (StringArray.size()*(0.7));
			
			for(int i= 0; i < len; i++)
			{
				if(i < top70)
					trainEyeData += StringArray.get(i) + "\n";
				else
					testEyeData += StringArray.get(i) + "\n";
			}
			
			System.out.println(trainEyeData.split("\n").length);
			System.out.println(testEyeData.split("\n").length);
			
			divided[0] = trainEyeData;
			divided[1] = testEyeData;
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return divided;
	}
	public static void main(String[] args) {

		divideCorpus("data_files/eyes_v2.txt");
	}
}

