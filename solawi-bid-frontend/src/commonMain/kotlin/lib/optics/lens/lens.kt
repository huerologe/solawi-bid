package lib.optics.lens

import org.evoleq.maths.Maths
import org.evoleq.maths.o
import org.evoleq.maths.x as X

@Maths
data class Lens<W, P> (
    val get: (W) -> P,
    val set: (P) -> (W) -> W
)

@Maths
operator fun <W, P, D> Lens<W, P>.times(other: Lens<P, D>): Lens<W, D> = Lens(
    other.get o get) {
    with(other.set(it)) {
        {w:W ->   (set o this o get) (w) (w) }
    }
}


@Maths
fun <V, W, P, Q> Lens<V, P>.x(other: Lens<W, Q>): Lens<Pair<V, W>, Pair<P, Q>> = Lens(
    get X other.get
) {
    pXq -> set(pXq.first) X other.set(pXq.second)
}




@Maths
infix fun <W, P, Q> Lens<W, P>.sX(other: Lens<W, Q>): Lens<W, Pair<P, Q>> = Lens(
    get = {w -> get(w) X other.get(w)}
) {
        pXq -> set(pXq.first) o other.set(pXq.second)
}
