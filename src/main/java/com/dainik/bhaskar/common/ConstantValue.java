package com.dainik.bhaskar.common;

import java.util.HashMap;
import java.util.Map;


public class ConstantValue {

	public static final String UNCOMMONSTORYINSERT = "uncommonstoryinsert";

	public static final String STORYPATH = "storypath";
	public static final String DIVYASTORYPATH = "divyastorypath";
	public static final String MONEYSTORYPATH = "moneystorypath";
	public static final String DIVYAMONEYSTORYPATH = "moneydivyastorypath";
	public static final String APPSTORYPATH = "appstorypath";


	public static final String STORYCONTENTURI = " http://appfeed.bhaskar.com/appFeedV2/News/521/";
	public static final String USERPASSWD = "admin:admin";
	public static final String STORYKEY = "story";
	public static final String OUTPUTSTORYCONTENTFILE = "StoryContent.csv";
	public static final String ID = "_id";
	public static final boolean RUNASREALTIME = true;
	public static final int RECOMMENDATIONLENGTH = 8;
	
	
	public static String STORYSEQ = "storysequence";
	public static String STORY_UNIQUE_DETAILS = "story_unique_detail";
	public static String REALTIME_TYPE = "realtime";
	public static String UNIQUE_USERSTORY_TYPE = "unique_userstory";
	
	public static String STORYTITLE = "storytitle";
	public static String TITLE = "title";
	public static String STORYID = "storyid";
	public static String HOST = "host";
	public static String PVS = "pvs";
	public static String PCAT_ID = "pcat_id";
	public static String DATETIME = "datetime";
	public static String STORY_MODIFIEDTIME = "story_modifiedtime";
	public static String PGTOTAL = "pgtotal";
	public static final String IMAGE = "image";
	public static final String BLANK = "";
	
	public static final String INDEX_STORY_DETAIL = "story_detail";
	public static final String STORY_ID_FIELD = "storyid";
	public static final String TRACKER = "tracker";
	public static final String URL = "url";
	public static final String CAT_ID_FIELD = "cat_id";
	public static final String STORY_ATTRIBUTE = "story_attribute";
	public static final String DIMENSION = "dimension";
	
	public static String STORY_PUBLISH_DATE = "story_pubtime";
	public static String MOSTREADTITLES = "MostReadStoryInThis";
	public static String MOSTREADOVERALL = "MostReadStoryOverall";
	
	public static int DEFAULTQUERYSIZE = 20;
	public static int REPEATDEFAULTQUERYSIZE = 10;
	public static int DEFAULTSCANSIZE = 1000;
	public static int TIMEOUTVALUE = 60000000;
	public static int SLEEPTIMEOUT = 5000;
	public static int MAXUPDATE = 100;
	
	public static String REGEX_SPLIT = "\\s+"; 
	public static String NOUNPOS = "NOUN";
	public static final String ADJECTIVEPOS = "ADJECTIVE";
	public static String DEFAULT_DATE_FORMAT  = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	
	public static String RECOMMENDATION_INDICE = "recommendation";
	public static String RECOMMENDATION_TYPE  = "keywordreco";
	public static final String FIELD_RECOMMENDATION = "recommended_ids";
	public static final String VERSION = "version";
	
	public static final String STORY_KEYWORD_INDEX = "storykeyword";
	
	public static final String UNCOMMONRECO = "uncommonrecommended_ids";
	public static final String UNCOMMONLOGICVERSION = "uncommon";
	public static final int MAXINSERTSIZE = 500;
	public static final String UNCOMMONRECO_TYPE = "uncommonreco";
	public static final String UNCOMMONRECOMMENDATION_INDICE = "uncommonrecommendation";
	public static final String KEYWORDLOGICVERSION = "keyword";
	public static final String COMBINEDKEYWORDUNCOMMONVERSION = "keywordplusuncommon";
	public static final String GLOBALTRENDINGVERSION = "globaltrendingrecommendation";
	
	public static final String FRESHSTORY_INDEX = "freshstory";
	public static final String STORYCONTENT = "story_content";
	public static final String METAKEYWORD = "meta_keyword";
	public static final String PERCOLATE = ".percolator";
	public static final String CONTENTFIELD = "content";
	public static final String FRESHSTORYRECOMMENDTION_INDEX = "freshstoryrecommendation";
	public static final String FRESHSTORYRECOMMENDATION_TYPE = "storytrend";
	public static final String TOP_ALL_CAT = "top_all_cat";
	public static final String ENGLISHSTORYTITLE = "englishtitle";
	public static final String ENGLISH_STORY_KEYWORD_INDEX = "englishindex";
	public static final String PERC_GLOBALTREND_RECO_INDEX = "percglobtrend";
	public static final String PLUG_CAT_FIELD = "plugh";
	
	public static int [] NONHINDIHOST = new int [] { 16,17,5,6,7,8,18,56,57,19,20,21,22,23,49,10,24,25,31,32,29,30,12,45,46,34,35,37,38,41,42,43,44,45,47,9,26};
	
    public static String CLUSTER_NAME = "cluster.name";
	public static String CONFIG_CONNECT_STR = "index.elasticsearch.connect";
	public static String CONFIG_CLUSTER_NAME_STR = "index.elasticsearch.cluster.name";
	public static String CONFIG_CATID_INDEX_MAPPING_PATH = "catid.index.mapping.filepath";
	
	public static String HADOOPNAMENODE_CONNECT_STR = "hadoop.namenode.name";

	
	public static long INVOCATION_TIME = 60*60*1000*1;
	public static long DOUBLE_INVOCATION_TIME = 60*60*1000*2;
	
	public static String INDICES_NAME = "com.dainik.bhaskar.indicesname";
	public static String TYPE_NAME = "com.dainik.bhaskar.typename";
	public static String storykey = "storyid";
	public static String SESSIONID = "session_id";
	public static String USERSTORY_INDICE = "user_story_cache";
	public static String USERSTORY_TYPE  = "uncommonreco";
	
	
	public static String ANOMALOUS_RECO_INDICE = "uncommonstoryinsert";//"uncommonstoryinsert";
	public static String ANOMALOUS_RECO_TYPE  = "storypath";
	
	public static String HADOOP_REALTIME_PATH = "/realtime/";
	public static String HADOOP_USER_STITCH_PATH = "/storystitch/";
	public static String HADOOP_ES_INSERT_PATH = "/ESNullInput/";
	public static int [] hostList = new int [] { 1, 2, 3, 15 };
	
	/*
	 * For Dainik Bhasker Host
	 */
	public static int [] bhaskerhostList = new int [] { 1, 2, 3, 15 };
		/*
	 * For Divya Bhasker Host
	 */
	public static int [] divyahostList = new int [] { 5, 6, 7, 20 };
	/*
	 * For Dainik Money Host
	 */
	public static int [] bhaskermoneyhostList = new int [] { 4, 27 };
	/*
	 * For Divya Money Host
	 */
	public static int [] divyamoneyhostList = new int [] { 8 };
	/*
	 * For App Host
	 */
	public static int [] apphostList = new int [] { 16 };
	
	
	/**
	 *  Map for Channel.... 
	 */
	
	public static Map<String,String> partFileForES = new HashMap<String, String>()
	{{
		put("dainikBhaskar-r-00000", "storypath");
		put("divyaBhaskar-r-00000", "divyastorypath");
		put("bhaskarMoney-r-00000", "moneystorypath");
	//	put("divyaMoney-r-00000", "moneydivyastorypath");
		put("app-r-00000", "appstorypath");
	}};

	public static Map<String,String> EStopartfile = new HashMap<String, String>()
	{{
		put(ANOMALOUS_RECO_INDICE+"/storypath","dainikBhaskar-r-00000");
		put(ANOMALOUS_RECO_INDICE+"/divyastorypath","divyaBhaskar-r-00000");
		put(ANOMALOUS_RECO_INDICE+"/moneystorypath","bhaskarMoney-r-00000");
		//	put("ANOMALOUS_RECO_INDICE/moneydivyastorypath","divyaMoney-r-00000");
		put(ANOMALOUS_RECO_INDICE+"/appstorypath","app-r-00000");

	}};


	@SuppressWarnings("serial")
	public static Map<String, Integer> SpecialStories = new HashMap<String, Integer>() {{
	    put("IPL",24);
	    put("FIFA",48);
	}};

	public static String URECO = "ureco";
	// Redis Settings
	
	public static String CONFIG_REDIS_CONNECT_STR = "com.redis.connect";
	/*public static Map<Long, String> Cat2IndexMapping = new HashMap<Long, String>() {{
		put((long) 5176,"top_khel_cat");
		put((long) 5179,"top_khel_cat");
		put((long) 5181,"top_khel_cat");
		put((long) 5196,"top_khel_cat");
		put((long) 5723,"top_khel_cat");
		put((long) 5890,"top_khel_cat");
		put((long) 5891,"top_khel_cat");
		put((long) 5892,"top_khel_cat");
		put((long) 1053,"top_khel_cat");
		put((long) 4270,"top_khel_cat");
		put((long) 5175,"top_khel_cat");
		put((long) 5920,"top_khel_cat");
		put((long) 5925,"top_khel_cat");
		put((long) 9884,"top_khel_cat");
		put((long) 9919,"top_khel_cat");
		put((long) 10151,"top_khel_cat");
		put((long) 10690,"top_khel_cat");
		put((long) 10694,"top_khel_cat");
	}};*/
	
	
	public static String BHASKARCODE="B";
	public static String DIVYACODE="D";
	public static String MONEYBHASKARCODE="MB";
	public static String MONEYDIVYACODE="MD";
	public static String APPCODE="APP";
	
	
	
	
	/* For Divya Reco */
	

    public static String DIVYASTITCHPATH = "/divyastitch/";
    public static String HADOOP_DIVYA_ES_INSERT_PATH = "/DivyaESNullInput/";
    public static Map<Integer, String> SelectedHost = new HashMap<Integer,String>() {{
          put(1,"BHASKAR");
          put(2,"BHASKAR");
          put(3,"BHASKAR");
          put(15,"BHASKAR");
          put(5,"DIVYA");
          put(6,"DIVYA");
          put(7,"DIVYA");
          put(20,"DIVYA");
      }};
    public static final String DIVYAUNCOMMONVERSION = "divyauncommon";
    public static int [] BhaskarDivyaHostList = new int [] { 1, 2, 3, 15,5, 6, 7, 20 };
}
