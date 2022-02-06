import java.lang.reflect.Method;
/**
 * Creator: yz
 * Date: 2019/12/17
 */
public class LoaderExecute extends ClassLoader {

    // TestHelloWorld类名
    public static String testClassName = "Test";

    // TestHelloWorld类字节码
    public static byte[] testClassBytes = new byte[]{
            -54, -2, -70, -66, 0, 0, 0, 52, 0, 33, 10, 0, 4, 0, 18, 9, 0, 19, 0, 20, 8, 0, 21, 7, 0, 22, 10, 0, 23, 0, 24, 8, 0, 25, 7, 0, 26, 1, 0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 3, 40, 41, 86, 1, 0, 4, 67, 111, 100, 101, 1, 0, 15, 76, 105, 110, 101, 78, 117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 4, 109, 97, 105, 110, 1, 0, 22, 40, 91, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 86, 1, 0, 5, 104, 101, 108, 108, 111, 1, 0, 20, 40, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70, 105, 108, 101, 1, 0, 9, 84, 101, 115, 116, 46, 106, 97, 118, 97, 12, 0, 8, 0, 9, 7, 0, 27, 12, 0, 28, 0, 29, 1, 0, 3, 49, 50, 51, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 7, 0, 30, 12, 0, 31, 0, 32, 1, 0, 11, 72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100, 1, 0, 4, 84, 101, 115, 116, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 121, 115, 116, 101, 109, 1, 0, 3, 111, 117, 116, 1, 0, 21, 76, 106, 97, 118, 97, 47, 105, 111, 47, 80, 114, 105, 110, 116, 83, 116, 114, 101, 97, 109, 59, 1, 0, 19, 106, 97, 118, 97, 47, 105, 111, 47, 80, 114, 105, 110, 116, 83, 116, 114, 101, 97, 109, 1, 0, 6, 112, 114, 105, 110, 116, 102, 1, 0, 60, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 91, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 41, 76, 106, 97, 118, 97, 47, 105, 111, 47, 80, 114, 105, 110, 116, 83, 116, 114, 101, 97, 109, 59, 0, 33, 0, 7, 0, 4, 0, 0, 0, 0, 0, 3, 0, 1, 0, 8, 0, 9, 0, 1, 0, 10, 0, 0, 0, 29, 0, 1, 0, 1, 0, 0, 0, 5, 42, -73, 0, 1, -79, 0, 0, 0, 1, 0, 11, 0, 0, 0, 6, 0, 1, 0, 0, 0, 3, 0, 9, 0, 12, 0, 13, 0, 1, 0, 10, 0, 0, 0, 42, 0, 3, 0, 1, 0, 0, 0, 14, -78, 0, 2, 18, 3, 3, -67, 0, 4, -74, 0, 5, 87, -79, 0, 0, 0, 1, 0, 11, 0, 0, 0, 10, 0, 2, 0, 0, 0, 5, 0, 13, 0, 6, 0, 1, 0, 14, 0, 15, 0, 1, 0, 10, 0, 0, 0, 27, 0, 1, 0, 1, 0, 0, 0, 3, 18, 6, -80, 0, 0, 0, 1, 0, 11, 0, 0, 0, 6, 0, 1, 0, 0, 0, 9, 0, 1, 0, 16, 0, 0, 0, 2, 0, 17

};

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        // 只处理TestHelloWorld类
        if (name.equals(testClassName)) {
            // 调用JVM的native方法定义TestHelloWorld类
            return defineClass(testClassName, testClassBytes, 0, testClassBytes.length);
        }

        return super.findClass(name);
    }

    public static void main(String[] args) {
        // 创建自定义的类加载器
        LoaderExecute loader = new LoaderExecute();

        try {
            // 使用自定义的类加载器加载TestHelloWorld类
            Class testClass = loader.loadClass(testClassName);

            // 反射创建TestHelloWorld类，等价于 TestHelloWorld t = new TestHelloWorld();
            Object testInstance = testClass.newInstance();

            // 反射获取hello方法
            Method method = testInstance.getClass().getDeclaredMethod("test",int.class);

            // 反射调用hello方法,等价于 String str = t.hello();
            int a = (int) method.invoke(testInstance,123);

            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
