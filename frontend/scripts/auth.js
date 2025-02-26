function login() {
    const user = {
        email: document.getElementsByName('emailLogin')[0].value,
        password: document.getElementsByName('passwordLogin')[0].value,
    };

    fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Login failed");
        }
        return response.json();
    })
    .then(data => {
        localStorage.setItem('jwtToken', data.token);
        window.location.href = "http://127.0.0.1:5500";
    })
    .catch(error => console.error("Error:", error));
}

function signUp() {
    const user = {
        username: document.getElementsByName('nameSignUp')[0].value,
        email: document.getElementsByName('emailSignUp')[0].value,
        password: document.getElementsByName('passwordSignUp')[0].value,
    };

    fetch("http://localhost:8080/auth/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Signup failed");
        }
        return response.json();
    })
    .then(data => {
        localStorage.setItem('jwtToken', data.token);
        window.location.href = "http://127.0.0.1:5500";
    })
    .catch(error => console.error("Error:", error));
}