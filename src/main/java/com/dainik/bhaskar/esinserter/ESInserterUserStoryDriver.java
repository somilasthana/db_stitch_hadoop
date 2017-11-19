package com.dainik.bhaskar.esinserter;


import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.elasticsearch.hadoop.mr.EsOutputFormat;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.fs.Path;

import com.dainik.bhaskar.DBAnalyticsException;
import com.dainik.bhaskar.common.ConstantValue;
import com.dainik.bhaskar.util.DBConfig;


public class ESInserterUserStoryDriver {
	
	public static DBConfig config = DBConfig.getInstance();
	public static Map<String, Integer> hostMap = new HashMap<String, Integer>();
	
	
	public static void hostlist(){
		
		String eSNodes = config.getProperty(ConstantValue.CONFIG_CONNECT_STR);
		System.out.println("eSNodes..."+eSNodes);
		for (Object node : eSNodes.split(",")) {
			 if ("".equals(node)) {
                 throw new DBAnalyticsException("Invalid index.elasticsearch.connect property in config file");
			 }
			 
			 String hostName = ((String) node).split(":")[0];
			 Integer port = Integer.parseInt(((String) node).split(":")[1]);
			// System.out.println("Hostname..."+hostName+" portNumber..."+port);
			 hostMap.put(hostName, port);
		}
	}
	
	public static boolean configure(String[] args) throws Exception {
		
		/*if(args.length < 4) {
			System.out.println("Usage : <inputpath> <outputpath>");
		}*/

		

		String INPUT_PATH = args[0];
		String OUTPUT_PATH = args[1];
		String esIndexMapping = args[2];
		System.out.println("Input Path" + INPUT_PATH);
		System.out.println("Output Path" + OUTPUT_PATH);
		
		Configuration conf = new Configuration();

		String eSNodes_ = config.getProperty(ConstantValue.CONFIG_CONNECT_STR);
		conf.set("es.nodes", eSNodes_.split(",")[0].split(":")[0]);//"10.140.0.56:9200");

		//conf.set("es.resource", "divyauncommonstoryinsert/divyastorypath");
		conf.set("es.resource", esIndexMapping);
		conf.set("es.mapping.id", ConstantValue.SESSIONID);

		conf.setBoolean("mapred.map.tasks.speculative.execution", false);
		conf.setBoolean("mapred.reduce.tasks.speculative.execution", false);

		Job job = Job.getInstance(conf, ":: MR code for ES insertion for channels ::");
		job.setJarByClass(ESInserterUserStoryDriver.class);
		job.setMapperClass(ESInserterUserStoryDriverMapper.class);
		
		
		job.setOutputFormatClass(EsOutputFormat.class);
		job.setMapOutputValueClass(MapWritable.class);

		try{
	    	Path p=new Path(INPUT_PATH);
	    	FileInputFormat.addInputPath(job, p);
	    	FileInputFormat.setInputDirRecursive(job, true);
		}
		catch(Exception e) {
			
		}
		 FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));
	    
	    job.setNumReduceTasks(0);
	    
	    job.waitForCompletion(true);
	    return true;

	}
  	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ESInserterUserStoryDriver.configure(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
