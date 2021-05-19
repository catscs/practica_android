package io.keepcoding.eh_ho.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import io.keepcoding.eh_ho.common.TextChangedWatcher
import io.keepcoding.eh_ho.databinding.FragmentSignUpBinding
import io.keepcoding.eh_ho.utils.isValidEmail
import io.keepcoding.eh_ho.utils.isValidPassword
import io.keepcoding.eh_ho.utils.showError

class SignUpFragment : Fragment() {

    private val vm: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSignUpBinding.inflate(inflater, container, false).apply {
        labelSignIn.setOnClickListener {
            vm.moveToSignIn()
        }
        vm.signUpData.observe(viewLifecycleOwner) {
            inputEmail.apply {
                if (it.email.isValidEmail()) {
                    setText(it.email)
                    setSelection(it.email.length)
                } else {
                    this@SignUpFragment.view?.showError("Email no valid")
                }
            }
            inputUsername.apply {
                if (it.userName.length <= 5) {
                    this@SignUpFragment.view?.showError("Username no valid")
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
                    this@SignUpFragment.view?.showError("Password no valid")
                }
            }
            inputConfirmPassword.apply {
                if (it.confirmPassword.isValidPassword()) {
                    setText(it.confirmPassword)
                    setSelection(it.confirmPassword.length)
                } else {
                    this@SignUpFragment.view?.showError("Confirm password no valid")
                }
            }
        }
        vm.signUpEnabled.observe(viewLifecycleOwner) {
            buttonSignUp.isEnabled = it
        }
        inputEmail.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignUpEmail))
        }
        inputUsername.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignUpUserName))
        }
        inputPassword.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignUpPassword))
        }
        inputConfirmPassword.apply {
            addTextChangedListener(TextChangedWatcher(vm::onNewSignUpConfirmPassword))
        }
        buttonSignUp.setOnClickListener {
            println("JcLog: clicking signup button")
            vm.signUp()
        }
    }.root

    companion object {
        fun newInstance(): SignUpFragment = SignUpFragment()
    }
}
