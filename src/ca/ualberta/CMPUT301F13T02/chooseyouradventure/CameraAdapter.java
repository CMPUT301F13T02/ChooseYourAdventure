package ca.ualberta.CMPUT301F13T02.chooseyouradventure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;







public class CameraAdapter {
	
	
	private ControllerApp app;
	private ViewPageActivity pageActivity;

	private Object tempSpace;
	

	
	public CameraAdapter(ControllerApp app, ViewPageActivity pageActivity) {
		super();
		this.app = app;
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


	
	
