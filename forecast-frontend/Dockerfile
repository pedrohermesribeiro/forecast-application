# Etapa 1: Build da aplicação Angular
FROM node:20-alpine AS build
WORKDIR /app

# Copia dependências
COPY package*.json ./
RUN npm install

# Copia o restante do código
COPY . .

# Faz o build em modo produção
RUN npm run build

FROM nginx:alpine

# Remove configuração padrão
RUN rm /etc/nginx/conf.d/default.conf

# Copia build do Angular
COPY dist/forecast-frontend /usr/share/nginx/html

# Copia configuração customizada
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

