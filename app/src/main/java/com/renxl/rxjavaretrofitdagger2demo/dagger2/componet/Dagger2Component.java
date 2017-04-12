package com.renxl.rxjavaretrofitdagger2demo.dagger2.componet;

import com.renxl.rxjavaretrofitdagger2demo.dagger2.Dagger22Activity;
import com.renxl.rxjavaretrofitdagger2demo.dagger2.Dagger2Activity;
import com.renxl.rxjavaretrofitdagger2demo.dagger2.Dagger2Module;
import com.renxl.rxjavaretrofitdagger2demo.dagger2.UserScope;

import dagger.Component;

/**
 * Created by renxl
 * On 2017/4/10 21:17.
 */

// 用 @Component 表示这个接口是一个连接器，能用 @Component 注解的只能是 interface 或者 抽象类
@UserScope
@Component(dependencies = ApplicationComponent.class, modules = Dagger2Module.class)
// 在这里我们可以定义从哪些 module（或者哪些Components）中获取依赖
public abstract class Dagger2Component {

    private static Dagger2Component dagger2Component;

    /**
     * 方法参数是需要用到这个连接器的对象，就是这个类的对象里面有需要注入的属性
     * (被标记为 @Inject 的属性)，这里 inject 表示注入的意思，方法名
     * 可以随便改，但建议使用 inject 即可
     */
    public abstract void inject(Dagger2Activity activity); // 用来定义哪些图表依赖应该公开可见（可以被注入）和哪里我们的component可以注入对象

    abstract void inject(Dagger22Activity activity);

    /**
     * 构造单例的 Dagger2Component 对象，由单例的 Dagger2Componet 可以注入单例的对象
     *
     * @return
     */
    public static Dagger2Component getInstance() {
        if (dagger2Component == null) {
            synchronized (Dagger2Component.class) {
                if (dagger2Component == null)
                    dagger2Component = DaggerDagger2Component.builder()
                            .applicationComponent(DaggerApplicationComponent.builder().build()) // 依赖 ApplicationComponent
                            .build();
            }
        }
        return dagger2Component;
    }

}
