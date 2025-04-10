package com.example.mobilear.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Service
public class FirebaseInitialization {

    @PostConstruct
    public void initialization(){
        FileInputStream serviceAccount;
        try {
            serviceAccount = new FileInputStream("./serviceAccountKey.json");


            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://test-dd862-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .setStorageBucket("test-dd862.firebasestorage.app")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Bean
    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    // 🔹 Tạo Firebase Storage bean để upload file
    @Bean
    public Bucket getStorageBucket() {
        return StorageClient.getInstance().bucket();
    }
}
