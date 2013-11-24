package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import java.util.ArrayList;
import java.util.UUID;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class DecisionGUIs {
	private ControllerApp app;
	private ViewPageActivity pageActivity;
	private StoryController storyController; 
	private PageController pageController; 
	private TileView tileView;
	private DecisionView decisionView;
	
	public DecisionGUIs(ControllerApp app, ViewPageActivity pageActivity) {
		super();
		this.app = app;
		this.pageActivity = pageActivity;
		storyController = app.getStoryController();
        pageController = app.getPageController();
        tileView = new TileView(app, pageActivity);
        decisionView = new DecisionView(app, pageActivity);
	}
	
	protected AlertDialog onEditDecisionGUI(View view, final LinearLayout decisionsLayout){
		int whichDecision = decisionsLayout.indexOfChild(view);
		final Decision decision = pageController.getPage().getDecisions().get(whichDecision);
		final Story story = storyController.getStory();
		final Page page = pageController.getPage();
		UUID toPageId = decision.getPageID();
		ArrayList<Page> pages = story.getPages();
		int toPagePosition = -1;
		for (int i = 0; i < pages.size(); i++) {

			UUID comparePage = pages.get(i).getId();
			System.out.println("toPageID: " + toPageId + "\ncomparePage: " + comparePage + "\nPage: " + page + "\nDecision: " + decision.getPageID() + decision.getText());
			if (toPageId.equals(comparePage)) {
				toPagePosition = i;
				break;
			}
		}
		
		final TextView decisionView = (TextView) view;
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
    	builder.setTitle(app.getString(R.string.setTextandPage));
    	
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
    	
    	ArrayList<String> pageStrings = app.getPageStrings(pages);
    	ArrayAdapter<String> pagesAdapter = new ArrayAdapter<String>(pageActivity, R.layout.list_item_base, pageStrings);

    	if(page.getDecisions().size() > 2){
    		pageStrings.add(app.getString(R.string.randomChoice));
    	}

    	pageSpinner.setAdapter(pagesAdapter);
    	pageSpinner.setSelection(toPagePosition);
    	
    	if(!story.isUsesCombat()) {
			combatOptions.setVisibility(View.GONE);
    	}
    	else {

        	alertTreasure.setText("" + decision.getChoiceModifiers().getTreasureStat());
        	playerDamage.setText("" + decision.getChoiceModifiers().getPlayerHpStat());

        	if(!page.isFightingFrag()) {
        		fightOptions.setVisibility(View.GONE);
        	}
        	else {
        		
            	seekPlayer.setProgress(decision.getChoiceModifiers().getPlayerHitPercent());
            	enemyDamage.setText("" + decision.getChoiceModifiers().getEnemyHpStat());
            	seekEnemy.setProgress(decision.getChoiceModifiers().getEnemyHitPercent());
        	}
    	}

    	builder.setView(layout);
    	builder.setPositiveButton(app.getString(R.string.done), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	Counters counter = decision.getChoiceModifiers();
        		int decisionNumber = decisionsLayout.indexOfChild(decisionView);
        		if(story.isUsesCombat() == true){
        			String treasure = alertTreasure.getText().toString();
        			String hp = playerDamage.getText().toString();
        			if(page.isFightingFrag() == false){      				
        				counter.setBasic(treasure, hp);
	        			app.updateDecisionFight(decisionText.getText().toString(), 
	                			pageSpinner.getSelectedItemPosition(), decisionNumber, counter);
        			}
	        		else{
	        			String ehp = enemyDamage.getText().toString();
	        			String hitP = "" + seekPlayer.getProgress();
	        			String hitE = "" + seekEnemy.getProgress();
	        			
	        			counter.setStats(treasure, hp, ehp, hitE, hitP);
	        			app.updateDecisionFight(decisionText.getText().toString(), 
	                			pageSpinner.getSelectedItemPosition(), decisionNumber, counter);
	        		}     			
        		}
        		else{
        		
            	app.updateDecision(decisionText.getText().toString(), 
            			pageSpinner.getSelectedItemPosition(), decisionNumber);
        		}
            }
        })
        .setNegativeButton(app.getString(R.string.cancel), null);
    	return builder.create();
	}

	protected AlertDialog onEditConditionsGUI(View view, LinearLayout decisionsLayout){
		final int whichDecision = decisionsLayout.indexOfChild(view);
		final Decision decision = pageController.findDecisionByIndex(whichDecision);
		ArrayList<Page> pages = storyController.getPages();
		int toPagePosition = pageController.findArrayPosition(decision, pages);
    	AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
    	builder.setTitle(app.getString(R.string.setDecisionConditions));
    	
    	final LinearLayout layout = (LinearLayout) View.inflate(pageActivity, R.layout.edit_conditionals_dialog, null);
    	final EditText decisionText = (EditText) layout.findViewById(R.id.edit_conditionals_dialog_decision_edittext);
    	final EditText conditionText = (EditText) layout.findViewById(R.id.edit_conditionals_dialog_threshold_edittext);
    	final Spinner pageSpinner = (Spinner) layout.findViewById(R.id.edit_conditionals_dialog_page_spinner);
    	final Spinner condSpinner = (Spinner) layout.findViewById(R.id.edit_conditionals_dialog_type_spinner);
    	final Spinner signSpinner = (Spinner) layout.findViewById(R.id.edit_conditionals_dialog_op_spinner);
    	
    	conditionText.setText("" + decision.getChoiceModifiers().getThresholdValue());
    	decisionText.setText(decision.getText());
    	
    	ArrayList<String> pageStrings = app.getPageStrings(pages);
    	ArrayAdapter<String> pagesAdapter = new ArrayAdapter<String>(pageActivity, R.layout.list_item_base, pageStrings);
    	pageSpinner.setAdapter(pagesAdapter);
    	pageSpinner.setSelection(toPagePosition);
    	
    	
    	condSpinner.setSelection(decision.getChoiceModifiers().getThresholdType());
    	
    	
    	signSpinner.setSelection(decision.getChoiceModifiers().getThresholdSign());
    	
    	builder.setView(layout);
    	builder.setPositiveButton(app.getString(R.string.done), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
        		Counters counter = decision.getChoiceModifiers();
        		counter.setThresholds(signSpinner.getSelectedItemPosition(), condSpinner.getSelectedItemPosition(), conditionText.getText().toString());
        		app.updateDecisionFight(decisionText.getText().toString(), pageSpinner.getSelectedItemPosition(), whichDecision, counter);
            }
        })
        .setNegativeButton(app.getString(R.string.cancel), null);
    	return builder.create();
	}
	
	protected AlertDialog onEditMessages(View view, LinearLayout decisionsLayout){
		final int whichDecision = decisionsLayout.indexOfChild(view);
		final Decision decision = pageController.findDecisionByIndex(whichDecision);
		ArrayList<Page> pages = storyController.getPages();
		int toPagePosition = pageController.findArrayPosition(decision, pages);
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
    	builder.setTitle(app.getString(R.string.counterMessage));
    	
    	final LinearLayout layout = (LinearLayout) View.inflate(pageActivity, R.layout.edit_messages_dialog, null);
    	final EditText decisionTitle = (EditText) layout.findViewById(R.id.edit_messages_dialog_decision_edittext);
    	final EditText dMessage = (EditText) layout.findViewById(R.id.edit_messages_dialog_takeDamage_edittext);
    	final EditText hMessage = (EditText) layout.findViewById(R.id.edit_messages_dialog_giveDamage_edittext);
    	final EditText tMessage = (EditText) layout.findViewById(R.id.edit_messages_dialog_coin_edittext);
    	final Spinner pageSpinner = (Spinner) layout.findViewById(R.id.edit_messages_dialog_page_spinner);

    	ArrayList<String> pageStrings = app.getPageStrings(pages);
    	ArrayAdapter<String> pagesAdapter = new ArrayAdapter<String>(pageActivity, R.layout.list_item_base, pageStrings);
    	pageSpinner.setAdapter(pagesAdapter);
    	pageSpinner.setSelection(toPagePosition);
    	
    	decisionTitle.setText(decision.getText());
    	dMessage.setText("" + decision.getChoiceModifiers().getDamageMessage());
    	hMessage.setText("" + decision.getChoiceModifiers().getHitMessage());
    	tMessage.setText("" + decision.getChoiceModifiers().getTreasureMessage());
    	
    	builder.setView(layout);
    	builder.setPositiveButton(app.getString(R.string.done), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
        		Counters counter = decision.getChoiceModifiers();
        		counter.setMessages(dMessage.getText().toString(), tMessage.getText().toString(), hMessage.getText().toString());
        		app.updateDecisionFight(decisionTitle.getText().toString(), pageSpinner.getSelectedItemPosition(),whichDecision, counter);
            }
        })
        .setNegativeButton(app.getString(R.string.cancel), null);
    	return builder.create();
	}
	
	protected AlertDialog decisionMenuGUI(final View view, final LinearLayout decisionsLayout){
		final String[] titles;
		final String[] titlesBasic = { app.getString(R.string.editProperties), app.getString(R.string.delete), app.getString(R.string.cancel) };
		final String[] titlesCounter = { app.getString(R.string.editProperties), app.getString(R.string.delete),
				                          app.getString(R.string.transitionMessages), app.getString(R.string.cancel) };
		final String[] titlesFight = { app.getString(R.string.editProperties), app.getString(R.string.delete), app.getString(R.string.transitionMessages),
				                        app.getString(R.string.setConditionals), app.getString(R.string.cancel) };
		final boolean fighting = pageController.getPage().isFightingFrag();
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
            		pageActivity.onEditDecision(view);
            		break;
            	case(1):
            		pageController.deleteDecision(whichDecision);
            		break;
            	case(2):
            		if(combat == true){
            			pageActivity.onEditMessages(view);
            		}
            		break;
            	case(3):
            		if(fighting == true){
            			pageActivity.onEditConditionals(view);
            		}
            		break;
            	}
            }
        });
        return builder.create();
	}
}
