package com.milkmachine.rxjava2interop

/**
 * Convert a V1 Observable into a V2 Flowable, composing backpressure and cancellation.

 * @param <T> the value type
</T> */
internal class ObservableV1ToFlowableV2<T>(val source: rx.Observable<T>) : io.reactivex.Flowable<T>() {

    override fun subscribeActual(s: org.reactivestreams.Subscriber<in T>) {
        val parent = ObservableSubscriber(s)
        val parentSubscription = ObservableSubscriberSubscription(parent)
        s.onSubscribe(parentSubscription)

        source.unsafeSubscribe(parent)
    }

    internal class ObservableSubscriber<T>(val actual: org.reactivestreams.Subscriber<in T>) : rx.Subscriber<T>() {

        var done: Boolean = false

        init {
            this.request(0L) // suppress starting out with Long.MAX_VALUE
        }

        override fun onNext(t: T?) {
            if (done) {
                return
            }
            if (t == null) {
                unsubscribe()
                onError(NullPointerException(
                        "The upstream 1.x Observable signalled a null value which is not supported in 2.x"))
            } else {
                actual.onNext(t)
            }
        }

        override fun onError(e: Throwable) {
            if (done) {
                io.reactivex.plugins.RxJavaPlugins.onError(e)
                return
            }
            done = true
            actual.onError(e)
        }

        override fun onCompleted() {
            if (done) {
                return
            }
            done = true
            actual.onComplete()
        }

        fun requestMore(n: Long) {
            request(n)
        }
    }

    internal class ObservableSubscriberSubscription(val parent: ObservableSubscriber<*>) : org.reactivestreams.Subscription {

        override fun request(n: Long) {
            parent.requestMore(n)
        }

        override fun cancel() {
            parent.unsubscribe()
        }
    }

}