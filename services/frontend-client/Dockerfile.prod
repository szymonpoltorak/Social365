FROM alpine:3.19.1 AS build

WORKDIR /social365

RUN apk update --no-cache && apk upgrade --no-cache && apk add --no-cache nodejs npm

COPY package*.json ./

RUN npm cache clean --force

RUN npm install

COPY . .

RUN npm run ng build --configuration=production

FROM nginx:alpine

COPY --from=build /social365/dist/frontend-client/browser /etc/nginx/html

COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
