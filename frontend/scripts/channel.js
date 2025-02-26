const urlParams = new URLSearchParams(window.location.search);
const channelName = urlParams.get('user');
const jwtToken = localStorage.getItem("jwtToken");

if (channelName) {
    const videosContainer = document.getElementById("videos");
    const bannerContainer = document.getElementById("banner");
    let bannerElement = `
        <div class="group">
            <img src="" alt="" srcset="" class="icon">
            <a href="" class="follow">Seguir</a>
        </div>
        <p class="name">@${channelName}</p>
    `;

    if (channelName == getUsername()) {
        bannerElement = `
            <div class="group">
                <img src="" alt="" srcset="" class="icon">
                <a href="post_video.html" class="follow">Postar</a>
            </div>
            <p class="name">@${channelName}</p>
        `;
    }
    bannerContainer.insertAdjacentHTML("beforeend", bannerElement);

    fetch(`http://localhost:8080/users/${channelName}/videos`)
    .then(response => response.json())
    .then(data => {
        const videos = data.content;

        videos.forEach((video) => {
            let videoElement = `
                <div class="video">
                    <a href="video.html?id=${video.id}">
                        <div class="thumbnail">
                            <img src="" alt="" srcset="">
                        </div>
                    </a>
                    <div class="info">
                        <img src="" alt="" srcset="" class="icon">
                        <div class="details">
                            <p>${video.title}</p>
                            <p>@${video.channel}</p>
                        </div>
                    </div>
            `;
            
            if (channelName == getUsername()) {
                videoElement += `
                    <div class="delete-video" data-id="${video.id}">&#10060;</div>
                `;
            }
            
            videoElement += `</div>`;
            videosContainer.insertAdjacentHTML("beforeend", videoElement);
        });
        
        if (channelName == getUsername()) {
            document.querySelectorAll(".delete-video").forEach(button => {
                button.addEventListener("click", (event) => {
                    const videoId = event.target.dataset.id;
                    if (confirm("Tem certeza que deseja deletar esse vídeo?")) {
                        fetch(`http://localhost:8080/videos/${videoId}`, {
                            method: "DELETE",
                            headers: { "Authorization": "Bearer " + jwtToken }
                        }).then(response => {
                            if (response.ok) {
                                location.reload();
                            } else {
                                alert("Erro ao deletar o vídeo.");
                            }
                        });
                    }
                });
            });
        }
    })
    .catch(error => console.error("Erro ao buscar vídeos:", error));
} else {
    window.location.href = "http://127.0.0.1:5500";
}
