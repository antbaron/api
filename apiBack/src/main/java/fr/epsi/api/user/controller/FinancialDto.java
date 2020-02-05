package fr.epsi.api.user.controller;

import java.math.BigDecimal;

import fr.epsi.api.user.Financial;

public class FinancialDto {

	private int id;
	private BigDecimal mensualNetIncome;
	private BigDecimal netImposable;
	
	private BigDecimal mensualCreditAmount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getMensualNetIncome() {
		return mensualNetIncome;
	}

	public void setMensualNetIncome(BigDecimal mensualNetIncome) {
		this.mensualNetIncome = mensualNetIncome;
	}

	public BigDecimal getNetImposable() {
		return netImposable;
	}

	public void setNetImposable(BigDecimal netImposable) {
		this.netImposable = netImposable;
	}

	public BigDecimal getMensualCreditAmount() {
		return mensualCreditAmount;
	}

	public void setMensualCreditAmount(BigDecimal mensualCreditAmount) {
		this.mensualCreditAmount = mensualCreditAmount;
	}

	public Financial toFinancial() {
		Financial financial = new Financial();
		financial.setId(id);
		financial.setMensualCreditAmount(mensualCreditAmount);
		financial.setMensualNetIncome(mensualNetIncome);
		financial.setNetImposable(netImposable);
		return financial;
	}
}
