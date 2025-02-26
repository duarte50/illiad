getRecentVideos();

function getRecentVideos() {
    const videosContainer = document.getElementById("videos");

    fetch("http://localhost:8080/videos/recent")
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao buscar vídeos recentes.");
            }
            return response.json();
        })
        .then(data => {
            const videos = data.content;

            videos.forEach((video) => {
                const videoElement = `
                    <a href="video.html?id=${video.id}">
                        <div class="video">
                            <div class="thumbnail">
                                <img src="" alt="" srcset="">
                            </div>
                            <div class="info">
                                <img src="" alt="" srcset="" class="icon">
                                <div class="details">
                                    <p>${video.title}</p>
                                    <p>@${video.channel}</p>
                                </div>
                            </div>
                        </div>
                    </a>
                `;
                videosContainer.insertAdjacentHTML("beforeend", videoElement);
            });
        })
        .catch(error => console.error("Erro ao carregar vídeos recentes:", error));
}
