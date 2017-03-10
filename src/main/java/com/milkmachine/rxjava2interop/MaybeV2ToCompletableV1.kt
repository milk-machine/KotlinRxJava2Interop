package com.milkmachine.rxjava2interop

import java.util.concurrent.atomic.AtomicReference


/**
 * Converts a V2 Maybe into a V1 Complete where an onSuccess value triggers onCompleted.

 * @param <T> the value type
</T> */
internal class MaybeV2ToCompletableV1<T>(val source: io.reactivex.MaybeSource<T>) : rx.Completable.OnSubscribe {

    override fun call(t: rx.CompletableSubscriber) {
        val parent = MaybeV2Observer<T>(t)
        t.onSubscribe(parent)
        source.subscribe(parent)
    }

    internal class MaybeV2Observer<T>(val actual: rx.CompletableSubscriber) : AtomicReference<io.reactivex.disposables.Disposable>(), io.reactivex.MaybeObserver<T>, rx.Subscription {

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
            actual.onCompleted()
        }

        override fun onError(e: Throwable) {
            actual.onError(e)
        }

        override fun onComplete() {
            actual.onCompleted()
        }

        companion object {

            private val serialVersionUID = 5045507662443540605L
        }
    }
}