package denys.koretskyi.txt2gpx.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileUtil {

    public static final String WINDOWS_1251 = "windows-1251";

    @SneakyThrows
    public static byte[] convertBodyBytesToCharset(byte[] bytes, String toCharset) {
        return new String(bytes, toCharset).getBytes();
    }

    public static String updateFileName(String oldFileName) {
        return oldFileName.replace(".txt", "_converted.txt");
    }
}
