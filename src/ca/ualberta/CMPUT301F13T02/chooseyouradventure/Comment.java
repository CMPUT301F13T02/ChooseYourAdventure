package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

public class Comment {

    private User poster;
    
    public Comment() {
    	
    }
    
    public User getPoster() {
    	return poster;
    }
    
    public boolean equals(Comment comment) {
    	return poster.equals(comment.getPoster());
    }
}
