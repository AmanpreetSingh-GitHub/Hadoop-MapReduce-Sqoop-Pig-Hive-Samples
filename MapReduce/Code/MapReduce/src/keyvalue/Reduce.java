package keyvalue;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce extends Reducer<Text, IntWritable, Text, FloatWritable> {

	public void reduce(Text key, Iterable<IntWritable> values, Context context)
	{
		try
		{
			long totalScore = 0L;
			int count = 0;
			
			for (IntWritable value : values)
			{
				totalScore += value.get();
				count++;
			}			
			
			context.write(key, new FloatWritable((float)totalScore/count));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
