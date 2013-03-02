package utils;

import java.util.HashMap;

public class ConfigManager {
	private static HashMap<String, Object> configs = new HashMap<String, Object>();;
	
	private ConfigManager() {}
	
	public static void addConfig(String key, Object value) {
		configs.put(key, value);
	}
	
	public static Object getConfig(String key) {
		return configs.get(key);
	}
}
