package keyvaluecustomdatatype;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map extends Mapper<Text, Text, Text, AgeScore> {

	public void map(Text key, Text value, Context context)
	{
		try
		{
			String[] values = value.toString().split(",");
						
			AgeScore outValue = new AgeScore();
			outValue.age.set(Integer.parseInt(values[1]));
			outValue.score.set(Integer.parseInt(values[2]));
			
			context.write(key, outValue);
		}
		catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}
	}
}
