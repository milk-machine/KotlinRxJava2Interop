package com.milkmachine.rxjava2interop

import java.util.concurrent.atomic.AtomicReference

/**
 * Convert a V2 Single into a V1 Single, composing cancellation.

 * @param <T> the value type
</T> */
internal class SingleV2ToSingleV1<T>(val source: io.reactivex.SingleSource<T>) : rx.Single.OnSubscribe<T> {

    override fun call(t: rx.SingleSubscriber<in T>) {
        val parent = SourceSingleObserver(t)
        t.add(parent)
        source.subscribe(parent)
    }

    internal class SourceSingleObserver<T>(val actual: rx.SingleSubscriber<in T>) : AtomicReference<io.reactivex.disposables.Disposable>(), io.reactivex.SingleObserver<T>, rx.Subscription {

        override fun unsubscribe() {
            io.reactivex.internal.disposables.DisposableHelper.dispose(this)
        }

        override fun isUnsubscribed(): Boolean {
            return io.reactivex.internal.disposables.DisposableHelper.isDisposed(get())
        }

        override fun onSubscribe(d: io.reactivex.disposables.Disposable) {
            io.reactivex.internal.disposables.DisposableHelper.setOnce(this, d)
        }

        override fun onSuccess(value: T) {
            actual.onSuccess(value)
        }

        override fun onError(e: Throwable) {
            actual.onError(e)
        }

        companion object {

            private val serialVersionUID = 4758098209431016997L
        }
    }
}