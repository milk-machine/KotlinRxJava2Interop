package com.milkmachine.rxjava2interop

/**
 * Wrap a V1 Subject and expose it as a V2 FlowableProcessor.
 * @param <T> the input/output value type
 * *
 * @since 0.9.0
</T> */
internal class SubjectV1ToProcessorV2<T>(val source: rx.subjects.Subject<T, T>) : io.reactivex.processors.FlowableProcessor<T>() {

    @Volatile var terminated: Boolean = false
    var error: Throwable? = null

    override fun onSubscribe(s: org.reactivestreams.Subscription) {
        if (terminated) {
            s.cancel()
        } else {
            s.request(java.lang.Long.MAX_VALUE)
        }
    }

    override fun onNext(t: T?) {
        if (!terminated) {
            if (t == null) {
                onError(NullPointerException())
            } else {
                source.onNext(t)
            }
        }
    }

    override fun onError(ex: Throwable?) {
        var e = ex
        if (!terminated) {
            if (e == null) {
                e = NullPointerException("Throwable was null")
            }
            error = e
            terminated = true
            source.onError(e)
        } else {
            io.reactivex.plugins.RxJavaPlugins.onError(e)
        }
    }

    override fun onComplete() {
        if (!terminated) {
            terminated = true
            source.onCompleted()
        }
    }

    override fun subscribeActual(s: org.reactivestreams.Subscriber<in T>) {
        val parent = com.milkmachine.rxjava2interop.ObservableV1ToFlowableV2.ObservableSubscriber(s)
        val parentSubscription = com.milkmachine.rxjava2interop.ObservableV1ToFlowableV2.ObservableSubscriberSubscription(parent)
        s.onSubscribe(parentSubscription)

        source.unsafeSubscribe(parent)
    }

    override fun hasSubscribers(): Boolean {
        return source.hasObservers()
    }

    override fun hasComplete(): Boolean {
        return terminated && error == null
    }

    override fun hasThrowable(): Boolean {
        return terminated && error != null
    }

    override fun getThrowable(): Throwable? {
        return if (terminated) error else null
    }
}