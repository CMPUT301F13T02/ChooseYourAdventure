package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

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
