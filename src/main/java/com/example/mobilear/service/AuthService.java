package com.example.mobilear.service;

import com.example.mobilear.Response.EHttpStatus;
import com.example.mobilear.entity.Account;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class AuthService {
    private static final String Collection_Name = "Account";

    public boolean saveAccount(Account account) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionApifuture = db.collection(Collection_Name).document(account.getUsername()).set(account);

        System.out.println(collectionApifuture.get().getUpdateTime());
        collectionApifuture.get();
        return true;
    }

    public boolean checkAccount(String username) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();

        DocumentReference documentReference =  db.collection(Collection_Name).document(username);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return true;
        }
        return false;
    }
    public EHttpStatus checkLogin(Account account) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference =  db.collection(Collection_Name).document(account.getUsername());
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            Account account1 = document.toObject(Account.class);
            assert account1 != null;
            if(account1.getPassword().equals(account.getPassword())){
                return EHttpStatus.OK;
            }
            return EHttpStatus.INCORRECT_PASSWORD;
        }
        return EHttpStatus.USERNAME_NOT_FOUND;
    }

    public List<Account> getAllAccounts() throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();

        Iterable<DocumentReference> documentReferences = db.collection(Collection_Name).listDocuments();
        Iterator<DocumentReference> iterator = documentReferences.iterator();

        List<Account> accountList = new ArrayList<>();
        Account account = null;
        while(iterator.hasNext()) {
            DocumentReference documentReference = iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot document = future.get();
            account = document.toObject(Account.class);
            accountList.add(account);
        }
        return accountList;
    }

    public String updateAccount(Account account) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionApifuture = db.collection(Collection_Name).document(account.getUsername()).set(account);

        return collectionApifuture.get().getUpdateTime().toString();
    }

    public String deleteAccount(String username) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> writeResult = db.collection(Collection_Name).document(username).delete();
        System.out.println("Update time : " + writeResult.get().getUpdateTime());
        return "Document with Username " + username + " has been deleted";
    }
}
