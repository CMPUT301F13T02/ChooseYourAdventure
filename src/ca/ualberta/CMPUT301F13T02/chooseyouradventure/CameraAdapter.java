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

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;







public class CameraAdapter {
	
	
	private ViewPageActivity pageActivity;
	private Object tempSpace;
	

	
	public CameraAdapter(ViewPageActivity pageActivity) {
		super();
		this.pageActivity = pageActivity;        
	}


	public void getPhoto(int value){
		Intent i = new Intent(
        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pageActivity.startActivityForResult(i, value);
	}
	
	protected void newPhoto(int value) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		pageActivity.startActivityForResult(intent, value);
	}
	
	
	
	
	public PhotoTile loadImage(Intent data){
		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = pageActivity.getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();       	
		Bitmap pickedPhoto = BitmapFactory.decodeFile(picturePath);
		PhotoTile newPhoto = new PhotoTile();
		newPhoto.setImageFile(pickedPhoto);	
		return newPhoto;
	}
	
	public Bitmap retrievePhoto(Intent data){
		Bundle bundle = data.getExtras();
		return  (Bitmap) bundle.get("data");	
	}
	public ImageView makeViewByPhoto(Bitmap image){
		ImageView pictureTaken = new ImageView(pageActivity);
		pictureTaken.setImageBitmap(image);
		return pictureTaken;
	}
	
	public Object getTempSpace() {
		return tempSpace;
	}


	public void setTempSpace(Object loadObject) {
		this.tempSpace = loadObject;
		
	}
}


	
	
