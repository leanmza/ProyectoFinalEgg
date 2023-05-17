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


////ESTRELLAS///
const ratingElements = document.querySelectorAll('.rating');

// Iterar sobre cada elemento "rating"
    ratingElements.forEach((ratingElement) => {
    const stars = ratingElement.querySelectorAll('.star');
   
    let selectedRating = 0; // Almacenar la calificación seleccionada

    // Añadir evento al pasar el mouse sobre las estrellas
    stars.forEach((star, index) => {
        star.addEventListener('mouseover', () => {
            // Cambiar el color de las estrellas
            stars.forEach((s, i) => {
                if (i <= index) {
                    s.style.backgroundImage = "url('../../estrella-amarilla.png')";
                } else {
                    s.style.backgroundImage = "url('../../estrella-gris.png')";
                }
            });
        });

        star.addEventListener('click', () => {
        selectedRating = index + 1; // Actualizar la calificación seleccionada
      
    
          
            console.log(selectedRating);

            // Cambiar el color de las estrellas permanentemente
            stars.forEach((s, i) => {
                   if (i <= index) {
                    s.style.backgroundImage = "url('../../estrella-amarilla.png')";
                } else {
                    s.style.backgroundImage = "url('../../estrella-gris.png')";
                }
            });
            
     // Asignar la calificación a los elementos input relacionados
     const input = ratingElement.querySelector('.calificacion-input');
     if (input) {
       input.value = selectedRating;
       console.log("ok");
       console.log(input.value);
     }
   });
 });

    ratingElement.addEventListener('mouseleave', () => {
        // Restaurar el color de las estrellas según la calificación seleccionada
        stars.forEach((s, i) => {
            if (i < selectedRating) {
                  s.style.backgroundImage = "url('../../estrella-amarilla.png')";
                } else {
                    s.style.backgroundImage = "url('../../estrella-gris.png')";
            }
        });
    });

});