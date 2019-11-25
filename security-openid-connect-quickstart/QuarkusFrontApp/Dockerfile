FROM trion/ng-cli as build

ARG USER_ID=1000

USER $USER_ID

COPY --chown=$USER_ID:$USER_ID . /app

RUN npm install && \
  npm rebuild node-sass && \
  npm run build -- --output-path=./dist/out

FROM nginx:1.15

COPY --from=build /app/dist/out/ /usr/share/nginx/html
