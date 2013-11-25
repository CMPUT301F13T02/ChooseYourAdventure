/*
* Copyright (c) 2013, TeamCMPUT301F13T02
* All rights reserved.
* 
* Redistribution and use in source and binary forms, with or without modification,
* are permitted provided that the following conditions are met:
* 
* Redistributions of source code must retain the above copyright notice, this
* list of conditions and the following disclaimer.
* 
* Redistributions in binary form must reproduce the above copyright notice, this
* list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
* 
* Neither the name of the {organization} nor the names of its
* contributors may be used to endorse or promote products derived from
* this software without specific prior written permission.
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;
import java.util.UUID;

public class PageController {
	private Page currentPage;
	
	// These variables shouldn't be saved.
	private ViewPageActivity pageActivity;
	private boolean tilesChanged;
	private boolean decisionsChanged;
	private boolean commentsChanged;
	private boolean endingChanged;
	private boolean onEntry;
	
	/**
	 * This sets the current Page
	 * @param A Page
	 */
	public void setPage(Page page) {
		this.currentPage = page;
		
		tilesChanged = true;
		decisionsChanged = true;
		commentsChanged = true;
		endingChanged = true;
	}
	
	/**
	 * This gets the current page
	 * @return The current Page
	 */
	public Page getPage() {
		return currentPage;
	}
	
	/**
	 * Lets the controller know that the view has finished updating by setting
	 * the *Changed variables back to false.
	 */
	public void finishedUpdating() {
		tilesChanged = false;
		decisionsChanged = false;
		commentsChanged = false;
		endingChanged = false;
	}
	
	/**
	 * Sets the tilesChanged mark to true so that the ViewPageActivity will 
	 * know that the tiles need to be updated, and then tells the 
	 * ViewPageActivity to update.
	 */
	public void setTilesChanged() {
		tilesChanged = true;
		updateActivity();
	}
	
	/**
	 * Get whether the tiles list has changed since the last update. 
	 * @return Returns whether tiles have changed.
	 */
	public boolean haveTilesChanged() {
		return tilesChanged;
	}
	
	/**
	 * Sets decisionsChanged to true so that the ViewPageActivity will know
	 * that the decisions need to be updated, and then tells the 
	 * ViewPageActivity to update.
	 */
	public void setDecisionsChanged() {
		decisionsChanged = true;
		updateActivity();
	}
	
	/**
	 * Get whether the decisions list has changed since the last update/
	 * @return Returns whether the decisions have changed.
	 */
	public boolean haveDecisionsChanged() {
		return decisionsChanged;
	}
	
	/**
	 * Sets commentsChanged to true so the ViewPageActivity will know to
	 * update the comments, and then tells ViewPageActivity to update.
	 */
	public void setCommentsChanged() {
		commentsChanged = true;
		updateActivity();
	}
	
	/**
	 * Get whether the comments have changed since the last update.
	 * @return Whether the comments have changed.
	 */
	public boolean haveCommentsChanged() {
		return commentsChanged;
	}
	
	/**
	 * Sets endingChanged to true so the ViewPageActivity will know to 
	 * update the ending, and then tells ViewPageActivity to update.
	 */
	public void setEndingChanged() {
		endingChanged = true;
		updateActivity();
	}
	
	/**
	 * Get whether the ending has changed since the last update.
	 * @return Whether the ending has changed.
	 */
	public boolean hasEndingChanged() {
		return endingChanged;
	}
	
	

	/**
	 * Calls the update method of the current ViewPageActivity associated with
	 * this page.
	 */
	private void updateActivity() {
		if (pageActivity != null) {
			pageActivity.update(currentPage);
		}
	}
	
	/**
	 * Sets the page ending the given string.
	 * @param text
	 */
	public void setEnding(String text) {
		currentPage.setPageEnding(text);
		setEndingChanged();
	}
	
	/**
	 * Deletes the decision at position whichDecision.
	 * @param whichDecision
	 */
	public void deleteDecision(int whichDecision) {
		currentPage.deleteDecision(whichDecision);
		setDecisionsChanged();
	}
	
	/**
	 * Adds a decision to the currentPage.
	 * @param decision
	 */
	public void addDecision() {
		Decision decision = new Decision(currentPage);
		currentPage.addDecision(decision);
		setDecisionsChanged();
	}
	
	public Decision findDecisionByIndex(int whichDecision){
		return getPage().getDecisions().get(whichDecision);
	}
	
	/**
	 * Sets all the changed variables to true and then tells ViewPageActivity
	 * to update. Basically it tells ViewPageActivity to refresh the whole
	 * view.
	 */
	public void reloadPage() {
		tilesChanged = true;
		decisionsChanged = true;
		commentsChanged = true;
		endingChanged = true;
		updateActivity();
	}
	
	/**
	 * Sets the view associated with the currentPage to activity.
	 * @param activity
	 */
	public void setActivity(ViewPageActivity activity) {
		pageActivity = activity;
	}

	/**
	 * Removes the view associated with this page.
	 */
	public void deleteActivity() {
		pageActivity = null;
	}
	
	public int findArrayPosition(Decision decision, ArrayList<Page> pages){		
		UUID toPageId = decision.getPageID();
		int toPagePosition = -1;
		for (int i = 0; i < pages.size(); i++) {

			UUID comparePage = pages.get(i).getId();
			System.out.println("toPageID: " + toPageId + "\ncomparePage: " + comparePage + "\nPage: " + getPage() + "\nDecision: " + decision.getPageID() + decision.getText());
			if (toPageId.equals(comparePage)) {
				toPagePosition = i;
				break;
			}
		}
		return toPagePosition;
	}
	
	/**
	 * Adds a tile to the currentPage.
	 * @param tile
	 */
	public void addTile(Tile tile) {
		currentPage.addTile(tile);
		setTilesChanged();
	}
	
	/**
	 * Updates the tile at position whichTile to show the given string.
	 * @param text
	 * @param whichTile
	 */
	public void updateTile(String text, int whichTile) {
		currentPage.updateTile(text, whichTile);
		setTilesChanged();
	}

	/**
	 * Deletes a tile from the page.
	 * @param whichTile
	 */
	public void deleteTile(int whichTile) {
		currentPage.removeTile(whichTile);
		setTilesChanged();
	}
	
	
	
	public boolean isOnEntry() {
		return onEntry;
	}

	public void setOnEntry(boolean onEntry) {
		this.onEntry = onEntry;
	}
	
	public void calculateEntry(Page toPage, UUID toPageId){
		setOnEntry(true);
		if(currentPage.getId().equals(toPageId) == true){
			setOnEntry(false);
		}
		setPage(toPage);		
		reloadPage();
		
	}

}
