
package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * This class displays the data pertaining to decisions in the form of a GUI. 
 *
 */

public class DecisionView {
	
	private ViewPageActivity pageActivity;
	private StoryController storyController; 
	private PageController pageController; 

	
	public DecisionView(StoryController storyController,PageController pageController, ViewPageActivity pageActivity) {
		super();
		this.pageActivity = pageActivity;
		this.storyController = storyController;
        this.pageController = pageController;
	}
	
	
	/**
	 * The most fundamental page of a decision.
	 * This lets the user name the decision and select the target page
	 * If the fragment is fighting, this page contains all the statistic values
	 * The menu is adaptive so only the traits needed are shown on a page by page basis.
	 * @param view
	 * @param decisionsLayout
	 */
	protected void onEditDecisionGUI(View view, final LinearLayout decisionsLayout){
		int whichDecision = decisionsLayout.indexOfChild(view);
		final Decision decision = pageController.getPage().getDecisions().get(whichDecision);
		final Story story = storyController.getStory();
		final Page page = pageController.getPage();
		
		ArrayList<Page> pages = story.getPages();
		int toPagePosition = pageController.findArrayPosition(decision, pages);
		
		final TextView decisionView = (TextView) view;
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
    	builder.setTitle(pageActivity.getString(R.string.setTextandPage));
    	
    	final LinearLayout layout = (LinearLayout) View.inflate(pageActivity, R.layout.edit_decision_dialog, null);
    	final LinearLayout combatOptions = (LinearLayout) layout.findViewById(R.id.edit_decision_dialog_page_combatoptions);
    	final LinearLayout fightOptions = (LinearLayout) layout.findViewById(R.id.edit_decision_dialog_page_fightoptions);
    	final Spinner pageSpinner = (Spinner) layout.findViewById(R.id.edit_decision_dialog_page_spinner);
    	final EditText decisionText = (EditText) layout.findViewById(R.id.edit_decision_dialog_decision_edittext);
    	final EditText alertTreasure = (EditText) layout.findViewById(R.id.edit_decision_dialog_coin_edittext);
    	final EditText playerDamage = (EditText) layout.findViewById(R.id.edit_decision_dialog_playerDamage_edittext); 
    	final EditText enemyDamage = (EditText) layout.findViewById(R.id.edit_decision_dialog_enemyDamage_edittext); 
    	final SeekBar seekPlayer = (SeekBar) layout.findViewById(R.id.edit_decision_dialog_playerPerc); 
    	final SeekBar seekEnemy = (SeekBar) layout.findViewById(R.id.edit_decision_dialog_enemyPerc); 
    	decisionText.setText(decision.getText());
    	
    	ArrayList<String> pageStrings = getPageStrings(pages);
    	ArrayAdapter<String> pagesAdapter = new ArrayAdapter<String>(pageActivity, R.layout.list_item_base, pageStrings);

    	if(page.getDecisions().size() > 2){
    		pageStrings.add(pageActivity.getString(R.string.randomChoice));
    	}

    	pageSpinner.setAdapter(pagesAdapter);
    	pageSpinner.setSelection(toPagePosition);
    	
    	if(!story.isUsesCombat()) {
			combatOptions.setVisibility(View.GONE);
    	}
    	else {

        	alertTreasure.setText("" + decision.getChoiceModifiers().getTreasureStat());
        	playerDamage.setText("" + decision.getChoiceModifiers().getPlayerHpStat());

        	if(!pageActivity.isFighting()) {
        		fightOptions.setVisibility(View.GONE);
        	}
        	else {
        		
            	seekPlayer.setProgress(decision.getChoiceModifiers().getPlayerHitPercent());
            	enemyDamage.setText("" + decision.getChoiceModifiers().getEnemyHpStat());
            	seekEnemy.setProgress(decision.getChoiceModifiers().getEnemyHitPercent());
        	}
    	}

    	builder.setView(layout);
    	builder.setPositiveButton(pageActivity.getString(R.string.done), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	Counters counter = decision.getChoiceModifiers();
        		int decisionNumber = decisionsLayout.indexOfChild(decisionView);
        		Page page = storyController.getPageFromSpinner(pageSpinner);
        		if(story.isUsesCombat() == true){
        			String treasure = alertTreasure.getText().toString();
        			String hp = playerDamage.getText().toString();
        			if(!pageActivity.isFighting()){      				
        				counter.setBasic(treasure, hp);
        			}
	        		else{
	        			String ehp = enemyDamage.getText().toString();
	        			String hitP = "" + seekPlayer.getProgress();
	        			String hitE = "" + seekEnemy.getProgress();
	        			
	        			counter.setStats(treasure, hp, ehp, hitE, hitP);
	        			
	        		}     			
        		}
        		
        		
            	pageController.updateDecision(decisionText.getText().toString(), 
            			page, decisionNumber, counter);
        		
            }
        })
        .setNegativeButton(pageActivity.getString(R.string.cancel), null);
    	builder.show();
	}

	/**
	 * This menu lets the player set the conditionals of when a particular page is shown
	 * @param view
	 * @param decisionsLayout
	 */
	protected void onEditConditionsGUI(View view, LinearLayout decisionsLayout){
		final int whichDecision = decisionsLayout.indexOfChild(view);
		final Decision decision = pageController.findDecisionByIndex(whichDecision);
		ArrayList<Page> pages = storyController.getPages();
		int toPagePosition = pageController.findArrayPosition(decision, pages);
    	AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
    	builder.setTitle(pageActivity.getString(R.string.setDecisionConditions));
    	
    	final LinearLayout layout = (LinearLayout) View.inflate(pageActivity, R.layout.edit_conditionals_dialog, null);
    	final EditText decisionText = (EditText) layout.findViewById(R.id.edit_conditionals_dialog_decision_edittext);
    	final EditText conditionText = (EditText) layout.findViewById(R.id.edit_conditionals_dialog_threshold_edittext);
    	final Spinner pageSpinner = (Spinner) layout.findViewById(R.id.edit_conditionals_dialog_page_spinner);
    	final Spinner condSpinner = (Spinner) layout.findViewById(R.id.edit_conditionals_dialog_type_spinner);
    	final Spinner signSpinner = (Spinner) layout.findViewById(R.id.edit_conditionals_dialog_op_spinner);
    	
    	conditionText.setText("" + decision.getChoiceModifiers().getThresholdValue());
    	decisionText.setText(decision.getText());
    	
    	ArrayList<String> pageStrings = getPageStrings(pages);
    	ArrayAdapter<String> pagesAdapter = new ArrayAdapter<String>(pageActivity, R.layout.list_item_base, pageStrings);
    	pageSpinner.setAdapter(pagesAdapter);
    	pageSpinner.setSelection(toPagePosition);
    	
    	
    	condSpinner.setSelection(decision.getChoiceModifiers().getThresholdType());
    	
    	
    	signSpinner.setSelection(decision.getChoiceModifiers().getThresholdSign());
    	
    	builder.setView(layout);
    	builder.setPositiveButton(pageActivity.getString(R.string.done), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
        		Counters counter = decision.getChoiceModifiers();
        		counter.setThresholds(signSpinner.getSelectedItemPosition(), condSpinner.getSelectedItemPosition(), conditionText.getText().toString());
        		Page page = storyController.getPageFromSpinner(pageSpinner);
        		pageController.updateDecision(decisionText.getText().toString(), page, whichDecision, counter);
            }
        })
        .setNegativeButton(pageActivity.getString(R.string.cancel), null);
    	builder.show();
	}
	
	/**
	 * This function lets the player choose the messages that will display to let them know that a change in couunters has occured.
	 * @param view
	 * @param decisionsLayout
	 */
	protected void onEditMessages(View view, LinearLayout decisionsLayout){
		final int whichDecision = decisionsLayout.indexOfChild(view);
		final Decision decision = pageController.findDecisionByIndex(whichDecision);
		ArrayList<Page> pages = storyController.getPages();
		int toPagePosition = pageController.findArrayPosition(decision, pages);
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
    	builder.setTitle(pageActivity.getString(R.string.counterMessage));
    	
    	final LinearLayout layout = (LinearLayout) View.inflate(pageActivity, R.layout.edit_messages_dialog, null);
    	final EditText decisionTitle = (EditText) layout.findViewById(R.id.edit_messages_dialog_decision_edittext);
    	final EditText dMessage = (EditText) layout.findViewById(R.id.edit_messages_dialog_takeDamage_edittext);
    	final EditText hMessage = (EditText) layout.findViewById(R.id.edit_messages_dialog_giveDamage_edittext);
    	final EditText tMessage = (EditText) layout.findViewById(R.id.edit_messages_dialog_coin_edittext);
    	final Spinner pageSpinner = (Spinner) layout.findViewById(R.id.edit_messages_dialog_page_spinner);

    	ArrayList<String> pageStrings = getPageStrings(pages);
    	ArrayAdapter<String> pagesAdapter = new ArrayAdapter<String>(pageActivity, R.layout.list_item_base, pageStrings);
    	pageSpinner.setAdapter(pagesAdapter);
    	pageSpinner.setSelection(toPagePosition);
    	
    	decisionTitle.setText(decision.getText());
    	dMessage.setText("" + decision.getChoiceModifiers().getDamageMessage());
    	hMessage.setText("" + decision.getChoiceModifiers().getHitMessage());
    	tMessage.setText("" + decision.getChoiceModifiers().getTreasureMessage());
    	
    	builder.setView(layout);
    	builder.setPositiveButton(pageActivity.getString(R.string.done), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
        		Counters counter = decision.getChoiceModifiers();
        		counter.setMessages(dMessage.getText().toString(), tMessage.getText().toString(), hMessage.getText().toString());
        		Page page = storyController.getPageFromSpinner(pageSpinner);
        		pageController.updateDecision(decisionTitle.getText().toString(), page,whichDecision, counter);
            }
        })
        .setNegativeButton(pageActivity.getString(R.string.cancel), null);
    	builder.show();
	}
	
	/**
	 * This is the main menu for a decision, which directs the players to more specific settings pages.
	 * @param view
	 * @param decisionsLayout
	 */
	protected void decisionMenuGUI(final View view, final LinearLayout decisionsLayout){
		final String[] titles;
		final String[] titlesBasic = { pageActivity.getString(R.string.editProperties), pageActivity.getString(R.string.delete), pageActivity.getString(R.string.cancel) };
		final String[] titlesCounter = { pageActivity.getString(R.string.editProperties), pageActivity.getString(R.string.delete),
				                          pageActivity.getString(R.string.transitionMessages), pageActivity.getString(R.string.cancel) };
		final String[] titlesFight = { pageActivity.getString(R.string.editProperties), pageActivity.getString(R.string.delete), pageActivity.getString(R.string.transitionMessages),
				                        pageActivity.getString(R.string.setConditionals), pageActivity.getString(R.string.cancel) };
		final boolean fighting = pageActivity.isFighting();
		final boolean combat = storyController.getStory().isUsesCombat();
		if(fighting == true){
			titles = titlesFight;
		}
		else if(combat == true){
			titles = titlesCounter;
		}
		else{
			titles = titlesBasic;
		}
        AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
        builder.setTitle(R.string.story_options);
        builder.setItems(titles, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
            	int whichDecision = decisionsLayout.indexOfChild(view);
            	switch(item){
            	case(0):
            		onEditDecisionGUI(view, decisionsLayout);
            		break;
            	case(1):
            		pageController.deleteDecision(whichDecision);
            		break;
            	case(2):
            		if(combat == true){
            			onEditMessages(view, decisionsLayout);          		     
            		}
            		break;
            	case(3):
            		if(fighting == true){
            			onEditConditionsGUI(view, decisionsLayout);
            		}
            		break;
            	}
            }
        });
        builder.show();
	}
	
	/**
	 * Returns a list of strings for each page to be displayed in the Spinner
	 * for editing a decision.
	 * @param pages
	 * @return A list of Strings, one representing each page in the story
	 */
	public ArrayList<String> getPageStrings(ArrayList<Page> pages) {
		ArrayList<String> pageNames = new ArrayList<String>();
		for (int i = 0; i < pages.size(); i++) {
			pageNames.add("(" + pages.get(i).getRefNum() + ") " + pages.get(i).getTitle());
		}		
		return pageNames;
	}
}
