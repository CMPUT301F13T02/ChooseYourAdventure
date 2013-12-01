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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * This class displays the data pertaining to comments in the form of a GUI. 
 *
 */
public class CommentView {
	private PageController pageController;
	private ViewPageActivity pageActivity;
	private CameraAdapter camera;
	
	
	public CommentView(PageController pageController, ViewPageActivity pageActivity, CameraAdapter camera) {
		super();
		this.pageController = pageController;
		this.pageActivity = pageActivity;
		this.camera = camera;
	}
	
	/**
	 * The first dialog that appears after clicked a comment, which dictates the image component of a comment
	 */
	protected void onCallCommentGUI(){
		final String[] titlesPhoto = { pageActivity.getString(R.string.noImage), pageActivity.getString(R.string.fromFile),
				pageActivity.getString(R.string.takePhoto) };
		final AlertDialog.Builder photoSelector = 
				new AlertDialog.Builder(pageActivity); 
		photoSelector.setTitle(pageActivity.getString(R.string.usePhotoComment));
		photoSelector.setItems(titlesPhoto, 
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, 
					int item) {
				switch(item){
				case(0):
					pageActivity.onEditComment();
				break;
				case(1):
					pageActivity.grabPhoto();	            		
				break;
				case(2):
					pageActivity.addPhoto();            		
				break;
				}

			}
		}
		);
		photoSelector.show();
	}
	
	/**
	 * This dialog is where the user enters what text they would like in their comment
	 * @param story
	 */
	protected void onEditCommentGUI(final Story story){
		AlertDialog.Builder builder = new AlertDialog.Builder(pageActivity);
    	builder.setTitle(pageActivity.getString(R.string.whatToSay));
    	
    	final LinearLayout layout = new LinearLayout(pageActivity);
    	layout.setOrientation(LinearLayout.VERTICAL);
    	
    	final EditText alertEdit = new EditText(pageActivity);
    	layout.addView(alertEdit);
    	
    	final ImageView alertImage = new ImageView(pageActivity);
    	
    	final PhotoTile photoAdd = (PhotoTile) camera.getTempSpace();
		camera.setTempSpace(null);

		if(photoAdd != null)
			alertImage.setImageBitmap(photoAdd.getImage());

    	layout.addView(alertImage);
    	
		builder.setView(layout);
    	builder.setPositiveButton(pageActivity.getString(R.string.save), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	pageController.addComment(alertEdit.getText().toString(),photoAdd, story );
            }
        })
        .setNegativeButton(pageActivity.getString(R.string.cancel), null);
    	builder.show();
	}
	
	


}
