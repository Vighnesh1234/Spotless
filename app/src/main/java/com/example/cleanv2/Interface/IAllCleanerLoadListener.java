package com.example.cleanv2.Interface;

import java.util.List;

public interface IAllCleanerLoadListener {
    void onAllCleanerLoadSuccess(List<String> cityNameList);
    void onAllCleanerLoadFailed(String message);



}
