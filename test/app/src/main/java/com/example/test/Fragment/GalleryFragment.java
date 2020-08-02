package com.example.test.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.test.Adapter.FolderAdapter;
import com.example.test.Model.Folder;
import com.example.test.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    ViewGroup viewGroup;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.gallery_fragment,container,false);
        ArrayList<Folder> folders=new ArrayList<>();
        Folder folder= Folder.builder()
                .name("서울여행")
                .place("서울")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1L))
                .withDescription("friends")
                .build();
        folders.add(folder);
        folders.add(folder);
        ListView listView = (ListView)viewGroup.findViewById(R.id.listView);
        final FolderAdapter findByCompanyAdapter=new FolderAdapter(getActivity(), folders);
        listView.setAdapter(findByCompanyAdapter);
        return viewGroup;
    }
}
