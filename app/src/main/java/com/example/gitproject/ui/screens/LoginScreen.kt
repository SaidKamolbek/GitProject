package com.example.gitproject.ui.screens

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.gitproject.R
import com.example.gitproject.databinding.LoginScreenBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginScreen : Fragment() {

    private var _binding: LoginScreenBinding? = null


    private val binding get() = _binding!!

    @Inject
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = LoginScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            createUser()
        }
        binding.notRegistered.setOnClickListener {
            findNavController().navigate(R.id.RegisterScreen)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createUser() {
        val email = binding.TIETUserName.text.toString()
        val password = binding.TIEDPassword.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.TILUsername.error = "email cannot be empty"
            binding.TILUsername.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            binding.TILPassword.error = "password cannot be empty"
            binding.TILPassword.requestFocus()
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "user logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.contactListScreen)
                    } else {
                        Toast.makeText(requireContext(), "login error", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}