package com.example.MyImage.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.MyImage.Adapter.BoardAdapter;
import com.example.MyImage.Model.Board;
import com.example.MyImage.Model.Network.PostURLConnector;
import com.example.MyImage.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CommunityFragment extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    Button writeButton;
    ListView listView;
    EditText searchText;
    Button searchButton;
    Button[] numButton;
    Button prevButton;
    Button doublePrevButton;
    Button nextButton;
    Button doubleNextButton;
    String search;
    int lastpage;
    int currentPage;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.community_fragment,container,false);
        setViewGroup();
        return viewGroup;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setViewGroup()
    {
        writeButton=(Button)viewGroup.findViewById(R.id.writeButton);
        writeButton.setOnClickListener(this);
        listView=(ListView)viewGroup.findViewById(R.id.listView);
        searchText=(EditText)viewGroup.findViewById(R.id.searchText);
        searchText.setText("");
        searchButton=(Button)viewGroup.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        numButton=new Button[5];
        numButton[0]=(Button)viewGroup.findViewById(R.id.button0);
        numButton[0].setOnClickListener(this);
        numButton[1]=(Button)viewGroup.findViewById(R.id.button1);
        numButton[1].setOnClickListener(this);
        numButton[2]=(Button)viewGroup.findViewById(R.id.button2);
        numButton[2].setOnClickListener(this);
        numButton[3]=(Button)viewGroup.findViewById(R.id.button3);
        numButton[3].setOnClickListener(this);
        numButton[4]=(Button)viewGroup.findViewById(R.id.button4);
        numButton[4].setOnClickListener(this);
        prevButton=(Button)viewGroup.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(this);
        nextButton=(Button)viewGroup.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        doublePrevButton=(Button)viewGroup.findViewById(R.id.doublePrevButton);
        doublePrevButton.setOnClickListener(this);
        doubleNextButton=(Button)viewGroup.findViewById(R.id.doubleNextButton);
        doubleNextButton.setOnClickListener(this);
        search="";
        setLastPage();//마지막 페이지 계산하여 셋팅
        listSetting(0);//현재 페이지에 따른 listview 및 여러 버튼들 셋팅.

    }
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void listSetting(int page)
    {
        //TODO:LISTVIEW SETTING
        currentPage=page;
        setListView();
        for(int i=currentPage/5*5;i<currentPage/5*5+5; i++)//버튼은 5개가 생김.
        {
            if(i==currentPage)//현재 클릭된 버튼 색깔 바꾸어줌.
            {
                numButton[i%5].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.numbuttonclicked));
                numButton[i%5].setTextColor(Color.parseColor("#FFFFFF"));
            }
            else
            {
                numButton[i%5].setBackground(ContextCompat.getDrawable(getContext(), R.drawable.numbuttonunclicked));
                numButton[i%5].setTextColor(Color.parseColor("#000000"));
            }
            numButton[i%5].setText(Integer.toString(i+1));
            if(i>lastpage)//마지막 페이지보다 크다면 unvisible해야함.
            {
                numButton[i%5].setVisibility(View.GONE);
            }
            else
            {
                numButton[i%5].setVisibility(View.VISIBLE);
            }
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setListView()
    {
        String postData = "search=" + search + "&" + "startfrom=" + 8 * currentPage;
        ArrayList<Board> boards=new ArrayList<>();
        PostURLConnector task=new PostURLConnector("/getBoard.php",postData);
        task.start();
        try{
            task.join();
            System.out.println("waiting... for result");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result=task.getResult();
        try{
            JSONObject root = new JSONObject(result);
            JSONArray ja=root.getJSONArray("result");
            for(int i=0; i< ja.length(); i++)
            {
                Board board=new Board();
                JSONObject jo=ja.getJSONObject(i);
                board.setId(jo.getLong("id"));
                board.setTitle(jo.getString("title"));
                board.setLocation(jo.getString("location"));
                board.setUpdatedBy(jo.getString("updated_by"));
                board.setUpdatedAt(LocalDateTime.parse(jo.getString("updated_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                boards.add(board);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        BoardAdapter boardAdapter=new BoardAdapter(getContext(), boards);
        listView.setAdapter(boardAdapter);
    }
    public void setLastPage()
    {
        String postData = "search=" + search;
        ArrayList<Board> boards=new ArrayList<>();
        PostURLConnector task=new PostURLConnector("/getLastPage.php",postData);
        task.start();
        try{
            task.join();
            System.out.println("waiting... for result");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result=task.getResult();
        result=result.replace("\n","");
        if(Integer.parseInt(result)>0)
            lastpage=(Integer.parseInt(result)-1)/8;
        else lastpage=0;
    }
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.writeButton:
                break;
            case R.id.searchButton://찾기 버튼을 누르면 마지막 페이지를 다시 계산하여야함.
                search=searchText.getText().toString();
                setLastPage();
                listSetting(0);
                break;
            case R.id.doublePrevButton:
                listSetting(0);
                break;
            case R.id.prevButton:
                if (currentPage != 0)
                    listSetting(currentPage - 1);
                else
                    Toast.makeText(getContext(),"첫 번째 페이지입니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button0:
                listSetting(Integer.parseInt(numButton[0].getText().toString())-1);
                break;
            case R.id.button1:
                listSetting(Integer.parseInt(numButton[1].getText().toString())-1);
                break;
            case R.id.button2:
                listSetting(Integer.parseInt(numButton[2].getText().toString())-1);
                break;
            case R.id.button3:
                listSetting(Integer.parseInt(numButton[3].getText().toString())-1);
                break;
            case R.id.button4:
                listSetting(Integer.parseInt(numButton[4].getText().toString())-1);
                break;
            case R.id.nextButton:
                if (currentPage != lastpage)
                    listSetting(currentPage + 1);
                else
                    Toast.makeText(getContext(),"마지막 페이지입니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.doubleNextButton:
                listSetting(lastpage);
                break;
        }
    }
}
