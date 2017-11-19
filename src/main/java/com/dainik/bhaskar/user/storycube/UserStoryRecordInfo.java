package com.dainik.bhaskar.user.storycube;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dainik.bhaskar.common.ConstantValue;
import com.google.gson.Gson;

import org.apache.hadoop.io.WritableComparable;

public class UserStoryRecordInfo implements WritableComparable<UserStoryRecordInfo> {
	
	private String session_id;
	private ArrayList<Integer> story_id;
	private Gson gson = new Gson();
	
	public UserStoryRecordInfo() {
		session_id = new String("");
		story_id = new ArrayList<Integer>();
	}

	public void readFields(DataInput in) throws IOException {
		session_id = new String(in.readUTF());
		int count = in.readInt();
		story_id = new ArrayList<Integer>();
		
		for (int i = 0; i < count; i++) {
			story_id.add(in.readInt());
		}
		
	}

	public void write(DataOutput out) throws IOException {
		
		out.writeUTF(session_id);
		out.writeInt(story_id.size());
		for (Integer sid : story_id){
			out.writeInt(sid);
		}
		
	}

	public int compareTo(UserStoryRecordInfo o) {
		
		int result = 0;
		
		result = session_id.compareTo(o.session_id);
		
		if(result != 0)
			return result;
					
		return result;
	}
	
	
	@Override
	public boolean equals(Object o) 
	{
	    if (o instanceof UserStoryRecordInfo) 
	    {
	    	UserStoryRecordInfo other = (UserStoryRecordInfo) o;
	        return session_id.equals(other.session_id);
	    }
	     return false;
	}

	
	@Override
	public int hashCode() { 	
		StringBuffer sb = new StringBuffer();
		sb.append(session_id);
		
		return sb.hashCode();
		
	}
	
	@Override 
	public String toString() {
		
		
		Map<String,Object> sessionDetail = new HashMap<String,Object>();
		sessionDetail.put(ConstantValue.SESSIONID, session_id);
		Integer [] story_list = new Integer[story_id.size()];
		int i=0;
		for(Integer sid: story_id){
			story_list[i] = sid;
			i++;
		}
		
		sessionDetail.put(ConstantValue.STORYSEQ, story_list);	
		String json = gson.toJson(sessionDetail);
		return json;
	}
	
	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public ArrayList<Integer> getStory_id() {
		return story_id;
	}

	public void setStory_id(Integer [] storyList) {
		Map<Integer, Object> dedup = new HashMap<Integer, Object>();
		Object one = new Object();
		for(Integer sid: storyList){
			if(dedup.get(sid) == null){
				story_id.add(sid);
				dedup.put(sid, one);
			}
		}
	}

}
