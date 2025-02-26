const username = getUsername();

if (username !== "") {
    const myChannel = document.getElementById("my-channel");
    const topButton = document.getElementById("top-button");
    myChannel.href = `channel.html?user=${username}`
    topButton.href = `channel.html?user=${username}`
    topButton.innerHTML = "Meu canal"
} else {
    myChannel.href = "auth.html"
    topButton.href = "auth.html"
    topButton.innerHTML = "Entrar"
}

function getUsername() {
    const token = localStorage.getItem("jwtToken");

    if (token == null) {
        return ""
    }

    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    const decodedToken = JSON.parse(jsonPayload);
    return decodedToken.username;
}
