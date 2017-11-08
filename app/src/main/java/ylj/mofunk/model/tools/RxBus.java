package ylj.mofunk.model.tools;//package com.mo.ylj.momo.tools;
//
//
//import org.reactivestreams.Subscriber;
//
//import io.reactivex.FlowableSubscriber;
//import io.reactivex.Observable;
//import io.reactivex.subjects.PublishSubject;
//import io.reactivex.subjects.Subject;
//
//public class RxBus {
//
//	private static volatile RxBus mInstance;
//
////    private final Subject bus;
//
////
////    public RxBus()
////    {
//////        bus = new SerializedSubject<>(PublishSubject.create());
////        bus = new FlowableSubscriber<>(Subscriber.class);
////    }
//
//    /**
//     * 单例模式RxBus
//     *
//     * @return
//     */
//    public static RxBus getInstance()
//    {
//
//        RxBus rxBus2 = mInstance;
//        if (mInstance == null)
//        {
//            synchronized (RxBus.class)
//            {
//                rxBus2 = mInstance;
//                if (mInstance == null)
//                {
//                    rxBus2 = new RxBus();
//                    mInstance = rxBus2;
//                }
//            }
//        }
//
//        return rxBus2;
//    }
//
//
//    /**
//     * 发送消息
//     *
//     * @param object
//     */
//    public void post(Object object)
//    {
//
//        bus.onNext(object);
//
//    }
//
//    /**
//     * 接收消息
//     *
//     * @param eventType
//     * @param <T>
//     * @return
//     */
//    public <T> Observable<T> toObserverable(Class<T> eventType)
//    {
//        return bus.ofType(eventType);
//    }
//}
