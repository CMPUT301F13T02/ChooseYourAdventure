package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

public class Comment {

	private String poster;
	private String text;

	public Comment(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
	
	public String getPoster() {
		return poster;
	}
	
	public boolean equals(Comment comment) {
		return text.equals(comment.getText());
	}
}
