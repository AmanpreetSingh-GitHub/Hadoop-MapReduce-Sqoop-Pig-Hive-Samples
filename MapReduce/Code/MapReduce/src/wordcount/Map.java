package wordcount;

import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map extends Mapper<LongWritable, Text, Text, IntWritable> {

	public void map(LongWritable key, Text value, Context context)
	{
		try
		{
			StringTokenizer token = new StringTokenizer(value.toString());
			
			while(token.hasMoreTokens())
			{
				context.write(new Text(token.nextToken()), new IntWritable(1));
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
