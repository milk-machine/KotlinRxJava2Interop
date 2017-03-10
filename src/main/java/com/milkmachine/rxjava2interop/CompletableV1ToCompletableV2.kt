package com.milkmachine.rxjava2interop

/**
 * Convert a V1 Completable into a V2 Completable, composing cancellation.
 */
internal class CompletableV1ToCompletableV2(val source: rx.Completable) : io.reactivex.Completable() {

    override fun subscribeActual(observer: io.reactivex.CompletableObserver) {
        source.subscribe(SourceCompletableSubscriber(observer))
    }

    internal class SourceCompletableSubscriber(val observer: io.reactivex.CompletableObserver) : rx.CompletableSubscriber, io.reactivex.disposables.Disposable {

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