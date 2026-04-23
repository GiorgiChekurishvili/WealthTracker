package com.example.wealthtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wealthtracker.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProfile()
    }

    private fun setupProfile() {
        binding.gcLiTvName.text = "გიორგი ჩეკურიშვილი"
        binding.gcLiTvBirthday.text = "დაბადების რიცხვი: 12"
        binding.gcLiTvKFormula.text = "K = (სახელი + გვარი) / რიცხვი\nK = (6 + 11) / 12 = %.4f".format(WealthManager.getK())
        binding.gcLiTvNamingConvention.text = "ID prefix: gc_li_"
        binding.gcLiTvOrientation.text = "ViewPager2: Vertical\n(გვარი იწყება თანხმოვანზე: \"ჩ\")"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}