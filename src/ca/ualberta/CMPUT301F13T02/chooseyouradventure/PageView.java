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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * This generates all the GUI elements for EditStoryActivity.
 * 
 *
 */
public class PageView {
	private ApplicationController app;
	private EditStoryActivity storyActivity;
	private StoryController storyController; 
	

	
	public PageView(ApplicationController app, EditStoryActivity storyActivity) {
		super();
		this.app = app;
		this.storyActivity = storyActivity;
		storyController = app.getStoryController();
        
	}
	/**
	 * This is the main options menu when you click on a page in a story in the story tree.
	 * @param pos
	 */
	protected void pageOptionsGUI(final int pos){
		final Story story = storyController.getStory();
        final AlertDialog.Builder titleEditor = new AlertDialog.Builder(storyActivity);
		final Page currentPage = story.getPages().get(pos);
		final Page FP = storyController.grabFirstPage();

		String[] titlesA = { storyActivity.getString(R.string.gotoEdit), storyActivity.getString(R.string.pageProperties), storyActivity.getString(R.string.cancel) };
		String[] titlesB = { storyActivity.getString(R.string.gotoEdit), storyActivity.getString(R.string.pageProperties), storyActivity.getString(R.string.assignFirst),
							storyActivity.getString(R.string.delete), storyActivity.getString(R.string.cancel) };

		final String[] titles;

		if(currentPage == FP)
			titles = titlesA;
		else
			titles = titlesB;
        
        AlertDialog.Builder builder = new AlertDialog.Builder(storyActivity);
        builder.setTitle(R.string.page_options);
        builder.setItems(titles, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
            	
            	switch(item){
            	case(0):
            		
            		app.jump(ViewPageActivity.class,story,story.getPages().get(pos));
            		
            	break;
            	case(1):
            		
                	final LinearLayout layout = (LinearLayout) View.inflate(titleEditor.getContext(), R.layout.create_page_dialog, null);
	            	final LinearLayout fightingLayout = (LinearLayout) layout.findViewById(R.id.create_page_dialog_fighting_options);
	            	final EditText titleEdit = (EditText) layout.findViewById(R.id.create_page_dialog_edittext);
	            	final EditText healthEdit = (EditText) layout.findViewById(R.id.create_page_dialog_health_edittext);
                	final EditText nameEdit = (EditText) layout.findViewById(R.id.create_page_dialog_name_edittext);
                	final CheckBox check = (CheckBox) layout.findViewById(R.id.create_page_dialog_checkbox);
	            	
	            	if(!story.isUsesCombat()) {
	            		fightingLayout.setVisibility(View.GONE);
	            	}
	            	else {
	            		
	                	

	            		check.setChecked(currentPage.getFightingState());
	            		healthEdit.setText("" + currentPage.getEnemyHealth());
	            		nameEdit.setText(currentPage.getEnemyName());
	            	}
            		
	            	titleEdit.setText(currentPage.getTitle());
	            	
            		titleEditor.setTitle(storyActivity.getString(R.string.createNew));
            		titleEditor.setView(layout);
            		titleEditor.setMessage(storyActivity.getString(R.string.enterPageTitle))
            		.setPositiveButton(storyActivity.getString(R.string.save), new DialogInterface.OnClickListener() {

            			public void onClick(DialogInterface dialog, int id) {
            				String pageTitle = titleEdit.getText().toString();
            				if(story.isUsesCombat() == true){
            					storyController.updatePageData(pageTitle, check.isChecked(), healthEdit.getText().toString(), nameEdit.getText().toString(), currentPage); 
            				}
            				else{
            					storyController.updatePageData(pageTitle, false, null, null, currentPage);    
            				}
            				storyActivity.refresh();
            			}
            		})
            		.setNegativeButton(storyActivity.getString(R.string.cancel), null);

            		titleEditor.show();
            		
            		break;

            	case(2):
            		storyController.updateFP(currentPage);
            		storyActivity.refresh();
            		break;
            	case(3):
            		storyController.removePage(pos);
            		storyActivity.refresh();
            		break;
            	}

            }	
                });
        builder.show();
	}
	/**
	 * This is teh menu for creating a new page
	 */
	protected	void createPageGUI(){
		final LinearLayout layout = (LinearLayout) View.inflate(storyActivity, R.layout.create_page_dialog, null);
    	final EditText titleEdit = (EditText) layout.findViewById(R.id.create_page_dialog_edittext);
    	final EditText healthEdit = (EditText) layout.findViewById(R.id.create_page_dialog_health_edittext);
    	final EditText nameEdit = (EditText) layout.findViewById(R.id.create_page_dialog_name_edittext);
    	final CheckBox check = (CheckBox) layout.findViewById(R.id.create_page_dialog_checkbox);
    	final LinearLayout fightingLayout = (LinearLayout) layout.findViewById(R.id.create_page_dialog_fighting_options);
    	if(!storyController.getStory().isUsesCombat())
    		fightingLayout.setVisibility(View.GONE);
    	nameEdit.setText("Enemy");
    	healthEdit.setText("0");
    	AlertDialog.Builder builder = new AlertDialog.Builder(storyActivity);
    	builder.setView(layout);
    	builder.setTitle(storyActivity.getString(R.string.createNew));
    	builder.setMessage(storyActivity.getString(R.string.enterPageTitle))
    	.setPositiveButton(storyActivity.getString(R.string.save), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	storyController.newPage(titleEdit.getText().toString(), check.isChecked(), healthEdit.getText().toString(), nameEdit.getText().toString());         	
            	storyActivity.refresh();
            }
        })
        .setNegativeButton(storyActivity.getString(R.string.cancel), null);
    	builder.show();
	}

}
