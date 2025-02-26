function postVideo() {
    const title = document.getElementById('title').value;
    const description = document.getElementById('description').value;
    const videoData = document.getElementById('video').files[0];

    if (!title || !description || !videoData) {
        alert("Por favor, preencha todos os campos!");
        return;
    }

    const formData = new FormData();
    formData.append('title', title);
    formData.append('description', description);
    formData.append('videoData', videoData);

    const jwtToken = localStorage.getItem('jwtToken');

    fetch("http://localhost:8080/videos", {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + jwtToken
        },
        body: formData
    })
    .then(response => {
        if (response.status === 201) {
            alert("Vídeo postado com sucesso!");
            window.location.href = "http://127.0.0.1:5500";
        } else {
            alert("Erro ao postar vídeo.");
        }
    })
    .catch(error => console.error("Erro ao postar vídeo:", error));
}
