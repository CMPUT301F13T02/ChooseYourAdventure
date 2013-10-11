package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

public interface Handler {

	public void updateStory(Story story);
	public void deleteStory(Story story);
	public void addStory(Story story);
	public void updatePage(Page page);
	public void addPage(Page page);
	public Page getPage(int id);
}
