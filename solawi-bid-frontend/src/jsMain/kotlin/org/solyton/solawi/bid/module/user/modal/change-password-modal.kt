package org.solyton.solawi.bid.module.user.modal


import androidx.compose.runtime.*
import org.evoleq.compose.Markup
import org.evoleq.compose.label.Label
import org.evoleq.compose.layout.Vertical
import org.evoleq.compose.modal.Modal
import org.evoleq.compose.modal.ModalData
import org.evoleq.compose.modal.ModalType
import org.evoleq.compose.modal.Modals
import org.evoleq.language.Lang
import org.evoleq.math.Source
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.storage.nextId
import org.evoleq.optics.storage.put
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.dom.*
import org.solyton.solawi.bid.application.data.device.DeviceType
import org.solyton.solawi.bid.application.ui.style.form.fieldDesktopStyle
import org.solyton.solawi.bid.application.ui.style.form.formDesktopStyle
import org.solyton.solawi.bid.application.ui.style.form.formLabelDesktopStyle
import org.solyton.solawi.bid.application.ui.style.form.textInputDesktopStyle
import org.solyton.solawi.bid.module.bid.component.styles.auctionModalStyles
import org.solyton.solawi.bid.module.user.service.PasswordCombinationCheck
import org.solyton.solawi.bid.module.user.service.onPasswordCombinationValid
import org.w3c.dom.HTMLElement

@Markup
@Suppress("FunctionName")
fun ChangePasswordModal(
    id: Int,
    texts: Lang.Block,
    modals: Storage<Modals<Int>>,
    device: Source<DeviceType>,
    storedPassword: String,
    setUserData: (password: String) -> Unit,
    cancel: ()->Unit,
    update: ()->Unit,
): @Composable ElementScope<HTMLElement>.()->Unit = Modal(
    id = id,
    modals = modals,
    device = device,

    onOk = {
        update()
    },
    onCancel = {
        cancel()
    },
    texts = texts,
    styles = auctionModalStyles(device),
) {
    var oldPassword by remember{ mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newPasswordRepeat by remember { mutableStateOf("") }
    var passwordCombinationCheck by remember { mutableStateOf<PasswordCombinationCheck>(PasswordCombinationCheck.Empty) }

    Vertical {
        Div(attrs = { style { formDesktopStyle() } }) {
            Div(attrs = { style { fieldDesktopStyle() } }) {
                Label("Altes Passwort", id = "oldPassword", labelStyle = formLabelDesktopStyle)
                PasswordInput(oldPassword) {
                    id("oldPassword")
                    style { textInputDesktopStyle() }
                    onInput {
                        oldPassword = it.value
                        passwordCombinationCheck = onPasswordCombinationValid(
                            value = newPassword,
                            storedPassword,
                            oldPassword,
                            newPassword,
                            newPasswordRepeat
                        ) {
                                pw : String -> setUserData(pw)
                        }
                    }
                }
            }

            Div(attrs = { style { fieldDesktopStyle() } }) {
                Label("Passwort", id = "password", labelStyle = formLabelDesktopStyle)
                PasswordInput(newPassword) {
                    id("password")
                    style { textInputDesktopStyle() }
                    onInput {
                        newPassword = it.value
                        passwordCombinationCheck = onPasswordCombinationValid(
                            value = newPassword,
                            storedPassword,
                            oldPassword,
                            newPassword,
                            newPasswordRepeat
                        ) {
                                pw : String -> setUserData(pw)
                        }
                    }
                }
            }
            Div(attrs = { style { fieldDesktopStyle() } }) {
                Label("Passwort wiederholen", id = "repeat-password", labelStyle = formLabelDesktopStyle)
                PasswordInput(newPasswordRepeat) {
                    id("repeat-password")
                    style { textInputDesktopStyle() }
                    onInput {
                        newPasswordRepeat = it.value
                        passwordCombinationCheck = onPasswordCombinationValid(
                            value = newPassword,
                            storedPassword,
                            oldPassword,
                            newPassword,
                            newPasswordRepeat
                        ) {
                            pw : String -> setUserData(pw)
                        }
                    }
                }
            }

            val  message: String? = when(passwordCombinationCheck) {
                PasswordCombinationCheck.Passed, PasswordCombinationCheck.Empty -> null
                PasswordCombinationCheck.WrongPassword -> "Falsches Passwort"
                PasswordCombinationCheck.RequirementsViolated -> "Passwortanforderungen verletzt"
                PasswordCombinationCheck.RepeatedPasswordMismatch -> "Pawwörter stimmen nicht überein"
                PasswordCombinationCheck.NewPasswordEqualsStoredPassword -> "Das neue Passwort muss sich vom alten unterscheiden"
            }

            if(message != null) {
                Div({ style { color(Color.crimson) } }){
                    Text(message)
                }
            }
        }
    }

}



@Markup
fun Storage<Modals<Int>>.showChangePasswordModal(
    texts: Lang.Block,
    device: Source<DeviceType>,
    storedPassword: String,
    setUserData: (password: String) -> Unit,
    cancel: ()->Unit,
    update: ()->Unit,
) = with(nextId()) {
    put(this to ModalData(
        ModalType.Dialog,
        ChangePasswordModal(
            this,
            texts,
            this@showChangePasswordModal,
            device,
            storedPassword,
            setUserData,
            cancel,
            update,
        )
    )
    )
}
