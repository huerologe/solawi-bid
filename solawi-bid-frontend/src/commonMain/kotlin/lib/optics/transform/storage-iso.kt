package lib.optics.transform

import lib.optics.iso.Iso
import lib.optics.storage.Storage
import org.evoleq.maths.Maths


@Maths
operator fun <V, W> Storage<V>.times(iso: Iso<V, W>): Storage<W> = (lens() * iso).storage()
