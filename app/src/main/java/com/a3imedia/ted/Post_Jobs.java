package com.a3imedia.ted;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Post_Jobs extends AppCompatActivity implements MultiSelectSpinner.OnMultipleItemsSelectedListener {


    List mSpinner_Key_val=new ArrayList();
    private Calendar mcalendar;
    private EditText aEnddate;
    private int day, month, year;
    ImageView aUpload_Photo;
    private String mBase64_conv, mImgFormat, mFilepath;
    LocationDatabase mLocation_Database;
    private static final int REQUEST_SELECT_PICTURE = 61;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    public void DateDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                aEnddate.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
            }
        };
        DatePickerDialog dpDialog = new DatePickerDialog(Post_Jobs.this, listener, year, month, day);
        dpDialog.show();
    }

    public static String Image_Path;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                    if (selectedUri != null) {
                        try {
                            Bitmap bitmap = decodeBitmapUri(this, selectedUri);
                            mBase64_conv = encodeImage(bitmap);
                            aUpload_Photo.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(Post_Jobs.this, "Can't Retrieve Image", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    }

    private String encodeImage(Bitmap photo) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] bytes = baos.toByteArray();
            return Base64.encodeToString(bytes, Base64.DEFAULT).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "imagepath";
        }
    }

    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }

    private void pickFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_SELECT_PICTURE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/font_qarmic_sans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_post__jobs);
        mLocation_Database = new LocationDatabase(Post_Jobs.this);
        mLocation_Database.open();
        mcalendar = Calendar.getInstance();
        day = mcalendar.get(Calendar.DAY_OF_MONTH);
        year = mcalendar.get(Calendar.YEAR);
        month = mcalendar.get(Calendar.MONTH);

        ((ImageView) findViewById(R.id.backbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post_Jobs.this.finish();
            }
        });

        final EditText aCompanyname = findViewById(R.id.Companyname);
        final EditText aContactno = findViewById(R.id.Contactno);
        aEnddate = findViewById(R.id.enddate);
        final Spinner aJobDesign = findViewById(R.id.jobdesignation);
        final Spinner aJobExp = findViewById(R.id.jobexperience);
        final Spinner aJobloca = findViewById(R.id.joblocation);
        aUpload_Photo = findViewById(R.id.carrerphoto);
        final MultiSelectSpinner aJobKeyskillsspin = findViewById(R.id.jobkeyskills);
        final Spinner aQualificationspin = findViewById(R.id.Qualification);
        final Spinner anoticeperiodspin = findViewById(R.id.noticeperiod);
        Glide.with(Post_Jobs.this).load(R.drawable.pnv_camera).into(aUpload_Photo);
        aUpload_Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery();
            }
        });
        aEnddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(aEnddate.getWindowToken(), 0);
                DateDialog();
            }
        });
        TextView aPostjobs = (TextView) findViewById(R.id.postjobs);
        ArrayList<String> aJobdesignlist = new ArrayList<>();
        aJobdesignlist.add("Designation");
        aJobdesignlist.add("Account Executive");
        aJobdesignlist.add("Administrative Assistant");
        aJobdesignlist.add("Administrative Manager");
        aJobdesignlist.add("Branch Manager");
        aJobdesignlist.add("Auditor");
        aJobdesignlist.add("HR Coordinator");
        aJobdesignlist.add("Mobile Developer");
        aJobdesignlist.add("Project Manager");
        aJobdesignlist.add("Content Manager");
        aJobdesignlist.add("Systems Engineer");
        aJobdesignlist.add("Web Developer");
        aJobdesignlist.add("Business Broker");
        aJobdesignlist.add("User Interface Specialist");
        aJobdesignlist.add("ISO consultant");

        LanguageLocale aJobdeadapter = new LanguageLocale(Post_Jobs.this, aJobdesignlist);
        aJobDesign.setAdapter(aJobdeadapter);

        ArrayList<String> aJobexplist = new ArrayList<>();
        aJobexplist.add("Experience");
        aJobexplist.add("1 Year");
        aJobexplist.add("1 to 2 years");
        aJobexplist.add("2 to 3 years");
        aJobexplist.add("3 to 4 years");
        aJobexplist.add("5 years");
        aJobexplist.add("6 years");
        aJobexplist.add("7 years");
        aJobexplist.add("8 years");
        aJobexplist.add("9 years");
        aJobexplist.add("10 years");

        LanguageLocale aJobExpadapter = new LanguageLocale(Post_Jobs.this, aJobexplist);
        aJobExp.setAdapter(aJobExpadapter);

        ArrayList<String> aJobdistrics = new ArrayList<>();
        aJobdistrics.add("Select Location");
        aJobdistrics.add("Ariyalur");
        aJobdistrics.add("Chennai");
        aJobdistrics.add("Coimbatore");
        aJobdistrics.add("Cuddalore");
        aJobdistrics.add("Dharmapuri");
        aJobdistrics.add("Dindigul");
        aJobdistrics.add("Erode");
        aJobdistrics.add("Kanchipuram");
        aJobdistrics.add("Kanyakumari");
        aJobdistrics.add("Karur");
        aJobdistrics.add("Krishnagiri");
        aJobdistrics.add("Madurai");
        aJobdistrics.add("Mandapam");
        aJobdistrics.add("Nagapattinam");
        aJobdistrics.add("Nilgiris");
        aJobdistrics.add("Namakkal");
        aJobdistrics.add("Perambalur");
        aJobdistrics.add("Pudukkottai");
        aJobdistrics.add("Ramanathapuram");
        aJobdistrics.add("Salem");
        aJobdistrics.add("Sivaganga");
        aJobdistrics.add("Thanjavur");
        aJobdistrics.add("Thiruvallur");
        aJobdistrics.add("Tirupur");
        aJobdistrics.add("Tiruchirapalli");
        aJobdistrics.add("Theni");
        aJobdistrics.add("Tirunelveli");
        aJobdistrics.add("Thanjavur");
        aJobdistrics.add("Thoothukudi");
        aJobdistrics.add("Tiruvallur");
        aJobdistrics.add("Tiruvannamalai");
        aJobdistrics.add("Vellore");
        aJobdistrics.add("Villupuram");
        aJobdistrics.add("Viruthunagar");
        LanguageLocale aJoblocaadapter = new LanguageLocale(Post_Jobs.this, aJobdistrics);
        aJobloca.setAdapter(aJoblocaadapter);
        ArrayList<String> aJobkeyskills = new ArrayList<>();
        aJobkeyskills.add("Key Skills");
        aJobkeyskills.add("Algorithms");
        aJobkeyskills.add("Analytical Skills");
        aJobkeyskills.add("Big Data");
        aJobkeyskills.add("Calculating");
        aJobkeyskills.add("Compiling Statistics");
        aJobkeyskills.add("Data Analytics");
        aJobkeyskills.add("Data Mining");
        aJobkeyskills.add("Database Design");
        aJobkeyskills.add("Database Management");
        aJobkeyskills.add("Documentation");
        aJobkeyskills.add("Modeling");
        aJobkeyskills.add("Modification");
        aJobkeyskills.add("Needs Analysis");
        aJobkeyskills.add("Quantitative Research");
        aJobkeyskills.add("Quantitative Reports");
        aJobkeyskills.add("Statistical Analysis");
        aJobkeyskills.add("Applications");
        aJobkeyskills.add("Certifications");
        aJobkeyskills.add("Coding");
        aJobkeyskills.add("Computing");
        aJobkeyskills.add("Configuration");
        aJobkeyskills.add("Customer Support");
        aJobkeyskills.add("Debugging");
        aJobkeyskills.add("Design");
        aJobkeyskills.add("Development");
        aJobkeyskills.add("Hardware");

        aJobkeyskills.add("Implementation");

        aJobkeyskills.add("Information Technology");

        aJobkeyskills.add("Infrastructure");

        aJobkeyskills.add("Languages");

        aJobkeyskills.add("Maintenance");

        aJobkeyskills.add("Network Architecture");

        aJobkeyskills.add("Network Security");

        aJobkeyskills.add("Networking");

        aJobkeyskills.add("New Technologies");
        aJobKeyskillsspin.setItems(aJobkeyskills);
        aJobKeyskillsspin.hasNoneOption(true);
        aJobKeyskillsspin.setSelection(new int[]{0});
        aJobKeyskillsspin.setListener(this);


        ArrayList<String> aQualilist = new ArrayList<>();
        aQualilist.add("Qualification");
        aQualilist.add("BE");
        aQualilist.add("BCA");
        aQualilist.add("Bsc");
        aQualilist.add("MCA");
        aQualilist.add("Msc");
        aQualilist.add("BCom");
        aQualilist.add("MCom CA");
        aQualilist.add("MBA");

        LanguageLocale aaQualilistadapter = new LanguageLocale(Post_Jobs.this, aQualilist);
        aQualificationspin.setAdapter(aaQualilistadapter);


        ArrayList<String> aNoticelist = new ArrayList<>();
        aNoticelist.add("Notice Period");
        aNoticelist.add("15 Days less");
        aNoticelist.add("1 Month");
        aNoticelist.add("2 Month");
        aNoticelist.add("3 Month");
        aNoticelist.add("Immediate");

        LanguageLocale aNoticeadapt = new LanguageLocale(Post_Jobs.this, aNoticelist);
        anoticeperiodspin.setAdapter(aNoticeadapt);

        aPostjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aComnameval = aCompanyname.getText().toString();
                String aenddateval = aEnddate.getText().toString();
                String aJobdesval = aJobDesign.getSelectedItem().toString();
                String aJobexpval = aJobExp.getSelectedItem().toString();
                String aJobLocaval = aJobloca.getSelectedItem().toString();
                String aQualifval = aQualificationspin.getSelectedItem().toString();
                String anoticeperiodval = anoticeperiodspin.getSelectedItem().toString();
                String aMobileval = aContactno.getText().toString();
                String aJobkeyskilval = "";
                for (int i = 0; i < mSpinner_Key_val.size(); i++) {
                    if (i == 0) {
                        aJobkeyskilval = mSpinner_Key_val.get(i).toString();
                    } else {
                        aJobkeyskilval = aJobkeyskilval + "," + mSpinner_Key_val.get(i);
                    }
                }
                if (aComnameval.equalsIgnoreCase("")) {
                    Toast.makeText(Post_Jobs.this, "Enter Company Name", Toast.LENGTH_SHORT).show();
                } else if (aenddateval.equalsIgnoreCase("")) {
                    Toast.makeText(Post_Jobs.this, "Select the Job Expiry Date", Toast.LENGTH_SHORT).show();
                } else if (aJobdesval.equalsIgnoreCase("Designation")) {
                    Toast.makeText(Post_Jobs.this, "Select Designation", Toast.LENGTH_SHORT).show();
                } else if (aJobexpval.equalsIgnoreCase("Experience")) {
                    Toast.makeText(Post_Jobs.this, "Select Experience", Toast.LENGTH_SHORT).show();
                } else if (aJobLocaval.equalsIgnoreCase("Select Location")) {
                    Toast.makeText(Post_Jobs.this, "Select Location", Toast.LENGTH_SHORT).show();
                } else if (aQualifval.equalsIgnoreCase("Qualification")) {
                    Toast.makeText(Post_Jobs.this, "Select Qualification", Toast.LENGTH_SHORT).show();
                } else if (anoticeperiodval.equalsIgnoreCase("Notice Period")) {
                    Toast.makeText(Post_Jobs.this, "Select Notice Period", Toast.LENGTH_SHORT).show();
                } else if (aMobileval.equalsIgnoreCase("")) {
                    Toast.makeText(Post_Jobs.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (aMobileval.length() < 10) {
                    Toast.makeText(Post_Jobs.this, "Mobile Number Must be Not Less than 10 Digits", Toast.LENGTH_SHORT).show();
                } else {
                    long aStatus = mLocation_Database.Insert_Carrer(aComnameval, aJobdesval, aJobexpval, aJobLocaval, aJobkeyskilval, aQualifval, anoticeperiodval, aenddateval, mBase64_conv, aMobileval);
                    if (aStatus != -1) {
                        Toast.makeText(Post_Jobs.this, "Carrer Posted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Post_Jobs.this, "Failed to Post Carrer", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        mSpinner_Key_val = strings;
    }

}
