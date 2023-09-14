//PARA LISTA DE PROFESIONALES
const especialidad = document.getElementById("especialidad");
especialidad.addEventListener("change", filtrarTabla);

function filtrarTabla() {
  console.log("hola");
  var filtro = document.getElementById("especialidad").value.toLowerCase();
  var filas = document.querySelectorAll("#tabla-profesionales tbody tr");

  filas.forEach(function (fila) {
    var especialidad = fila
      .querySelector("td:nth-child(2)")
      .textContent.toLowerCase();

    if (filtro === "todas" || especialidad.includes(filtro)) {
      fila.style.display = "";
    } else {
      fila.style.display = "none";
    }
  });
}
/////////ORDENAR POR VALOR///////////
const consulta = document.getElementById("consulta");

consulta.addEventListener("click", ordenarPorValorConsulta);

let ordenAscendente = true;

function ordenarPorValorConsulta() { 
    console.log("hola");
  let tabla = document.getElementById("tabla-profesionales");
  let filas = Array.from(tabla.querySelectorAll("tbody tr"));

  filas.sort(function (a, b) {
    let valorA = parseFloat(
      a
        .querySelector(".tdHonorario")
        .textContent.replace("$ ", "")
        .replace(",", ".")
    );
    let valorB = parseFloat(
      b
        .querySelector(".tdHonorario")
        .textContent.replace("$ ", "")
        .replace(",", ".")
    );

    if (ordenAscendente) {
      return valorA - valorB;
    } else {
      return valorB - valorA;
    }
  });

  filas.forEach(function (fila) {
    fila.parentNode.removeChild(fila);
  });

  var tbody = tabla.querySelector("tbody");
  filas.forEach(function (fila) {
    tbody.appendChild(fila);
  });

  ordenAscendente = !ordenAscendente;
}

/////////ORDENAR POR CALIFICACIÓN///////////
const califica = document.getElementById("califica");
califica.addEventListener("click", ordenarPorCalificacion);
let ordenAscendenteCalificacion = true;

function ordenarPorCalificacion() {
  let tabla = document.getElementById("tabla-profesionales");
  let filas = Array.from(tabla.querySelectorAll("tbody tr"));

  filas.sort(function (a, b) {
    let calificacionA = obtenerCalificacion(a.querySelector(".tdCalificacion"));
    let calificacionB = obtenerCalificacion(b.querySelector(".tdCalificacion"));

    if (ordenAscendenteCalificacion) {
      return calificacionA - calificacionB;
    } else {
      return calificacionB - calificacionA;
    }
  });

  filas.forEach(function (fila) {
    fila.parentNode.removeChild(fila);
  });

  var tbody = tabla.querySelector("tbody");
  filas.forEach(function (fila) {
    tbody.appendChild(fila);
  });

  ordenAscendenteCalificacion = !ordenAscendenteCalificacion;
}

function obtenerCalificacion(elemento) {
  let calificacion = 0;
  let imgEstrellas = elemento.querySelectorAll("img.imgEstrella");

  if (imgEstrellas.length > 0) {
    let src = imgEstrellas[0].getAttribute("src");
    let regex = /(\d+)/g;
    let matches = regex.exec(src);

    if (matches !== null && matches.length > 0) {
      calificacion = parseFloat(matches[0]) / 10;
    }
  }

  return calificacion;
}

let tabla = document.querySelector(".tabla-profesionales");
let botonesValorar = tabla.querySelectorAll(".btnValorar");
let formValorar = tabla.querySelectorAll(".formValorar");
let misProfesionales = document.getElementById("misProfesionales");
let btnCalificar = tabla.querySelectorAll("btnCalificar");

botonesValorar.forEach(function (botonesValorar) {
  botonesValorar.addEventListener("click", function () {
    botonesValorar.style.display = "none";
    //      formValorar.style.display = "block";

    // Puedes acceder a la fila correspondiente utilizando métodos de navegación del DOM, por ejemplo:
    let fila = this.closest("tr"); // Obtiene el elemento <tr> más cercano al botón
    let form = fila.querySelector(".formValorar");
    form.classList.remove("formValorar");
  });
});

////ESTRELLAS///
const ratingElements = document.querySelectorAll(".rating");

// Iterar sobre cada elemento "rating"
ratingElements.forEach((ratingElement) => {
  const stars = ratingElement.querySelectorAll(".star");

  let selectedRating = 0; // Almacenar la calificación seleccionada

  // Añadir evento al pasar el mouse sobre las estrellas
  stars.forEach((star, index) => {
    star.addEventListener("mouseover", () => {
      // Cambiar el color de las estrellas
      stars.forEach((s, i) => {
        if (i <= index) {
          s.style.backgroundImage = "url('../../estrella-amarilla.png')";
        } else {
          s.style.backgroundImage = "url('../../estrella-gris.png')";
        }
      });
    });

    star.addEventListener("click", () => {
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
      const input = ratingElement.querySelector(".calificacion-input");
      if (input) {
        input.value = selectedRating;
        console.log("ok");
        console.log(input.value);
      }
    });
  });

  ratingElement.addEventListener("mouseleave", () => {
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
