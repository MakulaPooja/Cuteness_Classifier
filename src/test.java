import java.util.*;
public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 1;
		while(true)
		{
			i++;
			System.out.println((double)Math.round(Math.random()*100)/100);
			if(i == 5)
				break;
			System.out.println("yoyo");
			//i++;
		}
		
		ArrayList<Integer> a = new ArrayList<Integer>();
		
		i = 0;
		int val = 1;
		for (i = 0; i<3 ; i++)
		{
			System.out.println("ek");
			a.add(val);
		}
		
		System.out.println(Math.random());
		
		for(int ele : a)
			System.out.println(ele);
		
		a.set(1, a.get(1)-22);
		
		for(int ele : a)
			System.out.println(ele);
		
		
		int aaaa = 1;
		int bbbb = 2;
		int cccc = 1;
		
		if(aaaa == cccc)
			System.out.println("equal hai");
		if(aaaa == bbbb)
			System.out.println("equal nai hai");
		
		System.out.println(Math.round(6.5));
		
		float ch = Float.parseFloat("1.917562e-016.11");
		System.out.println(ch);
	}

}
