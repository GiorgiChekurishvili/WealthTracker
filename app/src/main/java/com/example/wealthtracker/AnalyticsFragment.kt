package com.example.wealthtracker

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.wealthtracker.databinding.FragmentAnalyticsBinding

class AnalyticsFragment : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showEmptyState()

        setFragmentResultListener("wealth_data") { _, bundle ->
            val income = bundle.getDouble("income", 0.0)
            val expenses = bundle.getDouble("expenses", 0.0)
            updateAnalytics(income, expenses)
        }
    }

    private fun showEmptyState() {
        binding.gcLiGroupData.visibility = View.GONE
        binding.gcLiTvEmpty.visibility = View.VISIBLE
    }

    private fun updateAnalytics(income: Double, expenses: Double) {
        binding.gcLiTvEmpty.visibility = View.GONE
        binding.gcLiGroupData.visibility = View.VISIBLE

        val finalSavings = WealthManager.calculateFinalSavings(income, expenses)
        val netBalance = WealthManager.calculateNetBalance(income, expenses)
        val savingsRate = WealthManager.calculateSavingsRate(income, expenses)
        val expenseRatio = WealthManager.calculateExpenseRatio(income, expenses)
        val status = WealthManager.getFinancialStatus(income, expenses)
        val k = WealthManager.getK()

        binding.gcLiTvIncome.text = "₾%.2f".format(income)
        binding.gcLiTvExpenses.text = "₾%.2f".format(expenses)
        binding.gcLiTvNetBalance.text = "₾%.2f".format(netBalance)
        binding.gcLiTvFinalSavings.text = "₾%.2f".format(finalSavings)
        binding.gcLiTvKCoefficient.text = "K = %.4f".format(k)
        binding.gcLiTvSavingsRate.text = "%.1f%%".format(savingsRate)
        binding.gcLiTvExpenseRatio.text = "%.1f%%".format(expenseRatio)

        val progress = expenseRatio.coerceIn(0.0, 100.0).toInt()
        binding.gcLiProgressExpenses.progress = progress

        val (statusText, statusColor) = when (status) {
            WealthManager.FinancialStatus.EXCELLENT -> "შესანიშნავი ფინანსური მდგომარეობა" to "#00E676"
            WealthManager.FinancialStatus.GOOD -> "კარგი ფინანსური მდგომარეობა" to "#69F0AE"
            WealthManager.FinancialStatus.NEUTRAL -> "ნულოვანი ბალანსი" to "#FFD740"
            WealthManager.FinancialStatus.DANGER -> "ხარჯები შემოსავალს აჭარბებს" to "#FF5252"
        }
        binding.gcLiTvStatus.text = statusText
        binding.gcLiTvStatus.setTextColor(Color.parseColor(statusColor))

        binding.gcLiGroupData.alpha = 0f
        binding.gcLiGroupData.animate().alpha(1f).setDuration(500).start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
