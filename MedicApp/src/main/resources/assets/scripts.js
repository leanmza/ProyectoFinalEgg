
function exportToPdf() {
  const element = document.querySelector("table");
  const paciente = document.querySelector("#paciente").innerText;
  const fileName = `historia_clinica_${paciente}.pdf`;

  html2canvas(element).then((canvas) => {
    const imgData = canvas.toDataURL("image/png");
    const doc = new jsPDF();
    const imgProps = doc.getImageProperties(imgData);
    const pdfWidth = doc.internal.pageSize.getWidth();
    const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;

    doc.addImage(imgData, "PNG", 0, 0, pdfWidth, pdfHeight);
    doc.save(fileName);
  });
}
const texto = "¡Hola mundo!";
let i = 0;
const velocidadEscritura = 50; // velocidad en milisegundos

function escribirTexto() {
  if (i < texto.length) {
    document.getElementById("texto").innerHTML += texto.charAt(i);
    i++;
    setTimeout(escribirTexto, velocidadEscritura);
  }
}

escribirTexto();