package com.dainik.bhaskar.user.storylist;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

//import com.dblogic.test.DBMapper;
//import com.dblogic.test.DBReducer;

public class DBStitchPerUserStoryDriver {
	static String divyaBhaskar = "divyaBhaskar";
    static String dainikBhaskar = "dainikBhaskar";
    static String divyaMoney = "divyaMoney";
    static String bhaskarMoney = "bhaskarMoney";
    static String app = "app";
    
    
	public static boolean configure(String[] args) throws Exception {
		
		if(args.length < 2) {
			System.out.println("Usage : <inputpath> <outputpath>");
		}
		
		String INPUT_PATH = args[0];
		String OUTPUT_PATH = args[1];
		//INPUT_PATH = "/realtime/2016_03_31/realtime_2016_03_31,/realtime/2016_03_30/realtime_2016_03_30,/realtime/2016_03_29/realtime_2016_03_29,/realtime/2016_03_28/realtime_2016_03_28,/realtime/2016_03_27/realtime_2016_03_27";
		
		System.out.println("===============================================================");
		System.out.println("Input Path " + INPUT_PATH.replace("\"", ""));
		System.out.println("Output Path " + OUTPUT_PATH);
		System.out.println("===============================================================");
		
		 Configuration conf = new Configuration();
		    Job job = Job.getInstance(conf, ":: MR Code for Stitching for all channles ::");
		    System.out.println("--Inside MapReduce Job--");
		    job.setJarByClass(DBStitchPerUserStoryDriver.class);
		    job.setMapperClass(DBStitchPerUserStoryMapper.class);
		    job.setReducerClass(DBStitchPerUserStoryReducer.class);
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(Text.class);
		  //  job.setNumReduceTasks(0);
		  /*FileInputFormat.addInputPath(job, new Path(args[0]));
		    FileOutputFormat.setOutputPath(job, new Path(args[1]));*/
		    FileInputFormat.addInputPaths(job, INPUT_PATH.replace("\"", ""));
		//    FileInputFormat.setInputDirRecursive(job, true);
		    FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));
		    MultipleOutputs.addNamedOutput(job, divyaBhaskar, TextOutputFormat.class, NullWritable.class, Text.class);
	        MultipleOutputs.addNamedOutput(job, dainikBhaskar, TextOutputFormat.class, NullWritable.class, Text.class);
	        MultipleOutputs.addNamedOutput(job, bhaskarMoney, TextOutputFormat.class, NullWritable.class, Text.class);
	     //   MultipleOutputs.addNamedOutput(job, divyaMoney, TextOutputFormat.class, NullWritable.class, Text.class);
	 	    MultipleOutputs.addNamedOutput(job, app, TextOutputFormat.class, NullWritable.class, Text.class);
	     //   System.exit(job.waitForCompletion(true) ? 0 : 1);
	        job.waitForCompletion(true);
	        return true;
	}
	
	// Main Class of DB MR-Job
	/*public static void main(String[] args) {
		try {
			DBDriver.configure(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}