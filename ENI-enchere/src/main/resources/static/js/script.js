document.addEventListener("DOMContentLoaded", function() {
	const radioAchats = document.querySelector("input[name='filterType'][value='achats']");
	const radioVentes = document.querySelector("input[name='filterType'][value='ventes']");
	const selectAchats = document.getElementById("achats-options");
	const selectVentes = document.getElementById("ventes-options");

	function updateFilters() {
		if (radioAchats && radioAchats.checked) {
			selectAchats.disabled = false;
			selectVentes.disabled = true;
		} else if (radioVentes && radioVentes.checked) {
			selectVentes.disabled = false;
			selectAchats.disabled = true;
		}
	}

	if (radioAchats) {
		radioAchats.addEventListener("change", updateFilters);
	}
	if (radioVentes) {
		radioVentes.addEventListener("change", updateFilters);
	}
	updateFilters();
});

document.addEventListener("DOMContentLoaded", function() {
	const passwordField = document.getElementById("idMotDePasse");
	const confirmPasswordField = document.getElementById("idValidation");
	const passwordError = document.getElementById("password__error");

	const form = document.querySelector(".register__form");



	function validatePassword() {
		const password = passwordField.value;
		const minLength = /.{8,}/; // Au moins 8 caractères
		const hasUppercase = /[A-Z]/; // Au moins une majuscule
		const hasLowercase = /[a-z]/; // Au moins une minuscule
		const hasDigit = /\d/; // Au moins un chiffre
		const hasSpecialChar = /[@$!%*?&]/; // Au moins un caractère spécial

		let errors = [];

		if (!minLength.test(password)) errors.push("&#10060; 8 caractères");
		if (!hasUppercase.test(password)) errors.push("&#10060; 1 majuscule");
		if (!hasLowercase.test(password)) errors.push("&#10060; 1 minuscule");
		if (!hasDigit.test(password)) errors.push("&#10060; 1 chiffre");
		if (!hasSpecialChar.test(password)) errors.push("&#10060; 1 caractère spécial");

		if (errors.length > 0) {
			passwordError.innerHTML = errors.join("<br>"); // Affiche chaque erreur sur une nouvelle ligne
			return false;
		} else {
			passwordError.textContent = "";
			return true;
		}
	}

	function validateConfirmPassword() {
		if (passwordField.value !== confirmPasswordField.value) {
			passwordError.innerHTML = "&#10060; Les mots de passe sont différents";
			return false;
		} else {
			passwordError.textContent = "";
			return true;
		}
	}

	passwordField.addEventListener("input", validatePassword);
	confirmPasswordField.addEventListener("input", validateConfirmPassword);

	form.addEventListener("submit", function(event) {
		if (!validatePassword() || !validateConfirmPassword()) {
			event.preventDefault(); // Bloque l'envoi du formulaire si les validations échouent
		}
	});
});
