let btnMesAnterior = document.getElementById("btn-mes-anterior");
let btnMesSiguiente = document.getElementById("btn-mes-siguiente");

window.addEventListener("load", generarCalendario);




/////// CONTADOR DE CARACTERES /////// 
const textarea = document.getElementById('motivo');
const contadorCaracteres = document.getElementById('contador');
const maxLength = 140;

textarea.addEventListener('input', function () {
    const charLength = this.value.length;

    if (charLength > maxLength) {
        this.value = this.value.slice(0, maxLength);
    }

    contadorCaracteres.innerText = `Caracteres: ${this.value.length}/${maxLength}`;

    if (charLength > maxLength) {
        textarea.classList.add('error');
    } else {
        textarea.classList.remove('error');
    }
});



/////// GENERADOR DE CALENDARIO /////// 
function generarCalendario() {

    // Obtener el elemento div del HTML
    let mes = document.getElementById("mes");

    let divCalendario = document.getElementById("calendario");

    // Obtener la fecha actual
    let fechaActual = new Date();

    // Obtener el número del mes actual (0-11)
    let mesActual = fechaActual.getMonth();

    // Obtener el año
    let anioActual = fechaActual.getFullYear();

    //Obtener hoy
    let hoy = fechaActual.getDate();
    
    // Crear una matriz para almacenar los nombres de los meses
    let nombresMeses = [
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
    calendarioHTML += "<div class='col-xs-12'>"
    calendarioHTML += "<table class='col-xs-12 tablaCalendario'>";
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
    let primerDia = new Date(fechaActual.getFullYear(), mesActual, 1);
    
    // Obtener el número de días del mes actual
    let numDias = new Date(fechaActual.getFullYear(), mesActual + 1, 0).getDate();

    // Crear una variable para contar los días del mes
    let contadorDias = 1;

    // Crear una variable para almacenar el HTML de cada fila del calendario
    let filaHTML = "";

    // Crear las filas del calendario
    for (let i = 0; i < 5; i++) {
        // Crear una fila vacía
        filaHTML = "<tr>";

        // Crear las celdas de la fila
        for (let j = 1; j <= 7; j++) {

            let esDomingo = (j === 7); //posicion 7 corresponde a domingo

            // Si la celda corresponde a un día del mes actual, agregar el número del día

            if (i === 0 && j < primerDia.getDay()) {
                filaHTML += "<td class='celdaDias'></td>";
            } else if(contadorDias < hoy){
                filaHTML += "<td class='celdaDias' ><label for=" + contadorDias + " class='diaDeshabilitado'>" +
                contadorDias +
                "</label> <input class='inputRadio' type='radio' name='dia' id=" + contadorDias + " \n\
                value=" + contadorDias + "-" +  (mesActual +1 ) + "-" + anioActual + " disabled></td>";
                contadorDias++;
            } 
            else if ((esDomingo) && (contadorDias <= numDias)) {
                filaHTML += "<td class='celdaDias' ><label for=" + contadorDias + " class='diaDeshabilitado'>" +
                        contadorDias +
                        "</label> <input class='inputRadio' type='radio' name='dia' id=" + contadorDias + " \n\
          value=" + contadorDias + "-" +  (mesActual +1 ) + "-" + anioActual + " disabled></td>";
                // filaHTML += "<td class='celdaDias'></td>";
                contadorDias++;

            } 
            else 
            if ((contadorDias >= hoy) && (contadorDias <= numDias)) { 
                filaHTML +=
                        "<td class='celdaDias' ><label for=" +
                        contadorDias +
                        " class='diasCalendario'>" +
                        contadorDias +
                        "</label> <input class='inputRadio' type='radio' name='dia' id=" + contadorDias + " \n\
            value=" + contadorDias + "-" + (mesActual +1 )  + "-" + anioActual + " ></td>";
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
        calendarioHTML += "</div>"
    }

    // Insertar el HTML del calendario dentro del div
    divCalendario.innerHTML = calendarioHTML;

    mes.innerHTML = "<i class='fa-solid fa-calendar-days iconForm'></i>" + nombresMeses[mesActual];


}

btnMesAnterior.addEventListener("click", function () { ///TODAVIA NO ANDA
    // Obtener la fecha del mes anterior
    fecha.setMonth(fecha.getMonth() - 1);

    // Generar el calendario con el mes correspondiente
    generarCalendario();
});


