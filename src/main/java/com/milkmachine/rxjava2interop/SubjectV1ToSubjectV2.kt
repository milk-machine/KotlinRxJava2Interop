package com.milkmachine.rxjava2interop

import com.milkmachine.rxjava2interop.ObservableV1ToObservableV2


/**
 * Wrap a V1 Subject and expose it as a V2 Subject.
 * @param <T> the input/output value type
 * *
 * @since 0.9.0
</T> */
internal class SubjectV1ToSubjectV2<T>(val source: rx.subjects.Subject<T, T>) : io.reactivex.subjects.Subject<T>() {

    @Volatile var terminated: Boolean = false
    var error: Throwable? = null

    override fun onSubscribe(d: io.reactivex.disposables.Disposable) {
        if (terminated) {
            d.dispose()
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

    override fun subscribeActual(observer: io.reactivex.Observer<in T>) {
        val parent = com.milkmachine.rxjava2interop.ObservableV1ToObservableV2.ObservableSubscriber(observer)
        observer.onSubscribe(parent)

        source.unsafeSubscribe(parent)
    }

    override fun hasObservers(): Boolean {
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