## ClassLoader(类加载机制)

Java类必须经过JVM加载 加载器是分层的  不指定类加载器默认用App ClassLoader加载

获取类加载器可能return null 比如java.io.file 初始化使用Boostrap ClassLoader加载 所有被Bootstrap ClassLoader加载的类都会返回null



### ClassLoader核心方法

![image-20220126214044008](/Users/t4rn/Library/Application Support/typora-user-images/image-20220126214044008.png)

学习关键点 关注前四个核心方法

Tricks:IDea下使用alt+7的快捷键查看当前文件下所有方法

### Java类动态加载方式

显式 隐式

显式是java反射  隐式是类名.方法名()或 new 一个



### 类加载流程

![image-20220126215012857](/Users/t4rn/Library/Application Support/typora-user-images/image-20220126215012857.png)

### 自定义ClassLoader

重写findClass的逻辑 利用反射方法执行任意方法

代码逻辑 if判断是否为特定的类，return defineclass回去 否则直接回父类

自定义类加载器loadclass指定类

反射创建类

反射获取方法然后调用

getMethod 和getDeclaredMethod区别

##### getMethod()返回某个类的所有公用（public）方法包括其继承类的公用方法，当然也包括它所实现接口的方法

##### getDeclaredMethod()对象表示的类或接口声明的所有方法,包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。当然也包括它所实现接口的方法

思考:Integer 和int区别

Integer是int的包装类 int是基本类型

### URLClassLoder

可以加载远程的类 在远程留后门然后执行

知识点:用Process类下的runtime执行命令

![image-20220127204251458](/Users/t4rn/Library/Application Support/typora-user-images/image-20220127204251458.png)

### 类加载隔离

双亲委托 创建类加载器可以指定该类加载的父类加载器 两个ClassLoader可以加载相同的class(必须非继承)

![image-20220127204800914](/Users/t4rn/Library/Application Support/typora-user-images/image-20220127204800914.png)

### JSP自定义类加载后门

文件落地的趋势基本凉凉

冰蝎木马类加载木马

![image-20220127212543230](/Users/t4rn/Library/Application Support/typora-user-images/image-20220127212543230.png)

### BCEL ClassLoader

![image-20220127213027256](/Users/t4rn/Library/Application Support/typora-user-images/image-20220127213027256.png)

json反序列化注入类名和类加载器不会类加载 

fastjson自动调用getter方法 最终调用getConnection方法

![image-20220127214522060](/Users/t4rn/Library/Application Support/typora-user-images/image-20220127214522060.png)

在这里通过Class.forName来反射加载

### Xalan ClassLoader

类的字节码 fastjson可以通过json传入

摘自园长大大原文

Fastjson会创建`com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl`类实例，并通过json中的字段去映射`TemplatesImpl`类中的成员变量，比如将`_bytecodes` 经过Base64解码后将byte[]映射到`TemplatesImpl`类的`_bytecodes`上，其他字段同理。但仅仅是属性映射是无法触发传入的类实例化的，还必须要调用`getOutputProperties()`方法才会触发`defineClass`（使用传入的恶意类字节码创建类）和`newInstance`（创建恶意类实例，从而触发恶意类构造方法中的命令执行代码），此时已是万事俱备，只欠东风了。

Fastjson在解析类成员变量（`com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer#parseField`）的时候会将`private Properties _outputProperties;`属性与`getOutputProperties()`关联映射（FastJson的`smartMatch()`会忽略`_`、`-`、`is`（仅限boolean/Boolean类型），所以能够匹配到`getOutputProperties()`方法），因为`_outputProperties`是Map类型（Properties是Map的子类）所以不需要通过set方法映射值（`fieldInfo.getOnly`），因此在setValue的时候会直接调用`getOutputProperties()`

### JSP加载

JSP和PHP一样能动态修改，JSP能热更新借助的是自定义类加载行为

Tips:热更新不需要关闭服务器 直接重新部署即可 冷的需要关闭服务器