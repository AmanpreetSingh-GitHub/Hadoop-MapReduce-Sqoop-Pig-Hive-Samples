package keyvaluewithoutreducer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class Partition extends Partitioner<Text, AgeScore> {

	@Override
	public int getPartition(Text key, AgeScore value, int numReduceTasks) {
		
		try
		{
			int age = value.age.get();
			
			if(age <= 20)
				return 0;
			else if (age >=21 && age <= 30)
				return 1;
			else return 2;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return 0;
	}
}
