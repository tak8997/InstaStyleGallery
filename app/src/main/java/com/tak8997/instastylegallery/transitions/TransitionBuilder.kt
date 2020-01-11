package com.tak8997.instastylegallery.transitions

import android.transition.PathMotion
import android.transition.Transition
import android.transition.TransitionPropagation
import android.util.Pair
import android.view.View

internal class TransitionBuilder(private val transition: Transition) {

    fun duration(duration: Long): TransitionBuilder {
        transition.duration = duration
        return this
    }

    fun target(view: View): TransitionBuilder {
        transition.addTarget(view)
        return this
    }

    fun target(clazz: Class<*>): TransitionBuilder {
        transition.addTarget(clazz)
        return this
    }

    fun target(target: String): TransitionBuilder {
        transition.addTarget(target)
        return this
    }

    fun target(targetId: Int): TransitionBuilder {
        transition.addTarget(targetId)
        return this
    }

    fun delay(delay: Long): TransitionBuilder {
        transition.startDelay = delay
        return this
    }

    fun pathMotion(motion: PathMotion): TransitionBuilder {
        transition.pathMotion = motion
        return this
    }

    fun propagation(propagation: TransitionPropagation): TransitionBuilder {
        transition.propagation = propagation
        return this
    }

    fun pair(pair: Pair<View, String>): TransitionBuilder {
        pair.first.transitionName = pair.second
        transition.addTarget(pair.second)
        return this
    }

    fun excludeTarget(view: View, exclude: Boolean): TransitionBuilder {
        transition.excludeTarget(view, exclude)
        return this
    }

    fun excludeTarget(targetName: String, exclude: Boolean): TransitionBuilder {
        transition.excludeTarget(targetName, exclude)
        return this
    }

    fun link(from: View, to: View, transitionName: String): TransitionBuilder {
        from.transitionName = transitionName
        to.transitionName = transitionName
        transition.addTarget(transitionName)
        return this
    }

    fun build(): Transition {
        return transition
    }
}
