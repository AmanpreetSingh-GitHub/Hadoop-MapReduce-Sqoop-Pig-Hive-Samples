package keyvaluecustomdatatype;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce extends Reducer<Text, AgeScore, Text, FloatWritable> {

	public void reduce(Text key, Iterable<AgeScore> values, Context context)
	{
		try
		{
			long totalScore = 0L;
			int count = 0;
			
			for (AgeScore value : values){
				totalScore += value.score.get();
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
