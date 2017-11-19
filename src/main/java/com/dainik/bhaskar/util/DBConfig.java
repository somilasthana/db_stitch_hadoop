package com.dainik.bhaskar.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dainik.bhaskar.DBAnalyticsException;


/**
 * This class represents the Configuration Manager common to the project.
 * It is a singleton class causing only one instance of the configuration
 * to be created per VM. It further creates objects of the different 
 * configurations used.
 *
 */
public class DBConfig{	
	
	private static Logger log = Logger.getLogger(DBConfig.class);
	private SlaveConfig system_cfg = null;
	
	public static final long REFRESH_INTERVAL = 6000;
	
	 
	private DBConfig(){
		this.system_cfg = new SysConfig();
	}
	
	private static DBConfig instance = null;	
	public static DBConfig getInstance(){
		if(instance==null){
			synchronized (DBConfig.class) {
				DBConfig.instance = new DBConfig();
			}
		}
		return instance;
	}
	
	/**
	 * This interface defines the structure for the various types of configuration
	 * classes.
	 * 
	 *
	 */
	
	private interface SlaveConfig{
		public String getProperty(String key);
	}
	
	/**
	 * This class represents System Configurations and implements the
	 * getProperty function. This class will read the configurations from the
	 * file whose path is given as value to bdgConfigPath System property which\
	 * has been given as VM arguments or given as environment variable BDG_CONFIG_PATH. 
	 * 
	 *
	 */
	
	private class SysConfig implements SlaveConfig{
		private Properties confOverrides = null;		
		public SysConfig(){			
			try {
				File f = new File("/opt/config.properties");
		
					if(f.exists()&&f.canRead()){
						confOverrides = new Properties();
						confOverrides.load(new FileInputStream(f));
					}else{
						throw new DBAnalyticsException(new FileNotFoundException(f.getAbsolutePath())); //$NON-NLS-1$
					}				
			} catch (Exception e) {
				throw new DBAnalyticsException(e); 
			}						
		}
		
	
		public String getProperty(String key){
			if(this.confOverrides!=null && this.confOverrides.containsKey(key)){
				return this.confOverrides.getProperty(key);							
			}else{
//				return DBConfig.getConf(key);
				return null;
			}
		}
	}
	
	/**
	 * This function gets the String value of the key.
	 * @param key
	 * @return string value
	 */
	public String getString(String key){	
		return this.getProperty(key);
	}
	
	/**
	 * This function gets the integer value of the key.
	 * @param key
	 * @return integer value
	 */
	public Integer getInteger(String key){
		String s = this.getProperty(key);
		if(s!=null){			
			try {
				return new Integer(s);
			} catch (NumberFormatException e) {
				log.info("" + e);
			}
		}
		return null;
	}
	

	/**
	 * This function returns the configuration value according to the key.
	 * @param key
	 * @return
	 */
	public String getProperty(String key){		
			return this.system_cfg.getProperty(key);
	}

//	public static final String BDGCONFIG_NAME = "in.co.impetus.ilabs.bdg.utils.bdgconfig";
//	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BDGCONFIG_NAME);
//	private static String getConf(String key) {
//		try {
//			return RESOURCE_BUNDLE.getString(key);
//		} catch (MissingResourceException e) {
//			return '!' + key + '!';
//		}
//	}
	
	/**
	 * The Constants that are being used in the bdg-core project
	 */
//	
//	public static final String CONF_OVERRIDES_FILEPATH_ARG = "BDG_CONFIG_PATH";
//	public static final String CONF_OVERRIDES_FILEPATH_ENVVAR = "CONFIG_PATH"; 	 
//	public static final String BLOCK_ID = "blockId";
	
	public static void main(String[] args){
		System.out.println(DBConfig.getInstance().getProperty("index.retries"));
	
	}
}
