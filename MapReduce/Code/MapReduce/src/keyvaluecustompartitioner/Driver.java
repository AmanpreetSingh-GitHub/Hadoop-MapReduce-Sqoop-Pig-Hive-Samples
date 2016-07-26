package keyvaluecustompartitioner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class Driver extends Configured implements Tool {

	@Override
	public int run(String[] arg0) throws Exception {
		
		Configuration conf = new Configuration(); //Create Configuration object so as to use keyvalueinputtextformat
		conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", ","); //Comma delimited
		
		Job job = Job.getInstance(conf); //Singleton instance of the Job class
		
		job.setJobName("keyvalue"); //Set Job Name 
		job.setJarByClass(getClass());
				
		job.setNumReduceTasks(3); //Set Number of Reducers to 3
		
		job.setMapperClass(Map.class); //Set Mapper class
		job.setReducerClass(Reduce.class); //Set Reducer class
		job.setPartitionerClass(Partition.class); //Set Partitioner class
		
		job.setMapOutputKeyClass(Text.class); //Set the datatype of Key that Mapper outputs
		job.setMapOutputValueClass(Text.class); //Set the datatype of value that Mapper outputs
		
		job.setOutputKeyClass(Text.class); //Set the datatype of Key that Reducer outputs
		job.setOutputValueClass(FloatWritable.class); //Set the datatype of value that Reducer outputs
		
		job.setInputFormatClass(KeyValueTextInputFormat.class); //Set InputFormat class to KeyValueTextInputFormat
		
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