package com.milkmachine.rxjava2interop

/**
 * Convert a V1 Completable into a V2 Maybe, composing cancellation.
 */
internal class CompletableV1ToMaybeV2<T>(val source: rx.Completable) : io.reactivex.Maybe<T>() {

    override fun subscribeActual(observer: io.reactivex.MaybeObserver<in T>) {
        source.subscribe(SourceCompletableSubscriber(observer))
    }

    internal class SourceCompletableSubscriber<T>(val observer: io.reactivex.MaybeObserver<in T>) : rx.CompletableSubscriber, io.reactivex.disposables.Disposable {

        var s: rx.Subscription? = null

        override fun onSubscribe(d: rx.Subscription) {
            this.s = d
            observer.onSubscribe(this)
        }

        override fun onCompleted() {
            observer.onComplete()
        }

        override fun onError(error: Throwable) {
            observer.onError(error)
        }

        override fun dispose() {
            s!!.unsubscribe()
        }

        override fun isDisposed(): Boolean {
            return s!!.isUnsubscribed
        }
    }
}