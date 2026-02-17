// Login
const user = JSON.parse(localStorage.getItem("usuario"));

if (!user) {
    window.location.href = "/usuario/login.html";
}



const API = "http://localhost:8080/api/vehiculos";


const form = document.getElementById("formVehiculo");
const tabla = document.getElementById("tablaVehiculos");

const placa = document.getElementById("placa");
const tipoVehiculo = document.getElementById("tipoVehiculo");
const vistaVehiculos = document.getElementById("vista-vehiculos");
const vistaDetalle = document.getElementById("vista-detalle");



/* CAJA  */
const API_CAJA = "http://localhost:8080/api/caja";

const vistaCaja = document.getElementById("vista-caja");
const tablaCaja = document.getElementById("tablaCaja");


/* 
 ESPACIOS 
 */

const API_PISOS = "http://localhost:8080/api/pisos";

const vistaPisos = document.getElementById("vista-pisos");
const vistaEspacios = document.getElementById("vista-espacios");

const tablaPisos = document.getElementById("tabla-pisos");
const contenedorEspacios = document.getElementById("contenedor-espacios");
const tituloPiso = document.getElementById("titulo-piso");
const inputCantidadEspacios = document.getElementById("cantidadEspacios");

let pisoActualId = null;


/*INGRESO ESPACIO */
const tablaPisosAsignar = document.getElementById("tabla-pisos-asignar");
const spanPiso = document.getElementById("d-piso");
const spanEspacio = document.getElementById("d-espacio");
const btnAsignar = document.getElementById("btnAsignarEspacio");

let vehiculoActualId = null;
let modoAsignacion = false;


/* TARIFAS */
const API_TARIFAS = "http://localhost:8080/api/tarifas";

const vistaTarifas = document.getElementById("vista-tarifas");
const tablaTarifas = document.getElementById("tablaTarifas");
const selectTipoVehiculoTarifa = document.getElementById("tipoVehiculoTarifa");
const selectHorarioTarifa = document.getElementById("horarioTarifa");
const selectTipoDiaTarifa = document.getElementById("tipoDiaTarifa");
const inputCostoTarifa = document.getElementById("costoTarifa");

let tarifaEditandoId = null;




/* ======================
 REPORTES
 ====================== */

const API_REPORTES = "http://localhost:8080/api/vehiculos/reportes";

const vistaReportes = document.getElementById("vistaReportes");

const tablaReporteIngresos = document.getElementById("tablaReporteIngresos");
const tablaReporteSalidas = document.getElementById("tablaReporteSalidas");
const tablaReporteCaja = document.getElementById("tablaReporteCaja");

const rIngresoInicio = document.getElementById("rIngresoInicio");
const rIngresoFin = document.getElementById("rIngresoFin");

const rSalidaInicio = document.getElementById("rSalidaInicio");
const rSalidaFin = document.getElementById("rSalidaFin");

const rCajaInicio = document.getElementById("rCajaInicio");
const rCajaFin = document.getElementById("rCajaFin");




/* REPORTE GANANCIA REAL */
const ingresosMensuales = document.getElementById("ingresosMensuales");
const pagoLuz = document.getElementById("pagoLuz");
const pagoAgua = document.getElementById("pagoAgua");
const pagoImpuesto = document.getElementById("pagoImpuesto");
const pagoEmpleado = document.getElementById("pagoEmpleado");
const cantidadEmpleados = document.getElementById("cantidadEmpleados");
const pagoBanco = document.getElementById("pagoBanco");

const resultadoIngresoReal = document.getElementById("resultadoIngresoReal");
const tablaGananciaReal = document.getElementById("tablaGananciaReal");
const btnImprimirReal = document.getElementById("btnImprimirReal");









if (form) {
    form.addEventListener("submit", e => {
        e.preventDefault();

        const vehiculo = {
            placa: placa.value,
            tipoVehiculo: tipoVehiculo.value
        };

        fetch(API, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(vehiculo)
        })
                .then(res => res.json())
                .then(data => {
                    form.reset();
                    cargarVehiculos();
                    mostrarComprobante(data);
                });
    });
}




function cargarVehiculos() {
    fetch(API)
            .then(res => res.json())
            .then(data => {
                console.log("DATA VEHICULOS >>>", data);
                tabla.innerHTML = "";

                let ingresados = 0;
                let egresados = 0;
                const tipos = new Set();

                data.forEach(vehiculo => {

                    if (!vehiculo.id || !vehiculo.estado) {
                        return;
                    }



                    if (vehiculo.estado === "ACTIVO")
                        ingresados++;
                    if (vehiculo.estado === "INACTIVO")
                        egresados++;
                    tipos.add(vehiculo.tipoVehiculo);


                    tabla.innerHTML += `
                    <tr>
                        <td>${vehiculo.id}</td>
                        
                        <td>${formatearFecha(vehiculo.fechaIngreso)}</td>
                        <td>${mostrarEstado(vehiculo.estado)}</td>
                        <td>
                            <button onclick="verDetalle(${vehiculo.id})">Ver</button>
                            
                        </td>
                        <td>
                            <button onclick="verComprobante(${vehiculo.id})">Entrada</button>
                            ${vehiculo.estado === "INACTIVO"
                            ? `<button onclick="verComprobanteSalida(${vehiculo.id})">Salida</button>`
                            : ""}
                        </td>
            
                    </tr>
                `;
                });

                // Dashboard
                document.getElementById("countIngresados").innerText = ingresados;
                document.getElementById("countEgresados").innerText = egresados;
                document.getElementById("countTipos").innerText = tipos.size;
            });
}


//TICKET DE ENTRADA
function mostrarComprobante(v) {

    const ventana = window.open("", "Ticket", "width=380,height=700");

    ventana.document.write(`
    <html>
    <head>
        <title>Comprobante</title>
        <style>
            body {
                font-family: monospace;
                width: 320px;
                margin: 0 auto;
                padding: 10px;
                text-align: center;
            }

            .top-buttons {
                display: flex;
                justify-content: space-between;
                margin-bottom: 10px;
            }

            .btn {
                padding: 5px 8px;
                font-size: 12px;
                border: none;
                cursor: pointer;
                color: white;
            }

            .btn-print { background: #00a2c6; }
            .btn-pdf { background: #7fbf7f; }
            .btn-close { background: #e74c3c; }

            .logo-img {
                width: 90px;
                display: block;
                margin: 0 auto 5px auto;
            }

            .titulo {
                font-weight: bold;
                font-size: 16px;
            }

            .codigo {
                font-size: 12px;
                margin-bottom: 10px;
            }

            .linea {
                border-top: 1px solid black;
                margin: 10px 0;
            }

            .detalle {
                text-align: left;
                font-size: 14px;
            }

            .centrado {
                text-align: center;
                margin: 10px 0;
            }

            .footer {
                font-size: 12px;
                margin-top: 15px;
            }
        </style>
    </head>

    <body>

        <!-- BOTONES SUPERIORES -->
        <div class="top-buttons">
            <button class="btn btn-print" onclick="window.print()">Imprimir</button>
            <button class="btn btn-pdf" onclick="guardarPDF()">PDF</button>
            <button class="btn btn-close" onclick="window.close()">Cerrar</button>
        </div>

        <!-- LOGO -->
        <img src="http://localhost:8080/images/insigniacarro.png" class="logo-img">

        <div class="titulo">ESTACIONAMIENTO</div>
        <div class="codigo">${v.id}${Date.now()}</div>

        <div class="linea"></div>

        <div class="titulo">Comprobante de entrada</div>

        <div class="linea"></div>

        <div class="detalle">
            Ticket : ${v.id} <br>
            Placa : ${v.placa} <br>
            Entrada : ${formatearFecha(v.fechaIngreso)}
        </div>

        <div class="linea"></div>

        <div class="centrado">
            <strong>"Por favor, conserve este comprobante"</strong><br>
            <strong>En caso de pérdida del ticket, se aplicarán cargos adicionales</strong><br>
            <strong>El pago se realiza únicamente al momento del retiro</strong>
        </div>

        <div class="footer">
            952 784 631<br>
            Av. Bolognesi N° 890 - Cercados<br>
            Perú - Tacna
        </div>
    

        <script>
            function guardarPDF() {
                const opt = {
                    margin: 0.5,
                    filename: 'ticket_${v.id}.pdf',
                    image: { type: 'jpeg', quality: 0.98 },
                    html2canvas: { scale: 2 },
                    jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
                };

                const elemento = document.body;
                html2pdf().set(opt).from(elemento).save();
            }
        </script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>

    </body>
    </html>
    `);

    ventana.document.close();
}



//COMPROBANTE DE PAGO
function verComprobanteSalida(id) {

    fetch(`${API}/${id}`)
            .then(res => res.json())
            .then(v => {

                const ventana = window.open("", "TicketSalida", "width=380,height=700");

                ventana.document.write(`
            <html>
            <head>
                <title>Comprobante de Pago</title>
                <style>
                    body {
                        font-family: monospace;
                        width: 320px;
                        margin: 0 auto;
                        padding: 10px;
                        text-align: center;
                    }

                    .top-buttons {
                        display: flex;
                        justify-content: space-between;
                        margin-bottom: 10px;
                    }

                    .btn {
                        padding: 5px 8px;
                        font-size: 12px;
                        border: none;
                        cursor: pointer;
                        color: white;
                    }

                    .btn-print { background: #00a2c6; }
                    .btn-pdf { background: #7fbf7f; }
                    .btn-close { background: #e74c3c; }

                    .logo-img {
                        width: 90px;
                        display: block;
                        margin: 0 auto 5px auto;
                    }

                    .titulo {
                        font-weight: bold;
                        font-size: 16px;
                    }

                    .codigo {
                        font-size: 12px;
                        margin-bottom: 10px;
                    }

                    .linea {
                        border-top: 1px solid black;
                        margin: 10px 0;
                    }

                    .detalle {
                        text-align: left;
                        font-size: 14px;
                    }

                    .centrado {
                        text-align: center;
                        margin: 10px 0;
                    }

                    .footer {
                        font-size: 12px;
                        margin-top: 15px;
                    }
                </style>
            </head>

            <body>

                <div class="top-buttons">
                    <button class="btn btn-print" onclick="window.print()">Imprimir</button>
                    <button class="btn btn-pdf" onclick="guardarPDF()">PDF</button>
                    <button class="btn btn-close" onclick="window.close()">Cerrar</button>
                </div>

                <img src="http://localhost:8080/images/insigniacarro.png" class="logo-img">

                <div class="titulo">ESTACIONAMIENTO</div>
                <div class="codigo">${v.id}${Date.now()}</div>

                <div class="linea"></div>

                <div class="titulo">Comprobante de pago</div>

                <div class="linea"></div>

                <div class="detalle">
                    Ticket : ${v.id} <br>
                    Placa : ${v.placa} <br>
                    Entrada : ${formatearFecha(v.fechaIngreso)} <br>
                    Salida : ${formatearFecha(v.fechaSalida)} <br>
                    <br>
                    TOTAL PAGADO : S/ ${Number(v.valorPagado).toFixed(2)}
                </div>

                <div class="linea"></div>

                <div class="centrado">
                    <strong>Gracias por su preferencia</strong><br>
                    <strong>Vuelva pronto</strong>
                </div>

                <div class="footer">
                    952 784 631<br>
                    Av. Bolognesi N° 890 - Cercados<br>
                    Perú - Tacna
                </div>

                <script>
                    function guardarPDF() {
                        const opt = {
                            margin: 0.5,
                            filename: 'pago_${v.id}.pdf',
                            image: { type: 'jpeg', quality: 0.98 },
                            html2canvas: { scale: 2 },
                            jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
                        };

                        const elemento = document.body;
                        html2pdf().set(opt).from(elemento).save();
                    }
                </script>

                <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>

            </body>
            </html>
            `);

                ventana.document.close();
            });
}














function verComprobante(id) {

    fetch(`http://localhost:8080/api/vehiculos/${id}`)
            .then(res => res.json())
            .then(data => {
                mostrarComprobante(data);
            });
}







function filtrarTabla() {
    const texto = document.getElementById("buscador").value.toLowerCase();
    const filas = tabla.querySelectorAll("tr");

    filas.forEach(fila => {
        const ticket = fila.children[0].innerText.toLowerCase();
        const propietario = fila.children[1].innerText.toLowerCase();

        if (ticket.includes(texto) || propietario.includes(texto)) {
            fila.style.display = "";
        } else {
            fila.style.display = "none";
        }
    });
}








function verDetalle(id) {
    vehiculoActualId = id;



    fetch(`${API}/${id}`)
            .then(res => res.json())
            .then(vehiculo => {

                document.getElementById("d-id").innerText = vehiculo.id;
                document.getElementById("d-placa").innerText = vehiculo.placa;
                document.getElementById("d-tipo").innerText = vehiculo.tipoVehiculo;
                document.getElementById("d-ingreso").innerText =
                        formatearFecha(vehiculo.fechaIngreso);


                document.getElementById("d-salida").innerText =
                        vehiculo.fechaSalida ? formatearFecha(vehiculo.fechaSalida) : "-";

                document.getElementById("d-pago").innerText =
                        Number(vehiculo.valorPagado ?? 0).toFixed(2);



                if (vehiculo.espacio) {
                    spanPiso.innerText = vehiculo.piso;
                    spanEspacio.innerText = vehiculo.espacio;
                    btnAsignar.style.display = "none";
                } else {
                    spanPiso.innerText = "—";
                    spanEspacio.innerText = "—";
                    btnAsignar.style.display = "inline-block";
                }

                mostrarVistaDetalle();
                cargarCalculoPago(id);

            });
}



function cargarCalculoPago(id) {

    fetch(`${API}/${id}/calculo`)
            .then(res => res.json())
            .then(data => {

                document.getElementById("tarifarioAplicado").innerText =
                        data.tarifarioTexto;

                document.getElementById("tiempoCalculado").innerText =
                        data.tiempoTexto;

                document.getElementById("costoSugerido").innerText =
                        Number(data.total).toFixed(2);

                document.getElementById("costoFinal").value =
                        Number(data.total).toFixed(2);

                calcularVuelto();
            });
}



function calcularVuelto() {

    const costoFinal =
            parseFloat(document.getElementById("costoFinal").value) || 0;

    const efectivo =
            parseFloat(document.getElementById("efectivoRecibido").value) || 0;

    const sugerido =
            parseFloat(document.getElementById("costoSugerido").innerText) || 0;

    const vuelto = efectivo - costoFinal;

    document.getElementById("vuelto").innerText =
            vuelto >= 0 ? vuelto.toFixed(2) : "0.00";

    if (costoFinal !== sugerido) {
        document.getElementById("mensajeAjuste").style.display = "block";
    } else {
        document.getElementById("mensajeAjuste").style.display = "none";
    }
}







function aceptarPago() {

    const costoFinal =
            parseFloat(document.getElementById("costoFinal").value);

    if (!costoFinal || costoFinal <= 0) {
        alert("Monto inválido");
        return;
    }

    const efectivo =
            parseFloat(document.getElementById("efectivoRecibido").value) || 0;

    if (efectivo < costoFinal) {
        alert("El efectivo es menor al costo final");
        return;
    }




    fetch(`${API}/retirar/${vehiculoActualId}?monto=${costoFinal}`, {
        method: "PUT"
    })
            .then(() => {

                verComprobanteSalida(vehiculoActualId);

                // LIMPIAR CAMPOS
                document.getElementById("costoFinal").value = "";
                document.getElementById("efectivoRecibido").value = "";
                document.getElementById("vuelto").innerText = "0.00";
                document.getElementById("mensajeAjuste").style.display = "none";
                document.getElementById("tarifarioAplicado").innerText = "";
                document.getElementById("tiempoCalculado").innerText = "-";
                document.getElementById("costoSugerido").innerText = "0.00";

                vehiculoActualId = null;

                ocultarVistas();
                vistaVehiculos.style.display = "block";
                cargarVehiculos();
                cargarMetricas();
            });
}



function volver() {

    // LIMPIAR CAMPOS DE PAGO
    document.getElementById("costoFinal").value = "";
    document.getElementById("efectivoRecibido").value = "";
    document.getElementById("vuelto").innerText = "0.00";
    document.getElementById("mensajeAjuste").style.display = "none";
    document.getElementById("tarifarioAplicado").innerText = "";
    document.getElementById("tiempoCalculado").innerText = "-";
    document.getElementById("costoSugerido").innerText = "0.00";

    mostrarVistaVehiculos();
}





/* CAJA  */
function cargarMetricasCaja() {

    fetch(`${API_CAJA}/metricas`)
            .then(res => res.json())
            .then(data => {

                document.getElementById("m-hoy").innerText =
                        Number(data.hoy).toFixed(2);

                document.getElementById("m-semana").innerText =
                        Number(data.semana).toFixed(2);

                document.getElementById("m-mes").innerText =
                        Number(data.mes).toFixed(2);

                document.getElementById("m-anio").innerText =
                        Number(data.anio).toFixed(2);
            });
}







function calcularResumenCaja(vehiculos) {
    let total = 0;
    let egresados = 0;

    vehiculos.forEach(v => {
        if (v.estado === "INACTIVO") {
            egresados++;
            total += Number(v.valorPagado ?? 0);
        }
    });

    document.getElementById("total-ganancia").innerText =
            total.toFixed(2);

    document.getElementById("total-egresados").innerText =
            egresados;
}


function cargarCaja() {
    fetch(API)
            .then(res => res.json())
            .then(data => {
                tablaCaja.innerHTML = "";

                let totalGanancia = 0;


                data
                        .filter(v => v.estado === "INACTIVO") //Aqui solo hay egresados
                        .forEach(v => {


                            totalGanancia += Number(v.valorPagado ?? 0);

                            tablaCaja.innerHTML += `
                        <tr>
                            <td>${v.id}</td>
                            <td>${formatearFecha(v.fechaIngreso)}</td>
                            <td>${formatearFecha(v.fechaSalida)}</td>
                            <td>${calcularTiempo(v.fechaIngreso, v.fechaSalida)}</td>
                            <td>S/ ${Number(v.valorPagado).toFixed(2)}</td>
                        </tr>
                    `;
                        });


                document.getElementById("total-ganancia").innerText =
                        totalGanancia.toFixed(2);

            });
}









/* METODOS AUXLIARES */

function calcularTiempo(ingreso, salida) {
    if (!ingreso || !salida)
        return "-";

    const inicio = new Date(ingreso);
    const fin = new Date(salida);

    let totalSegundos = Math.floor((fin - inicio) / 1000);

    const horas = Math.floor(totalSegundos / 3600);
    totalSegundos %= 3600;

    const minutos = Math.floor(totalSegundos / 60);
    const segundos = totalSegundos % 60;

    if (horas > 0) {
        return `${horas} h ${minutos} min ${segundos} seg`;
    } else {
        return `${minutos} min ${segundos} seg`;
    }
}



function formatearFecha(fecha) {
    return fecha ? fecha.replace("T", " ").substring(0, 19) : "";
}







/*ESTADO  */
function mostrarEstado(estado) {
    switch (estado) {
        case "ACTIVO":
            return "INGRESADO";
        case "INACTIVO":
            return "EGRESADO";
        default:
            return estado;
    }
}

/*FIN ESTADO  */





/* ASIGNACION ESPACIO  */

function irAsignarEspacio() {
    modoAsignacion = true;
    ocultarVistas();
    document.getElementById("vista-asignar-espacio").style.display = "block";
    cargarPisos();
}


function asignarEspacio(espacioId) {
    fetch(`${API}/${vehiculoActualId}/espacio/${espacioId}`, {
        method: "PUT"
    }).then(() => {
        alert("Espacio asignado correctamente");
        verDetalle(vehiculoActualId);
    });
}







/* 
 ESPACIOS - LOGICA
 */

function cargarPisos() {
    const tablaDestino = modoAsignacion
            ? document.getElementById("tabla-pisos-asignar")
            : document.getElementById("tabla-pisos");

    fetch(API_PISOS)
            .then(res => res.json())
            .then(pisos => {
                tablaDestino.innerHTML = "";

                pisos.forEach(piso => {
                    const tr = document.createElement("tr");
                    tr.innerHTML = `
                    <td>${piso.nombre}</td>
                    <td>
                        <button onclick="verEspacios(${piso.id}, '${piso.nombre}')">
                            Ver
                        </button>
                        <button onclick="eliminarPiso(${piso.id})" 
                        style="background:#dc2626;color:white;margin-left:8px;">
                            Eliminar
                        </button>
                    </td>
                `;
                    tablaDestino.appendChild(tr);
                });
            });
}









function crearPiso() {
    fetch(API_PISOS, {method: "POST"})
            .then(() => cargarPisos());
}


//VerEspacios - cargarPisos
function verEspacios(pisoId, nombrePiso) {
    pisoActualId = pisoId;

    ocultarVistas();
    vistaEspacios.style.display = "block";

    tituloPiso.innerText = `Espacios – ${nombrePiso}`;
    cargarEspacios();
}


//eliminarPiso - cargarPisos
function eliminarPiso(id) {

    if (!confirm("¿Seguro que desea eliminar este piso?"))
        return;

    fetch(`${API_PISOS}/${id}`, {
        method: "DELETE"
    })
            .then(res => {
                if (!res.ok) {
                    return res.text().then(text => {
                        throw new Error(text);
                    });
                }
                alert("Piso eliminado correctamente");
                cargarPisos();
            })
            .catch(error => {
                alert("Error: " + error.message);
            });
}











function volverAPisos() {
    ocultarVistas();
    vistaPisos.style.display = "block";
}


function cargarEspacios() {
    fetch(`${API_PISOS}/${pisoActualId}/espacios`)
            .then(res => res.json())
            .then(espacios => {

                contenedorEspacios.innerHTML = "";

                espacios.forEach(e => {

                    const div = document.createElement("div");
                    div.className = `espacio ${e.estado.toLowerCase()}`;

                    div.innerHTML = `
                    <strong>${e.codigo}</strong><br>
                    ${e.estado}
                `;

                    // SI ESTA LIBRE → puede asignarse
                    if (e.estado === "LIBRE") {

                        const btnEliminar = document.createElement("button");
                        btnEliminar.innerText = "🗑";
                        btnEliminar.style.marginTop = "8px";
                        btnEliminar.onclick = (ev) => {
                            ev.stopPropagation(); // evita asignar espacio
                            eliminarEspacio(e.id);
                        };

                        div.appendChild(btnEliminar);

                        div.style.cursor = "pointer";
                        div.onclick = () => asignarEspacio(e.id);
                    }

                    contenedorEspacios.appendChild(div);
                });
            });
}





function crearEspacios() {
    const cantidad = Number(inputCantidadEspacios.value);

    if (cantidad <= 0) {
        alert("Ingrese una cantidad válida");
        return;
    }

    fetch(`${API_PISOS}/${pisoActualId}/espacios?cantidad=${cantidad}`, {
        method: "POST"
    }).then(() => {
        inputCantidadEspacios.value = "";
        cargarEspacios();
    });
}



//eliminarEspacio - CargarEspacio
function eliminarEspacio(id) {

    if (!confirm("¿Seguro que deseas eliminar este espacio?"))
        return;

    fetch(`http://localhost:8080/api/pisos/${pisoActualId}/espacios/${id}`, {
        method: "DELETE"
    })
            .then(res => {
                if (!res.ok) {
                    return res.text().then(text => {
                        throw new Error(text);
                    });
                }
            })
            .then(() => {
                alert("Espacio eliminado correctamente");
                cargarEspacios();
            })
            .catch(err => {
                alert(err.message);
            });
}


function eliminarEspaciosCantidad() {

    const cantidad = Number(
            document.getElementById("cantidadEliminar").value
            );

    if (cantidad <= 0) {
        alert("Ingrese una cantidad válida");
        return;
    }

    fetch(`${API_PISOS}/${pisoActualId}/espacios/cantidad?cantidad=${cantidad}`, {
        method: "DELETE"
    })
            .then(res => res.text())
            .then(eliminados => {

                alert(`Se eliminaron ${eliminados} espacios`);

                document.getElementById("cantidadEliminar").value = "";
                cargarEspacios();
            });
}












//TARIFA
function cargarTarifas() {

    fetch(API_TARIFAS)
            .then(res => res.json())
            .then(data => {

                tablaTarifas.innerHTML = "";

                data.forEach(t => {
                    tablaTarifas.innerHTML += `
                <tr>
                    <td>${t.tipoVehiculo}</td>
                    <td>${t.horario}</td>
                    <td>${t.tipoDia}</td>
                    <td>S/ ${t.costoPorHora}</td>
                    <td>
                        <button onclick="editarTarifa(${t.id})">Editar</button>
                        <button onclick="eliminarTarifa(${t.id})">Eliminar</button>
                    </td>
                </tr>
            `;
                });
            });
}


function guardarTarifa() {

    const tarifa = {
        tipoVehiculo: selectTipoVehiculoTarifa.value,
        horario: selectHorarioTarifa.value,
        tipoDia: selectTipoDiaTarifa.value,
        costoPorHora: inputCostoTarifa.value
    };

    let url = API_TARIFAS;
    let method = "POST";

    if (tarifaEditandoId) {
        url += `/${tarifaEditandoId}`;
        method = "PUT";
    }

    fetch(url, {
        method: method,
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(tarifa)
    })
            .then(() => {
                cargarTarifas();
                tarifaEditandoId = null;
                inputCostoTarifa.value = "";
            });
}


function editarTarifa(id) {

    fetch(`${API_TARIFAS}/${id}`)
            .then(res => res.json())
            .then(t => {

                selectTipoVehiculoTarifa.value = t.tipoVehiculo;
                selectHorarioTarifa.value = t.horario;
                selectTipoDiaTarifa.value = t.tipoDia;
                inputCostoTarifa.value = t.costoPorHora;

                tarifaEditandoId = t.id;
            });
}



function eliminarTarifa(id) {

    if (!confirm("¿Desea eliminar esta tarifa?"))
        return;

    fetch(`${API_TARIFAS}/${id}`, {
        method: "DELETE"
    })
            .then(() => cargarTarifas());
}


/*REPORTE*/
function consultarIngresos() {

    if (!rIngresoInicio.value || !rIngresoFin.value) {
        alert("Seleccione ambas fechas");
        return;
    }

    fetch(`${API_REPORTES}/ingresos?inicio=${rIngresoInicio.value}&fin=${rIngresoFin.value}`)
        .then(res => res.json())
        .then(data => {

            let htmlTabla = `
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Placa</th>
                            <th>Fecha Ingreso</th>
                            <th>Salida</th>
                        </tr>
                    </thead>
                    <tbody>
            `;

            data.forEach(v => {
                htmlTabla += `
                    <tr>
                        <td>${v.id}</td>
                        <td>${v.placa}</td>
                        <td>${v.fechaIngreso.replace("T"," ")}</td>
                        <td>${v.estadoSalida}</td>
                    </tr>
                `;
            });

            htmlTabla += `</tbody></table>`;

            abrirVentanaReporte("Reporte de Vehículos que Entraron", htmlTabla);
        });
}


function abrirVentanaReporte(titulo, contenidoTabla) {

    const ventana = window.open("", "_blank", "width=900,height=700");

    ventana.document.write(`
        <html>
        <head>
            <title>${titulo}</title>
            <style>
                body {
                    font-family: Arial;
                    padding: 20px;
                }
                h2 {
                    text-align:center;
                }
                table {
                    width:100%;
                    border-collapse: collapse;
                    margin-top:20px;
                }
                th, td {
                    border:1px solid #ccc;
                    padding:8px;
                    text-align:center;
                }
                th {
                    background:#111827;
                    color:white;
                }
                .top-buttons {
                    display:flex;
                    justify-content: space-between;
                    margin-bottom:20px;
                }
                button {
                    padding:8px 15px;
                    border:none;
                    cursor:pointer;
                    border-radius:4px;
                }
                .btn-volver {
                    background:#dc2626;
                    color:white;
                }
                .btn-imprimir {
                    background:#16a34a;
                    color:white;
                }
                @media print {
                    .top-buttons {
                        display:none;
                    }
                }
            </style>
        </head>
        <body>

            <div class="top-buttons">
                <button class="btn-volver" onclick="window.close()">Volver</button>
                <button class="btn-imprimir" onclick="window.print()">Imprimir</button>
            </div>

            <h2>${titulo}</h2>

            ${contenidoTabla}

        </body>
        </html>
    `);

    ventana.document.close();
}





function consultarSalidas() {

    if (!rSalidaInicio.value || !rSalidaFin.value) {
        alert("Seleccione ambas fechas");
        return;
    }

    fetch(`${API_REPORTES}/salidas?inicio=${rSalidaInicio.value}&fin=${rSalidaFin.value}`)
        .then(res => res.json())
        .then(data => {

            let htmlTabla = `
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Placa</th>
                            <th>Fecha Ingreso</th>
                            <th>Fecha Salida</th>
                            <th>Monto</th>
                        </tr>
                    </thead>
                    <tbody>
            `;

            data.forEach(v => {
                htmlTabla += `
                    <tr>
                        <td>${v.id}</td>
                        <td>${v.placa}</td>
                        <td>${v.fechaIngreso.replace("T"," ")}</td>
                        <td>${v.fechaSalida.replace("T"," ")}</td>
                        <td>S/ ${v.monto.toFixed(2)}</td>
                    </tr>
                `;
            });

            htmlTabla += `</tbody></table>`;

            abrirVentanaReporte("Reporte de Vehículos que Salieron", htmlTabla);
        });
}



function consultarCaja() {

    if (!rCajaInicio.value || !rCajaFin.value) {
        alert("Seleccione ambas fechas");
        return;
    }

    fetch(`${API_REPORTES}/caja?inicio=${rCajaInicio.value}&fin=${rCajaFin.value}`)
        .then(res => res.json())
        .then(data => {

            let htmlTabla = `
                <table>
                    <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Total Recaudado</th>
                        </tr>
                    </thead>
                    <tbody>
            `;

            let totalGeneral = 0;

            data.forEach(r => {

                totalGeneral += r.total;

                htmlTabla += `
                    <tr>
                        <td>${r.fecha}</td>
                        <td>S/ ${r.total.toFixed(2)}</td>
                    </tr>
                `;
            });

            htmlTabla += `
                    <tr style="font-weight:bold; background:#f4f4f4;">
                        <td>Total General</td>
                        <td>S/ ${totalGeneral.toFixed(2)}</td>
                    </tr>
                </tbody>
            </table>
            `;

            abrirVentanaReporte("Reporte de Caja por Día", htmlTabla);
        });
}




function calcularIngresoReal() {

    const ingresos = parseFloat(ingresosMensuales.value) || 0;
    const luz = parseFloat(pagoLuz.value) || 0;
    const agua = parseFloat(pagoAgua.value) || 0;
    const impuesto = parseFloat(pagoImpuesto.value) || 0;
    const empleado = parseFloat(pagoEmpleado.value) || 0;
    const cantidadEmp = parseInt(cantidadEmpleados.value) || 0;
    const banco = parseFloat(pagoBanco.value) || 0;

    if (!ingresos || !luz || !agua || !empleado) {
        alert("Complete los campos obligatorios");
        return;
    }

    const totalEmpleados = empleado * cantidadEmp;
    const totalGastos = luz + agua + impuesto + totalEmpleados + banco;
    const ingresoReal = ingresos - totalGastos;

    resultadoIngresoReal.innerHTML =
        `<strong>Ingreso Real: S/ ${ingresoReal.toFixed(2)}</strong>`;

    const fechaActual = new Date();
    const fechaTexto = fechaActual.toLocaleDateString("es-PE");
    const mesTexto = fechaActual.toLocaleString("es-PE", { month: "long", year: "numeric" });

    let html = `
        <div id="contenidoImprimibleReal">

        <h2 style="text-align:center;">SisParking</h2>
        <h3 style="text-align:center;">REPORTE MENSUAL – GANANCIA REAL</h3>
        <p><strong>Mes:</strong> ${mesTexto}</p>
        <p><strong>Fecha de emisión:</strong> ${fechaTexto}</p>

        <table border="1" width="100%" cellspacing="0" cellpadding="8">
            <thead>
                <tr>
                    <th>Concepto</th>
                    <th>Monto</th>
                </tr>
            </thead>
            <tbody>
                <tr><td>Ingresos Mensuales</td><td>S/ ${ingresos.toFixed(2)}</td></tr>
                <tr><td>Luz</td><td>S/ ${luz.toFixed(2)}</td></tr>
                <tr><td>Agua</td><td>S/ ${agua.toFixed(2)}</td></tr>
                <tr><td>Impuesto</td><td>S/ ${impuesto.toFixed(2)}</td></tr>
                <tr><td>Pago Empleados (${cantidadEmp})</td><td>S/ ${totalEmpleados.toFixed(2)}</td></tr>
                <tr><td>Banco</td><td>S/ ${banco.toFixed(2)}</td></tr>
                <tr style="font-weight:bold;">
                    <td>INGRESO REAL</td>
                    <td>S/ ${ingresoReal.toFixed(2)}</td>
                </tr>
            </tbody>
        </table>

        </div>
    `;

    tablaGananciaReal.innerHTML = html;
    btnImprimirReal.style.display = "inline-block";
}



function imprimirSoloTablaReal() {

    const tabla = document.getElementById("tablaGananciaReal");

    if (!tabla || tabla.innerHTML.trim() === "") {
        alert("No hay reporte para imprimir");
        return;
    }

    const ventana = window.open("", "_blank");

    ventana.document.write(`
        <html>
        <head>
            <title>Reporte Mensual - SisParking</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    padding: 40px;
                }

                h2, h3 {
                    text-align: center;
                    margin: 5px 0;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 20px;
                }

                th, td {
                    border: 1px solid #000;
                    padding: 10px;
                    text-align: left;
                }

                th {
                    background: #f2f2f2;
                }

                .total {
                    font-weight: bold;
                }
            </style>
        </head>
        <body>

            ${tabla.innerHTML}

        </body>
        </html>
    `);

    ventana.document.close();
    ventana.focus();
    ventana.print();
}





function resetearGananciaReal() {

    // inputs
    ingresosMensuales.value = "";
    pagoLuz.value = "";
    pagoAgua.value = "";
    pagoImpuesto.value = "";
    pagoEmpleado.value = "";
    cantidadEmpleados.value = 2; // dejamos 2 por defecto
    pagoBanco.value = "";

    // resultado
    resultadoIngresoReal.innerHTML = "";
    tablaGananciaReal.innerHTML = "";

    // Ocultar 
    document.getElementById("btnImprimirReal").style.display = "none";
}









/*  VISTAS   */
function ocultarVistas() {
    vistaVehiculos.style.display = "none";
    vistaDetalle.style.display = "none";
    vistaCaja.style.display = "none";
    vistaPisos.style.display = "none";
    vistaEspacios.style.display = "none";
    vistaTarifas.style.display = "none";
    vistaReportes.style.display = "none";




    const vistaAsignar = document.getElementById("vista-asignar-espacio");
    if (vistaAsignar)
        vistaAsignar.style.display = "none";
}

function mostrarVistaVehiculos() {
    ocultarVistas();
    vistaVehiculos.style.display = "block";
}

function mostrarVistaDetalle() {
    ocultarVistas();
    vistaDetalle.style.display = "block";
}

function mostrarVistaCaja() {
    ocultarVistas();
    vistaCaja.style.display = "block";
    cargarCaja();
    cargarMetricasCaja();
}

function mostrarVistaEspacios() {
    modoAsignacion = false;
    ocultarVistas();
    vistaPisos.style.display = "block";
    cargarPisos();
}

function mostrarVistaTarifas() {
    ocultarVistas();
    vistaTarifas.style.display = "block";
    cargarTarifas();
}

function mostrarVistaReportes() {
    ocultarVistas();
    vistaReportes.style.display = "block";
}




/*  FIN VISTAS */








cargarVehiculos();



document.addEventListener("input", function (e) {

    if (e.target.id === "costoFinal" ||
            e.target.id === "efectivoRecibido") {

        calcularVuelto();
    }
});





function logout() {
    localStorage.removeItem("usuario");
    window.location.href = "/usuario/login.html";
}

