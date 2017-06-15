package cn.bhl.xlsp.utils;
/**
 * 自定义UUID生成器
 * @ClassName: UUID 
 * @author chenj
 * @date: 2017年6月12日 下午1:23:10 
 * @version 1.0
 */
public final class UUID {
	private UUID() {
	}

	private final static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
			"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };

	/**
	 * 8位uuid生成方法
	 * 
	 * @author chenj
	 * @return String
	 * @date 2017年6月12日 下午1:19:37
	 */
	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}

	/**
	 * 32位uuid生成方法
	 * 
	 * @author chenj
	 * @return String
	 * @date 2017年6月12日 下午1:19:01
	 */
	public static String longUuid() {
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}

}