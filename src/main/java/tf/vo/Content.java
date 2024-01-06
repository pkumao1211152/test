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
public class Content {

    private boolean isLockedPreviewOnly;
    private String __typename;
    private BodyModel bodyModel;
    private String validatedShareKey;
    private String shareKeyCreator;
    public void setIsLockedPreviewOnly(boolean isLockedPreviewOnly) {
         this.isLockedPreviewOnly = isLockedPreviewOnly;
     }
     public boolean getIsLockedPreviewOnly() {
         return isLockedPreviewOnly;
     }

    public void set__typename(String __typename) {
         this.__typename = __typename;
     }
     public String get__typename() {
         return __typename;
     }

    public void setBodyModel(BodyModel bodyModel) {
         this.bodyModel = bodyModel;
     }
     public BodyModel getBodyModel() {
         return bodyModel;
     }

    public void setValidatedShareKey(String validatedShareKey) {
         this.validatedShareKey = validatedShareKey;
     }
     public String getValidatedShareKey() {
         return validatedShareKey;
     }

    public void setShareKeyCreator(String shareKeyCreator) {
         this.shareKeyCreator = shareKeyCreator;
     }
     public String getShareKeyCreator() {
         return shareKeyCreator;
     }

}