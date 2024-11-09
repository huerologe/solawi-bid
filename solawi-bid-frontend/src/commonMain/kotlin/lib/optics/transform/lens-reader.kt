package lib.optics.transform

import lib.optics.lens.Lens
import org.evoleq.math.Reader
import org.evoleq.math.map

operator fun <W, Q, P> Lens<W, Q>.times(reader: (Q)->P): Reader<W, P> = get map reader