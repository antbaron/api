package fr.epsi.api.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.epsi.api.user.controller.UserDto;

@Entity
public class User {
	@Id
	private String pseudo;

	private String password;
	
	@OneToOne(mappedBy = "user")
	private Financial financial;

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Financial getFinancial() {
		return financial;
	}

	public void setFinancial(Financial financial) {
		this.financial = financial;
	}
	
	public UserDto toUserDto() {
		UserDto userDto = new UserDto();
		userDto.setPseudo(pseudo);
		if(this.financial != null) {
			userDto.setFinancialDto(this.financial.toFinancialDto());		
		}
		return userDto;
	}



}
