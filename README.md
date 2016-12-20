###java源码等级要求，(基于java version 1.8.0_60)
#1.精读源码（基本要求）
##1.1java.util

       这个包主要以集合为主，这是数据结构的最佳实践。

#####集合罗列

|集合|  查询时间复杂度  |继承前列 |线程安全|支持排序|
| :------------ |:--|:---|:--|:--|
|Map |HashMap|LinkedHashMap |HashTable |TreeMap|
|Set |HashSet|LinkedHashSet ||TreeSet|
|~ |无| 无|**线程安全**|**继承前列线程安全**|
|List |ArrayList| LinkedList|Vector|Stack|

#####相似比较

|Name|  同作用  |不同作用|
| :------------ |:--|:--|
|Iterator|支持遍历 |支持fail-fast[ConcurrentModificationException]机制，通过迭代器可以对集合删除;可以对集合进行增强for循环遍历【**Iterable和Iterator一起使用，Iterable像管理者，Iterator负责执行**】|
|Enumeration| 支持遍历| |
|Map|支持key-value | 功能强（官方建议使用map）|
|Dictionary| 支持key-value|和map比较功能弱 |
|Comparable|支持比较 | 实体直接实现|
|Comparator| 支持比较|更灵活，实体可以实现多个|

###1.1.1Map相关
- 这是集合中个人认为是最关键最复杂的类，搞清楚map的常用实现类是非常关键的对于认识集合总体的实现。
- 每个key，value键值对封装为Map.Entry<K, V> 实体bean。

####1.1.1.1HashMap
- 数据结构：Node实体数组（又叫Hash表或散列表，hash函数又叫散列函数）+ 单向链表+红黑树（又叫自平衡二叉查找树）。
- Node实体基本结构：hash,key,value,next(链表的下一个实体);Map.Entry<K, V> 实现类。
- TreeNode实体基本结构：parent,left,right,prev;TreeNode继承LinkedHashMap.Entry继承HashMap.Node<K,V>。
- 数组上的元素为链表（或红黑树）的头节点（或根节点）。
- 对于hash冲突解决办法使用链表（或红黑树），当链表长度大于等于8时转化为红黑树。之所以转化为红黑树是当数量大时红黑树的查询效率更高。
- 操作红黑树，增加删除后为了自平衡 进行的左右旋和重新着色。

#####几种时间复杂度

|结构|  查询时间复杂度  |说明 |
| :------------ |:---------------:| -----:|
|hash表 |O（1）|       |
|链表 |  O（n）|遍历链表去查找 |
|红黑树 | O（log n）|  |

#####多线程下当共享变量使用时，扩容链表造成的问题


|版本|  元素是否倒置  |元素是否丢失 |复杂程度|
| :------------ |:---------------|:-----|:-----|
|1.7  | 倒置(易形成闭合回路) | 没验证|代码量约1000行|
|1.8  | 不倒置(99%的认为不会形成，1%再验证) |会丢失|代码量约2000行|
|解决办法| 不当共享变量使用（在局部变量中 new创建 是没问题的，在栈中操作，属于私有的）或使用Collections.synchronizedMap(Map<K, V>)包装或使用ConcurrentHashMap |||
#####1.1.1.2HashTable
- 数据结构：数组+单向链表。
- Entry实体基本结构：hash,key,value,next与HsahMap的Node实体结构一致;Map.Entry<K, V> 实现类。
- 扩容时元素顺序不变。
- 由于HashTable是同步的，多数方法添加了synchronized关键字同步。

#####1.1.1.3TreeMap
- 数据结构：红黑树（天然排序）。
- Entry结构：key，value， left，right，parent，color
- 由于这是排序的hash表，必须在构造函数传递比较器Comparator实现类或添加的实体实现Comparable接口（Comparator和Comparable区别是比较器更灵活，可以更具业务给实体实现不同维度的比较器）。
- 实现结果基本和HashMap的红黑树实现部分一致

#####1.1.1.4LinkedHashMap
- 数据结构：双向链表（**输入的顺序和添加顺序一致**）
- Entry结构，继承自HashMap.Node ,新添加before, after属性
- 继承自HashMap
- 新添加的元素放置到链表的尾部，遍历时从头节点开始，保证了输入的顺序和添加顺序一致

###         
    几种map总结：
        1、在存储上基本与value没有关系，主要依赖K和K的hash值来放置到合适位置。
        2、内部实现上基本以循环操作为主。

###1.1.2Set相关
#####1.1.2.1HashSet
- 实现依赖于HashMap

#####1.1.2.3LinkedHashSet
- 实现依赖于LinkedHashMap（通过构造函数调用HashSet的LinkedHashMap构造函数实现）

#####1.1.2.2TreeSet
- 支持排序，间接实现SortedSet接口
- 实现依赖于NavigableMap

###1.1.3List相关

#####1.1.2.1ArrayList
- 数据结构：数组
- 扩容依赖Arrays.copyOf()，它又依赖于 System.arraycopy（）
- 通过索引访问遍历效率最高，而使用迭代器的效率最低（还可以通过增强for循环遍历）

#####1.1.2.2LinkedList
- 数据结构：双向链表
- Node实体：item,next, prev(当前，下一个，上一个)
- LinkedList可以作为FIFO(先进先出)的队列
- LinkedList可以作为LIFO(后进先出)的栈

#####1.1.2.3Vector
- 数据结构：数组
- 支持同步

#####1.1.2.3Stack
- 数据结构：数组
- 继承自Vector，支持同步，先进后出

###1.1.4工具类
- Arrays：提供一系列静态方法，对数组排序，搜索等
- Collections ：提供一系列静态方法，对集合 排序，搜索，包装集合为安全类型等。包装基本实现思路为，传入之后赋值给局部变量，通过synchronized关键字同步对于传入集合的原有方法加以限制。

##1.2java.io
    理解类提供的功能,才能更准确的使用。要理解io必须先了解一些基本的概念和区别。

- 装饰模式和适配器模式
- 字符流和字节流
- 流的基本介质和区别

#####io字节输入流（不是所有的输入输出都一一对应）

 ![](/document/io_byte_inputStream.png "io字节输入流")

#####io字节输出流

 ![](/document/io_byte_outputStream.png "io字节输出流")


#####io字符输入流

 ![](/document/io_reader.png "io字符输出流")

#####io字符输出流

 ![](/document/io_writer.png "io字符输出流")

##1.3java.lang
    java的base包，唯一一个只使用不用导包的包。主要类的类型有：基本类型的包装类型，异常类，进程类，Thread的相关类，字符（串）的系列类。
- Object：主要提供hashcode，equals ，getClass，以及线程之间通信的方式：等待和唤醒。
- System ：主要是native和集成一些其他类像IO流，RunTime的方法等。
- RunTime：exit，gc，exec，获取os运行核数。
- Class：对象字节码对象，反射的重点

##1.4java.util除过集合的其他类

- Formatter：公式 %[argument_index$][flags][width][.precision]conversion


#2.深刻理解（高级要求）
####java.lang.reflect

#####反射

![](/document/reflect_class.png "反射类的主要关系")

- getClass()；对象.Class；Class.forName() 获取对象字节码对象
- Class 对象可以获取字段数组，方法数组，构造函数（通过方法可以创建实例），所涉及的权限修饰符，参数，参数类型，方法的返回值类等一切基本都可以获取到。

#####动态代理

![](/document/jdk_Proxy.png "动态代理执行逻辑")

- 代理模式的应用
- 创建过程类似于“高级装饰者模式”+“高级继承（非继承实现类，继承的是接口）”
- 生成代理类过程：生成代理对象的字节码数组->生成字节码对象->通过构造函数创建实例
- 代理对象和被代理对象的区别：代理对象由于是Proxy的子类，所以持有起桥梁作用的InvocationHandler子类（简称h），h又持有被代理对，通过代理对象给h传入Method对象和参数在加上h持有的被代理对象就可以对代理对象通过Method的invoke执行。其实就是代理对象持有自己实现的InvocationHandler对象，而被代理对象是没有的。（**Method的invoke传入的对象一定不能是h的invoke方法传入的proxy对象，proxy对象是代理对象传入的自己本身，如果传入Proxy，将会发生代理对象和h对象之间的递归调用，直到栈溢出，正确应该传入h持有的被代理对象。h的invoke方法传入的proxy对象也没有什么实际作用**）
- 代理对象里面的基本方法有equals，toString，hashCode和接口的方法。所有方法里面没有额外的逻辑，都是通过h.invoke(this, method, 参数)去调用真是对象的。通过 ProxyGenerator.generateProxyClass("$Proxy11", 接口字节码数组);  可以写入生成代理对象到文件进行查看。
- h对象里面的invoke是对被代理对象的所有方法的代理，想要定制化代理可以在里面通过传入的Method对象获取方法名进行判断处理。

####java.net
####javax.net.*
####java.nio.*
####java.util.concurrent.*
#3.其他包会使用即可
