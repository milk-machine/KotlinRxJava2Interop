package com.milkmachine.rxjava2interop

import io.reactivex.MaybeObserver

/**
 * Convert a V1 Single into a V2 Maybe, composing cancellation.

 * @param <T> the value type
</T> */
internal class SingleV1ToMaybeV2<T>(val source: rx.Single<T>) : io.reactivex.Maybe<T>() {

    override fun subscribeActual(observer: MaybeObserver<in T>) {
        val parent = SourceSingleSubscriber(observer)
        observer.onSubscribe(parent)
        source.subscribe(parent)
    }

    internal class SourceSingleSubscriber<T>(val observer: io.reactivex.MaybeObserver<in T>) : rx.SingleSubscriber<T>(), io.reactivex.disposables.Disposable {

        override fun onSuccess(value: T?) {
            if (value == null) {
                observer.onError(NullPointerException(
                        "The upstream 1.x Single signalled a null value which is not supported in 2.x"))
            } else {
                observer.onSuccess(value)
            }
        }

        override fun onError(error: Throwable) {
            observer.onError(error)
        }

        override fun dispose() {
            unsubscribe()
        }

        override fun isDisposed(): Boolean {
            return isUnsubscribed
        }
    }
}