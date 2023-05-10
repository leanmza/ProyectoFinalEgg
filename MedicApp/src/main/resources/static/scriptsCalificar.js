let tabla = document.querySelector(".misProfesionales");
let botonesValorar = tabla.querySelectorAll(".btnValorar");
let formValorar = tabla.querySelectorAll(".formValorar");
let misProfesionales = document.getElementById("misProfesionales");
let btnCalificar = tabla.querySelectorAll("btnCalificar");




botonesValorar.forEach(function(botonesValorar) {
  botonesValorar.addEventListener("click", function() {
      botonesValorar.style.display="none";
//      formValorar.style.display = "block";


    // Puedes acceder a la fila correspondiente utilizando métodos de navegación del DOM, por ejemplo:
    let fila = this.closest("tr"); // Obtiene el elemento <tr> más cercano al botón
   let form = fila.querySelector(".formValorar");
    form.classList.remove("formValorar");
  });
});

