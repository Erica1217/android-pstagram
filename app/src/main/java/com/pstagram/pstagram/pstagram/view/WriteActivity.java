package com.pstagram.pstagram.pstagram.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.pstagram.pstagram.pstagram.R;
import com.pstagram.pstagram.pstagram.RetrofitCreater;
import com.pstagram.pstagram.pstagram.UserIdManager;
import com.pstagram.pstagram.pstagram.Util;
import com.pstagram.pstagram.pstagram.databinding.ActivityWriteBinding;
import com.pstagram.pstagram.pstagram.model.UploadOutputModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WriteActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    private ActivityWriteBinding binding;
    private Context context;
    private File file;
    private final int MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write);
        context = this;
        requestReadExternalStoragePermission();
        binding.okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.productName.getText().toString().isEmpty()){
                    Util.showText(context, "상품이름을 입력해주세요");
                }
                else if (binding.ratingbar.getRating() == 0) {
                    Util.showText(context, "별점을 입력해주세요");
                } else if (file == null) {
                    Util.showText(context, "사진을 선택해주세요");
                } else if (binding.content.getText().toString().isEmpty()) {
                    Util.showText(context, "내용을 입력해주세요.");
                } else {
                    Map<String, RequestBody> requestData = new HashMap<String, RequestBody>();
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                    requestData.put("product_name",Util.createRequestBody(binding.productName.getText().toString()));
                    requestData.put("user_id", Util.createRequestBody(String.valueOf(UserIdManager.getInstance(context).getUserId())));
                    requestData.put("rate", Util.createRequestBody(String.valueOf(binding.ratingbar.getRating())));
                    requestData.put("content", Util.createRequestBody(binding.content.getText().toString()));
                    MultipartBody.Part fileBodyPart =
                            MultipartBody.Part.createFormData("image_upload", file.getName(), fileBody);
                    RetrofitCreater.newInstance().getService().uploadReview(requestData, fileBodyPart).enqueue(new Callback<UploadOutputModel>() {
                        @Override
                        public void onResponse(Call<UploadOutputModel> call, Response<UploadOutputModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getCode().equals("success")) {
                                    Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Util.showText(context, response.body().getMsg());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UploadOutputModel> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });

        binding.pictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            if (PackageManager.PERMISSION_GRANTED ==
                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                try {
//                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    String path = getRealPathFromURI(data.getData());
                    Bitmap image = BitmapFactory.decodeFile(path);
//                    file = new File(getRealPathFromURI(data.getData()));
                    ExifInterface exif = null;
                    try {
                        exif = new ExifInterface(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);
                    image = rotateBitmap(image, orientation);

                    file = reduceFileSize(image);
                    Glide.with(this).load(image).into(binding.preview);
                } catch (FileNotFoundException e) {
                    file = null;
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                requestReadExternalStoragePermission();
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        if (contentUri.getPath().startsWith("/storage")) {
            return contentUri.getPath();
        }
        String id = DocumentsContract.getDocumentId(contentUri).split(":")[1];
        String[] columns = {MediaStore.Files.FileColumns.DATA};
        String selection = MediaStore.Files.FileColumns._ID + " = " + id;
        Cursor cursor = getContentResolver().query(MediaStore.Files.getContentUri("external"), columns, selection, null, null);
        try {
            int columnIndex = cursor.getColumnIndex(columns[0]);
            if (cursor.moveToFirst()) {
                return cursor.getString(columnIndex);
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    private File reduceFileSize(Bitmap bitmap) throws IOException {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 2;
//        Bitmap orgImage = BitmapFactory.decodeFile(file.getPath(), options);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap resize = Bitmap.createScaledBitmap(bitmap, width, height, true);

        File f = new File(context.getCacheDir(), "test");
        f.createNewFile();

        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resize.compress(Bitmap.CompressFormat.JPEG, 60 /*ignored for PNG*/, bos);

        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

        return f;
    }

    private void requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE);
                // MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
