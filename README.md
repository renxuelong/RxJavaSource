## RxJava

主要内容还是以 **RxJava** 为主的，其中最最核心的内容就是手写的 RxJava 源码了，其中实现了**链式调度、事件转换、线程切换**等 RxJava 的核心功能，当然还包括其他一些库的使用


RxJava 解决的问题：传统回调地狱，代码不简洁

实现 RxJava

### 一、链式调度  

响应式编程、观察者模式
建造者模式  (链式调度就是建造者模式的变形)


**Observable  场景**

**OnSubscrible 被观察者** 

**Subscrible  观察者**

1. 创建场景 Observable 的 create 方法，参数为被观察者对象，返回值为 Observable 对象
2. 调用 Observable 对象的 subcribe 方法传入观察者
3. 传入观察者时 Observable 的 subscribe 方法中会调用被观察者的 call 方法，call 方法中传入参数 观察者 对象，call 方法中会调用 观察者 的 onNext 方法，onNext 方法参数为需要传递的事件，完成事件从被观察者传递到观察者
4. 完成事件传递和链式调度 


### 二、事件变换  比如： URL --> Bitmap

**Func1** 其中有一个  call 方法用于事件的转换

**OnSubscribleT** 其中持有 OnSubscrible 和 Func1 的引用，并在构造函数中传入这两个参数

**OperaChange** OnSubscribleT 的内部类，其也是 Subscrible 的子类，其中有一个 Func1 对象和一个 Subscrible 的对象，在收到事件事会通过 Func1 转换事件，并调用 Subscrible 的 onNext 处理转换后的事件


1. 创建场景 Observable 的 create 方法，参数为被观察者对象，返回值为 Observable 对象

2. 调用 Observable 对象的 map 方法传入 Func1 对象

3. map 方法中构造新的 Observable 对象，将由 OnSubscrible 和 Func1 对象构造一个 OnSubscribleT 对象，将 OnSubscribleT 传入新的 Observable 的构造方法，最后返回新的 Observable 对象

4. 调用新的 Observable 的 subscribe 方法传入可以处理转换后的事件的 Subscrible 对象

5. 新的 Observable 的 subscribe 方法中会调用 OnSubscribleT 的 call 方法并将新的 Subscrible 对象传入

6. OnSubscribleT 中有原来 OnSubscrible 和 Func1 对象的引用，首先由  Func1 和 Subscrible 构造 OperaChange 对象，然后调用原来 OnSubscrible 的 call 方法，将 OperaChange 对象传入

7. 原来 OnSubscrible 的 call 方法中收到 OperaChange 对象后，会调用 OperaChange 的 onNext 方法并将要处理原始事件传入

8. OperaChange 的 onNext 方法中会首先通过 Func1 将原来 OnSubscrible 传来的旧类型转换为 Subscrible 可以处理的类型，然后再调用其内部的可以处理事件的 Subscrible 对象的 onNext 方法将转换后的事件传入，完成事件的转换和链式调用


**责任链模式的运用，OnSubscribleT 中不处理事件，将事件传递到 OnSubscrible 中处理**


### 三、线程切换  

由线程切换可以清晰的看出 RxJava 的责任链模式和观察者模式的使用，包括上面提到的事件变换中其实事件并不是沿着 OnSubscrible 链处理，而是沿着 Observable 链处理。

RxJava 中，每次链式调用一次，除了 subscrible 方法外，都是返回一个新的 Observable 对象其中都会创建一个新的 OnSubscrible，最后调用 subscribe 时，会将 Subscrible 传入最后创建的 Observable 中的 OnSubscrible 的 call 方法，OnSubscrible 中的 call 方法会将原始 Subscrible 对象加一层处理然后返回上一层的 Observable 中的 OnSubscrible，在返回上一层时也可以做一些切换线程等操作，如果切换了线程那么再往上一层传递后，上面层级中的 OnSubscrible 的 call 方法都会执行在切换后的线程。直到事件传入到最开始的 Observable 中的 OnSubscrible 的 call 方法中，此时会开始调用  Subscrible 的 onNext() 方法，由于之前 Observable 链中对的 OnSubscrible 中对 Subscrible 做了处理(一般为包装一层 Subscrible)，此时会调用处理后的 Subscrible 的 onNext 方法，其 call 方法一般为先按照指定逻辑处理请求，比如事件转换，切换线程，然后再调用自己包装的 Subscrible 的 onNext 方法，此时传递到下一级 Subscrible 的事件已经经过转换，或者经过线程切换，经过传递最后到达了原始的 Subscrible 的 onNext 方法中，这时任务也就执行在了经过切换的线程中。


整个过程一般来说线程切换两次，OnSubscrible 的 call 方法中切换一次，Subscrible 的 onNext 方法中切换一次，这样保证了请求可以执行在子线程，而结果的处理执行在主线程。


说完了上面这一大段，我们就知道了 RxJava 的根本原理，不管是事件转换还是线程切换都是对责任链模式的使用。具体如何切换线程我们在下面层级中的 OnSubscrible 的 call 方法中将事件传递到上一层级时开启子线程，在子线程中传递。最后 Subscrible 中也是一样，将事件传递到自己包装了的 Subscrible 时通过绑定到主线程的 Handler 的 post 方法将线程切换到主线程中之后在主线程中执行自己包装了的 Subscrible 的 onNext 方法。实现线程切换。

