package org.solyton.solawi.bid.module.bid.component.button

import androidx.compose.runtime.Composable
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.language.Lang
import org.evoleq.language.get
import org.evoleq.math.Reader
import org.evoleq.math.Source
import org.evoleq.math.emit
import org.evoleq.math.times
import org.evoleq.optics.lens.FirstBy
import org.evoleq.optics.lens.Lens
import org.evoleq.optics.lens.times
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.actions
import org.solyton.solawi.bid.application.data.device.mediaType
import org.solyton.solawi.bid.application.data.deviceData
import org.solyton.solawi.bid.application.data.modals
import org.solyton.solawi.bid.application.ui.page.auction.action.changeRoundState
import org.solyton.solawi.bid.module.bid.data.Auction
import org.solyton.solawi.bid.module.bid.data.Round
import org.solyton.solawi.bid.module.bid.data.api.RoundState
import org.solyton.solawi.bid.module.bid.data.api.nextState
import org.solyton.solawi.bid.module.bid.data.rounds
import org.solyton.solawi.bid.module.control.button.StdButton
import org.solyton.solawi.bid.module.error.component.showErrorModal
import org.solyton.solawi.bid.module.error.lang.errorModalTexts

@Markup
@Composable
@Suppress("FunctionName")
fun ChangeRoundStateButton(
    storage: Storage<Application>,
    auction: Lens<Application, Auction>,
    round: Round,
    texts: Source<Lang.Block>
) {
    val commandName: (String) -> Reader<Lang.Block, String> = {name -> Reader {lang:Lang.Block ->
        (lang["commands.${name.toLowerCasePreservingASCIIRules()}"])
    } }

    StdButton(
        texts * commandName(RoundState.fromString(round.state).commandName),
            storage * deviceData * mediaType.get
    ) {
        // todo:refactor:extract trigger
        CoroutineScope(Job()).launch {
            val actions = (storage * actions).read()
            try {
                actions.emit( changeRoundState(
                    RoundState.fromString(round.state).nextState(),
                    auction * rounds * FirstBy { it.roundId == round.roundId })
                )
            } catch(exception: Exception) {
                (storage * modals).showErrorModal(
                    texts = errorModalTexts(exception.message?:exception.cause?.message?:"Cannot Emit action 'ChangeRoundState'"),
                    device = storage * deviceData * mediaType.get,
                )
            }
        }
    }
    /*
    // todo:refactor:extract button and extract trigger
    Button(attrs = {
        style {
            width(200.px)
        }
        onClick {
            CoroutineScope(Job()).launch {
                val actions = (storage * actions).read()
                try {
                    actions.emit( changeRoundState(
                        RoundState.fromString(round.state).nextState(),
                        auction * rounds * FirstBy { it.roundId == round.roundId })
                    )
                } catch(exception: Exception) {
                    (storage * modals).showErrorModal(
                        texts = errorModalTexts(exception.message?:exception.cause?.message?:"Cannot Emit action 'ChangeRoundState'"),
                        device = storage * deviceData * mediaType.get,
                    )
                }
            }
        }
    }
    ) {
        val commandName: (String) -> Reader<Lang.Block, String> = {name -> Reader {lang:Lang.Block ->
            (lang["commands.${name.toLowerCasePreservingASCIIRules()}"])
        } }

        Text((texts * commandName(RoundState.fromString(round.state).commandName)).emit())
    }

     */
}
