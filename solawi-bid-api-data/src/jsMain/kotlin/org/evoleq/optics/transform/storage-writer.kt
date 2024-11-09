package org.evoleq.optics.transform

import org.evoleq.optics.storage.Storage
import org.evoleq.math.Writer

operator fun <W, P> Storage<W>.times(writer: Writer<W, P>): Writer<Unit, P> = lens() * writer