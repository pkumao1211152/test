/**
  * Copyright 2024 bejson.com 
  */
package tf.vo;

/**
 * Auto-generated: 2024-01-05 23:45:38
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class NewsletterV3 {

    private String id;
    private String type;
    private String slug;
    private String name;
    private String collection;
    private User user;
    private String __typename;
    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setSlug(String slug) {
         this.slug = slug;
     }
     public String getSlug() {
         return slug;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setCollection(String collection) {
         this.collection = collection;
     }
     public String getCollection() {
         return collection;
     }

    public void setUser(User user) {
         this.user = user;
     }
     public User getUser() {
         return user;
     }

    public void set__typename(String __typename) {
         this.__typename = __typename;
     }
     public String get__typename() {
         return __typename;
     }

}