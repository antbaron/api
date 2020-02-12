package fr.epsi.api.user.controller;

import fr.epsi.api.user.User;

public class UserDto {

	private String pseudo;
	
	private FinancialDto financialDto;

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public FinancialDto getFinancialDto() {
		return financialDto;
	}

	public void setFinancialDto(FinancialDto financialDto) {
		this.financialDto = financialDto;
	}
	
	public User toUser() {
		User user = new User();
		user.setPseudo(this.pseudo);
		if(financialDto != null) {
			user.setFinancial(financialDto.toFinancial());
		}
		return user;
	}
}
