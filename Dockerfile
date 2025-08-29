FROM node:18-alpine as build
WORKDIR /app
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ .
RUN npm run build

FROM nginx:alpine
RUN rm /etc/nginx/conf.d/default.conf
COPY frontend/nginx.conf /etc/nginx/conf.d/
# CORREÇÃO: Copia o CONTEÚDO da pasta login-page
COPY --from=build /app/dist/login-page/* /usr/share/nginx/html/
EXPOSE 80
