package com.dainik.bhaskar.user.storylist;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.json.simple.JSONObject;

import com.dainik.bhaskar.common.ConstantValue;

public class DBStitchPerUserStoryReducer extends Reducer<Text, Text, Text, Writable>{
	
	static String divyaBhaskar = "divyaBhaskar";
    static String dainikBhaskar = "dainikBhaskar";
    static String divyaMoney = "divyaMoney";
    static String dainikMoney = "bhaskarMoney";
    static String app = "app";
     
    MultipleOutputs<Text, Text> mos;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void setup(Context context) {
        mos = new MultipleOutputs(context);
    }
    
 	@SuppressWarnings("unchecked")
	@Override
	protected void reduce(Text key, Iterable<Text> value,Context context)
			throws IOException, InterruptedException {
		    ArrayList<String> list = new  ArrayList<String>();
		    ArrayList<String> list1 = new  ArrayList<String>();
		    ArrayList<String> list2 = new  ArrayList<String>();
		    ArrayList<String> list3 = new  ArrayList<String>();
		    ArrayList<String> list4 = new  ArrayList<String>();
		    
			if(key.toString().startsWith(ConstantValue.BHASKARCODE)) {
				for(Text val:value) {
					list.add(val.toString());
				}
				  JSONObject obj = new JSONObject();
				      obj.put("session_id", key.toString().substring(1));
				      obj.put("storysequence", list);
				      obj.put("channel", "bhaskar");
				 mos.write(dainikBhaskar,new Text(obj.toString()), NullWritable.get(), "dainikBhaskar");
				 list.clear();
		    }
		    else if(key.toString().startsWith(ConstantValue.DIVYACODE)) {
					for(Text val:value) {
						list1.add(val.toString());
					}
					JSONObject obj1 = new JSONObject();
				      obj1.put("session_id", key.toString().substring(1));
				      obj1.put("storysequence", list1);
				      obj1.put("channel", "divya");
					 mos.write(divyaBhaskar,new Text(obj1.toString()), NullWritable.get(), "divyaBhaskar");
		            list1.clear();
			}
		    else if(key.toString().startsWith(ConstantValue.MONEYBHASKARCODE)) {
		    	for(Text val:value) {
					list2.add(val.toString());
				}
				JSONObject obj1 = new JSONObject();
			      obj1.put("session_id", key.toString().substring(2));
			      obj1.put("storysequence", list2);
			      obj1.put("channel", "bhaskarMoney");
				 mos.write(dainikMoney,new Text(obj1.toString()), NullWritable.get(), "bhaskarMoney");
	            list2.clear();
		    }
		/*    else if(key.toString().startsWith("MD")) {
		    	for(Text val:value) {
					list3.add(val.toString());
				}
				JSONObject obj1 = new JSONObject();
			      obj1.put("session_id", key.toString().substring(2));
			      obj1.put("storysequence", list3);
			      obj1.put("channel", "divyaMoney");
				 mos.write(divyaMoney,new Text(obj1.toString()), NullWritable.get(), "divyaMoney");
	            list3.clear();
		    } */
		    else if(key.toString().startsWith(ConstantValue.APPCODE)) {
		    	for(Text val:value) {
					list4.add(val.toString());
				}
				JSONObject obj1 = new JSONObject();
			      obj1.put("session_id", key.toString().substring(3));
			      obj1.put("storysequence", list4);
			      obj1.put("channel", "app");
				 mos.write(app,new Text(obj1.toString()), NullWritable.get(), "app");
	            list4.clear();
		    }
		}
	
	  protected void cleanup(Context context) throws IOException, InterruptedException {
         mos.close();
       }
		
}