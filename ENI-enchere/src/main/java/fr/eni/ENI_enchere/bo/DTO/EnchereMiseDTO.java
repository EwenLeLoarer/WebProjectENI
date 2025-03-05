package fr.eni.ENI_enchere.bo.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class EnchereMiseDTO {
	@NotNull(message="la mise ne peut pas etre null")
	@Getter
	@Setter
	private Integer mise;
}
