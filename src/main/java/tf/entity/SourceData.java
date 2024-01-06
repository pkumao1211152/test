package tf.entity;

public class SourceData {
    private String postId;
    private String title;
    private Integer clapCount;
    private String mediumUrl;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getClapCount() {
        return clapCount;
    }

    public void setClapCount(Integer clapCount) {
        this.clapCount = clapCount;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }
}
