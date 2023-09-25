/// Turno

let especialidad = document.getElementById("especialidad");
 let profesional = document.getElementById("profesional");
especialidad.addEventListener("change", mostrarProfesionales);

function mostrarProfesionales(){

 

  if(especialidad.value === "Especialidad"){
    profesional.disabled = true;
    console.log("hola");
  } else {
    profesional.disabled = false;
  }

} 
