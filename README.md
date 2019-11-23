# Quarkus で OpenAPI 入門

今回は以下のジェネレーターを使用します。
https://openapi-generator.tech/docs/generators/typescript-angular


ng コマンドは以下のコンテナを利用します。
https://hub.docker.com/r/trion/ng-cli/

`~/.bash_rc`に以下のコマンドを追加しておきましょう。

```
alias ng='docker run -u $(id -u) -p 4200:4200 --rm -v "$PWD":/app trion/ng-cli ng'
```

これで `ng` コマンドは docker 内で実行されることとなります。

```
$ ng new myQuarkusAngular

...
$ cd myQuarkusAngular
```

Angular のバージョンを試しに`9.0.0-rc`ってことにしてみます。

```shell
$ docker run --rm \
  -v ${PWD}:/local openapitools/openapi-generator-cli generate \
  -i http://docker.for.mac.localhost:8082/openapi \
  -g typescript-angular \
  -o /local/src/app/quarkus-client \
  -c /local/openapi-config.json
```

```
Exception in thread "main" java.lang.NumberFormatException: For input string: "0-rc"
        at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
        at java.lang.Integer.parseInt(Integer.java:580)
        at java.lang.Integer.parseInt(Integer.java:615)
        at org.openapitools.codegen.utils.SemVer.<init>(SemVer.java:30)
        at org.openapitools.codegen.languages.TypeScriptAngularClientCodegen.processOpts(TypeScriptAngularClientCodegen.java:145)
        at org.openapitools.codegen.DefaultGenerator.configureGeneratorProperties(DefaultGenerator.java:196)
        at org.openapitools.codegen.DefaultGenerator.generate(DefaultGenerator.java:913)
        at org.openapitools.codegen.cmd.Generate.run(Generate.java:416)
        at org.openapitools.codegen.OpenAPIGenerator.main(OpenAPIGenerator.java:61)
```
`-rc`はダメな模様。。

生成された/src/app/quarkus-client/README.mdの'In your Angular project'
を参考に実装を進めます。

"text/plain" のモノは受け取れない模様・。。。
以下のPRで絶賛、炎上中？
- https://github.com/OpenAPITools/openapi-generator/pull/3021

というわけで、text/plainはダメ！

さらに

以下の手順で紹介されている `Fruits` のAPIはOpenAPI対応がうまくできてないです。

- https://quarkus.io/guides/rest-json
- https://quarkus.io/guides/openapi-swaggerui

`Set<Fruit>` がうまく解決できない模様。List<Fruit> ならちゃんとarrayで定義されるので大丈夫です。
