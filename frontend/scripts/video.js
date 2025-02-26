const urlParams = new URLSearchParams(window.location.search);
const videoId = urlParams.get('id');

getVideoById(videoId);

function getVideoById(id) {
    const videosContainer = document.getElementById("main-video");

    fetch(`http://localhost:8080/videos/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao buscar o vídeo.");
            }
            return response.json();
        })
        .then(video => {
            const parseDate = (dateString) => {
                const date = new Date(dateString);
                const months = [
                  "janeiro", "fevereiro", "março", "abril", "maio", "junho",
                  "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"
                ];

                return `${date.getDate()} de ${months[date.getMonth()]} de ${date.getFullYear()}`;
            };

            const videoBlobUrl = `data:video/mp4;base64,${video.videoData}`;
            const createdAt = parseDate(video.createdAt);

            const videoElement = `
                <div class="video-container">
                    <video src="${videoBlobUrl}" controls></video>
                </div>
                <h1>${video.title}</h1>
                <div class="info">
                    <p class="date">${createdAt}</p>
                    <p class="views">${video.views} visualizações</p>
                    <p class="share" onclick="copyToClipboard(event)">Compartilhar</p>
                </div>
                <div class="channel-container">
                    <a href="channel.html?user=${video.channel}">
                        <div class="channel">
                            <img src="" alt="" srcset="" class="icon">
                            <p>@${video.channel}</p>
                        </div>
                    </a>
                </div>
                <hr class="line">
                <div class="desc">
                    <p>${video.description}</p>
                </div>
            `;

            videosContainer.insertAdjacentHTML("beforeend", videoElement);
        })
        .catch(error => console.error("Erro ao carregar o vídeo:", error));
}

function copyToClipboard(event) {
    const url = window.location.href;
    navigator.clipboard.writeText(url).then(() => {
        alert("Link copiado para a área de transferência!");
    }).catch(err => {
        console.error("Erro ao copiar:", err);
    });
}