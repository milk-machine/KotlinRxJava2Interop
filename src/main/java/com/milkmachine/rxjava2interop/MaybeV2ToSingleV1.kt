package com.milkmachine.rxjava2interop

import java.util.NoSuchElementException
import java.util.concurrent.atomic.AtomicReference

/**
 * Converts a V2 Maybe into a V1 Single where an onComplete triggers a NoSuchElementException.

 * @param <T> the value type
</T> */
internal class MaybeV2ToSingleV1<T>(val source: io.reactivex.MaybeSource<T>) : rx.Single.OnSubscribe<T> {

    override fun call(t: rx.SingleSubscriber<in T>) {
        val parent = MaybeV2Observer(t)
        t.add(parent)
        source.subscribe(parent)
    }

    internal class MaybeV2Observer<T>(val actual: rx.SingleSubscriber<in T>) : AtomicReference<io.reactivex.disposables.Disposable>(), io.reactivex.MaybeObserver<T>, rx.Subscription {

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

        override fun onComplete() {
            actual.onError(NoSuchElementException("The source Maybe was empty."))
        }

        companion object {

            private val serialVersionUID = 5045507662443540605L
        }
    }
}