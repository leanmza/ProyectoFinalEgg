let btnMesAnterior = document.getElementById("btn-mes-anterior");
let btnMesSiguiente = document.getElementById("btn-mes-siguiente");

window.addEventListener("load", calendario);

/////// CONTADOR DE CARACTERES ///////
const textarea = document.getElementById("motivo");
const contadorCaracteres = document.getElementById("contador");
const maxLength = 140;

textarea.addEventListener("input", function () {
  const charLength = this.value.length;

  if (charLength > maxLength) {
    this.value = this.value.slice(0, maxLength);
  }

  contadorCaracteres.innerText = `Caracteres: ${this.value.length}/${maxLength}`;

  if (charLength > maxLength) {
    textarea.classList.add("error");
  } else {
    textarea.classList.remove("error");
  }
});

/////// GENERADOR DE CALENDARIO ///////

//Tiene 2 funciones en cascada para asegurame que se ejecuten en el orden en que estan escritas

function calendario() {
  //traigo el id del profesional para buscar los días que atiende
  const idProfesional = document.getElementById("id").value;

  // URL del endpoint GET
  const url = `http://localhost:8080/profesional/dias/${idProfesional}`;

  let diasProfe = [];

  // Realiza la solicitud GET utilizando fetch
  fetch(url)
    .then((response) => {
      if (!response.ok) {
        throw new Error(`La solicitud falló con estado ${response.status}`);
      }
      return response.json(); // Convierte la respuesta en un objeto JSON
    })
    .then((respuestaDias) => {
      // Aquí puedes trabajar con el objeto profesional recibido
      console.log("Objeto respuestaDias:", respuestaDias);
      console.log("length " + respuestaDias.length);
      diasProfe = respuestaDias;

      procesarDias(diasProfe);
    })
    .catch((error) => {
      // Manejo de errores
      console.error("Error al obtener el objeto profesional:", error);
    });

  let diasAtencion = [];

  // Función para procesar los días una vez que se hayan cargado

  function procesarDias(diasProfe) {
    // Creo matriz para guardar los nombres de los días
    const diasSemana = [
      "lunes",
      "martes",
      "miercoles",
      "jueves",
      "viernes",
      "sabado",
      "domingo",
    ];

    for (let i = 0; i < diasProfe.length; i++) {
      for (let j = 1; j <= diasSemana.length; j++) {
        if (diasSemana[j] == diasProfe[i]) {
          diasAtencion.push(j);
        }
      }
    }

    // Obtener la fecha actual
    let fechaActual = new Date();

    // Obtener el número del mes actual (0-11)
    let mesActual = fechaActual.getMonth();

    // Obtener el año
    let anioActual = fechaActual.getFullYear();

    //Obtener hoy
    let hoy = fechaActual.getDate();
    
    btnMesSiguiente.addEventListener("click", function() {
      // Incrementar el mes solo cuando se hace clic en btnMesSiguiente
      event.preventDefault();
      if (mesActual < 11){
      mesActual = (mesActual + 1);     
      } else {
        mesActual = 0;
        anioActual = anioActual + 1;
      }
      // Actualizar el calendario con el nuevo mes
      generarCalendario(fechaActual, diasAtencion, mesActual, anioActual, hoy); // Pasar hoy=1
    });
  
    btnMesAnterior.addEventListener("click", function() {
      // Incrementar el mes solo cuando se hace clic en btnMesSiguiente
      event.preventDefault();
      if (mesActual > 0){
      mesActual = (mesActual - 1);     
      } else {
        mesActual = 11;
        anioActual = anioActual -1;
      }
      // Actualizar el calendario con el nuevo mes
      generarCalendario(fechaActual, diasAtencion, mesActual, anioActual, hoy); // Pasar hoy=1
    });


    generarCalendario(fechaActual, diasAtencion, mesActual, anioActual, hoy);
  }
      
    function generarCalendario(fechaActual, diasAtencion, mesActual, anioActual, hoy) {
          // Obtener el elemento div del HTML
    let mes = document.getElementById("mes");

    let divCalendario = document.getElementById("calendario");
    console.log("anioActual " + anioActual);

    console.log("año.getFullYear " + fechaActual.getFullYear());

    if (mesActual <= fechaActual.getMonth() && anioActual === fechaActual.getFullYear()){
      btnMesAnterior.disabled = true;
    } else {
      btnMesAnterior.disabled = false;
    }

    if (mesActual > fechaActual.getMonth()){
      hoy = 1;
    }
    console.log("mesActual " + mesActual);
    console.log("fechaActual.getMonth() " + fechaActual.getMonth());

    // Crear una matriz para almacenar los nombres de los meses
    const nombresMeses = [
      "Enero",
      "Febrero",
      "Marzo",
      "Abril",
      "Mayo",
      "Junio",
      "Julio",
      "Agosto",
      "Septiembre",
      "Octubre",
      "Noviembre",
      "Diciembre",
    ];

    // Crear una cadena para almacenar el HTML del calendario
    let calendarioHTML = "";

    // Agregar la tabla del calendario al HTML

    calendarioHTML += "<table class='col-12 tablaCalendario'>";
    calendarioHTML += "<thead class='headCalendario'>";

    // Agregar la fila de los días de la semana al HTML

    calendarioHTML += "<tr>";
    calendarioHTML += "<th class='celdaDias' scope='col'>Lu</th>";
    calendarioHTML += "<th class='celdaDias' scope='col'>Ma</th>";
    calendarioHTML += "<th class='celdaDias' scope='col'>Mi</th>";
    calendarioHTML += "<th class='celdaDias' scope='col'>Ju</th>";
    calendarioHTML += "<th class='celdaDias' scope='col'>Vi</th>";
    calendarioHTML += "<th class='celdaDias' scope='col'>Sa</th>";
    calendarioHTML += "<th class='celdaDias' scope='col'>Do</th>";
    calendarioHTML += "</tr>";
    calendarioHTML += "</thead>";

    // Crear una fecha para el primer día del mes actual
    let primerDia = new Date(fechaActual.getFullYear(), mesActual, 1).getDay();
    
    if (primerDia === 0) {
      primerDia = 7; // Establecer el día del mes en 7 si es domingo
    }
   

    // Obtener el número de días del mes actual
    let numDias = new Date(
      fechaActual.getFullYear(),
      mesActual + 1,0
    ).getDate();

    // Crear una variable para contar los días del mes
    let contadorDias = 1;

    //Traigo el profesional

    // Crear una variable para almacenar el HTML de cada fila del calendario
    let filaHTML = "";

    // Crear las filas del calendario
    for (let i = 0; i < 5; i++) {
      // Crear una fila vacía
      filaHTML = "<tr>";

      // Crear las celdas de la fila
      for (let j = 1; j <= 7; j++) {


        // Si la celda corresponde a un día del mes actual, agregar el número del día

        if (i === 0 && j < primerDia) {
          filaHTML += "<td class='celdaDias'></td>";
        } else if (contadorDias < hoy) {
          filaHTML +=
            "<td class='celdaDias' ><label for=" +
            contadorDias +
            " class='diaDeshabilitado'>" +
            contadorDias +
            "</label> <input class='inputRadio' type='radio' disabled></td>";
          contadorDias++;
        } else if (
          contadorDias >= hoy &&
          contadorDias <= numDias &&
          diasAtencion.includes(j)
        ) {
          filaHTML +=
            "<td class='celdaDias' ><label for=" +
            contadorDias +
            " class='diasCalendario diaHabilitado'>" +
            contadorDias +
            "</label> <input class='inputRadio inputHabilitado' type='radio' name='dia' id=" +
            contadorDias +
            " \n\
            value=" +
            contadorDias +
            "-" +
            (mesActual + 1) +
            "-" +
            anioActual +
            " ></td>";
          contadorDias++;
        } else if (
          contadorDias >= hoy &&
          contadorDias <= numDias &&
          !diasAtencion.includes(j)
        ) {
          filaHTML +=
            "<td class='celdaDias' ><label for=" +
            contadorDias +
            " class='diaDeshabilitado'>" +
            contadorDias +
            "</label> <input class='inputRadio' type='radio' disabled></td>";

          contadorDias++;
        } else {
          // Si ya se agregaron todos los días del mes, agregar una celda vacía
          filaHTML += "<td class='celdaDias'></td>";
          contadorDias++;
        }
      }

      // Cerrar la fila
      filaHTML += "</tr>";

      // Agregar la fila al HTML del calendario
      calendarioHTML += filaHTML;
     
    }

    // Insertar el HTML del calendario dentro del div
    divCalendario.innerHTML = calendarioHTML;

    mes.innerHTML =
      "<i class='fa-solid fa-calendar-days iconForm'></i>" +
      nombresMeses[mesActual];
  }

}
