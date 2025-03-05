package fr.eni.ENI_enchere.bo.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class EnchereMiseDTO {
	@NotNull(message="la mise ne peut pas etre null")
	@Min(value = 1, message = "La mise doit être supérieure à 0")
	private Integer mise;

	public Integer getMise() {
		return mise;
	}

	public void setMise(Integer mise) {
		this.mise = mise;
	}
	
	
}
