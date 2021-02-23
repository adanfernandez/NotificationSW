package conf;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private Properties props;
	private static Config instance = null;
	private static final String FILE_CONF = "\\conf.properties";

	public Config() {
		this.props = new Properties();
		try {
			props.load(Config.class.getClassLoader().getResourceAsStream(FILE_CONF));
		} catch (IOException e) {
			throw new RuntimeException("File properties cannot be loaded",e);
		}
		
	}
	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}
	
	public String get(String key) {
		String value = props.getProperty(key);
		if (value == null) {
			throw new RuntimeException("Property not found in config file");
		}
		return value;
	}

}
