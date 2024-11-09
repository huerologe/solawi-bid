package lib.optics.transform

import lib.optics.storage.Storage
import org.evoleq.math.Writer

operator fun <W, P> Storage<W>.times(writer: Writer<W, P>): Writer<Unit, P> = lens() * writer