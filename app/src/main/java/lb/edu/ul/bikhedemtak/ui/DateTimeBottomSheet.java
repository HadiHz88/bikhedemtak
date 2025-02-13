package lb.edu.ul.bikhedemtak.ui;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class DateTimeBottomSheet extends BottomSheetDialogFragment {
    private TextView selectedDate;
    private TextView selectedTime;
    private MaterialButton btnSelectDateTime;
    private DateTimePickerListener listener;

    public interface DateTimePickerListener {
        void onDateTimeSelected(String date, String time);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.date_time_bottom_sheet, container, false);

        selectedDate = view.findViewById(R.id.selectedDate);
        selectedTime = view.findViewById(R.id.selectedTime);
        btnSelectDateTime = view.findViewById(R.id.btnSelectDateTime);

        view.findViewById(R.id.datePickerContainer).setOnClickListener(v -> showDatePicker());
        view.findViewById(R.id.timePickerContainer).setOnClickListener(v -> showTimePicker());

        btnSelectDateTime.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDateTimeSelected(
                        selectedDate.getText().toString(),
                        selectedTime.getText().toString()
                );
            }
            dismiss();
        });

        return view;
    }

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
                selectedDate.setText(new SimpleDateFormat("MMM dd", Locale.getDefault())
                        .format(selected.getTime()));
            }
        });

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }

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
            selectedTime.setText(new SimpleDateFormat("h:mm a", Locale.getDefault())
                    .format(selected.getTime()));
        });

        timePicker.show(getParentFragmentManager(), "TIME_PICKER");
    }

    public void setListener(DateTimePickerListener listener) {
        this.listener = listener;
    }
}