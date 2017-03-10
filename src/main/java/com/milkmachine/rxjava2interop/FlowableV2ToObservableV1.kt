package com.milkmachine.rxjava2interop

import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

/**
 * Convert a V2 Flowable into a V1 Observable, composing backpressure and cancellation.

 * @param <T> the value type
</T> */
internal class FlowableV2ToObservableV1<T>(val source: org.reactivestreams.Publisher<T>) : rx.Observable.OnSubscribe<T> {

    override fun call(t: rx.Subscriber<in T>) {
        val parent = SourceSubscriber(t)

        t.add(parent)
        t.setProducer(parent)

        source.subscribe(parent)
    }

    internal class SourceSubscriber<T>(val actual: rx.Subscriber<in T>) : AtomicReference<org.reactivestreams.Subscription>(), org.reactivestreams.Subscriber<T>, rx.Subscription, rx.Producer {

        val requested: AtomicLong = AtomicLong()

        override fun request(n: Long) {
            if (n != 0L) {
                io.reactivex.internal.subscriptions.SubscriptionHelper.deferredRequest(this, requested, n)
            }
        }

        override fun unsubscribe() {
            io.reactivex.internal.subscriptions.SubscriptionHelper.cancel(this)
        }

        override fun isUnsubscribed(): Boolean {
            return io.reactivex.internal.subscriptions.SubscriptionHelper.isCancelled(get())
        }

        override fun onSubscribe(s: org.reactivestreams.Subscription) {
            io.reactivex.internal.subscriptions.SubscriptionHelper.deferredSetOnce(this, requested, s)
        }

        override fun onNext(t: T) {
            actual.onNext(t)
        }

        override fun onError(t: Throwable) {
            actual.onError(t)
        }

        override fun onComplete() {
            actual.onCompleted()
        }

        companion object {

            private val serialVersionUID = -6567012932544037069L
        }
    }
}