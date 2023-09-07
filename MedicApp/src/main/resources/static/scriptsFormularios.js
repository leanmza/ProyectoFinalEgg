// VALIDAR CONTRASEÑAS///////////
let formulario = document.getElementById("formulario");
let password = document.getElementById("password");
let password2 = document.getElementById("password2");
//formulario.addEventListener("submit", validateForm);
//
//function validateForm() {
//  let x = document.forms["formulario"]["nombre"].value;
//  if (x == "") {
//      
//    alert("Este campo es obligatorio");
//    return false;
//  }
//}


// JQUERY 
//Funciona para Paciente y Profesional
$(document).ready(function () {
    $('form[id="formulario"]').validate({
        rules: {
            nombre: 'required',
            apellido: 'required',
            dni: 'required',
            direccion: 'required',
            telefono: 'required',
            nacimiento: 'required',
            sexo: 'required',
            obraSocial: 'required',
            especialidad: 'required',
            ubicacion: 'required',
            modalidad: 'required',
            honorarios: 'required',
            horaInicio: 'required',
            horaFin: 'required',

            password: {
                required: true,
                minlength: 8,
                contieneMayuscula: true,
                contieneMinuscula: true,
                contieneNumero: true
            },
            password2: {
                required: true,
                equalTo: '#password' // Agrega la regla para verificar si es igual a #password
            },
            correo: {
                required: true,
                email: true
            },
        },
        messages: {
            nombre: {
                required: 'Este campo es obligatorio'
            },
            apellido: {
                required: 'Este campo es obligatorio'
            },
            dni: {
                required: 'Este campo es obligatorio'
            },
            direccion: {
                required: 'Este campo es obligatorio'
            },
            telefono: {
                required: 'Este campo es obligatorio'
            },
            nacimiento: {
                required: 'Este campo es obligatorio'
            },
            sexo: {
                required: 'Este campo es obligatorio'
            },
            obraSocial: {
                required: 'Este campo es obligatorio'
            },
            especialidad: {
                required: 'Este campo es obligatorio'
            },
            ubicacion: {
                required: 'Este campo es obligatorio'
            },
            modalidad: {
                required: 'Este campo es obligatorio'
            },
            honorarios: {
                required: 'Este campo es obligatorio'
            },
            horaInicio: {
                required: '*'
            },
            horaFin: {
                required: '*'
            },
            password: {
                required: 'Este campo es obligatorio',
                minlength: 'La contraseña debe tener al menos 8 carácteres',
                contieneMayuscula: 'La contraseña debe contener al menos 1 letra mayúscula',
                contieneMinuscula: 'La contraseña debe contener al menos 1 letra minúscula',
                contieneNumero: 'La contraseña debe contener al menos 1 número'
            },
            password2: {
                required: 'Este campo es obligatorio',
                equalTo: 'Las contraseñas no coinciden'
            },
            correo: {
                required: 'Este campo es obligatorio',
                email: 'Ingrese un correo electrónico válido'
            }
        },
        errorElement: "span",
        errorPlacement: function (error, element) {
            var errorSpan = element.closest("form").find("label[for='" + element.attr("id") + "']").find('.error-message');
            if (errorSpan.length === 0) {
                errorSpan = $('<span class="error-message"></span>');
                element.closest("form").find("label[for='" + element.attr("id") + "']").append(errorSpan);
            }
            errorSpan.html(error);
        },
    });
    // Regla personalizada para verificar si la contraseña contiene al menos una letra mayúscula
    $.validator.addMethod('contieneMayuscula', function (value) {
        return /[A-Z]/.test(value);
    }, '');
    // Regla personalizada para verificar si la contraseña contiene al menos una letra minúscula
    $.validator.addMethod('contieneMinuscula', function (value) {
        return /[a-z]/.test(value);
    }, '');
    // Regla personalizada para verificar si la contraseña contiene al menos un número
    $.validator.addMethod('contieneNumero', function (value) {
        return /\d/.test(value);
    }, '');
 
});
$(".formulario").on("submit", function (e) {
// validación de campos

    $(this)
            .find('input[type="text"], input[type="password"], input[type="date"], input[type="time"], select')
            .each(function () {
                if (!$(this).valid()) {
                    e.preventDefault();
                    $(this).addClass("input-error");
                    var label = $('label[for="' + $(this).attr("id") + '"]');
                    label.addClass("label-error");
                    if (!label.find('.error-message').length) {
                        label.append('<span class="error-message"></span>');
                    }
                } else {
                    $(this).removeClass("input-error");
                    var label = $('label[for="' + $(this).attr("id") + '"]');
                    label.removeClass("label-error");
                    label.find('.error-message').remove();
                }
            });
    // validación de campos
});


