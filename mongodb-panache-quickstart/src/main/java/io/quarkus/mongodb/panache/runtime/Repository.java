package io.quarkus.mongodb.panache.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.EncoderContext;
import org.eclipse.microprofile.config.ConfigProvider;

import io.quarkus.arc.Arc;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.mongodb.panache.runtime.PanacheQlQueryBinder;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

public class Repository<Entity extends PanacheMongoEntity> {
    public static final String ID = "_id";
    public static final String MONGODB_DATABASE = "quarkus.mongodb.database";

    public static final String SEP = "+";
    private Entity inst;
    private Class<Entity> entityClass;

    public Repository(Supplier<Entity> supplier) {
        this.inst = supplier.get();
        this.entityClass = (Class<Entity>)inst.getClass();
    }

    //
    // Instance methods
    public String collectionSuffix;

    private String getCollectionName(String prefix) {
        return collectionSuffix != null && collectionSuffix.length() > 0 ? 
        prefix + SEP + collectionSuffix : prefix;
    }

    public static String escape(Object value) {
        return CommonQueryBinder.escape(value);
    }

    public void persist(Entity entity) {
        MongoCollection<Entity> collection = mongoCollection(entity);
        persist(collection, entity);
    }

    public void persist(Iterable<Entity> entities) {
        // not all iterables are re-traversal, so we traverse it once for copying inside a list
        List<Entity> objects = new ArrayList<>();
        for (Entity entity : entities) {
            objects.add(entity);
        }

        if (objects.size() > 0) {
            // get the first entity to be able to retrieve the collection with it
            Entity firstEntity = objects.get(0);
            MongoCollection<Entity> collection = mongoCollection(firstEntity);
            persist(collection, objects);
        }
    }

    public void persist(Entity firstEntity, Entity[] entities) {
        MongoCollection<Entity> collection = mongoCollection(firstEntity);
        if (entities == null || entities.length == 0) {
            persist(collection, firstEntity);
        } else {
            List<Entity> entityList = new ArrayList<>();
            entityList.add(firstEntity);
            entityList.addAll(Arrays.asList(entities));
            persist(collection, entityList);
        }
    }

    public void persist(Stream<Entity> entities) {
        List<Entity> objects = entities.collect(Collectors.toList());
        if (objects.size() > 0) {
            // get the first entity to be able to retrieve the collection with it
            Entity firstEntity = objects.get(0);
            MongoCollection<Entity> collection = mongoCollection(firstEntity);
            persist(collection, objects);
        }
    }

    public void update(Entity entity) {
        MongoCollection<Entity> collection = mongoCollection(entity);
        update(collection, entity);
    }

    public void update(Iterable<Entity> entities) {
        // not all iterables are re-traversal, so we traverse it once for copying inside a list
        List<Entity> objects = new ArrayList<>();
        for (Entity entity : entities) {
            objects.add(entity);
        }

        if (objects.size() > 0) {
            // get the first entity to be able to retrieve the collection with it
            Entity firstEntity = objects.get(0);
            MongoCollection<Entity> collection = mongoCollection(firstEntity);
            update(collection, objects);
        }
    }

    public void update(Entity firstEntity, Entity[] entities) {
        MongoCollection<Entity> collection = mongoCollection(firstEntity);
        if (entities == null || entities.length == 0) {
            update(collection, firstEntity);
        } else {
            List<Entity> entityList = new ArrayList<>();
            entityList.add(firstEntity);
            entityList.addAll(Arrays.asList(entities));
            update(collection, entityList);
        }
    }

    public void update(Stream<Entity> entities) {
        List<Entity> objects = entities.collect(Collectors.toList());
        if (objects.size() > 0) {
            // get the first entity to be able to retrieve the collection with it
            Entity firstEntity = objects.get(0);
            MongoCollection<Entity> collection = mongoCollection(firstEntity);
            update(collection, objects);
        }
    }

    public void persistOrUpdate(Entity entity) {
        MongoCollection<Entity> collection = mongoCollection(entity);
        persistOrUpdate(collection, entity);
    }

    public void persistOrUpdate(Iterable<Entity> entities) {
        // not all iterables are re-traversal, so we traverse it once for copying inside a list
        List<Entity> objects = new ArrayList<>();
        for (Entity entity : entities) {
            objects.add(entity);
        }

        if (objects.size() > 0) {
            // get the first entity to be able to retrieve the collection with it
            Entity firstEntity = objects.get(0);
            MongoCollection<Entity> collection = mongoCollection(firstEntity);
            persistOrUpdate(collection, objects);
        }
    }

    public void persistOrUpdate(Entity firstEntity, Entity[] entities) {
        MongoCollection<Entity> collection = mongoCollection(firstEntity);
        if (entities == null || entities.length == 0) {
            persistOrUpdate(collection, firstEntity);
        } else {
            List<Entity> entityList = new ArrayList<>();
            entityList.add(firstEntity);
            entityList.addAll(Arrays.asList(entities));
            persistOrUpdate(collection, entityList);
        }
    }

    public void persistOrUpdate(Stream<Entity> entities) {
        List<Entity> objects = entities.collect(Collectors.toList());
        if (objects.size() > 0) {
            // get the first entity to be able to retrieve the collection with it
            Entity firstEntity = objects.get(0);
            MongoCollection<Entity> collection = mongoCollection(firstEntity);
            persistOrUpdate(collection, objects);
        }
    }

    public void delete(Entity entity) {
        MongoCollection<Entity> collection = mongoCollection(entity);
        BsonDocument document = getBsonDocument(collection, entity);
        BsonValue id = document.get(ID);
        BsonDocument query = new BsonDocument().append(ID, id);
        collection.deleteOne(query);
    }

    public MongoCollection<Entity> mongoCollection() {
        MongoEntity mongoEntity = entityClass.getAnnotation(MongoEntity.class);
        MongoDatabase database = mongoDatabase(mongoEntity);
        if (mongoEntity != null && !mongoEntity.collection().isEmpty()) {
            return database.getCollection(getCollectionName(mongoEntity.collection()), entityClass);
        }
        return database.getCollection(entityClass.getSimpleName(), entityClass);
    }

    //
    // Private stuff

    private void persist(MongoCollection<Entity> collection, Entity entity) {
        collection.insertOne(entity);
    }

    private void persist(MongoCollection<Entity> collection, List<Entity> entities) {
        collection.insertMany(entities);
    }

    private void update(MongoCollection<Entity> collection, Entity entity) {
        //we transform the entity as a document first
        BsonDocument document = getBsonDocument(collection, entity);

        //then we get its id field and create a new Document with only this one that will be our replace query
        BsonValue id = document.get(ID);
        BsonDocument query = new BsonDocument().append(ID, id);
        collection.replaceOne(query, entity);
    }

    private void update(MongoCollection<Entity> collection, List<Entity> entities) {
        for (Entity entity : entities) {
            update(collection, entity);
        }
    }

    private void persistOrUpdate(MongoCollection<Entity> collection, Entity entity) {
        //we transform the entity as a document first
        BsonDocument document = getBsonDocument(collection, entity);

        //then we get its id field and create a new Document with only this one that will be our replace query
        BsonValue id = document.get(ID);
        if (id == null) {
            //insert with autogenerated ID
            collection.insertOne(entity);
        } else {
            //insert with user provided ID or update
            BsonDocument query = new BsonDocument().append(ID, id);
            collection.replaceOne(query, entity, ReplaceOptions.createReplaceOptions(new UpdateOptions().upsert(true)));
        }
    }

    private void persistOrUpdate(MongoCollection<Entity> collection, List<Entity> entities) {
        //this will be an ordered bulk: it's less performant than a unordered one but will fail at the first failed write
        List<WriteModel<Entity>> bulk = new ArrayList<>();
        for (Entity entity : entities) {
            //we transform the entity as a document first
            BsonDocument document = getBsonDocument(collection, entity);

            //then we get its id field and create a new Document with only this one that will be our replace query
            BsonValue id = document.get(ID);
            if (id == null) {
                //insert with autogenerated ID
                bulk.add(new InsertOneModel<Entity>(entity));
            } else {
                //insert with user provided ID or update
                BsonDocument query = new BsonDocument().append(ID, id);
                bulk.add(new ReplaceOneModel<Entity>(query, entity,
                        ReplaceOptions.createReplaceOptions(new UpdateOptions().upsert(true))));
            }
        }

        collection.bulkWrite(bulk);
    }

    private BsonDocument getBsonDocument(MongoCollection<Entity> collection, Entity entity) {
        BsonDocument document = new BsonDocument();
        Codec<Entity> codec = (Codec<Entity>)collection.getCodecRegistry().get(entity.getClass());
        codec.encode(new BsonDocumentWriter(document), entity, EncoderContext.builder().build());
        return document;
    }

    private MongoCollection<Entity> mongoCollection(Entity entity) {
        return mongoCollection();
    }

    private MongoDatabase mongoDatabase(MongoEntity entity) {
        MongoClient mongoClient = Arc.container().instance(MongoClient.class).get();
        if (entity != null && !entity.database().isEmpty()) {
            return mongoClient.getDatabase(entity.database());
        }
        String databaseName = ConfigProvider.getConfig()
                .getValue(MONGODB_DATABASE, String.class);
        return mongoClient.getDatabase(databaseName);
    }

    //
    // Queries

    public Entity findById(Object id) {
        MongoCollection<Entity> collection = mongoCollection();
        return collection.find(new Document(ID, id)).first();
    }

    public Optional<Entity> findByIdOptional(Object id) {
        return Optional.ofNullable(findById(id));
    }

    public PanacheQuery<Entity> find(String query, Object... params) {
        return find(query, null, params);
    }

    public PanacheQuery<Entity> find(String query, Sort sort, Object... params) {
        String bindQuery = bindQuery(entityClass, query, params);
        Document docQuery = Document.parse(bindQuery);
        Document docSort = sortToDocument(sort);
        MongoCollection<Entity> collection = mongoCollection();
        return new PanacheQueryImpl<Entity>(collection, entityClass, docQuery, docSort);
    }

    /**
     * We should have a query like <code>{'firstname': ?1, 'lastname': ?2}</code> for native one
     * and like <code>firstname = ?1</code> for PanacheQL one.
     */
    String bindQuery(Class<Entity> entryClass, String query, Object[] params) {
        String bindQuery = null;

        //determine the type of the query
        if (query.charAt(0) == '{') {
            //this is a native query
            bindQuery = NativeQueryBinder.bindQuery(query, params);
        } else {
            //this is a PanacheQL query
            bindQuery = PanacheQlQueryBinder.bindQuery(entryClass, query, params);
        }

        return bindQuery;
    }

    /**
     * We should have a query like <code>{'firstname': :firstname, 'lastname': :lastname}</code> for native one
     * and like <code>firstname = :firstname and lastname = :lastname</code> for PanacheQL one.
     */
    String bindQuery(Class<Entity> entryClass, String query, Map<String, Object> params) {
        String bindQuery = null;

        //determine the type of the query
        if (query.charAt(0) == '{') {
            //this is a native query
            bindQuery = NativeQueryBinder.bindQuery(query, params);
        } else {
            //this is a PanacheQL query
            bindQuery = PanacheQlQueryBinder.bindQuery(entryClass, query, params);
        }

        return bindQuery;
    }

    public PanacheQuery<Entity> find(String query, Map<String, Object> params) {
        return find(query, null, params);
    }

    public PanacheQuery<Entity> aggs(String query) {
        return find(Document.parse(query));
    }

    public PanacheQuery<Entity> find(String query, Sort sort, Map<String, Object> params) {
        String bindQuery = bindQuery(entityClass, query, params);
        Document docQuery = Document.parse(bindQuery);
        Document docSort = sortToDocument(sort);
        MongoCollection<Entity> collection = mongoCollection();
        return new PanacheQueryImpl<Entity>(collection, entityClass, docQuery, docSort);
    }

    public PanacheQuery<Entity> find(String query, Parameters params) {
        return find(query, null, params.map());
    }

    public PanacheQuery<Entity> find(String query, Sort sort, Parameters params) {
        return find(query, sort, params.map());
    }

    public PanacheQuery<Entity> find(Document query, Sort sort) {
        MongoCollection<Entity> collection = mongoCollection();
        Document sortDoc = sortToDocument(sort);
        return new PanacheQueryImpl<Entity>(collection, entityClass, query, sortDoc);
    }

    public PanacheQuery<Entity> find(Document query, Document sort) {
        MongoCollection<Entity> collection = mongoCollection();
        return new PanacheQueryImpl<Entity>(collection, entityClass, query, sort);
    }

    public PanacheQuery<Entity> find(Document query) {
        return find(query, (Document) null);
    }

    public List<Entity> list(String query, Object... params) {
        return find(query, params).list();
    }

    public List<Entity> list(String query, Sort sort, Object... params) {
        return find(query, sort, params).list();
    }

    public List<Entity> list(String query, Map<String, Object> params) {
        return find(query, params).list();
    }

    public List<Entity> list(String query, Sort sort, Map<String, Object> params) {
        return find(query, sort, params).list();
    }

    public List<Entity> list(String query, Parameters params) {
        return find(query, params).list();
    }

    public List<Entity> list(String query, Sort sort, Parameters params) {
        return find(query, sort, params).list();
    }

    //specific Mongo query
    public List<Entity> list(Document query) {
        return find(query).list();
    }

    //specific Mongo query
    public List<Entity> list(Document query, Document sort) {
        return find(query, sort).list();
    }

    public Stream<Entity> stream(String query, Object... params) {
        return find(query, params).stream();
    }

    public Stream<Entity> stream(String query, Sort sort, Object... params) {
        return find(query, sort, params).stream();
    }

    public Stream<Entity> stream(String query, Map<String, Object> params) {
        return find(query, params).stream();
    }

    public Stream<Entity> stream(String query, Sort sort, Map<String, Object> params) {
        return find(query, sort, params).stream();
    }

    public Stream<Entity> stream(String query, Parameters params) {
        return find(query, params).stream();
    }

    public Stream<Entity> stream(String query, Sort sort, Parameters params) {
        return find(query, sort, params).stream();
    }

    //specific Mongo query
    public Stream<Entity> stream(Document query) {
        return find(query).stream();
    }

    //specific Mongo query
    public Stream<Entity> stream(Document query, Document sort) {
        return find(query, sort).stream();
    }

    public PanacheQuery<Entity> findAll() {
        MongoCollection<Entity> collection = mongoCollection();
        return new PanacheQueryImpl<Entity>(collection, entityClass, null, null);
    }

    public PanacheQuery<Entity> findAll(Sort sort) {
        MongoCollection<Entity> collection = mongoCollection();
        Document sortDoc = sortToDocument(sort);
        return new PanacheQueryImpl<Entity>(collection, entityClass, null, sortDoc);
    }

    private Document sortToDocument(Sort sort) {
        if (sort == null) {
            return null;
        }

        Document sortDoc = new Document();
        for (Sort.Column col : sort.getColumns()) {
            sortDoc.append(col.getName(), col.getDirection() == Sort.Direction.Ascending ? 1 : -1);
        }
        return sortDoc;
    }

    public List<Entity> listAll() {
        return findAll().list();
    }

    public List<Entity> listAll(Sort sort) {
        return findAll(sort).list();
    }

    public Stream<Entity> streamAll() {
        return findAll().stream();
    }

    public Stream<Entity> streamAll(Sort sort) {
        return findAll(sort).stream();
    }

    public long count() {
        MongoCollection<Entity> collection = mongoCollection();
        return collection.countDocuments();
    }

    public long count(String query, Object... params) {
        String bindQuery = bindQuery(entityClass, query, params);
        Document docQuery = Document.parse(bindQuery);
        MongoCollection<Entity> collection = mongoCollection();
        return collection.countDocuments(docQuery);
    }

    public long count(String query, Map<String, Object> params) {
        String bindQuery = bindQuery(entityClass, query, params);
        Document docQuery = Document.parse(bindQuery);
        MongoCollection<Entity> collection = mongoCollection();
        return collection.countDocuments(docQuery);
    }

    public long count(String query, Parameters params) {
        return count(query, params.map());
    }

    //specific Mongo query
    public long count(Document query) {
        MongoCollection<Entity> collection = mongoCollection();
        return collection.countDocuments(query);
    }

    public long deleteAll() {
        MongoCollection<Entity> collection = mongoCollection();
        return collection.deleteMany(new Document()).getDeletedCount();
    }

    public long delete(String query, Object... params) {
        String bindQuery = bindQuery(entityClass, query, params);
        Document docQuery = Document.parse(bindQuery);
        MongoCollection<Entity> collection = mongoCollection();
        return collection.deleteMany(docQuery).getDeletedCount();
    }

    public long delete(String query, Map<String, Object> params) {
        String bindQuery = bindQuery(entityClass, query, params);
        Document docQuery = Document.parse(bindQuery);
        MongoCollection<Entity> collection = mongoCollection();
        return collection.deleteMany(docQuery).getDeletedCount();
    }

    public long delete(String query, Parameters params) {
        return delete(query, params.map());
    }

    //specific Mongo query
    public long delete(Document query) {
        MongoCollection<Entity> collection = mongoCollection();
        return collection.deleteMany(query).getDeletedCount();
    }

}