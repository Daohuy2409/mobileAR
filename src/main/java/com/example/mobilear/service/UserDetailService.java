package com.example.mobilear.service;

import com.example.mobilear.entity.UserDetails;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserDetailService {

    private static final String Collection_Name = "User";

    public boolean saveUserDetails(String username, UserDetails userDetails) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionApifuture = db.collection(Collection_Name).document(username).set(userDetails);

        System.out.println(collectionApifuture.get().getUpdateTime());
        collectionApifuture.get();
        return true;
    }
}
