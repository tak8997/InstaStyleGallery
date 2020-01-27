package com.tak8997.instastylegallery.data.model

import java.io.Serializable

internal data class Quadruple<out A, out B, out C, out D>(
    val first: A? = null,
    val second: B? = null,
    val thrid: C? = null,
    val fourth: D? = null
) : Serializable
