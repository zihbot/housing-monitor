FROM node:15.11-alpine as build
LABEL stage=intermediate
WORKDIR /usr/src/app

COPY package.json ./
RUN ["npm", "install", "--legacy-peer-deps"]

COPY tsconfig.json ./
COPY ./public ./public
COPY ./src ./src
RUN ["npm", "run", "build"]

FROM nginx:stable-alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/src/app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]