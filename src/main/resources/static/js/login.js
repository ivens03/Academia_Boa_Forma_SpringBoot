/* src/main/resources/static/js/login.js */

document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('branding');
    if (!container) return;

    const elementCount = 20;
    const containerWidth = container.offsetWidth;
    const containerHeight = container.offsetHeight;
    const dumbbellImageUrl = 'https://img.icons8.com/?size=100&id=1784&format=png&color=000000'; // URL da imagem do halter

    for (let i = 0; i < elementCount; i++) {
        // Criação do elemento de imagem
        const element = document.createElement('img');
        element.src = dumbbellImageUrl;
        element.classList.add('falling-element');
        container.appendChild(element);

        // Animação com a lógica GSAP
        startAnimation(element);
    }

    function startAnimation(el) {
        const size = gsap.utils.random(40, 80); // Tamanhos variados
        gsap.set(el, {
            width: size,
            height: size,
            x: gsap.utils.random(0, containerWidth - size),
            y: -350,
            opacity: gsap.utils.random(0.5, 1),
        });

        gsap.to(el, {
            y: containerHeight + 100,
            rotation: gsap.utils.random(-360, 360),
            duration: gsap.utils.random(6, 12),
            ease: "none",
            onComplete: () => {
                startAnimation(el);
            }
        });
    }
});