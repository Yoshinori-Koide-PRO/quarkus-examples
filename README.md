# quarkus-examples
Quarkus のサンプル実装あれこれ

## プロジェクト構築

https://quarkus.io/guides/maven-tooling を参考に。

```
$ mvn io.quarkus:quarkus-maven-plugin:0.27.0:create
```

## プラグインの追加

```
$ cd my-quarkus-project
$ mvn quarkus:add-extension -Dextensions="quarkus-jdbc-postgresql,quarkus-smallrye-metrics,quarkus-smallrye-openapi,quarkus-smallrye-opentracing,quarkus-hibernate-orm-panache,quarkus-keycloak-authorization,quarkus-smallrye-jwt"
```