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

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * This class stores all of the layout builders needed for interacting with decisions. 
 * This prepares data and the view, but does not display anything. Its sister class DecisionView creates the GUI 
 * From data set in this class
 *
 * @author James Cadek
 */

public class DecisionLayoutBuilder {
	private ViewPageActivity pageActivity;
	private StoryController storyController; 
	
	
	public DecisionLayoutBuilder(StoryController storyController, ViewPageActivity pageActivity) {
		super();
		this.pageActivity = pageActivity;
		this.storyController = storyController;
	}

	/**
	 * Adds a decision to the page. If we are in editing mode, give the view a
	 * onClickListener to allow you to edit the decision. If we are in 
	 * viewing mode add an onClickListener to go to the next page.
	 * 
	 * @param i
	 * @param decision
	 * @param decisionsLayout
	 */
	protected void addDecision(int i, Decision decision, LinearLayout decisionsLayout) {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT
				);
		TextView view = new TextView(pageActivity);
		lp.setMargins(0, 0, 0, 3);
		view.setPadding(20, 5, 0, 5);
		view.setBackgroundColor(0xFFFFFFFF);
		view.setLayoutParams(lp);
		view.setText(decision.getText());
		decisionsLayout.addView(view, i);
		if(!pageActivity.isFighting()){
			view.setVisibility(View.VISIBLE);
		}
		else if(pageActivity.getEditing() == true){
			view.setVisibility(View.VISIBLE);
		}
		else{			
			boolean outcome = passThreshold(decision);
			if(outcome == false){
				view.setVisibility(View.GONE);
			}
		}

		if (pageActivity.getEditing()) {
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pageActivity.decisionMenu(v);
				}
			});
		} else {
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pageActivity.decisionClicked(v);
				}
			});
		}
	}
	
	/**
	 * This decides if a particular decision will be shown, based on the levels of various counters
	 * @param decision
	 * @return a true of false if the decision will be shown
	 */
	private boolean passThreshold(Decision decision) {
		int type = decision.getChoiceModifiers().getThresholdType();
		int sign = decision.getChoiceModifiers().getThresholdSign();
		int value = decision.getChoiceModifiers().getThresholdValue();
		Counters counter = storyController.getStory().getPlayerStats();
		boolean outcome = false;
		int[] typeBase = {counter.getPlayerHpStat(),counter.getEnemyHpStat(),counter.getTreasureStat()};
		switch(sign){
			case(0):
				if(typeBase[type] < value){outcome = true;};
				break;
			case(1):
				if(typeBase[type] > value){outcome = true;};
				break;
			case(2):
				if(typeBase[type] == value){outcome = true;};
				break;
		}
		return outcome;
	}
	
	/**
	 * Removes all the decisions from the decisionsLayout and repopulates it
	 * with the current state of the decisions.
	 * @param page
	 * @param decisionsLayout
	 */
	protected void updateDecisions(Page page, LinearLayout decisionsLayout) {
		decisionsLayout.removeAllViews();
		
		//For each decision in the page, add it to decisionsLayout
		ArrayList<Decision> decisions = page.getDecisions();
		for (int i = 0; i < decisions.size(); i++) {
			addDecision(i, decisions.get(i), decisionsLayout);
			
			
		}
	}

}
