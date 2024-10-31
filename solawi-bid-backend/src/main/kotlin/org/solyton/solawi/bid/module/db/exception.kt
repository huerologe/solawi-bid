package org.solyton.solawi.bid.module.db

sealed class BidRoundException(override val message: String) : Exception(message)
{
    data object NoSuchRound: BidRoundException("Round not found")
    data class NoSuchRoundState(val state: String): BidRoundException("No such RoundState: $state")
    data class LinkNotPresent(val link: String): BidRoundException("Link not present")
    data object RoundNotStarted: BidRoundException("Round not started")
    data class UnregisteredBidder(val username: String): BidRoundException("Unregistered bidder: $username")
    data class RegisteredBidderNotPartOfTheAuction(val username: String) : BidRoundException("bidder: $username is registered but not part of the auction")
    data object NoSuchAuction : BidRoundException("No such auction")
    data class IllegalNumberOfParts(val value: Int): BidRoundException("Illegal number of parts: $value")
}