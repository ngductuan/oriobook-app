# build
FROM node:18.18-alpine AS build

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install

COPY . .
RUN npm run build

#run
FROM nginx:alpine

RUN adduser -D orio-fe

COPY --from=build /app/dist/ /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf

RUN chown -R orio-fe /usr/share/nginx/html /etc/nginx/conf.d

USER orio-fe

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"] 
