document.addEventListener("DOMContentLoaded", function () {
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
