package io.keepcoding.eh_ho.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.keepcoding.eh_ho.common.TextChangedWatcher
import io.keepcoding.eh_ho.databinding.FragmentSignInBinding
import io.keepcoding.eh_ho.utils.isValidPassword
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignInFragment : Fragment() {

    private val vm: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSignInBinding.inflate(inflater, container, false).apply {
        labelCreateAccount.setOnClickListener {
            vm.moveToSignUp()
        }
        vm.signInData.observe(viewLifecycleOwner) {
            inputUsername.apply {
                if (it.userName.length <= 5) {
                    inputUsername.error = "El campo es requerido"
                } else {
                    setText(it.userName)
                    setSelection(it.userName.length)
                }
            }
            inputPassword.apply {
                if (it.password.isValidPassword()) {
                    setText(it.password)
                    setSelection(it.password.length)
                } else {
                    inputPassword.error = "El campo es requerido"
                }
            }
        }
        vm.signInEnabled.observe(viewLifecycleOwner) {
            buttonLogin.isEnabled = it
        }
        inputUsername.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignInUserName))
        }
        inputPassword.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignInPassword))
        }
        buttonLogin.setOnClickListener { vm.signIn() }
    }.root



    companion object {
        fun newInstance(): SignInFragment = SignInFragment()
    }
}
