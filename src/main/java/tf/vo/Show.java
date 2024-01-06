package tf.vo;

public class Show {

	private String originalText;
	
	private String chineseTest;

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	public String getChineseTest() {
		return chineseTest;
	}

	public void setChineseTest(String chineseTest) {
		this.chineseTest = chineseTest;
	}

	public Show(String originalText, String chineseTest) {
		super();
		this.originalText = originalText;
		this.chineseTest = chineseTest;
	}

	public Show() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
