const formulario = document.getElementById("formulario");
let obraSocial = document.getElementById("obraSocial");

obraSocial.addEventListener("change", abrirModal);


function abrirModal() {

    let value = obraSocial.options[obraSocial.selectedIndex].value;

    if (value == "Cargar otra...") {
     
        let datosFormulario = new FormData(formulario);

        fetch('/paciente/guardarDatosFormulario', {
            method: 'POST',
            body: datosFormulario
        })
                .then(function (respuesta) {
                    if (respuesta.ok) {
                        // En caso de éxito, mostrar mensaje de confirmación
                        console.log("Formulario enviado con éxito");
                    } else {
                        // En caso de error, mostrar mensaje de error
                        alert("Error al enviar el formulario");
                    }
                })
                .catch(function (error) {
                    // En caso de error, mostrar mensaje de error
                    alert("Error al enviar el formulario: " + error.message);
                });

        var ventanaModal = document.getElementById("myModal");
        ventanaModal.style.display = "block";
 }
}
 
