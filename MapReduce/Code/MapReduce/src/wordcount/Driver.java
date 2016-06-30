package wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Driver extends Configured implements Tool {

	@Override
	public int run(String[] arg0) throws Exception {
		
		Job job = Job.getInstance(); //Singleton instance of the Job class
		
		job.setJobName("wordcount"); //Set Job Name 
		job.setJarByClass(getClass());
		
		job.setMapperClass(Map.class); //Set Mapper class
		job.setReducerClass(Reduce.class); //Set Reducer class
		
		job.setMapOutputKeyClass(Text.class); //Set the datatype of Key that Mapper outputs
		job.setMapOutputValueClass(IntWritable.class); //Set the datatype of value that Mapper outputs
		
		job.setOutputKeyClass(Text.class); //Set the datatype of Key that Reducer outputs
		job.setOutputValueClass(LongWritable.class); //Set the datatype of value that Reducer outputs
		
		job.setInputFormatClass(TextInputFormat.class); //Set the type of InputFormat to use
		FileInputFormat.setInputPaths(job, new Path(arg0[0])); //Set the path of Input file to process
		FileOutputFormat.setOutputPath(job, new Path(arg0[1])); //Set the path of Final Output
		
		return job.waitForCompletion(true) ? 1 : 0; //Wait for completion of the Job		
	}

	public static void main(String[] args) {
		try
		{
			ToolRunner.run(new Configuration(), new Driver(), args);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}