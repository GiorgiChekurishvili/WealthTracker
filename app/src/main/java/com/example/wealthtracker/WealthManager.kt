package com.example.wealthtracker

object WealthManager {

    private const val K = (6.0 + 11.0) / 12.0

    fun getK(): Double = K
    fun calculateFinalSavings(income: Double, expenses: Double): Double {
        return (income - expenses) * K
    }

    fun calculateNetBalance(income: Double, expenses: Double): Double {
        return income - expenses
    }

    fun calculateSavingsRate(income: Double, expenses: Double): Double {
        if (income == 0.0) return 0.0
        return ((income - expenses) / income) * 100.0
    }

    fun calculateExpenseRatio(income: Double, expenses: Double): Double {
        if (income == 0.0) return 0.0
        return (expenses / income) * 100.0
    }

    fun getFinancialStatus(income: Double, expenses: Double): FinancialStatus {
        val savings = calculateFinalSavings(income, expenses)
        return when {
            savings > income * 0.3 -> FinancialStatus.EXCELLENT
            savings > 0 -> FinancialStatus.GOOD
            savings == 0.0 -> FinancialStatus.NEUTRAL
            else -> FinancialStatus.DANGER
        }
    }

    enum class FinancialStatus {
        EXCELLENT, GOOD, NEUTRAL, DANGER
    }
}
