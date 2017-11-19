package com.dainik.bhaskar.anomalousreco;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.dainik.bhaskar.DBAnalyticsException;
import com.dainik.bhaskar.common.ConstantValue;
import com.dainik.bhaskar.esinserter.ESInserterUserStoryDriver;
import com.dainik.bhaskar.user.storylist.DBStitchPerUserStoryDriver;
import com.dainik.bhaskar.util.DBConfig;
//import com.dainik.bhaskar.util.ESQueryExecutorService;
//import com.dainik.bhaskar.util.ESQueryNew;

public class AnomalousRecommendationMainClass {
	private static Logger log = Logger.getLogger(AnomalousRecommendationMainClass.class);
	private static int NUMDAYS = -15;
	private static final SimpleDateFormat ftv = new SimpleDateFormat ("yyyy-MM-dd:HH:mm:00");
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		FileSystem hdfs = null;
		//ESQueryExecutorService queryExecutorService = null;
		
		DateTime currentTime = DateTime.now();
		DateTime prevTime = null;
		Map<String, Integer> hostMap = new HashMap<String, Integer>();
		DBConfig config = DBConfig.getInstance();
		boolean hadoop = true;
	
		String eSNodes = config.getProperty(ConstantValue.CONFIG_CONNECT_STR);
		System.out.println(" Starting Anomalous Driver... " + ftv.format(Calendar.getInstance().getTime()));
		
		try {
			hdfs = FileSystem.get(new Configuration());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		       
		
		
		    String clusterName = config.getProperty(ConstantValue.CONFIG_CLUSTER_NAME_STR);//context.getConfiguration().get(ConstantValue.CONFIG_CLUSTER_NAME_STR);
     	//	System.out.println("Cluster Name - "+clusterName);
     	//	System.out.println("host Map "+hostMap);
		//  client=EsTransportClient.getInstance();
		//  queryExecutorService = new ESQueryExecutorService(hostMap, clusterName);
     		try {
     		//	  queryExecutorService = new ESQueryExecutorService("10.140.0.48",9200);
     		//	 queryExecutorService = new ESQueryExecutorService();
			} catch (Exception e1) {
				e1.getMessage();
			}

			/* Stitched Stories in ES for Uncommon/common Query Search
			 * 
			 *  current date we can last 15 days of realtime data which could approx 40 G * 15  = 600 G of data 
			 *  it will stitch records 
			 *  id, session_id, [ storyid...]
			 * 
			 * */
		
			/* Hadoop is required to stitch */
			/* Hadoop is also required to populate the stitched table in ES  ConstantValue.ANOMALOUS_RECO_INDICE, ConstantValue.ANOMALOUS_RECO_TYPE */
			
		if(hadoop)
		{
			System.out.println(" Starting Hadoop Jobs... " + ftv.format(Calendar.getInstance().getTime()));
			Calendar calNow = Calendar.getInstance();
			calNow.add(Calendar.DATE, -1); // 6th April then 5th April realtime data will be in HDFS 
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy_MM_dd");
			String currentdate = ft.format(calNow.getTime());
			calNow.add(Calendar.DATE, NUMDAYS);
			String backdate = ft.format(calNow.getTime());
		
			int iday = NUMDAYS;
			StringBuffer dateArg = new StringBuffer();
			calNow = Calendar.getInstance();
			calNow.add(Calendar.DATE, -1);
			while(iday <= 0){
				String realtime_filename = "/realtime/"+ft.format(calNow.getTime()) + "/realtime_"+ft.format(calNow.getTime());
				try {
					if(hdfs.exists(new Path(realtime_filename))){
						dateArg.append(realtime_filename).append(",");
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				calNow.add(Calendar.DATE, -1);
				iday++;
			}
			
			
			   
			try {
				/**
				 * MR Job for processing the last 15th days data.
				 */
				
				boolean dBRecoJob = true;
				if(dBRecoJob) {
					System.out.println(" Starting Bhaskar Hadoop Jobs... " + ftv.format(Calendar.getInstance().getTime()));
					String stitch_output = ConstantValue.HADOOP_USER_STITCH_PATH + currentdate; // "stitch_output/"
			//		System.out.println("Stitch Input  ::"+dateArg.toString().substring(0, dateArg.length()-1));
					System.out.println("Stitch Output :: "+stitch_output);
		    //		String input="/realtime/2017_09_06/realtime_2017_09_06";
					/*  /realtime/2016_03_22/realtime_2016_03_22,/realtime/2016_03_22/realtime_2016_03_21 */
					String [] stitch_args = new String [] { "\""+dateArg.toString().substring(0, dateArg.length()-1) +"\"", stitch_output};
					
			//		String [] stitch_args = new String [] { input, stitch_output};
					System.out.println("Cleaning Stitch Directory " + stitch_output);
					Path stitch_output_path = new Path(stitch_output);
					if(hdfs.exists(stitch_output_path)){
						System.out.println("------------Delete HDFS Directory------------------");
//						hdfs.delete(stitch_output_path, true);
					}
					System.out.println("Feeding Input to DBDriver..." + Arrays.toString(stitch_args) + " " + ftv.format(Calendar.getInstance().getTime()));
					try{
					     DBStitchPerUserStoryDriver.configure(stitch_args);
					}catch(Exception e) {
						e.printStackTrace();
						System.exit(0);
					}
					
					System.out.println("\n\n*********************MR-Job Done Now**************************\n\n");
	                
					/**
					 *  Insert data to Elastic Search
					 */
					System.out.println("Now ES-MR Job is start now.....");
	                String es_insert_output="/ESNullInput";

	             //   String es_insert_input="/storystitch/2017_05_02/";
		              
	                Path es_insert_path = new Path(es_insert_output);
					if(hdfs.exists(es_insert_path)){
						hdfs.delete(es_insert_path, true);
					}

					String [] indexMappingInfo = new String [] { ConstantValue.ANOMALOUS_RECO_INDICE+"/"+ConstantValue.DIVYASTORYPATH,
																 ConstantValue.ANOMALOUS_RECO_INDICE+"/"+ConstantValue.STORYPATH,
																 ConstantValue.ANOMALOUS_RECO_INDICE+"/"+ConstantValue.APPSTORYPATH,
																 ConstantValue.ANOMALOUS_RECO_INDICE+"/"+ConstantValue.MONEYSTORYPATH
												};

					for(String tableInfo: indexMappingInfo) {

						String es_insert_input = stitch_output +"/"+ConstantValue.EStopartfile.get(tableInfo);
						String[] esinsert_args = new String[]{es_insert_input, es_insert_output,tableInfo};
						System.out.println("ES Args.. " + es_insert_input + "," + es_insert_output + "," + tableInfo);

						ESInserterUserStoryDriver.configure(esinsert_args);
					}
	                
	                System.out.println("\n\n*********************ES-MR-Job Done Now**************************\n\n");
	                System.exit(0);
					
				}else {}
				} catch (Exception e) {e.printStackTrace();}
			    /*finally{queryExecutorService.close();}*/
			    }
       	try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {e.printStackTrace();}
	   }
   }
