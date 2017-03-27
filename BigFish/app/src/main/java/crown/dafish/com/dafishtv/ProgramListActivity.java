package crown.dafish.com.dafishtv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import crown.dafish.com.model.ProgramModel;

/**
 * Created by jianggy on 2017/2/19.
 */

public class ProgramListActivity extends Activity {

    private ArrayList<ProgramModel> mList = new ArrayList<>();
    private ListView mListView;
    private ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.program_list);

        Intent intent=getIntent();
        mList =(ArrayList<ProgramModel>)intent.getSerializableExtra("program_list");

        mListView = (ListView) findViewById(R.id.program_list);
        ProgramListAdapter adapter = new ProgramListAdapter(getApplicationContext(), mList);
        mListView.setAdapter(adapter);

        mImageButton = (ImageButton) findViewById(R.id.program_close_button);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
