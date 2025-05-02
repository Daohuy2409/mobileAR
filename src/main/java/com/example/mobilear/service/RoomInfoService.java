package com.example.mobilear.service;

import com.example.mobilear.entity.RoomInfo;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class RoomInfoService {
    private static final String Collection_Name = "Rooms";

    public ResponseEntity<?> getRoomInfo(String roomId) {
        // Implement the logic to get room information
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference dbCollection = db.collection(Collection_Name);
        DocumentReference roomRef = dbCollection.document(roomId);

        try {
            return ResponseEntity.ok(roomRef.get().get().toObject(RoomInfo.class));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching room info: " + e.getMessage());
        }
    }

    public ResponseEntity<?> renameRoom(String roomId, String newName) {
        // Implement the logic to rename the room
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference dbCollection = db.collection(Collection_Name);
        DocumentReference roomRef = dbCollection.document(roomId);

        try {
            roomRef.update("roomName", newName).get();
            return ResponseEntity.ok("Room renamed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error renaming room: " + e.getMessage());
        }
    }

    public ResponseEntity<?> addModel(String roomId, String modelId) {
        // Implement the logic to add a model to the room
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference dbCollection = db.collection(Collection_Name);
        DocumentReference roomRef = dbCollection.document(roomId);

        try {
            roomRef.update("modelInRoom", FieldValue.arrayUnion(modelId)).get();
            return ResponseEntity.ok("Model added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding model: " + e.getMessage());
        }
    }

    public ResponseEntity<?> deleteModel(String roomId, String modelId) {
        // Implement the logic to delete a model from the room
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference dbCollection = db.collection(Collection_Name);
        DocumentReference roomRef = dbCollection.document(roomId);

        try {
            roomRef.update("modelInRoom", FieldValue.arrayRemove(modelId)).get();
            return ResponseEntity.ok("Model deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting model: " + e.getMessage());
        }
    }
}
