package org.evoleq.optics.transform

import org.evoleq.optics.iso.Iso
import org.evoleq.optics.storage.Storage
import org.evoleq.math.Maths


@Maths
operator fun <V, W> Storage<V>.times(iso: Iso<V, W>): Storage<W> = (lens() * iso).storage()
