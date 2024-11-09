package lib.optics.transform

import lib.optics.lens.Lens
import org.evoleq.math.Writer

operator fun <W, P, Q> Lens<W, P>.times(writer: Writer<P, Q>): Writer<W, Q> = {
    q -> {w -> set(writer(q)(get(w)))(w)}
}