package keyvaluecustompartitioner;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class Partition extends Partitioner<Text, Text> {

	@Override
	public int getPartition(Text key, Text value, int numReduceTasks) {
		
		try
		{
			String[] values = value.toString().split(",");
			
			int age = Integer.parseInt(values[0]);
			
			if(age <= 20)
			{
				return 0;
			}
			else if (age >=21 && age <= 30)
			{
				return 1;
			}
			else 
			{
				return 2;
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return 0;
	}
}
