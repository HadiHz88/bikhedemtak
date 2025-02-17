package lb.edu.ul.bikhedemtak.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import lb.edu.ul.bikhedemtak.R;
import lb.edu.ul.bikhedemtak.activities.MainActivity;

/**
 * A BottomSheetDialogFragment that allows users to select a date and time.
 */
public class DateTimeBottomSheet extends BottomSheetDialogFragment {
    private TextView selectedDate;
    private TextView selectedTime;
    private MaterialButton btnSelectDateTime;
    private DateTimePickerListener listener;

    /**
     * Interface for listening to date and time selection events.
     */
    public interface DateTimePickerListener {
        /**
         * Called when a date and time are selected.
         *
         * @param date the selected date
         * @param time the selected time
         */
        void onDateTimeSelected(String date, String time);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.date_time_bottom_sheet, container, false);

        // Initialize UI components
        selectedDate = view.findViewById(R.id.selectedDate);
        selectedTime = view.findViewById(R.id.selectedTime);
        btnSelectDateTime = view.findViewById(R.id.btnSelectDateTime);

        // Set click listeners for date and time pickers
        view.findViewById(R.id.datePickerContainer).setOnClickListener(v -> showDatePicker());
        view.findViewById(R.id.timePickerContainer).setOnClickListener(v -> showTimePicker());

        // Handle button click
        btnSelectDateTime.setOnClickListener(v -> {
            String date = selectedDate.getText().toString();
            String time = selectedTime.getText().toString();

            // Retrieve the tasker_id from the arguments
            int taskerId = getArguments() != null ? getArguments().getInt("tasker_id", -1) : -1;

//            Log.d("DateTimeBottomSheet", "selected date: " + date + " " + time);

            // Create the intent to pass data back to MainActivity
            Intent i = new Intent(getContext(), MainActivity.class);
            i.putExtra("tasker_id", taskerId); // Pass tasker_id as an integer
            i.putExtra("booking_time", date + " " + time); // Pass the combined date and time
            startActivity(i); // Start MainActivity with the passed data
        });

        return view;
    }


    /**
     * Shows the date picker dialog.
     */
    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar selected = Calendar.getInstance();
            selected.setTimeInMillis(selection);

            if (DateUtils.isToday(selected.getTimeInMillis())) {
                selectedDate.setText("Today");
            } else if (DateUtils.isToday(selected.getTimeInMillis() - DateUtils.DAY_IN_MILLIS)) {
                selectedDate.setText("Tomorrow");
            } else {
                selectedDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(selected.getTime()));
            }
        });

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }

    /**
     * Shows the time picker dialog.
     */
    private void showTimePicker() {
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
                .setMinute(Calendar.getInstance().get(Calendar.MINUTE))
                .setTitleText("Select Time")
                .build();

        timePicker.addOnPositiveButtonClickListener(v -> {
            Calendar selected = Calendar.getInstance();
            selected.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            selected.set(Calendar.MINUTE, timePicker.getMinute());
            selectedTime.setText(new SimpleDateFormat(" HH:mm:ss", Locale.getDefault())
                    .format(selected.getTime()));
        });

        timePicker.show(getParentFragmentManager(), "TIME_PICKER");
    }

    /**
     * Sets the listener for date and time selection events.
     *
     * @param listener the listener to set
     */
    public void setListener(DateTimePickerListener listener) {
        this.listener = listener;
    }
}