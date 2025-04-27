package com.example.mobilear.service;

import com.example.mobilear.entity.Model3D;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ModelInfoService {
    private static final String Collection_Name = "Model3D";

    public List<Model3D> getAllModels() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference dbCollection = db.collection(Collection_Name);

        Iterable<DocumentReference> documentReferences = dbCollection.listDocuments();
        Iterator<DocumentReference> iterator = documentReferences.iterator();
        List<Model3D> models = new ArrayList<>();
        while(iterator.hasNext()) {
            DocumentReference documentReference = iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot document = future.get();
            models.add(document.toObject(Model3D.class));
        }
        return models;
    }

    public List<Model3D> getSpecialOffers() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference dbCollection = db.collection(Collection_Name);

        // Query lấy giới hạn số lượng document
        ApiFuture<QuerySnapshot> future = dbCollection.limit(5).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Model3D> models = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            models.add(document.toObject(Model3D.class));
        }

        return models;
    }


    public List<Model3D> getModelByCategory(String category)
            throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference dbCollection = db.collection("Model3D");

        ApiFuture<QuerySnapshot> future = dbCollection.whereEqualTo("category", category).get();
        List<QueryDocumentSnapshot> docs = future.get().getDocuments();

        List<Model3D> result = new ArrayList<>();
        for (QueryDocumentSnapshot doc : docs) {
            Model3D model = doc.toObject(Model3D.class);
            result.add(model);
        }

        return result;
    }


    public List<Model3D> getPersonalModels(String username) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference dbCollection = db.collection(Collection_Name);

        ApiFuture<QuerySnapshot> future = dbCollection.whereEqualTo("createdBy", username).get();
        List<QueryDocumentSnapshot> docs = future.get().getDocuments();

        List<Model3D> result = new ArrayList<>();
        for (QueryDocumentSnapshot doc : docs) {
            Model3D model = doc.toObject(Model3D.class);
            result.add(model);
        }

        return result;
    }

    public ResponseEntity<?> addModelInfo(Model3D modelInfo) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference dbCollection = db.collection(Collection_Name);
        ApiFuture<DocumentReference> future = dbCollection.add(modelInfo);
        DocumentReference docRef = future.get();

        return ResponseEntity.ok("Model info added successfully at " + modelInfo.getCreatedAt() + " with ID: " + docRef.getId());
    }
}
