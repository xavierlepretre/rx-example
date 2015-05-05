package com.example.xavier.rx.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

public class AnimatorObservable
{
    public static Observable<AnimatorStateEvent> getAnimatorStateEvents(final Animator animator)
    {
        return Observable.create(new Observable.OnSubscribe<AnimatorStateEvent>()
        {
            @Override public void call(final Subscriber<? super AnimatorStateEvent> subscriber)
            {
                final AnimatorListenerAdapter listenerAdapter = new AnimatorListenerAdapter()
                {
                    @Override public void onAnimationCancel(Animator animation)
                    {
                        super.onAnimationCancel(animation);
                        subscriber.onNext(new AnimatorStateEvent(animation, AnimatorState.CANCEL));
                    }

                    @Override public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        subscriber.onNext(new AnimatorStateEvent(animation, AnimatorState.END));
                    }

                    @Override public void onAnimationRepeat(Animator animation)
                    {
                        super.onAnimationRepeat(animation);
                        subscriber.onNext(new AnimatorStateEvent(animation, AnimatorState.REPEAT));
                    }

                    @Override public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                        subscriber.onNext(new AnimatorStateEvent(animation, AnimatorState.START));
                    }

                    @Override public void onAnimationPause(Animator animation)
                    {
                        super.onAnimationPause(animation);
                        subscriber.onNext(new AnimatorStateEvent(animation, AnimatorState.PAUSE));
                    }

                    @Override public void onAnimationResume(Animator animation)
                    {
                        super.onAnimationResume(animation);
                        subscriber.onNext(new AnimatorStateEvent(animation, AnimatorState.RESUME));
                    }
                };
                animator.addListener(listenerAdapter);

                Subscription onUnSubscribe = Subscriptions.create(new Action0()
                {
                    @Override public void call()
                    {
                        animator.removeListener(listenerAdapter);
                    }
                });
                subscriber.add(onUnSubscribe);
            }
        });
    }
}
