线程间协作
==========

多个线程可以协作完成一项任务，比如典型的“生产-消费”问题，生产者线程负责创建一些产品（数据），消费者负责使用这些产品（数据）。

**多线程协作可以保持生产者和消费者互相独立**，各做各的事情，生产者只管生产，不管谁来消费以及如何消费，消费者也不管是谁来生产产品。这使得程序逻辑清晰简洁。

不过，**无法避免的接触就是它们两者之间传递的产品**，消费者必须确认产品已经生产出来才能开始消费，生产者通常也必须确认有足够的仓库容量来存放新生产的产品。因此必须采取某种机制来保证他们的工作不会出现混乱。

-   **订阅和投送报纸的例子（“生产-消费”问题）**

我们用订阅和投送报纸的例子来考虑这个问题。A订阅了一份报纸，邮递员B负责投递，但投递时间是不一定的。A希望尽快的拿到报纸，最好是B一完成投递就拿到报纸阅读。简单粗暴的方式是A反复的检查邮箱，看到报纸就拿走。但这样太辛苦了，绝大多数时候都是无效劳动。用计算机程序来做的话就是不断查询邮箱状态，很浪费CPU时间。

-   **门铃（notifyAll, notify）**

所以比较好的方法是安装一个**专用门铃**（专用的意思是只要铃响就意味着邮箱中肯定有报纸），A只需在房间**休息(wait)**，当B投递报纸后立刻**按一下门铃（notifyAll,
notify）**，A就知道出来拿报纸了。

但是可能出现两种问题。

一个是**虚假门铃（spurious
wakeups）**。邻居小孩C有点淘气，有时候会去乱按门铃。A这时出来就拿不到报纸。另一个是**共用门铃（多个生产线程；多个消费线程；多种唤醒条件）**。比如A家里夫妻两个（多个消费线程）都准备去拿报纸，门铃响了以后，A1去拿回报纸，A2再去就只是空邮箱了。

结果就是**非专用门铃**（即无法确保门铃是专用的，铃响不代表邮箱中一定有报纸）。不过解决办法也很简单，就是**检查后再行动（"check
and
act"）**，每次门铃响了以后，先检查邮箱，如果有报纸再拿回来，如果没有就继续回去休息。

-   **时间差问题（多线程交错运行）**

但是，检查后再行动引起了**时间差问题**。对于人来说打开邮箱拿报纸似乎是一件事情，但如果事先并不确定里面是否有报纸的话，检查后再行动其实是两个步骤。时间差问题，比如这样的情况：A出来检查邮箱，发现没有报纸，于是决定回去休息，往回走的路上，邮递员到了，邮递员把报纸放进邮箱，按了门铃，但这时候A还在回房间的路上，并没有听见门铃响，也就不知道邮递员已经送来报纸，于是A按预定计划回房间休息，而邮递员已经走了，结果A错过了这份报纸。

如何解决呢？我们要确保A查看邮箱到回到房间这段时间内，邮递员不能进行投递。所以可以修建一个**保护罩（synchronized）**，将邮箱和门口的道路（"check
and
act"）都笼罩在内，而保护罩同一时间只允许A或B之中的一个人进入。比如上面的情况，A进入保护罩查看邮箱，发现没有报纸后回去休息，这时邮递员B到达准备投递，但A还在保护罩里面，禁止B进入，B只好在外面等一下。等A离开保护罩回到房间，这时邮递员才能进入保护罩并投递报纸，然后按下门铃，而这时A已经在房间里了，肯定能听到铃声，就会出来拿报纸了。

-   **小结**

在java程序中，A休息用 wait() 实现，B按门铃用 notifyAll() 或 notify()
实现，邮箱的状态（有没有报纸）可以通过定义一个变量来表示，A检查该变量的值来决定是拿报纸还是回去休息。要使用
while循环检查变量状态（"check and act"）来避免虚假门铃和共用门铃的问题。保护罩用
synchronized关键字 实现，确保（"check and act"）成为一个原子操作。
