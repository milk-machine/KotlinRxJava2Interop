package com.milkmachine.rxjava2interop

import io.reactivex.CompletableTransformer
import io.reactivex.FlowableOperator
import io.reactivex.FlowableTransformer
import io.reactivex.SingleTransformer
import rx.Completable
import rx.Observable
import rx.Single


/**
 * Conversion methods for converting between 1.x and 2.x reactive types, composing backpressure
 * and cancellation through.
 */

/** Utility class.  */

// -----------------------------------------------------------------------------------------
// Conversions to 2.x
// -----------------------------------------------------------------------------------------

/**
 * Converts an 1.x Observable into a 2.x Flowable, composing the backpressure and
 * cancellation (unsubscription) through.
 *
 *
 * Note that in 1.x Observable's backpressure somewhat optional; you may need to apply
 * one of the onBackpressureXXX operators on the source Observable or the resulting Flowable.
 * <dl>
 * <dt>**Backpressure:**</dt>
 * <dd>The operator doesn't interfere with backpressure which is determined by the
 * source 1.x `Observable`'s backpressure behavior.</dd>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the value type
 * *
 * @param source the source 1.x Observable instance, not null
 * *
 * @return the new 2.x Flowable instance
 * *
 * @throws NullPointerException if `source` is null
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> rx.Observable<T>.toV2Flowable(): io.reactivex.Flowable<T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "source is null")
    return ObservableV1ToFlowableV2(this)
}

/**
 * Converts an 1.x Observable into a 2.x Observable, cancellation (unsubscription) through.
 * <dl>
 * <dt>**Backpressure:**</dt>
 * <dd>The operator consumes the source 1.x `Observable` in an
 * unbounded manner (without applying backpressure).</dd>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the value type
 * *
 * @param source the source 1.x Observable instance, not null
 * *
 * @return the new 2.x Observable instance
 * *
 * @throws NullPointerException if `source` is null
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> rx.Observable<T>.toV2Observable(): io.reactivex.Observable<T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "source is null")
    return ObservableV1ToObservableV2(this)
}

/**
 * Converts an 1.x Completable into a 2.x Maybe, composing cancellation (unsubscription) through.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the value type
 * *
 * @param source the source 1.x Completable instance, not null
 * *
 * @return the new 2.x Maybe instance
 * *
 * @throws NullPointerException if `source` is null
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> rx.Completable.toV2Maybe(): io.reactivex.Maybe<T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "source is null")
    return CompletableV1ToMaybeV2(this)
}

/**
 * Converts an 1.x Single into a 2.x Maybe, composing cancellation (unsubscription) through.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the value type
 * *
 * @param source the source 1.x Single instance, not null
 * *
 * @return the new 2.x Maybe instance
 * *
 * @throws NullPointerException if `source` is null
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> rx.Single<T>.toV2Maybe(): io.reactivex.Maybe<T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "source is null")
    return SingleV1ToMaybeV2(this)
}

/**
 * Converts an 1.x Single into a 2.x Single, composing cancellation (unsubscription) through.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the value type
 * *
 * @param source the source 1.x Single instance, not null
 * *
 * @return the new 2.x Single instance
 * *
 * @throws NullPointerException if `source` is null
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> rx.Single<T>.toV2Single(): io.reactivex.Single<T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "source is null")
    return SingleV1ToSingleV2(this)
}

/**
 * Converts an 1.x Completable into a 2.x Completable, composing cancellation (unsubscription) through.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param source the source 1.x Completable instance, not null
 * *
 * @return the new 2.x Completable instance
 * *
 * @throws NullPointerException if `source` is null
 */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun rx.Completable.toV2Completable(): io.reactivex.Completable {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "source is null")
    return CompletableV1ToCompletableV2(this)
}

/**
 * Wraps a 1.x Subject into a 2.x Subject.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the input and output value type of the Subjects
 * *
 * @param subject the subject to wrap with the same input and output type;
 * * 2.x Subject supports only the same input and output type
 * *
 * @return the new 2.x Subject instance
 * *
 * @throws NullPointerException if `source` is null
 * *
 * @since 0.9.0
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> rx.subjects.Subject<T, T>.toV2Subject(): io.reactivex.subjects.Subject<T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "subject is null")
    return SubjectV1ToSubjectV2(this)
}

/**
 * Wraps a 1.x Subject into a 2.x FlowableProcessor.
 * <dl>
 * <dt>**Backpressure:**</dt>
 * <dd>The operator doesn't interfere with backpressure which is determined by the
 * source 1.x `Subject`'s backpressure behavior.</dd>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the input and output value type of the Subjects
 * *
 * @param subject the subject to wrap with the same input and output type;
 * * 2.x FlowableProcessor supports only the same input and output type
 * *
 * @return the new 2.x FlowableProcessor instance
 * *
 * @throws NullPointerException if `source` is null
 * *
 * @since 0.9.0
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> rx.subjects.Subject<T, T>.toV2Processor(): io.reactivex.processors.FlowableProcessor<T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "subject is null")
    return SubjectV1ToProcessorV2(this)
}

/**
 * Convert the 1.x Observable.Transformer into a 2.x FlowableTransformer.
 * <dl>
 * <dt>**Backpressure:**</dt>
 * <dd>The operator doesn't interfere with backpressure which is determined by the
 * 1.x Observable returned by the 1.x Transformer.</dd>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the input value type
 * *
 * @param <R> the output value type
 * *
 * @param transformer the 1.x Observable.Transformer to convert
 * *
 * @return the new FlowableTransformer instance
 * *
 * @since 0.9.0
</R></T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T, R> rx.Observable.Transformer<T, R>.toV2Transformer(): io.reactivex.FlowableTransformer<T, R> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "transformer is null")
    return FlowableTransformer { call(it.toV1Observable()).toV2Flowable() }
}

/**
 * Convert the 1.x Single.Transformer into a 2.x SingleTransformer.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the input value type
 * *
 * @param <R> the output value type
 * *
 * @param transformer the 1.x Single.Transformer to convert
 * *
 * @return the new SingleTransformer instance
 * *
 * @since 0.9.0
</R></T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T, R> rx.Single.Transformer<T, R>.toV2Transformer(): io.reactivex.SingleTransformer<T, R> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "transformer is null")
    return SingleTransformer { call(it.toV1Single()).toV2Single() }
}

/**
 * Convert the 1.x Completable.Transformer into a 2.x CompletableTransformer.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param transformer the 1.x Completable.Transformer to convert
 * *
 * @return the new CompletableTransformer instance
 * *
 * @since 0.9.0
 */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun rx.Completable.Transformer.toV2Transformer(): io.reactivex.CompletableTransformer {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "transformer is null")
    return CompletableTransformer { call(it.toV1Completable()).toV2Completable() }
}

/**
 * Convert the 1.x Observable.Operator into a 2.x FlowableOperator.
 * <dl>
 * <dt>**Backpressure:**</dt>
 * <dd>The operator doesn't interfere with backpressure which is determined by the
 * 1.x Subscriber returned by the 1.x Operator.</dd>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the input value type
 * *
 * @param <R> the output value type
 * *
 * @param operator the 1.x Observable.Operator to convert
 * *
 * @return the new FlowableOperator instance
 * *
 * @since 0.9.0
</R></T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T, R> rx.Observable.Operator<R, T>.toV2Operator(): io.reactivex.FlowableOperator<R, T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "operator is null")
    return FlowableOperator { s ->
        val parent = com.milkmachine.rxjava2interop.ObservableV1ToFlowableV2.ObservableSubscriber(s)
        val parentSubscription = com.milkmachine.rxjava2interop.ObservableV1ToFlowableV2.ObservableSubscriberSubscription(parent)
        s.onSubscribe(parentSubscription)

        var t: rx.Subscriber<in T>

        try {
            t = io.reactivex.internal.functions.ObjectHelper.requireNonNull(this.call(parent), "The operator returned a null rx.Subscriber")
        } catch (ex: Throwable) {
            io.reactivex.exceptions.Exceptions.throwIfFatal(ex)
            rx.exceptions.Exceptions.throwIfFatal(ex)
            s.onError(ex)
            t = rx.observers.Subscribers.empty<T>()
            t.unsubscribe()
        }

        val z = com.milkmachine.rxjava2interop.FlowableV2ToObservableV1.SourceSubscriber(t)

        t.add(z)
        t.setProducer(z)

        z
    }
}

// -----------------------------------------------------------------------------------------
// Conversions to 1.x
// -----------------------------------------------------------------------------------------

/**
 * Converts a Reactive-Streams Publisher of any kind (the base type of 2.x Flowable)
 * into an 1.x Observable, composing the backpressure and cancellation
 * (unsubscription) through.
 *
 *
 * Note that this method can convert **any** Reactive-Streams compliant
 * source into an 1.x Observable, not just the 2.x Flowable.
 * <dl>
 * <dt>**Backpressure:**</dt>
 * <dd>The operator doesn't interfere with backpressure which is determined by the
 * source `Publisher`'s backpressure behavior.</dd>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the value type
 * *
 * @param source the source Reactive-Streams Publisher instance, not null
 * *
 * @return the new 1.x Observable instance
 * *
 * @throws NullPointerException if `source` is null
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> org.reactivestreams.Publisher<T>.toV1Observable(): rx.Observable<T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "source is null")
    return rx.Observable.unsafeCreate(FlowableV2ToObservableV1(this))
}

/**
 * Converts a 2.x ObservableSource (the base type of 2.x Observable) into an 1.x
 * Observable instance, applying the specified backpressure strategy and composing
 * the cancellation (unsubscription) through.
 * <dl>
 * <dt>**Backpressure:**</dt>
 * <dd>The operator applies the backpressure strategy you specify.</dd>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the value type
 * *
 * @param source the source ObservableSource instance, not null
 * *
 * @param strategy the backpressure strategy to apply: BUFFER, DROP or LATEST.
 * *
 * @return the new 1.x Observable instance
 * *
 * @throws NullPointerException if `source` is null
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> io.reactivex.ObservableSource<T>.toV1Observable(strategy: io.reactivex.BackpressureStrategy): rx.Observable<T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "source is null")
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(strategy, "strategy is null")
    return io.reactivex.Observable.wrap(this).toFlowable(strategy).toV1Observable()
}

/**
 * Converts an 2.x SingleSource (the base type of 2.x Single) into a
 * 1.x Single, composing cancellation (unsubscription) through.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the value type
 * *
 * @param source the source 2.x SingleSource instance, not null
 * *
 * @return the new 1.x Single instance
 * *
 * @throws NullPointerException if `source` is null
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> io.reactivex.SingleSource<T>.toV1Single(): rx.Single<T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "source is null")
    return rx.Single.create(SingleV2ToSingleV1(this))
}

/**
 * Converts an 2.x CompletableSource (the base type of 2.x Completable) into a
 * 1.x Completable, composing cancellation (unsubscription) through.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param source the source 2.x CompletableSource instance, not null
 * *
 * @return the new 1.x Completable instance
 * *
 * @throws NullPointerException if `source` is null
 */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun io.reactivex.CompletableSource.toV1Completable(): rx.Completable {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "source is null")
    return rx.Completable.create(CompletableV2ToCompletableV1(this))
}

/**
 * Converts an 2.x MaybeSource (the base type of 2.x Maybe) into a
 * 1.x Single, composing cancellation (unsubscription) through and
 * signalling NoSuchElementException if the MaybeSource is empty.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the source's value type
 * *
 * @param source the source 2.x MaybeSource instance, not null
 * *
 * @return the new 1.x Single instance
 * *
 * @throws NullPointerException if `source` is null
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> io.reactivex.MaybeSource<T>.toV1Single(): rx.Single<T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "source is null")
    return rx.Single.create(MaybeV2ToSingleV1(this))
}

/**
 * Converts an 2.x MaybeSource (the base type of 2.x Maybe) into a
 * 1.x Completable, composing cancellation (unsubscription) through
 * and ignoring the success value.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the source's value type
 * *
 * @param source the source 2.x MaybeSource instance, not null
 * *
 * @return the new 1.x Completable instance
 * *
 * @throws NullPointerException if `source` is null
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> io.reactivex.MaybeSource<T>.toV1Completable(): rx.Completable {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "source is null")
    return rx.Completable.create(MaybeV2ToCompletableV1(this))
}

/**
 * Wraps a 2.x Subject into a 1.x Subject.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the input and output value type of the Subjects
 * *
 * @param subject the subject to wrap with the same input and output type;
 * * 2.x Subject supports only the same input and output type
 * *
 * @return the new 1.x Subject instance
 * *
 * @throws NullPointerException if `source` is null
 * *
 * @since 0.9.0
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> io.reactivex.subjects.Subject<T>.toV1Subject(): rx.subjects.Subject<T, T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "subject is null")
    return SubjectV2ToSubjectV1.createWith(this)
}

/**
 * Wraps a 2.x FlowableProcessor into a 1.x Subject.
 * <dl>
 * <dt>**Backpressure:**</dt>
 * <dd>The operator doesn't interfere with backpressure which is determined by the
 * source `FlowableProcessor`'s backpressure behavior.</dd>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the input and output value type of the Subjects
 * *
 * @param processor the flowable-processor to wrap with the same input and output type;
 * * 2.x FlowableProcessor supports only the same input and output type
 * *
 * @return the new 1.x Subject instance
 * *
 * @throws NullPointerException if `source` is null
 * *
 * @since 0.9.0
</T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T> io.reactivex.processors.FlowableProcessor<T>.toV1Subject(): rx.subjects.Subject<T, T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "processor is null")
    return ProcessorV2ToSubjectV1.createWith(this)
}

/**
 * Convert the 2.x FlowableTransformer into a 1.x Observable.Transformer.
 * <dl>
 * <dt>**Backpressure:**</dt>
 * <dd>The operator doesn't interfere with backpressure which is determined by the
 * 2.x Flowable returned by the 2.x FlowableTransformer.</dd>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the input value type
 * *
 * @param <R> the output value type
 * *
 * @param transformer the 2.x FlowableTransformer to convert
 * *
 * @return the new Observable.Transformer instance
 * *
 * @since 0.9.0
</R></T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T, R> io.reactivex.FlowableTransformer<T, R>.toV1Transformer(): rx.Observable.Transformer<T, R> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "transformer is null")
    return Observable.Transformer<T, R> { apply(it.toV2Flowable()).toV1Observable() }
}

/**
 * Convert the 2.x SingleTransformer into a 1.x Single.Transformer.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the input value type
 * *
 * @param <R> the output value type
 * *
 * @param transformer the 2.x SingleTransformer to convert
 * *
 * @return the new Single.Transformer instance
 * *
 * @since 0.9.0
</R></T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T, R> io.reactivex.SingleTransformer<T, R>.toV1Transformer(): rx.Single.Transformer<T, R> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "transformer is null")
    return Single.Transformer<T, R> { apply(it.toV2Single()).toV1Single() }
}

/**
 * Convert the 2.x CompletableTransformer into a 1.x Completable.Transformer.
 * <dl>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param transformer the 2.x CompletableTransformer to convert
 * *
 * @return the new Completable.Transformer instance
 * *
 * @since 0.9.0
 */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun io.reactivex.CompletableTransformer.toV1Transformer(): rx.Completable.Transformer {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "transformer is null")
    return Completable.Transformer { apply(it.toV2Completable()).toV1Completable() }
}

/**
 * Convert the 2.x FlowableOperator into a 1.x Observable.Operator.
 * <dl>
 * <dt>**Backpressure:**</dt>
 * <dd>The operator doesn't interfere with backpressure which is determined by the
 * 2.x Subscriber returned by the 2.x FlowableOperator.</dd>
 * <dt>**Scheduler:**</dt>
 * <dd>The method does not operate by default on a particular `Scheduler`.</dd>
</dl> *
 * @param <T> the input value type
 * *
 * @param <R> the output value type
 * *
 * @param operator the 2.x FlowableOperator to convert
 * *
 * @return the new Observable.Operator instance
 * *
 * @since 0.9.0
</R></T> */
@io.reactivex.annotations.SchedulerSupport(io.reactivex.annotations.SchedulerSupport.NONE)
fun <T, R> io.reactivex.FlowableOperator<R, T>.toV1Operator(): rx.Observable.Operator<R, T> {
    io.reactivex.internal.functions.ObjectHelper.requireNonNull(this, "operator is null")
    return Observable.Operator<R, T> { t ->
        val z = com.milkmachine.rxjava2interop.FlowableV2ToObservableV1.SourceSubscriber(t)

        t.add(z)
        t.setProducer(z)

        val s: org.reactivestreams.Subscriber<in T>

        try {
            s = io.reactivex.internal.functions.ObjectHelper.requireNonNull(apply(z), "The operator returned a null Subscriber")
        } catch (ex: Throwable) {
            io.reactivex.exceptions.Exceptions.throwIfFatal(ex)
            rx.exceptions.Exceptions.throwIfFatal(ex)
            t.onError(ex)
            val r = rx.observers.Subscribers.empty<T>()
            r.unsubscribe()
            return@Operator r
        }

        val parent = com.milkmachine.rxjava2interop.ObservableV1ToFlowableV2.ObservableSubscriber(s)
        val parentSubscription = com.milkmachine.rxjava2interop.ObservableV1ToFlowableV2.ObservableSubscriberSubscription(parent)
        s.onSubscribe(parentSubscription)

        parent
    }
}