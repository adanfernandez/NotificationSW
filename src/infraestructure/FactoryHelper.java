package infraestructure;

import java.lang.reflect.InvocationTargetException;

import conf.Config;
import persistence.DataServiceFactory;

public class FactoryHelper {

	private static Config config = Config.getInstance();

//	public static BusinessFactory services = (BusinessFactory) createFactory(
//			config.get("SERVICES_FACTORY"));
	
	public static DataServiceFactory dataServices = (DataServiceFactory) createFactory(
			config.get("PERSISTENCE_FACTORY"));
	

	public static Object createFactory(String factoryName) {
		try {

			if (factoryName == null) {
				throw new RuntimeException("Property not found in config file");
			}
			Class<?> clazz = Class.forName(factoryName);
			return clazz.getDeclaredConstructor().newInstance();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

}
