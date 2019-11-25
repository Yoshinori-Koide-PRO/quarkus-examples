# Quarkus REST API & OIDC Auth ＆ OpenAPIサンプル

https://quarkus.io/guides/security-openid-connect



## Client Generator

今回は Angualr の Client Generator を使用する

https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/typescript-angular.md

こちらも開発中だが悪くなさそう？OpenAPI v3と明記されているのは好感触。

https://github.com/cyclosproject/ng-openapi-gen


以下のコマンドで Angular アプリを生成する

```
$ ng ne QuarkusFrontApp
```
以下のコマンドでクライアントを生成する。

```
$ docker run --rm -v ${PWD}:/local openapitools/openapi-generator-cli generate \
  -i http://quarkus-backend.192.168.1.65.nip.io/openapi \
  -g typescript-angular \
  -o /local/QuarkusFrontApp/src/app/quarkus-api \
  -c /local/openapi-config.json
```

Angular のビルドしてみる

```
$ npm i
$ ng server --host 0.0.0.0
```

以下の作業
EnvironmentにAPI_BASE_PATHを書く
app.module.ts で ApiModuleを追加

app.component.ts でDefaultServiceを追加
