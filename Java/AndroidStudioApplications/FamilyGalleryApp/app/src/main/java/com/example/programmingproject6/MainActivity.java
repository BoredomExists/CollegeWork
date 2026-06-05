package com.example.programmingproject6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Integer[] family = {R.drawable.dad, R.drawable.mom, R.drawable.sister, R.drawable.niece, R.drawable.belle, R.drawable.gallo, R.drawable.charlie, R.drawable.snake};
    ImageView pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView grid = (GridView)findViewById(R.id.gridView);
        final ImageView pic = (ImageView) findViewById(R.id.imgLarge);
        grid.setAdapter(new ImageAdapter(this));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        Toast.makeText(getBaseContext(), "Father: William", Toast.LENGTH_SHORT).show();
                        pic.setImageResource(family[position]);
                        break;
                    case 1:
                        Toast.makeText(getBaseContext(), "Mother: Elizabeth", Toast.LENGTH_SHORT).show();
                        pic.setImageResource(family[position]);
                        break;
                    case 2:
                        Toast.makeText(getBaseContext(), "Sister: Brandi", Toast.LENGTH_SHORT).show();
                        pic.setImageResource(family[position]);
                        break;
                    case 3:
                        Toast.makeText(getBaseContext(), "Niece: Magnolia", Toast.LENGTH_SHORT).show();
                        pic.setImageResource(family[position]);
                        break;
                    case 4:
                        Toast.makeText(getBaseContext(), "First Dog: Belle", Toast.LENGTH_SHORT).show();
                        pic.setImageResource(family[position]);
                        break;
                    case 5:
                        Toast.makeText(getBaseContext(), "Second Dog: Gallo", Toast.LENGTH_SHORT).show();
                        pic.setImageResource(family[position]);
                        break;
                    case 6:
                        Toast.makeText(getBaseContext(), "Third Dog: Charlie", Toast.LENGTH_SHORT).show();
                        pic.setImageResource(family[position]);
                        break;
                    case 7:
                        Toast.makeText(getBaseContext(), "Pet Snake: Mike", Toast.LENGTH_SHORT).show();
                        pic.setImageResource(family[position]);
                        break;
                }

            }
        });
    }
    public class ImageAdapter extends BaseAdapter
    {
        private Context context;
        public ImageAdapter(Context c) {
            context = c;
        }

        @Override
        public int getCount() {
            return family.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            pic = new ImageView(context);
            pic.setImageResource(family[position]);
            pic.setScaleType(ImageView.ScaleType.FIT_XY);
            pic.setLayoutParams(new GridView.LayoutParams(330,300));
            return pic;
        }
    }
}