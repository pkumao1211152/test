package tf.vo.req;

import java.util.HashMap;
import java.util.Map;

public class VariablesVo {

	private String postId;
	
	private Map<String, String> postMeteringOptions = new HashMap<String, String>();

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Map<String, String> getPostMeteringOptions() {
		return postMeteringOptions;
	}

	public void setPostMeteringOptions(Map<String, String> postMeteringOptions) {
		this.postMeteringOptions = postMeteringOptions;
	}

	public VariablesVo(String postId) {
		super();
		this.postId = postId;
	
	}
	
	public VariablesVo() {
	
	}
	
}
