package com.example.gitproject.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.gitproject.R
import com.example.gitproject.databinding.RegisterScreenBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterScreen : Fragment() {

    private var _binding: RegisterScreenBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = RegisterScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.registerBtn.setOnClickListener {
            createUser()
        }
        binding.haveAcc.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createUser() {
        binding.loadingFr.visibility = View.VISIBLE
        val email = binding.TIETUserName.text.toString()
        val password = binding.TIEDPassword.text.toString()
        val confirmPassword = binding.TIEDConfirm.text.toString()

        if (password == confirmPassword)
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            requireContext(), "Authentication succeed.", Toast.LENGTH_SHORT
                        )
                        binding.loadingFr.visibility = View.GONE
                        findNavController().navigate(R.id.LoginScreen)
                    } else {
                        Log.w("TAG", "createUserWithEmail:failure", it.exception)
                        Toast.makeText(
                            requireContext(), "Authentication failed.", Toast.LENGTH_SHORT
                        )
                    }
                }
        else {
            binding.TILConfirm.error = "please confirm the password "
        }
    }
}