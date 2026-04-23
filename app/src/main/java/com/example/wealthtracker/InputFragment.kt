package com.example.wealthtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.wealthtracker.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gcLiBtnCalculate.setOnClickListener {
            if (validateInputs()) {
                sendData()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val incomeText = binding.gcLiEtIncome.text.toString().trim()
        val expensesText = binding.gcLiEtExpenses.text.toString().trim()
        var isValid = true

        if (incomeText.isEmpty()) {
            binding.gcLiEtIncome.error = "შეიყვანეთ შემოსავალი"
            isValid = false
        } else if (incomeText.toDoubleOrNull() == null || incomeText.toDouble() < 0) {
            binding.gcLiEtIncome.error = "შეიყვანეთ სწორი თანხა"
            isValid = false
        }

        if (expensesText.isEmpty()) {
            binding.gcLiEtExpenses.error = "შეიყვანეთ ხარჯები"
            isValid = false
        } else if (expensesText.toDoubleOrNull() == null || expensesText.toDouble() < 0) {
            binding.gcLiEtExpenses.error = "შეიყვანეთ სწორი თანხა"
            isValid = false
        }

        return isValid
    }

    private fun sendData() {
        val income = binding.gcLiEtIncome.text.toString().trim().toDouble()
        val expenses = binding.gcLiEtExpenses.text.toString().trim().toDouble()

        val result = bundleOf(
            "income" to income,
            "expenses" to expenses
        )
        setFragmentResult("wealth_data", result)

        showSuccessState(income, expenses)
    }

    private fun showSuccessState(income: Double, expenses: Double) {
        val savings = WealthManager.calculateFinalSavings(income, expenses)
        val status = WealthManager.getFinancialStatus(income, expenses)

        val statusText = when (status) {
            WealthManager.FinancialStatus.EXCELLENT -> "შესანიშნავი!"
            WealthManager.FinancialStatus.GOOD -> "კარგია!"
            WealthManager.FinancialStatus.NEUTRAL -> "ნული"
            WealthManager.FinancialStatus.DANGER -> "საშიში!"
        }

        binding.gcLiTvResult.visibility = View.VISIBLE
        binding.gcLiTvResult.text = "შენახვა: ₾%.2f\n%s\nმონაცემები გაიგზავნა ანალიტიკაში".format(
            savings, statusText
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}