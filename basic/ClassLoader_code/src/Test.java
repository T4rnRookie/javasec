
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.*;
public class Test {
    public static void main(String[] args) {
        System.out.printf("123");
        run("whoami");
    }

    public String hello() {
        return "Hello World";
    }

    public int test(int a) {
        return a;
    }

    public static  void run(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream in = process.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int a= 0;
            byte b[] = new byte[1024];
            while((a = in.read(b))!=-1){
                baos.write(b,0,a);
            }
            System.out.println(baos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}