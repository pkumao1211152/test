/**
  * Copyright 2024 bejson.com 
  */
package tf.vo;
import java.util.List;

/**
 * Auto-generated: 2024-01-05 23:45:38
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BodyModel {

    private List<Sections> sections;
    private List<Paragraphs> paragraphs;
    private String __typename;
    public void setSections(List<Sections> sections) {
         this.sections = sections;
     }
     public List<Sections> getSections() {
         return sections;
     }

    public void setParagraphs(List<Paragraphs> paragraphs) {
         this.paragraphs = paragraphs;
     }
     public List<Paragraphs> getParagraphs() {
         return paragraphs;
     }

    public void set__typename(String __typename) {
         this.__typename = __typename;
     }
     public String get__typename() {
         return __typename;
     }

}