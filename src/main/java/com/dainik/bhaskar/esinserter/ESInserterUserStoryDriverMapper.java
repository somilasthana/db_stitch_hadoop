package com.dainik.bhaskar.esinserter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import com.dainik.bhaskar.common.ConstantValue;
import com.google.gson.Gson;

public class ESInserterUserStoryDriverMapper extends Mapper<LongWritable, Text, NullWritable, MapWritable> {
	
	private Gson gson;
	private static Logger log = Logger.getLogger(ESInserterUserStoryDriverMapper.class);


	public void setup(Context context) throws IOException, InterruptedException {
		
		 gson = new Gson();
	}


	@SuppressWarnings({ "unchecked" })
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
 
	    String json = value.toString().trim();
        HashMap<String,Object> sStoryStitch = gson.fromJson(json, HashMap.class);
        
        String strChannel= sStoryStitch.get("channel").toString().trim();
		MapWritable stich = new MapWritable();
		//divyaStich.put(new Text(ID), new Text(String.valueOf(sStoryStitch.get("session_id"))));
		stich.put(new Text(ConstantValue.SESSIONID), new Text(String.valueOf(sStoryStitch.get(ConstantValue.SESSIONID))));
		ArrayList<String> divyaSeq = (ArrayList<String>) sStoryStitch.get(ConstantValue.STORYSEQ);
		IntWritable [] storylist = new IntWritable[divyaSeq.size()];
		int i=0;
		for(String storyid: divyaSeq){
			storylist[i] = new IntWritable(Integer.parseInt(storyid));
			i++;
		}
		ArrayWritable aw = new ArrayWritable(IntWritable.class, storylist);
		stich.put(new Text(ConstantValue.STORYSEQ),aw);

		context.write(NullWritable.get(), stich);

    }

	public void cleanup(Context context) throws IOException, InterruptedException {
	}
}
