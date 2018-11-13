package pl.pikart.ami.contracts;

import java.util.Map;

public interface Resource {

	public String getType();
	public String getKey(String key);
	public String getName();
	public Map<String, String> getData();
	
}