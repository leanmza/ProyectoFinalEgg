// Este script maneja la carga de datos a los formularios de crear y editar usuarios

document.addEventListener("DOMContentLoaded", function () { //el script se ejecuta luego de que se haya cargado la página

const fileInput = document.getElementById("foto");
const imagePreview = document.getElementById("imagePreview");

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
