// IOnNewBookArrivedListener.aidl
package com.example.artaidlserver;

// Declare any non-default types here with import statements
import com.example.artaidlserver.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}