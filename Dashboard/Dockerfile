
FROM node:18-alpine AS build-stage


WORKDIR /app


COPY package*.json ./
RUN npm install --legacy-peer-deps


COPY . .


ENV NODE_OPTIONS=--openssl-legacy-provider


RUN npm run build 


FROM nginx:alpine


COPY --from=build-stage /app/dist/Dashboard /usr/share/nginx/html


EXPOSE 80


CMD ["nginx", "-g", "daemon off;"]
