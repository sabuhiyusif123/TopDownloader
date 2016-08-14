package com.example.sabuhi.topdownloader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by SABUHI on 7/29/2016.
 */
public class ParseApplications {
    private String xmlData;
    private ArrayList<Applications> applications;

    public ParseApplications(String xmlData) {
        this.xmlData = xmlData;
        applications = new ArrayList<>();
    }

    public ArrayList<Applications> getApplications() {
        return applications;
    }

    public boolean process() {
        boolean status = true;
        Applications currentRecord=null;
        boolean inEntry = false;
        String textValue = " ";
        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();  //Xml file ni parse etmek ucun yaradilir
            factory.setNamespaceAware(true);

            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));            //data nin oxunmasi ucun uygun formata getirilir //moterizenin icinde
            int eventType= xpp.getEventType();                  //hal hazirdaki eventType i gosterir// Returns the type of the current event (START_TAG, END_TAG, TEXT, etc.)
            while(eventType != XmlPullParser.END_DOCUMENT ){  // documentin axirina catana qeder loop edecek
                String tagName= xpp.getName();                  //oldugu tagin adini goturur
                switch (eventType){
                    case(XmlPullParser.START_TAG):              //Baslangic tag da olanda
//                        Log.d("ParseApplications","Starting tag for "+tagName);     //
                        if(tagName.equalsIgnoreCase("entry")){          //tagName entry e beraber olanda asagidaki emelleri yerine yetirecey
                            inEntry=true;
                            currentRecord = new Applications();
                        }
                        break;
                    case(XmlPullParser.TEXT):
                        textValue=xpp.getText();
                        break;

                    case(XmlPullParser.END_TAG):                // Son tag de olanda

                        if(inEntry){
                            if(tagName.equalsIgnoreCase("entry")){
                                applications.add(currentRecord);
                                inEntry=false;

                            }else if(tagName.equalsIgnoreCase("name")){
                                currentRecord.setAppName(textValue);
                            }else if(tagName.equalsIgnoreCase("artist")){
                                currentRecord.setAppArtist(textValue);
                            }else  if(tagName.equalsIgnoreCase("releasedate")){
                                currentRecord.setAppReleaseDate(textValue);
                            }
                        }
//                        Log.d("ParseApplications","Ending tag for "+ tagName);
                        break;
                    default:
                        //nothing to do
                }
                eventType = xpp.next();             // novbeti tag e kecemesini temin edir
            }

        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }

        for(Applications app : applications){
            Log.d("ParseApplications","************");
            Log.d("ParseApplications","App name "+app.getAppName());
            Log.d("ParseApplications","App artist "+app.getAppArtist());
            Log.d("ParseApplications","App release date "+app.getAppReleaseDate());
        }
        return true;
    }
}
