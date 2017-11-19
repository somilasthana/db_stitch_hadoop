package com.dainik.bhaskar.esinserter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import com.dainik.bhaskar.DBAnalyticsException;
import com.dainik.bhaskar.common.ConstantValue;
import com.dainik.bhaskar.util.DBConfig;
//import com.dainik.bhaskar.util.ESQueryExecutorService;
//import com.dainik.bhaskar.util.ESQueryNew;
//import com.dainik.bhaskar.util.EsTransportClient;
import com.google.gson.Gson;

public class ESInserterUserStoryDriverMapper extends Mapper<LongWritable, Text, NullWritable, MapWritable> {
	
	private Gson gson;
	private Map<String, Integer> hostMap = null;
	//private static DBConfig config = DBConfig.getInstance();

	//private ESQueryNew cli = null;
//	private EsTransportClient queryExecutorService = null;
	private static Logger log = Logger.getLogger(ESInserterUserStoryDriverMapper.class);


	//private int bhakharBulkCNT = 0;
	//private int divyaBulkCNT = 0;
	//private int divyaMoneyBulkCNT = 0;
	//private int bhakharMoneyBulkCNT = 0;
	//private int appBulkCNT = 0;
	
	
	//private List<Map<String, Object>> bhaskarRecordList;
	//private List<Map<String, Object>> divyaRecordList;
	//private List<Map<String, Object>> bhaskarMoneyRecordList;
	//private List<Map<String, Object>> divyaMoneyRecordList;
	//private List<Map<String, Object>> appRecordList;
	
	
	//private String _INDICE;
	private static String ID = "_id";
	
	//private String _TYPE_BHASKAR;
	//private String _TYPE_DIVYA;
	//private String _TYPE_DIVYA_MONEY;
	//private String _TYPE_BHASKAR_MONEY;
	//private String _TYPE_APP;
	
	
    
	
	public void setup(Context context) throws IOException, InterruptedException {
		
		//bhakharBulkCNT = 0;
		//divyaBulkCNT=0;
		//divyaMoneyBulkCNT=0;
		//bhakharMoneyBulkCNT=0;
		//appBulkCNT=0;
		/*
		 hostMap = new HashMap<String, Integer>();
		 
		 bhaskarRecordList = new ArrayList<Map<String, Object>>();
		 divyaMoneyRecordList = new ArrayList<Map<String, Object>>();
		 bhaskarMoneyRecordList = new ArrayList<Map<String, Object>>();
		 divyaRecordList = new ArrayList<Map<String, Object>>();
		 appRecordList = new ArrayList<Map<String, Object>>();
		*/
		 
		 gson = new Gson();
        /* String clusterName = context.getConfiguration().get(ConstantValue.CONFIG_CLUSTER_NAME_STR);
		 String eSNodes = context.getConfiguration().get(ConstantValue.CONFIG_CONNECT_STR);//"172.31.22.157:9300,172.31.31.152:9300";//config.getProperty(Constants.CONFIG_CONNECT_STR);
		 for (Object node : eSNodes.split(",")) {
			 if ("".equals(node)) {
                 throw new DBAnalyticsException("Invalid index.elasticsearch.connect property in config file");
			 }
			 
			 String hostName = ((String) node).split(":")[0];
			 Integer port = Integer.parseInt(((String) node).split(":")[1]);
			 hostMap.put(hostName, port);
			 
		}*/
		 
		//cli=ESQueryNew.getInstance();
		 
	/*	_INDICE = context.getConfiguration().get(ConstantValue.INDICES_NAME, ConstantValue.ANOMALOUS_RECO_INDICE);
		_TYPE_BHASKAR = context.getConfiguration().get(ConstantValue.TYPE_NAME, "storypath");
		_TYPE_DIVYA = context.getConfiguration().get(ConstantValue.TYPE_NAME, "divyastorypath");
		_TYPE_DIVYA_MONEY = context.getConfiguration().get(ConstantValue.TYPE_NAME, "moneydivyastorypath");
		_TYPE_BHASKAR_MONEY = context.getConfiguration().get(ConstantValue.TYPE_NAME, "moneystorypath");
		_TYPE_APP = context.getConfiguration().get(ConstantValue.TYPE_NAME, "appstorypath");*/
		 
	}
	
	private enum Channels {
	   divya,bhaskar,bhaskarMoney,divyaMoney,app;
	}
	
	@SuppressWarnings({ "unchecked" })
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
 
	    String json = value.toString().trim();
        HashMap<String,Object> sStoryStitch = gson.fromJson(json, HashMap.class);
        
        String strChannel= sStoryStitch.get("channel").toString().trim();
		MapWritable divyaStich = new MapWritable();
		//divyaStich.put(new Text("id"), new Text(String.valueOf(sStoryStitch.get("session_id"))));
		divyaStich.put(new Text(ConstantValue.SESSIONID), new Text(String.valueOf(sStoryStitch.get("session_id"))));
		ArrayList<String> divyaSeq = (ArrayList<String>) sStoryStitch.get(ConstantValue.STORYSEQ);
		IntWritable [] storylist = new IntWritable[divyaSeq.size()];
		int i=0;
		for(String storyid: divyaSeq){
			storylist[i] = new IntWritable(Integer.parseInt(storyid));
			i++;
		}
		ArrayWritable aw = new ArrayWritable(IntWritable.class, storylist);
		divyaStich.put(new Text(ConstantValue.STORYSEQ),aw);

		context.write(NullWritable.get(), divyaStich);

		try {
                                                                                                                                                                                                                                                                                                		Channels switchVar=Channels.valueOf(strChannel);
            
            
	        
	       switch (switchVar) {
			/*case bhaskar:
		        HashMap<String,Object> bhaskarStich=new HashMap<String, Object>();
				ArrayList<String> bhaskarPath = (ArrayList<String>) sStoryStitch.get("storysequence");
				    ArrayList<Integer> bhaskarList=new ArrayList<Integer>();
			        for (String bhaskarId: bhaskarPath){
			        	bhaskarList.add(Integer.parseInt(bhaskarId)); 
			        }
			        bhaskarStich.put("storysequence", bhaskarList);
			
			        if(sStoryStitch.get("session_id")!= null) {
			           	bhaskarStich.put(ID, sStoryStitch.get("session_id"));
			        }
			        if(bhakharBulkCNT == ConstantValue.MAXUPDATE) {
			        	
			        //	queryExecutorService.UpdateBulkRecordIndex("uncommonstoryinsert","storypath", bhaskarRecordList);
			        //log.info(":: Inside Bulk Count :: \n"+bhaskarRecordList);
			        	cli.UpdateBulkRecordIndex("uncommonstoryinsert","storypath", bhaskarRecordList);
			        	
			        	bhaskarRecordList = new ArrayList<Map<String, Object>>();
			        	bhakharBulkCNT = 0;
			        }
			        else {
			        	bhakharBulkCNT++;
					}
			        bhaskarRecordList.add(bhaskarStich);
			        // bhaskarStich.clear();
				break;*/
			/*case divya:
		        HashMap<String,Object> divyaStich=new HashMap<String, Object>();
				ArrayList<String> divyaPath = (ArrayList<String>) sStoryStitch.get("storysequence");
			        ArrayList<Integer> divyaList=new ArrayList<Integer>();
			        for (String divyaId: divyaPath){
			        	divyaList.add(Integer.valueOf(divyaId)); 
			        }
			        divyaStich.put("storysequence", divyaList);
			        if(sStoryStitch.get("session_id")!= null)
			        	divyaStich.put(ID, sStoryStitch.get("session_id"));
			        if(divyaBulkCNT == ConstantValue.MAXUPDATE) {
			        	cli.UpdateBulkRecordIndex("divyauncommonstoryinsert","divyastorypath", divyaRecordList);
			        	divyaRecordList = new ArrayList<Map<String, Object>>();
			        	divyaBulkCNT = 0;
			        }
			        else {
			        	divyaBulkCNT++;
					}
			        divyaRecordList.add(divyaStich);
				break;*/
			/*case bhaskarMoney:
		        HashMap<String,Object> bhaskarMoneyStich=new HashMap<String, Object>();
				ArrayList<String> bhaskarMoneyPath = (ArrayList<String>) sStoryStitch.get("storysequence");
			        ArrayList<Integer> bhaskarMoneyList=new ArrayList<Integer>();
			        for (String bhaskarMoneyId: bhaskarMoneyPath){
			        	bhaskarMoneyList.add(Integer.valueOf(bhaskarMoneyId)); 
			        }
			        bhaskarMoneyStich.put("storysequence", bhaskarMoneyList);
			        if(sStoryStitch.get("session_id")!= null)
			        	bhaskarMoneyStich.put(ID, sStoryStitch.get("session_id"));
			        if(bhakharMoneyBulkCNT == ConstantValue.MAXUPDATE) {
			        	cli.UpdateBulkRecordIndex("bhaskarmoneyuncommonstoryinsert","moneystorypath", bhaskarMoneyRecordList);
			        	bhaskarMoneyRecordList = new ArrayList<Map<String, Object>>();
			        	bhakharMoneyBulkCNT = 0;
			        }
			        else {
			        	bhakharMoneyBulkCNT++;
					}
			        bhaskarMoneyRecordList.add(bhaskarMoneyStich);
				break;*/
				/*	case divyaMoney:
				  HashMap<String,Object> divyaMoneyStich=new HashMap<String, Object>();
				ArrayList<String>  divyaMoneyPath = (ArrayList<String>) sStoryStitch.get("storysequence");
			        ArrayList<Integer> divyaMoneyList=new ArrayList<Integer>();
			        for (String divyaMoneyId: divyaMoneyPath){
			        	divyaMoneyList.add(Integer.valueOf(divyaMoneyId)); 
			        }
			        divyaMoneyStich.put("storysequence", divyaMoneyList);
			        if(sStoryStitch.get("session_id")!= null)
			        	divyaMoneyStich.put(ID, sStoryStitch.get("session_id"));
			        if(divyaMoneyBulkCNT == ConstantValue.MAXUPDATE) {
			        	cli.UpdateBulkRecordIndex("divyamoneyuncommonstoryinsert","moneydivyastorypath", divyaMoneyRecordList);
			        	divyaMoneyRecordList = new ArrayList<Map<String, Object>>();
			        	divyaMoneyBulkCNT = 0;
			        }
			        else {
			        	divyaMoneyBulkCNT++;
					}
			        divyaMoneyRecordList.add(divyaMoneyStich);
				break;*/
			/*case app:
			    HashMap<String,Object> appStich=new HashMap<String, Object>();
				ArrayList<String> appPath = (ArrayList<String>) sStoryStitch.get("storysequence");
			        ArrayList<Integer> appList=new ArrayList<Integer>();
			        for (String appId: appPath){
			        	appList.add(Integer.valueOf(appId)); 
			        }
			        appStich.put("storysequence", appList);
			        if(sStoryStitch.get("session_id")!= null)
			        	appStich.put(ID, sStoryStitch.get("session_id"));
			        if(appBulkCNT == ConstantValue.MAXUPDATE) {
			        	cli.UpdateBulkRecordIndex("appuncommonstoryinsert","appstorypath", appRecordList);
			        	appRecordList = new ArrayList<Map<String, Object>>();
			        	appBulkCNT = 0;
			        }
			        else {
			        	appBulkCNT++;
					}
			        appRecordList.add(appStich);
				break;*/
				
			default:
				//	break;
			}
	        }catch(Exception e) {e.printStackTrace();}
    }

	public void cleanup(Context context) throws IOException, InterruptedException {
		/*
		if(bhakharBulkCNT != 0) {
				cli.UpdateBulkRecordIndex("uncommonstoryinsert","storypath", bhaskarRecordList);
		}
		else if(divyaBulkCNT != 0) {
			cli.UpdateBulkRecordIndex("divyauncommonstoryinsert","divyastorypath", divyaRecordList);
	  	}
		else if(bhakharMoneyBulkCNT != 0) {
			cli.UpdateBulkRecordIndex("bhaskarmoneyuncommonstoryinsert","moneystorypath", bhaskarMoneyRecordList);
		}
//		else if(divyaMoneyBulkCNT != 0) {
//				cli.UpdateBulkRecordIndex("divyamoneyuncommonstoryinsert","moneydivyastorypath", divyaMoneyRecordList);
//		}
		else if(appBulkCNT != 0) {
			cli.UpdateBulkRecordIndex("appuncommonstoryinsert","appstorypath", appRecordList);
		}else {
			
		}
		*/
	}
}
