function login() {

  const usuario = document.getElementById("usuario").value;
  const password = document.getElementById("password").value;
  const error = document.getElementById("error");

  error.textContent = "";

  fetch("http://localhost:8080/api/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ usuario, password })
  })
  .then(res => {
    if (!res.ok) throw new Error("Credenciales incorrectas");
    return res.json();
  })
  .then(data => {
    localStorage.setItem("usuario", JSON.stringify(data));
    window.location.href = "/index.html";
  })
  .catch(err => {
    error.textContent = err.message;
  });
}

