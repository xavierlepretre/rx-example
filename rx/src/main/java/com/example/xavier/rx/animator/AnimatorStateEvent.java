package com.example.xavier.rx.animator;

import android.animation.Animator;

public class AnimatorStateEvent
{
    public final Animator animator;
    public final AnimatorState state;

    //<editor-fold desc="Constructors">
    public AnimatorStateEvent(Animator animator, AnimatorState state)
    {
        this.animator = animator;
        this.state = state;
    }
    //</editor-fold>
}
