
/////// CONTROLADOR MODAL PROFESIONALES TURNO /////// 
window.addEventListener("load", ocultarInputs);
const btnProfesional = document.getElementById("btnProfesional");
const btnEspecialidad = document.getElementById("btnEspecialidad");
const btnCloseModal = document.getElementById("btnCloseModal");
const btnCancelarModal = document.getElementById("btnCancelarModal");
const labelProfesionalSecundaria = document.getElementById("labelProfesionalSecundaria");
const labelProfesionalPrimaria = document.getElementById("labelProfesionalPrimaria");
const labelEspecialidadSecundaria = document.getElementById("labelEspecialidadSecundaria");
const labelEspecialidadPrimaria = document.getElementById("labelEspecialidadPrimaria");
let inputId = document.getElementById("inputId");
let inputProfesional = document.getElementById("inputProfesional");
let labelProfesional = document.getElementById("labelProfesional");

let modalProfesionales = document.getElementById("modalProfesionales");

btnEspecialidad.addEventListener("click", abrirModal);
btnProfesional.addEventListener("click", abrirModal);
btnCancelarModal.addEventListener("click", cerrarModal);
btnCloseModal.addEventListener("click", cerrarModal);


function ocultarInputs() {
    inputProfesional.style.display = "none";
    labelProfesional.style.display = "none";
    modalProfesionales.style.display = "none";
    btnProfesional.style.display = "block";
    labelProfesionalSecundaria.style.display = "none";
    labelEspecialidadSecundaria.style.display = "none";
}

function cerrarModal() {
    modalProfesionales.style.display = "none";
    inputProfesional.style.display = "none";
    labelProfesional.style.display = "none";
    labelProfesionalSecundaria.style.display = "none";
    labelEspecialidadSecundaria.style.display = "none";
    btnProfesional.style.display = "block";
}



function abrirModal() {
    modalProfesionales.style.display = "block";
    inputProfesional.style.display = "block";
    labelProfesional.style.display = "block";


    let filasTabla = document.querySelectorAll("#tablaProfesionales table tr");

    filasTabla.forEach(function (fila) {
        fila.addEventListener("click", function () {
            let id = fila.cells[0].textContent;
            let nombre = fila.cells[1].textContent;
            let apellido = fila.cells[2].textContent;
            let especialidad = fila.cells[3].textContent;
            console.log(id);
            console.log(nombre);
            console.log(apellido);

            inputId.value = id;

            labelEspecialidadPrimaria.innerHTML = " ";
            labelEspecialidadPrimaria.style.paddingTop = '5%';
            labelEspecialidadPrimaria.innerHTML = especialidad;

            labelProfesionalPrimaria.innerHTML = " ";
            labelProfesionalPrimaria.style.paddingTop = '5%';
            labelProfesionalPrimaria.innerHTML = apellido + " " + nombre;

            labelProfesionalSecundaria.style.display = "block";
            labelEspecialidadSecundaria.style.display = "block";
     });
    });
}

