import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

/**
 * 字符串、文件快速转换成byte数组
 * Creator: yz
 * Date: 2019/12/8
 */
public class Bytes {

    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            String str = args[0];

            if (args.length == 2) {
                if (str.equals("-f")) {
                    File file = new File(args[1]);
                    System.out.println(Arrays.toString(Files.readAllBytes(file.toPath())));
                } else if (str.equals("-b")) {
                    String[]              strs = args[1].split(",");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    for (String b : strs) {
                        baos.write((byte) Integer.parseInt(b.trim()));
                    }

                    System.out.println(new String(baos.toByteArray()));
                }
            } else {
                System.out.println(Arrays.toString(str.getBytes()));
            }

        } else {
            System.out.println("Examples:");
            System.out.println("java Bytes ABCDEFG");
            System.out.println("java Bytes -f /data/1.txt");
            System.out.println("java Bytes -b \"97, 98, 199\"");
        }
    }

}
