package com.milkmachine.rxjava2interop

/**
 * Convert a V1 Single into a V2 Single, composing cancellation.

 * @param <T> the value type
</T> */
internal class SingleV1ToSingleV2<T>(val source: rx.Single<T>) : io.reactivex.Single<T>() {

    override fun subscribeActual(observer: io.reactivex.SingleObserver<in T>) {
        val parent = SourceSingleSubscriber(observer)
        observer.onSubscribe(parent)
        source.subscribe(parent)
    }

    internal class SourceSingleSubscriber<T>(val observer: io.reactivex.SingleObserver<in T>) : rx.SingleSubscriber<T>(), io.reactivex.disposables.Disposable {

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