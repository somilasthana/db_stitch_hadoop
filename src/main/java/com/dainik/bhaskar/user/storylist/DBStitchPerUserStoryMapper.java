package com.dainik.bhaskar.user.storylist;

import java.io.IOException;
import java.util.Calendar;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
/*import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.Days;
import org.elasticsearch.common.joda.time.format.DateTimeFormat;
import org.elasticsearch.common.joda.time.format.DateTimeFormatter;*/
import org.joda.time.format.DateTimeFormatter;

import com.dainik.bhaskar.common.ConstantValue;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DBStitchPerUserStoryMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	String Session_ID="";
    String Story_ID="";
    String host="";
  
    private DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
	private static int NUMDAYS = -30;
	Calendar starttime = null;
	Calendar endtime = null;
	 JsonParser parser=null;
	 JsonElement element=null;
	 JsonObject obj=null;
	 
	@Override
	protected void map(LongWritable key, Text value,Context context) throws IOException,InterruptedException {
	
		try {
			 parser = new JsonParser();
 	    	 element = parser.parse(value.toString());
 		     obj=element.getAsJsonObject();
 		         Session_ID=obj.get("session_id").getAsString();
		 		Story_ID=obj.get("storyid").getAsString();
		 		host=obj.get("host").getAsString();
		 		 
//		       if(obj.get("host").getAsString() != null){
//		         }else {
//		         	return;
//		         }
		 		
		 		System.out.println("record:"+obj.toString());
		         String image = null;
		         
		         if(obj.get("image") != null){
		         	image = obj.get("image").getAsString().trim();
		         }else {
		         	return;
		         }
		         
		         
		         if(image.equals("")){
		 			// We would not consider a recommendation without an image.
		 			return;
		 		}
		         
		         int pgTotal = 0;
		         
		         
		         if(obj.get("pgtotal") != null){
		         	try{
		         		double dpgTotal = obj.get("pgtotal").getAsDouble();
		         		pgTotal = (int)dpgTotal;
		         	}catch(java.lang.NumberFormatException ne){
		         		throw new IOException(value.toString());
		         	}
		         }else {
		         	return;
		         }
		         
		         if(pgTotal < 2){
		 			// We would not consider a recommendation without an image.
		 			return;
		 		}
		         
		         
		         if(obj.get("tracker") != null ){
		         	
		              String tracker = obj.get("tracker").getAsString();
		              
		              if(tracker.contains("ureco")){
		             	 return;
		              }
		         }
		         
		         if(obj.get("story_pubtime") != null){
		         	
		         	DateTime storypubTime = formatter.parseDateTime(obj.get("story_pubtime").getAsString());
		         	int days = Days.daysBetween(storypubTime, DateTime.now()).getDays();
		         	if(days > 30)
		         		{return;}
		         }else {
		         	return;
		         }
		
		        /* if((Session_ID.equals(null)) || (Session_ID.isEmpty())) {
		        	 return;
		         }
		         
		         if((Story_ID.equals(null)) || (Story_ID.isEmpty()) || (Story_ID.equals("0")) ) {
		        	return; 
		         }*/
		         
		         if(Session_ID==null) {
		        	 
		         }else if(host == null) {
		        	 
		         }
		         else if(host.isEmpty()) {
		        	 
		         }
		         else if (Session_ID.isEmpty()) {
		        	 
		         }else if (Story_ID==null) {
		        	 
		         }else if(Story_ID.isEmpty()) {
		        	 
		         }else if (Story_ID.equals("0")) {
		        	 
		         }else
			 		
			 			if((host.equals("1")) || (host.equals("2")) || (host.equals("3")) || (host.equals("15"))) {
			 				// For Dainik Bhaskar
			 	     	    context.write(new Text(ConstantValue.BHASKARCODE+Session_ID), new Text(Story_ID));
			 			}else if((host.equals("5")) || (host.equals("6")) || (host.equals("7")) || (host.equals("20"))) {
			 			    // For Divya Bhaskar
		 		    		context.write(new Text(ConstantValue.DIVYACODE+Session_ID), new Text(Story_ID));
			 			}else if((host.equals("4")) || (host.equals("27")) ) {
			 			    // For Bhaskar Money
		 	     			context.write(new Text(ConstantValue.MONEYBHASKARCODE+Session_ID), new Text(Story_ID));
			 			}/*else if((host.equals("8"))) {
			 			    // For Divya Money
			 				context.write(new Text("MD"+Session_ID), new Text(Story_ID));
			 			}*/else if((host.equals("16"))) {
			 			    // For App
			 				context.write(new Text(ConstantValue.APPCODE+Session_ID), new Text(Story_ID));
			 			}
		        	
		}catch(Exception e) {e.printStackTrace();}
     }


 }
 
