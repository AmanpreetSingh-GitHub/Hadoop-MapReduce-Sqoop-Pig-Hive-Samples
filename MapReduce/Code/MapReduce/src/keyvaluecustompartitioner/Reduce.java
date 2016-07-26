package keyvaluecustompartitioner;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce extends Reducer<Text, Text, Text, FloatWritable> {

	public void reduce(Text key, Iterable<Text> values, Context context)
	{
		try
		{
			long totalScore = 0L;
			int count = 0;
			
			for (Text value : values){
				String[] reduceValues = value.toString().split(",");
				totalScore += Long.parseLong(reduceValues[1]);
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
