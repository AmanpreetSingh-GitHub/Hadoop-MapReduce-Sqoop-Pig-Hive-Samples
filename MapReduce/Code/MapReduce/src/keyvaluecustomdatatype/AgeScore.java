package keyvaluecustomdatatype;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;

public class AgeScore implements Writable {

	IntWritable age = new IntWritable();
	IntWritable score = new IntWritable();
	
	@Override
	public void readFields(DataInput in) throws IOException {
		
		age.readFields(in);
		score.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		
		age.write(out);
		score.write(out);
	}
}
