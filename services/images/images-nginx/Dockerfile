FROM nginx:alpine

COPY ./nginx.conf /etc/nginx/nginx.conf

RUN mkdir -p /etc/nginx/html/images/nouser@example.com

COPY ./images/nouser@example.com /etc/nginx/html/images/nouser@example.com

RUN chown -R nginx:nginx /etc/nginx/html/images

EXPOSE 4000

CMD ["nginx", "-g", "daemon off;"]
