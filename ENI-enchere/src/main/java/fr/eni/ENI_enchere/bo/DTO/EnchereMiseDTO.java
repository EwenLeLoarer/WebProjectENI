package fr.eni.ENI_enchere.bo.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class EnchereMiseDTO {
	@NotNull(message="la mise ne peut pas etre null")
	@Min(value = 1, message = "La mise doit être supérieure à 0")
	@Getter
	@Setter
	private Integer mise;
}
