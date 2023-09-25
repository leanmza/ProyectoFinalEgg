let prevBtn = document.getElementById("prevBtn");
let nextBtn = document.getElementById("nextBtn");

const fileInput = document.getElementById("foto");
const imagePreview = document.getElementById("imagePreview");

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

// Agrega un evento de cambio al elemento de entrada de archivo.
fileInput.addEventListener("change", function () {
  const file = this.files[0]; // Obtiene el archivo seleccionado (solo el primero si se seleccionan múltiples).

  if (file) {
    // Crea un objeto URL para la imagen seleccionada.
    const imageURL = URL.createObjectURL(file);

    // Establece la fuente de la imagen de vista previa con la URL generada.
    imagePreview.src = imageURL;

    // Muestra la imagen de vista previa.
    imagePreview.style.display = "block";
  } else {
    // Si no se selecciona ningún archivo, oculta la imagen de vista previa.
    imagePreview.style.display = "none";
  }
});

// Cargardor de Obras Sociales nuevas

const formulario = document.getElementById("formulario");
const formObraSocial = document.getElementById("formObraSocial");
let obraSocial = document.getElementById("obraSocial");
const btnSubmitOS = document.getElementById("btnSubmitOS");
const btnCancelar = document.getElementById("btnCancelar");

obraSocial.addEventListener("change", mostrarCargardorOS);

btnSubmitOS.addEventListener("click", guardarObraSocial);

btnCancelar.addEventListener("click", function () {
  formObraSocial.style.display = "none";
});

function mostrarCargardorOS() {
  let value = obraSocial.options[obraSocial.selectedIndex].value;

  if (value == "Cargar otra...") {
    formObraSocial.style.display = "block";
  } ;

}

function guardarObraSocial() {
  // Obtén el valor del campo 'nombreObraSocial' del formulario
  let nombreObraSocial = document.getElementById("nombreObraSocial").value;

  // Crea un objeto FormData
  let nuevaObraSocial = new FormData();

  // Agrega el valor del campo 'nombreObraSocial' al FormData
  nuevaObraSocial.append("nombreObraSocial", nombreObraSocial);

   // Realiza una solicitud fetch para enviar los datos del formulario al servidor
   fetch("/obraSocial/registroObraSocial", {
    method: "POST",
    body: nuevaObraSocial,
  })
    .then((response) => response.json()) // Puedes agregar esto si esperas una respuesta JSON del servidor
    .then((data) => {
      // Realiza acciones con la respuesta del servidor si es necesario
      console.log(data); // Puedes mostrar la respuesta en la consola
    })
    .catch((error) => {
      console.error("Error al enviar los datos al servidor:", error);
    });

  // Modifica el elemento HTML 'obraSocial' para agregar la nueva opción
  let obraSocialSelect = document.getElementById("obraSocial");

  // Crea un nuevo elemento de opción y establece su valor y texto
  let nuevaOpcion = document.createElement("option");
  nuevaOpcion.value = nombreObraSocial;
  nuevaOpcion.text = nombreObraSocial;
  nuevaOpcion.selected = true;

  // Agrega la nueva opción al select
  obraSocialSelect.appendChild(nuevaOpcion);

  formObraSocial.style.display = "none";
}

document.addEventListener("DOMContentLoaded", function () {
  const password = document.getElementById("password");
  const longCaracteres = document.getElementById("longCaracteres"); 
  const mayuscula = document.getElementById("mayuscula"); 
  const minuscula = document.getElementById("minuscula"); 
  const numero = document.getElementById("numero"); 

  password.addEventListener("input", function () {
    const passwordValue = password.value;
    const hasUpperCase = /[A-Z]/.test(passwordValue); // Devuelve un booleano si tiene mayuscula
    const hasLowerCase = /[a-z]/.test(passwordValue);// Devuelve un booleano si tiene minuscula
    const hasNumber = /\d/.test(passwordValue); // Devuelve un booleano si tiene numero

    if (passwordValue.length >= 8) { // valida longitud de 8 caracteres
      longCaracteres.style.color = "green";
    } else {
      longCaracteres.style.color = "black"; // Cambiar de nuevo a negro si la longitud es menor de 8
    }

    if (hasUpperCase){
      mayuscula.style.color = "green";
    } else {
      mayuscula.style.color = "black";
    }

    if (hasLowerCase){
      minuscula.style.color = "green";
      
    }else {
      minuscula.style.color = "black";
    }

    if (hasNumber){
      numero.style.color = "green";
    } else {
      numero.style.color = "black";
    }
  });
});


