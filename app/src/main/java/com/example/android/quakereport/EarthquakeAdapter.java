package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.drawable.GradientDrawable;

public class EarthquakeAdapter extends ArrayAdapter<earthquakeItem>  {

    public EarthquakeAdapter(Context context, List<earthquakeItem> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_activity, parent, false);
        }

        final earthquakeItem currentItem = getItem(position);

        //Finding magnitude textView in earthquake_activity.xml
        TextView magTextView = (TextView)listItemView.findViewById(R.id.mag_text_view);
        double magnitude = currentItem.getMagnitude();
        DecimalFormat formatter = new DecimalFormat("0.0");
        String magToDisplay = formatter.format(magnitude);
        //Setting its text to magnitude double value
        magTextView.setText(magToDisplay);

        //Getting the location String value
        String wholeLocation = currentItem.getLocations();

        if(wholeLocation.contains("of")){
            //Spliting the wholeLocation String value into two Strings (directions from that place(55km N), that place(NewYork, NewYork))
            String[] splitLocation = wholeLocation.split(" of ");
            //Getting the first element (exact place(NewYork, NewYork)) from array of two Strings (directions from that place(55km N), that place(NewYork, NewYork))
            String exactLocation = splitLocation[1];
            //Finding exactLocation textView in earthquake_activity.xml
            TextView exactLocationTextView = (TextView) listItemView.findViewById(R.id.exact_location_text_view);
            //Setting its text to exactLocation String value
            exactLocationTextView.setText(exactLocation);

            //Getting the zeroth element (directions from that place(55km N)) from array of two Strings (directions from that place(55km N), that place(NewYork, NewYork))
            String directionsLocation = splitLocation[0];
            //Adding " of" to directionsLocation String value because originally it was there but we have lost it because of splitting the wholeLocation String value
            directionsLocation = directionsLocation + " of";
            //Finding directions textView in earthquake_activity.xml
            TextView directionsTextView = (TextView)listItemView.findViewById(R.id.direction_text_view);
            //Setting its text to directionsLocation String value
            directionsTextView.setText(directionsLocation);
        }
        else{
            //Getting the first element (exact place(NewYork, NewYork)) from array of two Strings (directions from that place(55km N), that place(NewYork, NewYork))
            String exactLocation = wholeLocation;
            //Finding exactLocation textView in earthquake_activity.xml
            TextView exactLocationTextView = (TextView) listItemView.findViewById(R.id.exact_location_text_view);
            //Setting its text to exactLocation String value
            exactLocationTextView.setText(exactLocation);

            //Finding directions textView in earthquake_activity.xml
            TextView directionsTextView = (TextView)listItemView.findViewById(R.id.direction_text_view);
            //Setting its text to "Near the"
            directionsTextView.setText("Near the ");
        }

        //Converting from UNIX time to readable time
        Date dateNotFormat =  new Date(currentItem.getDate());
        Log.d("Date1: ", dateNotFormat + "");
        //Creating a formatter that sets how will be the date (String value) organized (DD for day as int; MMM for month as String; yyyy for year as int)
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MMM, yyyy");
        //Setting the formatter on that Date date
        String dateToDisplay = dateFormatter.format(dateNotFormat);
        Log.d("Date2: ", dateToDisplay);

        //Finding date textView in earthquake_activity.xml
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        //Setting its text to dateToDisplay String value = time in miliseconds (UNIX time)
        dateTextView.setText(dateToDisplay);

        //Converting from UNIX time to readable time
        Date time = new Date(currentItem.getDate());
        //Creating a formatter that sets how will be the date (String value) organized (h for hour as int; mm for minutes as int; ss for seconds as int; a for pm or am as String)
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm:ss a");
        //Setting the formatter on that Date time = time in miliseconds (UNIX time)
        String timeToDisplay = timeFormatter.format(time);

        //Finding time textView in earthquake_activity.xml
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_text_view);
        //Setting its text to timeToDisplay String value
        timeTextView.setText(timeToDisplay);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentItem.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        LinearLayout mainLinearLayout = (LinearLayout) listItemView.findViewById(R.id.main_linear_layout);

        mainLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentItem.getUrl()));
                getContext().startActivity(browserIntent);
            }
        });

        return listItemView;
    }

    /**
     * Method that returns the right color for background of magTextView based on earthquake´s magnitude (indicates severity).
     * @param magForColor
     * @return
     */
    public int getMagnitudeColor(double magForColor){
        int colorOfMagCircle;
        //Because magnitude is originally a decimal number. We need to round it.
        double mRoundedMagnitude = Math.round(magForColor);
        //And then turn it into an int.
        int roundedMagnitude = (int) mRoundedMagnitude;
        //This switch statement decides what color to return based on magnitude.
        switch (roundedMagnitude){
            //Magnitude with value 0 will have the same color as magnitude with value 1. That is why case 0 is empty, because after it reads the line 'case 0:' it falls into case 1. So the colors for both values will be  the same.
            case 0:
            case 1:
                colorOfMagCircle = R.color.magnitude1;
                break;
            case 2:
                colorOfMagCircle = R.color.magnitude2;
                break;
            case 3:
                colorOfMagCircle = R.color.magnitude3;
                break;
            case 4:
                colorOfMagCircle = R.color.magnitude4;
                break;
            case 5:
                colorOfMagCircle = R.color.magnitude5;
                break;
            case 6:
                colorOfMagCircle = R.color.magnitude6;
                break;
            case 7:
                colorOfMagCircle = R.color.magnitude7;
                break;
            case 8:
                colorOfMagCircle = R.color.magnitude8;
                break;
            case 9:
                colorOfMagCircle = R.color.magnitude9;
                break;
            default:
                colorOfMagCircle = R.color.magnitude10plus;
        }
        //colorOfMagCircle is juts a "link" to that color. We need the actual color. That is why we have returned this 'ContextCompat.getColor(getContext(), colorOfMagCircle);' instead of just this 'return colorOfMagCircle;'
        return ContextCompat.getColor(getContext(), colorOfMagCircle);
    }
}