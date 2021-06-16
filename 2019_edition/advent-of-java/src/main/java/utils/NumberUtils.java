package utils;

public class NumberUtils {

	public static <T extends Number> T parseNumber(String stringToParse, Class<T> parseAs) {
		T result;
		if (parseAs.equals(Byte.class)) {
			result = (T) Byte.valueOf(stringToParse);
			
		} else if (parseAs.equals(Long.class)) {
			result = (T) Long.valueOf(stringToParse);
			
		} else {
			result = (T) Integer.valueOf(stringToParse);
		}
		return result;
	}
	
	public static <T extends Number> T valueOf(T number, Class<T> returnType) {
		T result;
		if (returnType.equals(Byte.class)) {
			result = (T) (Byte) number.byteValue();
			
		} else if (returnType.equals(Long.class)) {
			result = (T) (Long) number.longValue();
			
		} else {
			result = (T) (Integer) number.intValue();
		}
		return result;
	}
}
