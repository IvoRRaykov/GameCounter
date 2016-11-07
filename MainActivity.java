package com.example.preshlen.counter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<String> names;

    RecyclerView recyclerView;
    LinearLayoutManager lim;
    Button addPlayer;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        names = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.table_rec_view);

        lim = new LinearLayoutManager(MainActivity.this);

        addPlayer = (Button) findViewById(R.id.add_player_button);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final EditText input = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setText("ivo0,ivo1,ivo2,ivo3,ivo4,ivo5,ivo6,ivo7,ivo8");


                new AlertDialog.Builder(v.getContext())
                        .setView(input)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String allNames = input.getText().toString();
                                String[] arr = allNames.split(",");
                                MainActivity.this.names = Arrays.asList(arr);
                                adapter = new Adapter(names);
                                recyclerView.setLayoutManager(lim);
                                recyclerView.setAdapter(adapter);

                                dialog.cancel();
                            }
                        }).show();

                addPlayer.setVisibility(View.GONE);
            }


        });

        recyclerView.getRecycledViewPool().setMaxRecycledViews(R.layout.row, 0);
    }


    public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private int finalScoreInteger = 0;
        private ArrayList<String> players = new ArrayList<>();
        final int[] arrFinalScore;

        protected class CustomViewHolder extends RecyclerView.ViewHolder {

            private TextView finalScore;
            private TextView name;
            private EditText currentScore;

            CustomViewHolder(View view) {
                super(view);

                finalScore = (TextView) view.findViewById(R.id.final_score);
                name = (TextView) view.findViewById(R.id.name);
                currentScore = (EditText) view.findViewById(R.id.current_score);
                currentScore.setSelection(currentScore.getText().length());

                currentScore.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        currentScore.setSelection(currentScore.getText().length());
                        return false;
                    }
                });
            }
        }

        public Adapter(List dataSource) {
            this.players.addAll(dataSource);
            arrFinalScore = new int[players.size()];
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.row, parent, false);

            return new CustomViewHolder(row);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final CustomViewHolder vh = (CustomViewHolder) holder;
            final String name = players.get(position);

            vh.name.setText(name);

            vh.currentScore.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    vh.currentScore.setSelection(vh.currentScore.getText().length());

                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        int[] tempArrFinalScore;

                        vh.currentScore.append(" + ");
                        for (char c : vh.currentScore.getText().toString().toCharArray()) {
                            if (c >= '0' && c <= '9') {
                                finalScoreInteger += c - '0';
                                arrFinalScore[position] = finalScoreInteger;
                                String text = "" + finalScoreInteger;
                                vh.finalScore.setText(text);
                            }
                        }

                        /*if (position == players.size() - 1) {
                            tempArrFinalScore = Arrays.copyOf(arrFinalScore, arrFinalScore.length);

                            Arrays.sort(tempArrFinalScore);
                            for (int i = 0; i < arrFinalScore.length; i++) {
                                System.out.println("out " + i);
                                if (lim.findViewByPosition(i) != null) {
                                System.out.println("in " + i);
                                    if (tempArrFinalScore[tempArrFinalScore.length - 1] == arrFinalScore[i]) {
                                        lim.findViewByPosition(i).setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorGold));
                                    } else if (tempArrFinalScore[tempArrFinalScore.length - 2] == arrFinalScore[i]) {
                                        lim.findViewByPosition(i).setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorSilver));
                                    } else if (tempArrFinalScore[tempArrFinalScore.length - 3] == arrFinalScore[i]) {
                                        lim.findViewByPosition(i).setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorBronze));
                                    } else {
                                        lim.findViewByPosition(i).setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                                    }
                                }

                            }

                        }*/
                        finalScoreInteger = 0;
                    }
                    return false;
                }
            });

        }

        @Override
        public int getItemCount() {
            return players.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }
}


