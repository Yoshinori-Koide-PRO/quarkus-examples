# テンプレートエンジンのThymeleafを使用したプロジェクトはちゃんとNative化できるのか検証

手順についての記事はこちら→ https://qiita.com/koinori/items/7599cbb2db0dd8b17e79

めでたくやってるページを発見。

→ http://www.natswell.com/techcolumn/2019/09/13/quarkus-html-response/

```shell
$ mvn io.quarkus:quarkus-maven-plugin:1.0.0.CR1:create
...
$ cd thymeleaf-quarkus-project

$ mvn quarkus:add-extension -Dextensions="quarkus-jdbc-postgresql,quarkus-smallrye-metrics,quarkus-smallrye-openapi,quarkus-smallrye-opentracing,quarkus-hibernate-orm-panache,quarkus-resteasy-jsonb"
```

pom.xml の dependencies に追加

```xml:pom.xml
...
<dependency>
  <groupId>org.jboss.resteasy</groupId>
  <artifactId>resteasy-html</artifactId>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>org.thymeleaf</groupId>
  <artifactId>thymeleaf</artifactId>
  <version>3.0.11.RELEASE</version>
  <scope>compile</scope>
</dependency>
...
```

エラーが発生。。。。

```
org.thymeleaf.exceptions.TemplateProcessingException: Exception evaluating OGNL expression: "#temporals.format(p.birth,'yyyy/MM/dd')" (template: "META-INF/resources/person.html" - line 6, col 25)Error id 583145e9-b143-4723-aef6-0a7dc7a07391-1
```

`#temporals`がダメみたいなので、ちょっと初期化をRuntimeに持ってきてみる？

http://dplatz.de/blog/2018/graal-native-app.html

メソッドの注釈？を json で追加できる模様。

なんとかなった。