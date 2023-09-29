//Este script maneja las vistas y pasos de los formularios Wizard

let prevBtn = document.getElementById("prevBtn");
let nextBtn = document.getElementById("nextBtn");

let pestanaActual = 0; // La pestaña actual se setea para ser la primer pestaña (0)

mostrarPestana(pestanaActual); // Se muestra la pestaña actual

prevBtn.addEventListener("click", function () {
  nextPrev(-1); //CUANDO SE HACE CLICK EN EL BOTÓN "ATRÁS" LLAMA LA FUNCIÓN nextPrev Y LE PASA EL VALOR -1
});

nextBtn.addEventListener("click", function () {
  nextPrev(1); //CUANDO SE HACE CLICK EN EL BOTÓN "SIGUIENTE" LLAMA LA FUNCIÓN nextPrev Y LE PASA EL VALOR 1
});

function mostrarPestana(n) {
  // n es el indice

  // Esta función va mostrar una pestaña específica del formulario
  let pestana = document.getElementsByClassName("pestana"); // x
  pestana[n].style.display = "block"; // pone visible la pestaña del índice
  // ... and fix the Previous/Next buttons:
  if (n == 0) {
    // si el índice es cero
    document.getElementById("prevBtn").style.display = "none"; //no se muestra el botón "atrás"
  } else {
    document.getElementById("prevBtn").style.display = "inline"; // se mustra el botón
  }
  if (n == pestana.length - 1) {
    // si el índice es igual al largo del conjunto de pestañas menos 1
    document.getElementById("nextBtn").innerHTML = "Registrarme"; //Muestra el botón enviar
  } else {
    document.getElementById("nextBtn").innerHTML = "Siguiente"; //Muestra el botón "siguiente"
  }

  fixStepIndicator(n); //función que muestra el correcto indicador de pasos
}

function nextPrev(n) {
  // Esta función va a establecer qué pestaña mostrar
  let pestana = document.getElementsByClassName("pestana");
  // Oculta la pestaña actual:
  pestana[pestanaActual].style.display = "none";
  // Incrementa o reduce la pestaña actual en 1
  pestanaActual = pestanaActual + n;
  // si se ha llegado al final del formulario
  if (pestanaActual >= pestana.length) {
    // el formulario es enviado
    document.getElementById("formulario").submit();
    return false;
  }
  // Por lo contrario, se muestra la pestaña actual
  mostrarPestana(pestanaActual);
}


function validarFormulario() {
  // Maneja la validación de los campos del formulario
  let pestana = document.getElementsByClassName("pestana"); //pestana x
  let input = pestana[pestanaActual].getElementsByTagName("input"); //input y
  let valid = true;

   // Itera para verificar cada campo de los input en la pestaña actual
   for (let i = 0; i < input.length; i++) {
    if (input[i].type === "file") {
      // Si es de tipo "file", no lo validamos y pasamos al siguiente campo
      continue;
    }
    // Si hay un campo vacío y no es el campo 'obraSocial' con valor "Cargar otra..."
    if (input[i].value == "" && !(input[i].id == "obraSocial" && obraSocial.value != "Cargar otra...")) {
      // Agrega una clase "invalida" al campo
      input[i].className += " invalida";
      // y setea el estado de valid a falso
      valid = false;
    }
  }
  // Si valid es true, marca el paso como terminado y válido
  if (valid) {
    document.getElementsByClassName("step")[pestanaActual].className +=
      " finish";
  }
  return valid; // retorna el estado de valid
}

function fixStepIndicator(n) {
  // Esta funcion elimina la clase "active" en todos los pasos
  let paso = document.getElementsByClassName("step");
  for (let i = 0; i < paso.length; i++) {
    paso[i].className = paso[i].className.replace(" active", "");
  }
  // Y agrega la clase "active" al paso actual
  paso[n].className += " active";
}






