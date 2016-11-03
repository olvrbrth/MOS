package at.fhooe.mc.mos.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import at.fhooe.mc.mos.R;
import at.fhooe.mc.mos.model.Step;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;


public class ActivityFragment extends Fragment {

    private static final String TAG = ActivityFragment.class.getSimpleName();

    private View mView;
    private Button mBtnSend;
    private DatabaseReference mFirebaseDatabaseReference;

    private CircleProgressView mCircleView;


    public ActivityFragment() {
        // Required empty public constructor
    }

    public static ActivityFragment newInstance() {
        ActivityFragment fragment = new ActivityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_activity, container, false);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("steps");

        mBtnSend = (Button) mView.findViewById(R.id.btn_activity_send);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnSend.setEnabled(false);
                Step step = new Step();
                int count = (int) (Math.random() * 1000);
                step.setCount(count);

                // push new value
                mFirebaseDatabaseReference.push().setValue(step, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        mBtnSend.setEnabled(true);
                        if (databaseError == null) {
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        // set up circular view
        mCircleView = (CircleProgressView) mView.findViewById(R.id.circleView);
        mCircleView.setMaxValue(100);

        mCircleView.setUnit("/ 100");
        mCircleView.setUnitVisible(true);

        mCircleView.setTextMode(TextMode.VALUE); // Set text mode to text to show text

        mCircleView.setOnProgressChangedListener(new CircleProgressView.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(float value) {
                Log.d(TAG, "Progress Changed: " + value);
            }
        });

        mCircleView.setValueAnimated(42);



        return mView;
    }

}
