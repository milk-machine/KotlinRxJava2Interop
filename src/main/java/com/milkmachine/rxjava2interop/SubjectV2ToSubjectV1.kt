package com.milkmachine.rxjava2interop

import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference


/**
 * Wrap a 2.x Subject into a 1.x Subject.
 * @param <T> the input and output value type
 * *
 * @since 0.9.0
</T> */
internal class SubjectV2ToSubjectV1<T> private constructor(val state: SubjectV2ToSubjectV1.State<T>) : rx.subjects.Subject<T, T>(state) {

    override fun onNext(t: T) {
        state.onNext(t)
    }

    override fun onError(e: Throwable) {
        state.onError(e)
    }

    override fun onCompleted() {
        state.onCompleted()
    }

    override fun hasObservers(): Boolean {
        return state.hasObservers()
    }

    internal class State<T>(val subject: io.reactivex.subjects.Subject<T>) : rx.Observable.OnSubscribe<T> {

        override fun call(t: rx.Subscriber<in T>) {
            val parent = SourceObserver(t)

            t.add(parent)
            t.setProducer(parent)

            subject.subscribe(parent)
        }

        fun onNext(t: T) {
            subject.onNext(t)
        }

        fun onError(e: Throwable) {
            subject.onError(e)
        }

        fun onCompleted() {
            subject.onComplete()
        }

        fun hasObservers(): Boolean {
            return subject.hasObservers()
        }
    }

    internal class SourceObserver<T>(val actual: rx.Subscriber<in T>) : AtomicReference<io.reactivex.disposables.Disposable>(), io.reactivex.Observer<T>, rx.Subscription, rx.Producer {

        val requested: AtomicLong = AtomicLong()

        override fun request(n: Long) {
            if (n > 0L) {
                io.reactivex.internal.util.BackpressureHelper.add(requested, n)
            }
        }

        override fun unsubscribe() {
            io.reactivex.internal.disposables.DisposableHelper.dispose(this)
        }

        override fun isUnsubscribed(): Boolean {
            return io.reactivex.internal.disposables.DisposableHelper.isDisposed(get())
        }

        override fun onSubscribe(d: io.reactivex.disposables.Disposable) {
            io.reactivex.internal.disposables.DisposableHelper.setOnce(this, d)
        }

        override fun onNext(t: T) {
            if (requested.get() != 0L) {
                actual.onNext(t)
                io.reactivex.internal.util.BackpressureHelper.produced(requested, 1)
            } else {
                unsubscribe()
                actual.onError(rx.exceptions.MissingBackpressureException())
            }
        }

        override fun onError(t: Throwable) {
            lazySet(io.reactivex.internal.disposables.DisposableHelper.DISPOSED)
            actual.onError(t)
        }

        override fun onComplete() {
            lazySet(io.reactivex.internal.disposables.DisposableHelper.DISPOSED)
            actual.onCompleted()
        }

        companion object {

            private val serialVersionUID = -6567012932544037069L
        }
    }

    companion object {

        fun <T> createWith(subject: io.reactivex.subjects.Subject<T>): rx.subjects.Subject<T, T> {
            val state = State(subject)
            return SubjectV2ToSubjectV1(state)
        }
    }
}