

import java.lang.reflect.Method;


public class TestCrossClassLoader {

    public static class ClassLoaderA extends ClassLoader {

        public ClassLoaderA(ClassLoader parent) {
            super(parent);
        }

        {
            // 加载类字节码
            defineClass(LoaderExecute.testClassName, LoaderExecute.testClassBytes, 0, LoaderExecute.testClassBytes.length);
        }

    }

    public static class ClassLoaderB extends ClassLoader {

        public ClassLoaderB(ClassLoader parent) {
            super(parent);
        }

        {
            // 加载类字节码
            defineClass(LoaderExecute.testClassName, LoaderExecute.testClassBytes, 0, LoaderExecute.testClassBytes.length);
        }

    }

    public static void main(String[] args) throws Exception {
        // 父类加载器
        ClassLoader parentClassLoader = ClassLoader.getSystemClassLoader();

        // A类加载器
        ClassLoaderA aClassLoader = new ClassLoaderA(parentClassLoader);

        // B类加载器
        ClassLoaderB bClassLoader = new ClassLoaderB(parentClassLoader);

        // 使用A/B类加载器加载同一个类
        Class<?> aClass  = Class.forName(LoaderExecute.testClassName, true, aClassLoader);
        Class<?> aaClass = Class.forName(LoaderExecute.testClassName, true, aClassLoader);
        Class<?> bClass  = Class.forName(LoaderExecute.testClassName, true, bClassLoader);

        // 比较A类加载和B类加载器加载的类是否相等
        System.out.println("aClass == aaClass：" + (aClass == aaClass));
        System.out.println("aClass == bClass：" + (aClass == bClass));

        System.out.println("\n" + aClass.getName() + "方法清单：");

        // 获取该类所有方法
        Method[] methods = aClass.getDeclaredMethods();

        for (Method method : methods) {
            System.out.println(method);
        }

        // 创建类实例
        Object instanceA = aClass.newInstance();

        // 获取hello方法
        Method helloMethod = aClass.getMethod("hello");

        // 调用hello方法
        String result = (String) helloMethod.invoke(instanceA);

        System.out.println("\n反射调用：" + LoaderExecute.testClassName + "类" + helloMethod.getName() + "方法，返回结果：" + result);
    }

}