package mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class Main {
  public static void main(String[] args) {
    // TODO: replace username, password and cluster-url with your own configuration data!
    String uri = "mongodb+srv://<username>:<password>@<cluster-url>?retryWrites=true&w=majority";
    MongoClient mongoClient = MongoClients.create(uri);

    MongoDatabase database = mongoClient.getDatabase("mongodb-stats");
    MongoCollection<Document> collection = database.getCollection("drivers");

    // insert multiple documents
    List<Document> movieList = Arrays.asList(
            new Document().append("language", "C").append("name", "MongoDB C Driver"),
            new Document().append("language", "C++").append("name", "MongoDB C++ Driver"),
            new Document().append("language", "C#").append("name", "MongoDB C#/.NET Driver"),
            new Document().append("language", "Go").append("name", "MongoDB Go Driver"),
            new Document().append("language", "JAVA").append("name", "Java MongoDB Driver"),
            new Document().append("language", "JAVA").append("name", "MongoDB Java Reactive Streams"),
            new Document().append("language", "Node.js").append("name", "MongoDB Node.js Driver"),
            new Document().append("language", "Perl").append("name", "MongoDB Perl Driver"),
            new Document().append("language", "PHP").append("name", "Mongo PHP Adapter"),
            new Document().append("language", "Python").append("name", "PyMongo"),
            new Document().append("language", "Python").append("name", "Motor (Async Driver)"),
            new Document().append("language", "Ruby").append("name", "Ruby MongoDB Driver"),
            new Document().append("language", "Ruby").append("name", "Mongoid"),
            new Document().append("language", "Scala").append("name", "MongoDB Scala Driver"),
            new Document().append("language", "Swift").append("name", "MongoDB Swift Driver"));

    try {
      InsertManyResult result = collection.insertMany(movieList);
      System.out.println("Inserted document ids: " + result.getInsertedIds());
    } catch (MongoException me) {
      System.err.println("Unable to insert any driver documents due to an error: " + me);
    }

    // find one of the inserted documents
    Document doc = collection.find().first();
    if(doc != null) {
      System.out.println(doc.toJson());
    }

    // update JAVA document to Java
    try {
      UpdateResult result = collection.updateMany(eq("language", "JAVA"),
              Updates.set("language", "Java"));
      System.out.println(String.format("Updated %d documents.", result.getModifiedCount()));

    } catch (MongoException me) {
      System.err.println("Unable to update due to an error: " + me);
    }

    // delete Perl driver document
    try {
      DeleteResult result = collection.deleteOne(new Document()
              .append("language", "Perl"));
      System.out.println(String.format("Deleted %d documents.", result.getDeletedCount()));

    } catch (MongoException me) {
      System.err.println("Unable to delete Perl driver document due to an error: " + me);
    }

    // insert Rust driver document
    try {
      InsertOneResult result = collection.insertOne(new Document()
              .append("language", "Rust").append("name", "MongoDB Rust Driver"));
      System.out.println("Inserted document id: " + result.getInsertedId());

    } catch (MongoException me) {
      System.err.println("Unable to insert Rust driver document due to an error: " + me);
    }

    // create a text index to enable text search on the name field of the collection
    try {
      collection.createIndex(Indexes.text(), new IndexOptions().languageOverride("en"));
      System.out.println("Created a text index on the drivers collection.");

    } catch (MongoException me) {
      System.err.println("Unable to create an index due to an error: " + me);
    }

    // find all drivers whose name does not end with the word "Driver" using regex
    Bson projectionFields = Projections.fields(
            Projections.include("language"),
            Projections.include("name"),
            Projections.excludeId());

    try (MongoCursor<Document> cursor = collection.find(Filters.not(Filters.regex("name", ".Driver")))
            .projection(projectionFields)
            .sort(Sorts.descending("language")).iterator()) {
      while (cursor.hasNext()) {
        System.out.println(cursor.next().toJson());
      }
    }

    // use aggregation to find every language that uses the word "MongoDB" in the driver name
    AggregateIterable<Document> aggregate = collection.aggregate(Arrays.asList(
            Aggregates.match(Filters.text("MongoDB")),
            Aggregates.group("$language")));

    aggregate.forEach(System.out::println);

    // delete all the driver documents we inserted
    try {
      collection.drop();
      System.out.println("Dropped collection.");

    } catch (MongoException me) {
      System.err.println("Unable to clean up collection due to an error: " + me);
    }

    // always close the connection when done working with the client
    mongoClient.close();
  }
}
