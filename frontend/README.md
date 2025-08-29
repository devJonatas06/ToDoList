# 🛂 Auth App

Uma aplicação de autenticação simples com **Login** e **Signup**, utilizando Angular standalone components, reactive forms e integração com API REST.

## ✨ Funcionalidades

- ✅ Login com e-mail e senha
- 📝 Cadastro de novo usuário
- 🔒 Validação de formulário reativa
- 💾 Armazenamento de token em `sessionStorage`
- 🔁 Navegação entre páginas
- ⚠️ Toasts de feedback com ngx-toastr

## 🛠️ Tecnologias utilizadas

<div style="display:flex; gap:10px; align-items:center; flex-wrap:wrap">

<img src="https://angular.io/assets/images/logos/angular/angular.svg" alt="Angular" width="24"/> **Angular 17+**  
<img src="https://cdn.worldvectorlogo.com/logos/typescript.svg" alt="TypeScript" width="24"/> **TypeScript**  
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/sass/sass-original.svg" alt="Sass" width="24"/> **Sass**  
<img src="https://raw.githubusercontent.com/FortAwesome/Font-Awesome/master/svgs/solid/toast.svg" alt="Toastr" width="20"/> **ngx-toastr**  
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original.svg" width="24" /> **HTML5**  
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original.svg" width="24" /> **CSS3 (SCSS)**  
</div>

## 🚀 Como rodar

```bash
# Instale as dependências
npm install

# Rode o servidor local
ng serve

# Acesse em
http://localhost:4200/login

📂 Estrutura

src/
│
├── app/
│   ├── components/
│   ├── pages/
│   ├── services/
│   └── styles/
│
├── assets/svg/
└── main.ts

📢 Endpoints esperados da API

    POST /auth/login → Login do usuário

    POST /auth/register → Registro do usuário

📸 Screenshots
<p float="left"> <img src="screenshot-login.png" width="300"/> <img src="screenshot-signup.png" width="300"/> </p>

👨‍💻 Autor

[JonataFreitas](https://github.com/devJonatas06)

