package com.milkmachine.rxjava2interop

/**
 * Convert a V2 Completable into a V1 Completable, composing cancellation.
 */
internal class CompletableV2ToCompletableV1(val source: io.reactivex.CompletableSource) : rx.Completable.OnSubscribe {

    override fun call(observer: rx.CompletableSubscriber) {
        source.subscribe(SourceCompletableSubscriber(observer))
    }

    internal class SourceCompletableSubscriber(val observer: rx.CompletableSubscriber) : io.reactivex.CompletableObserver, rx.Subscription {

        var d: io.reactivex.disposables.Disposable? = null

        override fun onSubscribe(d: io.reactivex.disposables.Disposable) {
            this.d = d
            observer.onSubscribe(this)
        }

        override fun onComplete() {
            observer.onCompleted()
        }

        override fun onError(error: Throwable) {
            observer.onError(error)
        }

        override fun unsubscribe() {
            d!!.dispose()
        }

        override fun isUnsubscribed(): Boolean {
            return d!!.isDisposed
        }
    }
}