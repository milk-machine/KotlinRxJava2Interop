package com.milkmachine.rxjava2interop

import com.milkmachine.rxjava2interop.FlowableV2ToObservableV1


/**
 * Wrap a 2.x FlowableProcessor into a 1.x Subject.
 * @param <T> the input and output value type
 * *
 * @since 0.9.0
</T> */
internal class ProcessorV2ToSubjectV1<T> private constructor(val state: ProcessorV2ToSubjectV1.State<T>) : rx.subjects.Subject<T, T>(state) {

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

    internal class State<T>(val processor: io.reactivex.processors.FlowableProcessor<T>) : rx.Observable.OnSubscribe<T> {

        override fun call(t: rx.Subscriber<in T>) {
            val parent = com.milkmachine.rxjava2interop.FlowableV2ToObservableV1.SourceSubscriber(t)

            t.add(parent)
            t.setProducer(parent)

            processor.subscribe(parent)
        }

        fun onNext(t: T) {
            processor.onNext(t)
        }

        fun onError(e: Throwable) {
            processor.onError(e)
        }

        fun onCompleted() {
            processor.onComplete()
        }

        fun hasObservers(): Boolean {
            return processor.hasSubscribers()
        }
    }

    companion object {

        fun <T> createWith(processor: io.reactivex.processors.FlowableProcessor<T>): rx.subjects.Subject<T, T> {
            val state = State(processor)
            return ProcessorV2ToSubjectV1(state)
        }
    }
}