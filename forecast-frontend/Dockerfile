# Build stage
FROM node:20-alpine AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN apk add --no-cache python3 make g++ && npm ci
COPY . .
RUN npm run build --prod

# Final stage
FROM nginx:alpine
COPY --from=build /app/dist/forecast-frontend/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
