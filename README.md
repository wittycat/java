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

 ![](/document/io/io_byte_inputStream.png "io字节输入流")

#####io字节输出流

 ![](/document/io/io_byte_outputStream.png "io字节输出流")


#####io字符输入流

 ![](/document/io/io_reader.png "io字符输出流")

#####io字符输出流

 ![](/document/io/io_writer.png "io字符输出流")

##1.3java.lang
    java的base包，唯一一个只使用不用导包的包。
	主要类的类型有：基本类型的包装类型，异常类，进程类，Thread的相关类，字符（串）的系列类。
- Object：主要提供hashcode，equals ，getClass，以及线程之间通信的方式：等待和唤醒。
- System 
  - 主要是native方法
  - 集成一些其他类像IO流，
  - 集成RunTime的方法
  - nanoTime（）：获取微妙值，主要用于线程阻塞时间的计算。
  - arraycopy（）：高效数组的拷贝
- RunTime：exit，gc，exec，获取os运行核数。
- Class：对象字节码对象，反射的重点

##1.4java.util除过集合的其他类
- Formatter：公式 %[argument_index$][flags][width][.precision]conversion

#2.深刻理解（高级要求）

##2.1java.lang.reflect
###2.1.1反射
![](/document/reflect/reflect_class.png "反射类的主要关系")

- getClass()；对象.Class；Class.forName() 获取对象字节码对象
- Class 对象可以获取字段数组，方法数组，构造函数（通过方法可以创建实例），所涉及的权限修饰符，参数，参数类型，方法的返回值类等一切基本都可以获取到。

###2.1.2动态代理
![](/document/reflect/jdk_Proxy.png "动态代理执行逻辑")

- 代理模式的应用
- 创建过程类似于“高级装饰者模式”+“高级继承（非继承实现类，继承的是接口）”
- 生成代理类过程：生成代理对象的字节码数组->生成字节码对象->通过构造函数创建实例
- 代理对象和被代理对象的区别：代理对象由于是Proxy的子类，所以持有起桥梁作用的InvocationHandler子类（简称h），h又持有被代理对，通过代理对象给h传入Method对象和参数在加上h持有的被代理对象就可以对代理对象通过Method的invoke执行。其实就是代理对象持有自己实现的InvocationHandler对象，而被代理对象是没有的。（**Method的invoke传入的对象一定不能是h的invoke方法传入的proxy对象，proxy对象是代理对象传入的自己本身，如果传入Proxy，将会发生代理对象和h对象之间的递归调用，直到栈溢出，正确应该传入h持有的被代理对象。h的invoke方法传入的proxy对象也没有什么实际作用**）
- 代理对象里面的基本方法有equals，toString，hashCode和接口的方法。所有方法里面没有额外的逻辑，都是通过h.invoke(this, method, 参数)去调用真是对象的。通过 ProxyGenerator.generateProxyClass("$Proxy11", 接口字节码数组);  可以写入生成代理对象到文件进行查看。
- h对象里面的invoke是对被代理对象的所有方法的代理，想要定制化代理可以在里面通过传入的Method对象获取方法名进行判断处理。

##2.2java.util.concurrent.*

	并发包主要内容：原子类，锁，容器，线程池，框架，工具类

![](/document/juc/JUC_main.png "并发主要内容")

###2.2.1原子类

###2.2.2锁
	主要类关系：
![](/document/juc/JUC_Lock_Main.png "Lock包主要类关系")

####2.2.2.1AQS
- 锁是面向用户的，同步器是面向锁的（就是锁的内部实现），AQS支持互斥锁，共享锁的实现。
- AQS实现一个FIFO等待队列。
- 通过对state的原子修改来实现获取锁和释放锁；
- 互斥锁：state为1时可以实现互斥锁（TCustomSyncExclusiveLock），大于1可以实现重入锁；
- 共享锁：state大于1时可以实现共享锁（TCustomSyncShareLock），此时state的值即表示并发的线程数，此时不便实现重入锁。
	
#####2.2.2.1.1关键词理解
	模板方法，互斥锁(排它锁)，共享锁，同步队列，阻塞队列（条件队列），LockSupport，ConditionObject
- AQS：提供一组模板方法用于具体业务实现互斥锁或者共享锁
- 同步队列：保存等待获取锁的线程节点
- 阻塞队列：保存执行了await的线程节点
- LockSupport：用于阻塞或者唤醒线程，属于工具类，不和锁关联（Condition和synchronized阻止线程必须和锁关联）
- ConditionObject：AQS的监视器

（**注意：Object的监视器只有一个同步队列和一个阻塞队列；而AQS却有一个同步队列和多个阻塞队列，对应多个ConditionObject**）

#####2.2.2.1.2同步队列和阻塞队列的节点互相转化（结合ReentrantLock说明）
- TReentrantLock3运行示例
- 同步队列：双向链表；阻塞队列：单向链表
- 基本流程：首先所有节点会被加入同步队列，在执行await后会加入阻塞队列，唤醒时又会被转化回同步队列
- 2个队列独立存在，都使用Node作为基本节点

（**注意使线程节点进入阻塞队列的不适合AQS实现的共享锁操作，因为newCondition.await()其中会调用tryRelease(long arg),而共享锁不会实现这个**）

#####2.2.2.1.3lock ,unLock,await,signal(signalAll:相当于循环操作signal)的逻辑分析（结合ReentrantLock说明）
- lock:获取不到锁阻塞该线程，加入同步队列
- unLock:释放锁唤醒后继节点
- await:主要干三件事:1.阻塞该线程 2.添加到等待队列3. 唤醒后继线程
- signal:主要干1件事:加入同步队列

####2.2.2.2ReentrantLock
- 主要理解公平锁和非公平锁的在获取锁时的不同之处（**二者都使用同步队列，非公平锁再获取时存在插队现象，这样对于队列其他的节点线程就是不公平的**）
- ReentrantLock,属于互斥锁，重入锁（**释放必须和获取执行次数一样**）
- ReentrantLock的方法在调用时 如果抛出 IllegalMonitorStateException - 则该方法必须在锁的区域内调用
- 不管是公平锁或者非公平锁都使用的AQS的同步队列
- 性能问题：（TReentrantLock2测试）
  - 前提:把不同线程获取锁的一次定义为一次上下文切换
  - 获取同等锁次数情况下，非公平锁相对用时更少，原因是减少了cpu的上下文切换
  - 公平锁执行较多上下文切换次数， 而非公平锁执行上下文切换次数较少（原因是当一个线程获取释放锁后，下一次如果它再需要锁，相对比其他线程获取锁的概率更大，此时就不需要切换就相对省时）
	

![](/document/juc/JUC_ReentrantLock.png "ReentrantLock内部关系")

####2.2.2.3ReentrantReadWriteLock
- 出现读写锁的缘由：当多读少写时，使用读写锁比使用互斥锁具有更高的并发性
- 特点：（**读锁可以并发访问，写锁时其他的读锁和其他的写锁都被阻塞**）
- 主要依据32位的int类型的state的低16位的值表示写锁（为互斥锁）；高16位的值表示读锁（为共享锁）
- 通过位运算把state值拆分为2个值：假设当前状态为s
- 写状态：s & (1 << 16) - 1   即：s&65535，也就是s与16个1（2进制的），这样就把高16位抹去了；当写状态+1时，即为s+1，只给低位加。
- 读状态：s>>>16   ,表示无符号补0右移16位；当读状态+1时，等于s+（1<<16）,结果就是只给高位加
- （**锁降级**）流程：先获取写锁，在获取读锁，在释放写锁，在释放读锁（不支持锁升级）
![](/document/juc/JUC_ReentrantReadWriteLock.png "ReentrantReadWriteLock内部关系")

###2.2.3容器
* 结构图
* 阻塞队列主要方法说明：
  * [add:remove]没有值或队列满时，操作报异常；属于Queue接口的规范
  * [offer:poll]返回特殊值（null或波尔值），队列满时添加失败，造成丢失元素；属于Queue接口的规范
  * [put:take]空或满时操作阻塞，但是不会丢失元素；属于BlockingQueue接口的规范

###2.2.3.1队列
- ArrayBlockingQueue：数组实现的有节阻塞队列，此队列按 FIFO（先进先出）原则。
    <br/>自定义CArrayBlockingQueue注意：
	- putIndex和takeIndex，当到达数组末端时，必须从0开始 。
	- while和if的选择？
	 - 必须使用while，因为在signal（）take1线程时，只会从阻塞队列转移到同步队列（不见得会获取锁真正唤醒）， 此时可能已经有其他take2线程获取到了锁，把队列中仅有的元素移除了，此时take2线程执行完后是释放锁，唤醒后 继节点也就是take1线程。此时take1线程应该再次判断，条件不满足继续阻塞。
	 - 能否通过公平锁或不公平锁避免这个问题？ 不能，因为上面的take2线程可以认为是不公平锁的插队线程。 加入是公平锁呢？上面 take1线程顺利获取到了锁，取走了唯一的元素。而新线程在获取不到锁时，加入同步队列。当线程 take1 线程执行完后释放锁，take2线程被唤醒，也必须再次检测while条件。否则将返回null元素，不符合阻塞队列。 
	- signal和signalAll:使用这俩个都可以，只不过只会有一个线程获取锁得到元素，所以使用signal比signalAll更恰当 
- LinkedBlockingQueue：一个单向链表实现的有界（可指定大小，默认Integer.MAX_VALUE）阻塞队列，此队列按 FIFO（先进先出）原则。链接队列的吞吐量通常要高于基于数组的队列，但是在大多数并发应用程序中，其可预知的性能要低。 
- PriorityBlockingQueue：一个无界阻塞队列，虽然此队列逻辑上是无界的，但是资源被耗尽时试图执行 add 操作也将失败，导致 OutOfMemoryError。`不支持先进先出原则` 
 - 由于队列是无界的，所以put方法不会由于满了（始终不会满）而导致阻塞 。
 - 不保证具有同等优先级的元素的顺序
 - 入队（通过比较找到合适位置入队）相同（值相等）元素不会覆盖，出对从队列头出队


###2.2.4线程池

###2.2.5框架

###2.2.6工具类

##2.3java.net
##2.4javax.net.*
##2.5java.nio.*
#3.其他包会使用即可
