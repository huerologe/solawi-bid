package org.solyton.solawi.bid.module.bid.data

import org.evoleq.optics.Lensify
import org.evoleq.optics.ReadOnly
import org.evoleq.optics.ReadWrite

@Lensify data class Auction(
    @ReadOnly val id: String,
    @ReadWrite val name: String,
    // TODO()
)