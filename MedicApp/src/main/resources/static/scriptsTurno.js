// /// Turno
// document.addEventListener("DOMContentLoaded", function () {

//   const especialidadSelect = document.getElementById("especialidad");
//   const profesionalSelect = document.getElementById("profesional");

//   const opcionesProfesionales = Array.from(profesionalSelect.options);

//   especialidadSelect.addEventListener("change", function () {
//     const especialidadSeleccionada = especialidadSelect.value;
//     console.log(especialidadSeleccionada);

//     // Oculta todas las opciones en el segundo select
//     opcionesProfesionales.forEach(function (opcion) {
//       opcion.style.display = "none";
//     });

//     // Muestra solo las opciones que coinciden con la especialidad seleccionada
//     opcionesProfesionales.forEach(function (opcion) {
//       console.log("data-id " + opcion.getAttribute("data-id"));
//       if (opcion.getAttribute("data-especialidad") === especialidadSeleccionada || especialidadSeleccionada === "todas") {
//         opcion.style.display = "";
//       }
//     });
//   });

//   profesionalSelect.addEventListener("change", function(){
//     const profesionalSeleccionado = profesionalSelect.value;
//     console.log(profesionalSeleccionado);
//     console.log("id " + profesionalSeleccionado.getAttribute("data-id"));
//   })
  
//   profesionalSelect.addEventListener("change", function(){
//     const optionSeleccionado = profesionalSelect.options[profesionalSelect.selectedIndex];
//     const profesionalSeleccionado = optionSeleccionado.value;
//     const idProfesional = optionSeleccionado.getAttribute("data-id");
    
//     console.log("Profesional seleccionado: " + profesionalSeleccionado);
//     console.log("ID del Profesional: " + idProfesional);
// });

// });


// // function mostrarProfesionales() {
// //   if (especialidad.value === "Especialidad") {
// //     profesional.disabled = true;
// //     console.log("hola");
// //   } else {
// //     profesional.disabled = false;
// //   }
// // }
