package fr.epsi.api.user;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.epsi.api.user.controller.FinancialDto;

@Entity
public class Financial {
	@Id
	@GeneratedValue
	private int id;

	private BigDecimal mensualNetIncome;
	private BigDecimal netImposable;
	
	private BigDecimal mensualCreditAmount;
	
	@OneToOne
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FinancialDto toFinancialDto() {
		FinancialDto financialDto = new FinancialDto();
		financialDto.setId(id);
		financialDto.setMensualCreditAmount(mensualCreditAmount);
		financialDto.setMensualNetIncome(mensualNetIncome);
		financialDto.setNetImposable(netImposable);
		return financialDto;
	}

}
