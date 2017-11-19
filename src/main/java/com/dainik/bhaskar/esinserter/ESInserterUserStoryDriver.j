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
//import com.dainik.bhaskar.util.ESQueryExecutorService;
//import com.dainik.bhaskar.util.ESQueryNew;


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
		//	 System.out.println("Inside ES insert Driver "+hostMap);
		}
	}
	
	public static boolean configure(String[] args) throws Exception {
		
		/*if(args.length < 4) {
			System.out.println("Usage : <inputpath> <outputpath>");
		}*/
	/*	try{
		System.out.println("Mapping deleting start...");	
		ESQueryNew esQueryExecutorService=ESQueryNew.getInstance();
		esQueryExecutorService.deleteIndex("uncommonstoryinsert","storypath");
		System.out.println("Bhaskar mapping deleted...");	
		esQueryExecutorService.deleteIndex("divyauncommonstoryinsert","divyastorypath");
		System.out.println("Divya mapping deleted...");	
		esQueryExecutorService.deleteIndex("bhaskarmoneyuncommonstoryinsert","moneystorypath");
		System.out.println("Money mapping deleted...");	
		esQueryExecutorService.deleteIndex("appuncommonstoryinsert","appstorypath");
		System.out.println("App mapping deleted...");	
		System.out.println("All mapping deleted...");
		esQueryExecutorService.close();
		}catch(Exception e) {
			e.printStackTrace();
		}*/
		
		
		//System.exit(0);
		
		String INPUT_PATH = args[0];
		String OUTPUT_PATH = args[1];
		System.out.println("Input Path" + INPUT_PATH);
		System.out.println("Output Path" + OUTPUT_PATH);
		
		Configuration conf = new Configuration();

		String eSNodes_ = config.getProperty(ConstantValue.CONFIG_CONNECT_STR);
		//conf.set("es.nodes", eSNodes_.split(",")[0]);//"es-server:9200");
		conf.set("es.nodes","10.140.0.56:9200");
		conf.set("es.resource", "divyauncommonstoryinsert/divyastorypath");
		conf.set("es.mapping.id", ConstantValue.SESSIONID);

		conf.setBoolean("mapred.map.tasks.speculative.execution", false);
		conf.setBoolean("mapred.reduce.tasks.speculative.execution", false);

		Job job = Job.getInstance(conf, ":: MR code for ES insertion for channels ::");
		job.setJarByClass(ESInserterUserStoryDriver.class);
		job.setMapperClass(ESInserterUserStoryDriverMapper.class);
		
		
		//job.setMapOutputKeyClass(NullWritable.class);
		//job.setMapOutputValueClass(NullWritable.class);
		job.setOutputFormatClass(EsOutputFormat.class);
		job.setMapOutputValueClass(MapWritable.class);

		// FileInputFormat.addInputPath(job, new Path("/output_new2/"));
	 
		try{
	    	Path p=new Path(INPUT_PATH);
	    	FileInputFormat.addInputPath(job, p);
	    	FileInputFormat.setInputDirRecursive(job, true);
		}
		catch(Exception e) {
			
		}
		 FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));
	    
	  /*  String eSNodes = config.getProperty(ConstantValue.CONFIG_CONNECT_STR);
	    System.out.println(">> Es Node >> "+eSNodes);
        String clusterName = config.getString(ConstantValue.CONFIG_CLUSTER_NAME_STR); 	    
        System.out.println(">> Cluster Name >> "+clusterName);
        
        job.getConfiguration().set(ConstantValue.CONFIG_CONNECT_STR, eSNodes);
        job.getConfiguration().set(ConstantValue.CONFIG_CLUSTER_NAME_STR, clusterName);*/
       
	  /*  System.out.println("Pushing Data in Indice " + args[2] + " MapType " + args[3]);
	    job.getConfiguration().set(ConstantValue.INDICES_NAME, args[2]);
	    job.getConfiguration().set(ConstantValue.TYPE_NAME, args[3]);*/
	    
	    job.setNumReduceTasks(0);
	    
	    job.waitForCompletion(true);
	    return true;
	    //System.exit(job.waitForCompletion(true) ? 0 : 1);
		
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
