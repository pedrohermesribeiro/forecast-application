# Etapa 1: Build da aplicação Angular
FROM node:20-alpine AS build
WORKDIR /app

# Instala dependências necessárias para compilar pacotes nativos
RUN apk add --no-cache python3 make g++

# Copia os manifests de dependências
COPY package.json package-lock.json ./

# Instala dependências de forma reprodutível
RUN npm ci

# Copia o restante do código
COPY . .

# Faz o build em modo produção
RUN npm run build --prod

# Etapa 2: Servir com Nginx
FROM nginx:alpine

# Remove configuração padrão do Nginx
RUN rm /etc/nginx/conf.d/default.conf

# Copia o build do Angular da etapa anterior
COPY --from=build /app/dist/forecast-frontend /usr/share/nginx/html

# Copia configuração customizada do Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]


