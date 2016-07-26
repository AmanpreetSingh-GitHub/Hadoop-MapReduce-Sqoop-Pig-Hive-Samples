package keyvaluecustompartitioner;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map extends Mapper<Text, Text, Text, Text> {

	public void map(Text key, Text value, Context context)
	{
		try
		{
			String[] values = value.toString().split(",");
						
			String outValue = values[1].trim() + "," + values[2].trim();
			
			context.write(key, new Text(outValue));
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}
	}
}
