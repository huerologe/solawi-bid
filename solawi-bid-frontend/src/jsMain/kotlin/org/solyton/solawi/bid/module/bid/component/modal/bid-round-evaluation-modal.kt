package org.solyton.solawi.bid.module.bid.component.modal

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.modal.Modal
import org.evoleq.compose.modal.ModalData
import org.evoleq.compose.modal.ModalType
import org.evoleq.compose.modal.Modals
import org.evoleq.language.Lang
import org.evoleq.optics.lens.Lens
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.storage.nextId
import org.evoleq.optics.storage.put
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.module.bid.component.BidRoundEvaluation
import org.solyton.solawi.bid.module.bid.data.Round
import org.w3c.dom.HTMLElement

@Markup
@Suppress("FunctionName")
fun BidRoundEvaluationModal(
    id: Int,
    texts: Lang.Block,
    modals: Storage<Modals<Int>>,
    storage: Storage<Application>,
    round: Lens<Application, Round>,
    cancel: ()->Unit,
    update: ()->Unit
): @Composable ElementScope<HTMLElement>.()->Unit = Modal(
    id,
    modals,
    onOk = {
        update()
    },
    onCancel = {
        cancel()
    },
    texts = texts
) {
    // todo:i18n
    Text("Evaluation of Bid Round")
    BidRoundEvaluation(
        storage = storage,
        round = round
    )
}

@Markup
fun Storage<Modals<Int>>.showBidRoundEvaluationModal(
    storage: Storage<Application>,
    round: Lens<Application, Round>,
    texts: Lang.Block,

    cancel: ()->Unit,
    update: ()->Unit
) = with(nextId()) {
    put(this to ModalData(
        ModalType.Dialog,
        BidRoundEvaluationModal(
            this,
            texts,
            this@showBidRoundEvaluationModal,
            storage,
            round,
            cancel,
            update
        )
    ) )
}
